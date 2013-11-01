package com.fredwilby.math.mandelbrot.calc;

import java.awt.geom.Point2D;

import com.amd.aparapi.Kernel;
import com.fredwilby.math.mandelbrot.nui.View;
import com.fredwilby.math.mandelbrot.ui_legacy.RDEvent;

public class AparapiKernel implements RenderKernel<Double, Point2D.Double>
{
	class MandelKernel extends Kernel
	{
		private double[] inputs, result;
		private long max_it;

        final double log2 = Math.log(2.0);
		
		public MandelKernel(double[] inputs, long maxit)
		{
			super();
			
			this.inputs = inputs;
			result = new double[inputs.length/2];
			max_it = maxit;
		}
		
		public double[] getResults()
		{
			return result;
		}
		

		@Override public void run()
        {
			int i = getGlobalId();
			int it = 0;
			 
			double ci = inputs[2*i], cj = inputs[2*i+1];
			double zi = 0, zj = 0;
			  
			while(zi*zi+zj*zj < 4 && it < max_it)
			{
			    double t = zi*zi - zj*zj + ci;
			    zj = 2*zi*zj + cj;
			    zi = t;
			      
			    it++;
			}
			
			if(it == max_it)
				result[i] = 0;
			else
			{
			/*
			 * 1/2 log |Zn|^2 = log(Zn)
			 * |Zn| = sqrt(zi*zi+zj*zj)
			 * 
			 * result = it + 1 + 
			 * log2(log(|Zn|)/log(2)) = 
			 * log(log(|Zn|^2) / 2*log(2))/log(2)       
			 */
        result[i] = (double) it + 1d - Math.log((Math.log(zi*zi+zj*zj)/(2*log2)))/log2;
      }
  }
	}
	
    @Override 
    public void startRender(Point2D.Double[] input, View<Point2D.Double> view)
    {
        
    }
    
	@Override
	public double getProgress()
	{
	    return 0d;
	}
    
	@Override
	public Double[][] getData()
	{
	    return new Double[1][1];
	}
	
    public double[][] normalizedIterationValues(RDEvent  redraw)
    {
        double[] flat = flatNormalizedIterationValues(redraw);
        double[][] result = new double[redraw.pixel_size.width][redraw.pixel_size.height];
        
        for(int x = 0; x < flat.length; x++)
        {
            int px = x / redraw.pixel_size.height, 
                py = x % redraw.pixel_size.height;
            
            result[px][py] = flat[x];
        }
        
        return result;
    }
    
    public double[] flatNormalizedIterationValues(RDEvent redraw)
    {
        final double[] inputs = new double[redraw.pixel_size.width*redraw.pixel_size.height*2];
        final long max_it = redraw.iterations; 
        
        
        ViewConverter vc = new ViewConverter(redraw.pixel_size, redraw.tl, redraw.br);

        for(int x = 0; x < inputs.length/2; x++)
        {
            int h = redraw.pixel_size.height,
                px = x/h,
                py = x % h;
            
            Point2D.Double p2dd = vc.convert(px, py);
            inputs[2*x]  = p2dd.x;
            inputs[2*x+1] = p2dd.y;
        }
        
        MandelKernel kernel = new MandelKernel(inputs, max_it);
        kernel.execute(inputs.length/2);
        
        double[] result = kernel.getResults();
        kernel.execute(result.length);
        if(!kernel.getExecutionMode().equals(Kernel.EXECUTION_MODE.GPU))
        {
            System.out.println(kernel.getExecutionMode()+ "\t sad day");
        }
        
        return result;
    }

}
