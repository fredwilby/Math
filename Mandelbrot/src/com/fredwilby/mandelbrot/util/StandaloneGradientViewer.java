/**
 * Draws a gradient using formula in getRGB. 
 * @author Fred
 *
 */

package com.fredwilby.mandelbrot.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class StandaloneGradientViewer extends JFrame 
{
	public static final int WIDTH = 1024, HEIGHT=768;
	
	
	JPanel gradient;
	BufferedImage gradImage;
	
	public StandaloneGradientViewer()
	{
		super("gradient viewer");
		
		gradImage = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D G = gradImage.createGraphics();
		
		for(int x = 0; x < WIDTH; x++)
		{
			int[] rgb = getRGB((double)x/(double)WIDTH);
			G.setColor(new Color(rgb[0],rgb[1],rgb[2]));
			G.drawLine(x, 0, x, HEIGHT);
		}
		
		gradient = new JPanel() {
			
			@Override
			public void paint(Graphics G)
			{
				G.drawImage(gradImage, 0, 0, null);
			}
			
		};
		
		gradient.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		getContentPane().add(gradient);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public int[] getRGB(double x)
	{
		return new int[] { (int) (255*Math.sin(x*Math.PI)), 
						   (int) (255*(1d-Math.sin(x*Math.PI))),
						   128 + (int) (127*Math.sin(x*2*Math.PI))
							};
	}
	
	

	public static void main(String[] args) 
	{	
		new StandaloneGradientViewer();
	}

}
