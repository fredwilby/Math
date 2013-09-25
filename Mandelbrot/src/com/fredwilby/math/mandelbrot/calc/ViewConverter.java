package com.fredwilby.math.mandelbrot.calc;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;

public class ViewConverter 
{
	public Dimension px;
	public Point2D.Double tl, br;
	
	public ViewConverter(Dimension px, Point2D.Double ztl, Point2D.Double zbr)
	{
		this.px = px;
		tl = ztl;
		br = zbr;		
	}
	
	public Point2D.Double convert(int pix, int piy)
	{
		double x = pix * (br.x - tl.x) / (double) px.width + tl.x, 
			   y = piy * (br.y - tl.y) / (double) px.height + tl.y;
		
		return new Point2D.Double(x, y);
	}
	
	public Point convert(double cx, double cy)
	{
		int x = (int)((cx - tl.x) * px.width / (br.x - tl.x)),
		    y = (int)((cy - tl.y) * px.height / (br.y - tl.y));
		return new Point(x, y);
	}

}
