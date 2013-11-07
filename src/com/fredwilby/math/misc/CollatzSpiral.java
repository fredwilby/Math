package com.fredwilby.math.misc;

import java.awt.Color;

public class CollatzSpiral extends SpiralWindow
{
    private int max;
    
    public CollatzSpiral(int sl)
    {
        super(sl);
    }
    

    @Override
    public void setup()
    {
        max = -1;
        for(int x = 0; x < super.getSpiralLength(); x++)
        {
            if(countCollatz(x) > max)
                max = countCollatz(x);
        }
    }
            
    
    public static int countCollatz(int input)
    {
        int result = 0;
        
        while(input > 1)
        {
            if(input % 2 == 0)
                input /= 2;
            else
                input = 3 * input + 1;
            
            result++;
        }
        
        return result;
    }
    
    public int getMaxValue()
    {
        return max;
    }

    @Override
    public Color getPoint(int pos)
    {
        int val = countCollatz(pos);
        
        return new Color((255*val/getMaxValue()),(255*val/getMaxValue()),(255*val/getMaxValue()));        
    }
    
    public static void main(String[] args)
    {
        SpiralWindow.Show(new CollatzSpiral(1000));
    }



}
