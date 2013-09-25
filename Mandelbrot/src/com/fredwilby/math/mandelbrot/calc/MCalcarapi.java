package com.fredwilby.math.mandelbrot.calc;

import java.awt.geom.Point2D;

import com.amd.aparapi.Kernel;
import com.fredwilby.math.mandelbrot.ui.RDEvent;

public class MCalcarapi implements MCalc
{

    @Override    
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
        final double[] result = new double[redraw.pixel_size.width*redraw.pixel_size.height];
        final double[] inputs = new double[result.length*2];
        final long max_it = redraw.iterations; 
        
        final double log4 = Math.log(4.0), log2 = Math.log(2.0);
        
        ViewConverter vc = new ViewConverter(redraw.pixel_size, redraw.tl, redraw.br);

        for(int x = 0; x < result.length; x++)
        {
            int h = redraw.pixel_size.height,
                px = x/h,
                py = x % h;
            
            Point2D.Double p2dd = vc.convert(px, py);
            inputs[2*x]  = p2dd.x;
            inputs[2*x+1] = p2dd.y;
        }
        
        Kernel kernel = new Kernel() {
          @Override public void run()
          {
              int i = getGlobalId();
              int it = 0;
              
              double ci = inputs[2*i], cj = inputs[2*i+1];
              double zi = 0, zj = 0;
              
              while(zi*zi+zj*zj < 4 && it < max_it)
              {
                  double t = zi*zi-zj*zj+ ci;
                  zj = 2*zi*zj + cj;
                  zi = t;
                  
                  it++;
              }
              
              result[i] = (double) it - Math.log((Math.log(zi*zi+zj*zj)/log4))/log2;;
          }
        };
        
        kernel.execute(result.length);
        if(!kernel.getExecutionMode().equals(Kernel.EXECUTION_MODE.GPU))
            System.out.println("sad day");
        
        return result;
    }

}
