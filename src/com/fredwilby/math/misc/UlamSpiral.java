package com.fredwilby.math.misc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UlamSpiral extends SpiralWindow
{
    private boolean[] allPrimes;

    private static final Color center = new Color(249, 49, 49),
                          edge = new Color(229, 139, 29);
    

    public UlamSpiral(int sideLength)
    {
        super(sideLength);
    }

    @Override
    public Color getPoint(int pos)
    {
        if(isPrime(pos))
            return Color.white;
        else
            return interpolate((double) pos/(double) super.getSpiralLength(),
                    center, edge);
    }
    
    private int interpolate(double pos, int a, int b)
    {
        return (int) ((1d - pos) * a + pos * b);
    }
    
    private Color interpolate(double pos, Color a, Color b)
    {
        Color result = new Color(interpolate(pos, a.getRed(),   b.getRed()),
                                 interpolate(pos, a.getGreen(), b.getGreen()),
                                 interpolate(pos, a.getBlue(),  b.getBlue()));
        
        return result;        
    }
    
    public boolean isPrime(int num)
    {
        /* Lazy allocation */
        if(allPrimes == null)
        {
            allPrimes = getPrimesBelow(super.getSpiralLength() + 1);
        }
        
        return allPrimes[num];
    }
    
    
    /**
     * Returns a upperb-large array of boolean that satisfy
     * prime[x] == true iff x is prime. Uses seive of eratosthenes.
     * 
     */
    public static boolean[] getPrimesBelow(int upperb)
    {
        boolean[] ints = new boolean[upperb];
        
        /* initialize all values to true */
        for(int x = 0; x < upperb; x++)
        {
            ints[x] = true;
        }
        
        /* 0, 1 are not prime */
        ints[0] = false;
        ints[1] = false; 
        
        /* eliminate all composites */ 
        for(int x = 2; x < Math.sqrt(upperb); x++)
        {
            if(ints[x])
            {
                for(int j = x*x; j < upperb; j += x)
                    ints[j] = false;
            }
        }
        
        return ints;
    }
    
    public static void main(String[] args)
    {
        SpiralWindow.Show(new UlamSpiral(1000));
    }
    

}
