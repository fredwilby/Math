package com.fredwilby.math.mandelbrot.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MFrame extends JFrame 
{
	public static final int FWIDTH = 1600, CWIDTH=250, HEIGHT = (int) ((2.0/3.5)*FWIDTH);
	private MPanel mandel;
	private CPanel brot;
	private JPanel cont;
	
	public MFrame()
	{
		super("mandelbrot");
		
		/* Setup container panel */
		cont = new JPanel();
		((FlowLayout)cont.getLayout()).setVgap(0);
		((FlowLayout)cont.getLayout()).setHgap(0);
		
		/* Setup fractal View */
		mandel = new MPanel(FWIDTH, HEIGHT);
		mandel.setPreferredSize(new Dimension(FWIDTH, HEIGHT));
		mandel.addMouseListener(mandel);
		
		brot   = new CPanel(FWIDTH, HEIGHT);
		mandel.addRDEventListener(brot);
		brot.addRDEventListener(mandel);
		brot.setPreferredSize(new Dimension(CWIDTH, HEIGHT));
		
		cont.add(mandel);
		cont.add(brot);
		
		getContentPane().add(cont);
				
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	public static void main(String[] args) 
	{
		new MFrame();

	}

}
