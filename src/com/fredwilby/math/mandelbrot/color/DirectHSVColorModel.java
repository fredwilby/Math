package com.fredwilby.math.mandelbrot.color;

import java.awt.Color;

public class DirectHSVColorModel implements ColorModel
{
    @Override
    public Color getColor(double ix)
    {
        final float density = .05f; 
                
        if(ix == 0d)
            return Color.black;
        else
        return new Color(Color.HSBtoRGB((float) (ix*density), .75f, .75f));
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName();
    }

}
