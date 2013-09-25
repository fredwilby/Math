package com.fredwilby.math.mandelbrot.ui;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

/**
 * Control Panel for fractal. Responds to RDEvents from display by updating param fields. 
 * Sends RDevents when redraw button is pressed.
 * @author Fred
 *
 */
public class CPanel extends JPanel implements RDEventListener
{
	private ArrayList<RDEventListener> plots;
	private JTextField jtli, jtlj,jbri, jbrj, jitf, jwf, jhf;
	private JLabel jrl, jtl, jbr, i1,i2, jit, jw, jh, jrd;
	private JButton redrawb,renderb;
	private JProgressBar renderbar;
	private MigLayout lay;
	private final int boxw = 15;
	
	private Point2D.Double tl,br;
	private long it = 1000;
	private int fw, fh;
	
	public CPanel(int w, int h)
	{
		fw = w;
		fh = h;

		setupUI();
	}
	
	private void setupUI()
	{
		lay = new MigLayout("", "[10%][85%][5%]", "");
		setLayout(lay);
		
		/* Top Left coord input */
		jtl = new JLabel("TL: ");
		jtli = new JTextField("0.0");
		jtlj = new JTextField("0.0");
		i1 = new JLabel("i");

		add(jtl);
		add(jtli, new CC().growX().span(2).wrap());
		add(jtlj, new CC().cell(1,1).growX());
		add(i1, "wrap");

		/* Bottom Right coord input */
		jbr = new JLabel("BR: ");
		jbri = new JTextField("0.0");
		jbrj = new JTextField("0.0");
		i2 = new JLabel("i");

		add(jbr);
		add(jbri, new CC().growX().span(2).wrap());
		add(jbrj, new CC().cell(1,3).growX());
		add(i2, "wrap");

		/* Iteration control */
		jit = new JLabel("IT: ");
		jitf = new JTextField(Long.toString(it));
		
		add(jit);
		add(jitf, new CC().growX().span(2).wrap());
		
		/* Redraw button*/
		redrawb = new JButton("Redraw");
		redrawb.addMouseListener(redrawml);
		add(redrawb, new CC().growX().span(3).wrap());
		
		/* Redraw status label */
		jrd = new JLabel("Drawing Complete.");
		add(jrd, new CC().growX().spanX(3).wrap());
		
		/* Render size inputs */
		jw = new JLabel("W: ");
		jwf = new JTextField(Integer.toString(fw));
		
		add(jw);
		add(jwf, new CC().growX().span(2).wrap());
		
		jh = new JLabel("H: ");
		jhf = new JTextField("");

		add(jh);
		add(jhf, new CC().growX().span(2).wrap());
		
		/* Render Button */
		renderb = new JButton("Render");
		renderb.addMouseListener(renderml);
		
		add(renderb, new CC().growX().span(3).wrap());
		
		tl = new Point2D.Double(0,0);
		br = new Point2D.Double(0,0);
		
		/* Render status label & progress bar */
		renderbar = new JProgressBar(0, 100);
		jrl = new JLabel("No Render Task.");
		
		add(renderbar, new CC().grow().spanX(3).wrap());
		add(jrl, "span 3");
	}
	
	private void render()
	{
		getEvent(); // update tl, br, it based on field values 
		
		new Thread(new Runnable() 
		{
			
			public void run() 
			{
				int[] renders = renderSize();
				jrl.setText("Render in progress...");
				
				try
				{
					ImageIO.write(MRender.render(new RDEvent(new Dimension(renders[0], renders[1]),tl,br,it)), "png", new File("render"+System.currentTimeMillis()+".png"));
			
				} catch(IOException e) { jrl.setText("Render failed: see stack trace"); e.printStackTrace(); return; }
				
				
				jrl.setText("Render ("+renders[0]+"x"+renders[1]+") complete");
			}
		}).start();
		
		new Thread(new Runnable() {
		public void run() {
		while(MRender.progress < 1)
		{
			renderbar.setValue((int)(MRender.progress*100));
			try { Thread.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
		}
			renderbar.setValue(100);
		}
		}).start();
		
	}
	
	private int[] renderSize()
	{
		try {
			return new int[] { Integer.parseInt(jwf.getText()), Integer.parseInt(jhf.getText()) };
		} catch(Exception e)
		{
			return new int[] { Integer.parseInt(jwf.getText()),  (int)(Integer.parseInt(jwf.getText())*(2.0/3.5)) };
		}
	}
	
	private RDEvent getEvent()
	{	
		tl = new Point2D.Double(Double.parseDouble(jtli.getText()),Double.parseDouble(jtlj.getText()));
		br = new Point2D.Double(Double.parseDouble(jbri.getText()),Double.parseDouble(jbrj.getText()));
		it = Integer.parseInt(jitf.getText());
		
		return new RDEvent(new Dimension(fw,fh),tl,br, it);
	}
	
	private void sendRDEvent(RDEvent a)
	{
		jrd.setText("Redrawing in progress...");
	
		for(RDEventListener r : plots)
			r.invokeRDEvent(a);
	}
	
	public void addRDEventListener(RDEventListener e)
	{
		if(plots == null)
			plots = new ArrayList<RDEventListener>();
		
		plots.add(e);
	}

	@Override
	public void invokeRDEvent(RDEvent e) 
	{
		tl = e.tl;
		br = e.br; 
		it = e.iterations;
		
		jrd.setText("Drawing complete.");
		
		updateLabels();
	}

	private void updateLabels()
	{
		jitf.setText(Long.toString(it));
		jtli.setText(Double.toString(tl.x));
		jtlj.setText(Double.toString(tl.y));
		jbri.setText(Double.toString(br.x));
		jbrj.setText(Double.toString(br.y));
	}
	
	private MouseListener renderml = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			render();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}	
	}, redrawml = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			new Thread(new Runnable() 
			{
				
			public void run()
			{
			sendRDEvent(getEvent());
			}
			}).start();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}	
	};
	
}
