package com.fredwilby.math.mandelbrot.ui_legacy;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

import com.fredwilby.math.mandelbrot.color.ColorModel;

public class RDEvent
{
	public RDEvent(Dimension pxs, Point2D.Double tl, Point2D.Double br, ColorModel model, long it)
	{
		pixel_size = pxs;
		this.tl = tl;
		this.br = br; 
		this.model = model;
		iterations = it;
	}
	
	public Dimension pixel_size;
	public Point2D.Double tl, br;
	public long iterations;
	public ColorModel model;
	
	@Override
	public String toString()
	{
	    DecimalFormat df = new DecimalFormat("#.###");
	    
	    return "["+df.format(tl.x)+" + "+df.format(tl.y)+"i] x [" +
	               df.format(br.x)+" + "+df.format(br.y)+"i]";
	            
	}
}
