package com.fredwilby.math.mandelbrot.standalone_render;

import com.amd.aparapi.Kernel;

public class RowKernel extends Kernel
{
    private double[] inputs;
    public double[] result;
    private long max_it;
    
    private static final double log2 = Math.log(2d);
    
    public RowKernel(double[] inputs, long max_it)
    {
        this.inputs = inputs;
        this.max_it = max_it;
        
        result = new double[inputs.length-1];
    }

    @Override
    public void run()
    {
        int i = getGlobalId();
        int it = 0;

        double ci = inputs[i+1], cj = inputs[0];
        double zi = 0, zj = 0, zim = -1, zjm = -1;
        boolean repeat = false;  
        
        
        while(zi*zi+zj*zj < 4 && it < max_it && !repeat)
        {
            zim = zi;
            zjm = zj;
            
            double t = zi*zi - zj*zj + ci;
            zj = 2*zi*zj + cj;
            zi = t;
            
            if(zim == zi && zjm == zj)
                repeat = true;
            
            it++;
        }
        
        if(it == max_it || repeat)
        {
            result[i] = 0d;
        }
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
