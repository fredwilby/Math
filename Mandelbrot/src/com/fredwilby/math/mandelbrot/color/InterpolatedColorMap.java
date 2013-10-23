package com.fredwilby.math.mandelbrot.color;

import java.awt.Color;

public class InterpolatedColorMap implements AbstractColorMap
{
    private Color[] colors;
    
    public InterpolatedColorMap(Color[] colors)
    {
        this.colors = colors;
    }
    

    /**
     * Returns a color based on ix; 
     */
    @Override
    public Color getColor(double ix, long max)
    {
        /* In the set */
        if(ix == 0d)
            return Color.black;
        
        int indexa = (int) Math.floor(ix), indexb = (int) Math.ceil(ix); 
        
        Color a = colors[indexa % colors.length], 
              b = colors[indexb % colors.length];
        
        double c = ix - indexa; // decimal part of ix
        
        return WeightedAverage(c, a, b);
    }
    
    private Color WeightedAverage(double pos, Color a, Color b)
    {
        final float pow = 3f;
        
        double adj = pos; //5*pos*pos*pos*pos - 4*pos*pos*pos*pos*pos;
        
        Color result = new Color((int)((1d-adj)*a.getRed()+adj*b.getRed()),
                                 (int)((1d-adj)*a.getGreen()+adj*b.getGreen()),
                                 (int)((1d-adj)*a.getBlue()+adj*b.getBlue()));
        
        return result;        
    }
    

}
