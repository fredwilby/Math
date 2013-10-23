package com.fredwilby.math.mandelbrot.color;

import java.awt.Color;

public class ColorMap implements AbstractColorMap
{
	private static final int repeats = 2; 	// change the frequency of color change
	private static final double offset = 0; // change the 'starting' color
	
	public Color getColor(double ix, long max)
	{
		double x = (ix*repeats)/max + offset;
		
		x -= Math.floor(x);
		
		// Gradient formula via trial & error in StandaloneGradientViewer
		if(ix == 0)
			return Color.black;
		else
		return new Color((int) (255*Math.sin(x*Math.PI)), 
						 (int) (255*(1d-Math.sin(x*Math.PI))),
						 128 + (int) (127*Math.sin(x*2*Math.PI)));
	}

}
