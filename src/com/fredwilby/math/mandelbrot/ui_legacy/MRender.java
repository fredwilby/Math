package com.fredwilby.math.mandelbrot.ui_legacy;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import com.fredwilby.math.mandelbrot.calc.MCalcarapi;
import com.fredwilby.math.mandelbrot.calc.ViewConverter;
import com.fredwilby.math.mandelbrot.color.AbstractColorMap;
import com.fredwilby.math.mandelbrot.color.InterpolatedColorMap;

/**
 * Seems to be able to render up to 30k wide images. Conserves memory by 
 * splitting job into parts allowing for large images to be rendered.
 * 
 * Seems to exhibit odd performance issues: 1st 1/2 - 2/3 runs fairly quickly
 * then it becomes much slower until the end. System slowdown becomes pretty
 * total for a large part of the render. Hopefully DMI (below) will ameliorate 
 * these issues. 
 * 
 * TODO DiskMemImage: http://jaitools.org/docs/jaitools/stable/apidocs/
 * or pngj https://code.google.com/p/pngj/
 */
public class MRender 
{
	public static volatile double progress = 1;
	
	public static BufferedImage render(RDEvent param)
	{
		final int max_size = 2000;
		progress = 0;
		
		MCalcarapi mc = new MCalcarapi();
		ViewConverter vc = new ViewConverter(param.pixel_size, param.tl, param.br);
		
		int gridw = (int)Math.ceil((double) param.pixel_size.width/(double) max_size),
		    gridh = (int)Math.ceil((double) param.pixel_size.height/(double) max_size),
		    complw = param.pixel_size.width/max_size,
			complh = param.pixel_size.height/max_size;
		
		BufferedImage bff = new BufferedImage(param.pixel_size.width, param.pixel_size.height, BufferedImage.TYPE_INT_RGB);
		
		
		for(int x = 0; x < gridw; x++)
		{
			for(int y = 0; y < gridh; y++)
			{
				int incw, inch;
				
				progress = (double)(x*gridh + y)/(double)(gridw*gridh);
					
				if(x==complw) // incomplete square
					incw = param.pixel_size.width -  max_size*(param.pixel_size.width/max_size);
				else     	  // complete square
					incw = max_size;
					
				if(y==complh) // incomplete square
					inch = param.pixel_size.height -  max_size*(param.pixel_size.height/max_size);
				else		  // complete square
					inch = max_size;
					
				Point2D.Double tl = vc.convert(max_size*x, max_size*y);
				Point2D.Double br = vc.convert(max_size*x+incw, max_size*y+inch);
				RDEvent rend = new RDEvent(new Dimension(incw,inch), tl,br, param.iterations);
				
				AbstractColorMap map = InterpolatedColorMap.wikiMap;
				
				//  = new DirectHSVColorMap();
				
				
				double[][] data = mc.normalizedIterationValues(rend);
					
				for(int xx = 0; xx < data.length; xx++)
					for(int yy = 0; yy < data[0].length; yy++)
					{
						bff.setRGB(x*max_size+xx, y*max_size+yy, map.getColor(data[xx][yy], param.iterations).getRGB());
					}

			}
		}
		
		progress = 1;
		
		return bff;
	}

}
