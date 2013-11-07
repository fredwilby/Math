package com.fredwilby.math.misc;

import static org.junit.Assert.*;

import java.text.DecimalFormat;

import org.junit.Test;

public class UlamSpiralTest
{

    @Test
    public void test()
    {
        
        
    }
    
    @Test
    public void timeBenchMark()
    {
        int[] inputs = new int[] { 100, 250, 500, 1000, 2500, 5000, 6000, 7000, 8000, 9000 };
        double[] times = new double[inputs.length];
        
        final double nsperms = 1000000d;
        
        for(int x = 0; x < inputs.length; x++)
        {
            long t0 = System.nanoTime();
            new UlamSpiral(inputs[x]);
            times[x] = (double)(System.nanoTime() - t0)/nsperms;      
            System.out.println(inputs[x] + "\t" + times[x]);
        }
    }
    

}
