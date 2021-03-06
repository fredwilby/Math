package com.fredwilby.math.misc;

import java.awt.Color;
import java.util.Random;

public class RandomSpiral extends SpiralWindow
{
    
    private Random r;
    
    public RandomSpiral(int sl)
    {
        super(sl);
        
    }
    
    public Color getRandomGrey()
    {
        int shade  = r.nextInt(256);
        return new Color(shade, shade, shade);
    }

    @Override
    public void setup()
    {
        r = new Random();
    }

    @Override
    public Color getPoint(int pos)
    {        
        return getRandomGrey();
    }

    public static void main(String[] args)
    {
        SpiralWindow.Show(new RandomSpiral(1000));
    }

    
}
