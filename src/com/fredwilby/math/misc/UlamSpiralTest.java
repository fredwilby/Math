package com.fredwilby.math.misc;

import static org.junit.Assert.*;

import java.text.DecimalFormat;

import org.junit.Test;

public class UlamSpiralTest
{

    @Test
    public void test()
    {
        UlamSpiral test = new UlamSpiral(4);
        
        int[][] result = new int[][] {
                { 16, 15, 14, 13 },
                {  5,  4,  3, 12 },
                {  6,  1,  2, 11 },
                {  7,  8,  9, 10 }
        };
        
        for(int y = 0; y < 4; y++)
        { 
            for(int x = 0; x < 4; x++)
            {
                assertEquals("spiral data incorrect", result[y][x], test.getValue(x, y));
            }
        }
        
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
