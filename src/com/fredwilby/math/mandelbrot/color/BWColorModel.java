package com.fredwilby.math.mandelbrot.color;

import java.awt.Color;

public class BWColorModel implements ColorModel
{

    @Override
    public Color getColor(double val)
    {
        
        if(val == 0d)
            return Color.black;
        else
            return Color.white;
    }
    
    @Override
    public String toString()
    {
        return "Black and White";
    }
}
