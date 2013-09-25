package com.fredwilby.math.mandelbrot.ui;

import java.awt.Dimension;
import java.awt.geom.Point2D;

public class RDEvent
{
	public RDEvent(Dimension pxs, Point2D.Double tl, Point2D.Double br, long it)
	{
		pixel_size = pxs;
		this.tl = tl;
		this.br = br; 
		iterations = it;
	}
	
	public Dimension pixel_size;
	public Point2D.Double tl, br;
	public long iterations;
}
