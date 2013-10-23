package com.fredwilby.math.mandelbrot.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.fredwilby.math.mandelbrot.calc.MCalc;
import com.fredwilby.math.mandelbrot.calc.MCalcarapi;
import com.fredwilby.math.mandelbrot.calc.ViewConverter;
import com.fredwilby.math.mandelbrot.color.AbstractColorMap;
import com.fredwilby.math.mandelbrot.color.InterpolatedColorMap;

public class MPanel extends JPanel implements RDEventListener, MouseListener
{
	private MCalc calculator;
	private BufferedImage view; 
	private ViewConverter vc;
	private ArrayList<RDEventListener> plots;
	
	private static final Point2D.Double defTL = new Point2D.Double(-2.5, 1), 
										defBR = new Point2D.Double(1, -1);
	
	private static final long defIt = 900;
	private static final double zoomscale = .3; 
	
	private int w, h;
	private long it; 
	
	public MPanel(int w, int h)
	{
		super();

		this.w = w;
		this.h = h;
		
		
		plots = new ArrayList<RDEventListener>();
		
		view = new BufferedImage(this.w, this.h, BufferedImage.TYPE_INT_RGB);	

		//calculator = new MCalcThreaded();
		calculator = new MCalcarapi();
	}
	
	@Override
	public void addNotify()
	{
		super.addNotify();
		it = defIt;
		invokeRDEvent(new RDEvent(new Dimension(w,h),defTL,defBR,it));
	}
	
	public Rectangle2D.Double getRect(Point2D.Double tl, Point2D.Double br)
	{
		return new Rectangle2D.Double(tl.x, tl.y, br.x - tl.x, tl.y - br.y);
	}

	/**
	 * Redraws the fractal. Assumes e.pixel_size == new Dimension(w,h)
	 */
	@Override
	public void invokeRDEvent(RDEvent e) 
	{
		it = e.iterations;
		vc = new ViewConverter(e.pixel_size, e.tl, e.br);

		double[][] fdata = calculator.normalizedIterationValues(e);

		Color[] rawmap = new Color[] {
		        new Color(66, 30, 15),
		        new Color(25, 7,  26),
		        new Color(9, 1,  47),
                new Color(4, 4,  73), 
                new Color(0, 7, 100),
                new Color(12, 44, 138), 
                new Color(24, 82, 177),
                new Color(57, 125, 209),
                new Color(134, 181, 229),
                new Color(211, 236, 248),
                new Color(241, 233, 191),
                new Color(248, 201,  95),
                new Color(255, 170,   0),
                new Color(204, 128,   0),
                new Color(153,  87,   0),
                new Color(106,  52,   3)
		        
		};
		
		
		AbstractColorMap map = new InterpolatedColorMap(rawmap);
		
		for(int x = 0; x < fdata.length; x++)
			for(int y = 0; y < fdata[0].length; y++)
				view.setRGB(x, y, map.getColor(fdata[x][y], it).getRGB());

		
		// TODO adjust image size if needed
		
		//System.out.println(e.tl +"\t" + e.br);
		
		for(RDEventListener rdel : plots)
		{
			rdel.invokeRDEvent(e);
		}
		
		repaint();
	}
	
	private String p2dp(Point2D.Double p)
	{
		return Double.toString(p.x)+", "+Double.toString(p.y);
	}
	
	@Override
	public void paint(Graphics G)
	{
	    G.drawImage(view, 0, 0, w, h, 0, 0, w, h, null);
//		G.drawImage(view, 0, 0, null);
		//G.drawImage(view, 0, 0, d.width, d.height, offset.x, offset.y, offset.x+d.width, offset.y+d.height, null);
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{	
		Point2D.Double center = vc.convert(arg0.getX(), arg0.getY());
		double dw = vc.br.x - vc.tl.x, dh = vc.br.y - vc.tl.y;
		
		if(arg0.getButton() == MouseEvent.BUTTON1)
		{
			dw *= zoomscale;
			dh *= zoomscale;
			
		}else if(arg0.getButton() == MouseEvent.BUTTON3)
		{
			dw /= zoomscale;
			dh /= zoomscale;			
		}
		
		Point2D.Double tl = new Point2D.Double(center.x - (dw/2d), center.y - dh/2d),
				       br = new Point2D.Double(center.x + (dw/2d), center.y + dh/2d);
		
		invokeRDEvent(new RDEvent(new Dimension(w,h),tl,br,it));
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addRDEventListener(RDEventListener brot) 
	{
		plots.add(brot);
	}


}
