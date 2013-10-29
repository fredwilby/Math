package com.fredwilby.math.mandelbrot.color;

import java.awt.Color;

public class InterpolatedColorMap implements AbstractColorMap
{
    private Color[] colors;
    
    
    
    /* Coloring from wikipedia's section on the algorithm */
    public static final InterpolatedColorMap wikiMap = new InterpolatedColorMap(
            new Color[] {
                new Color(66, 30, 15),
                new Color(25, 7,  26),
                new Color(9, 1,  47),
                new Color(4, 4,  73), 
                new Color(0, 7, 100),
                new Color(12, 44, 138), 
                new Color(24, 82, 177),
                new Color(57, 125, 209),
                new Color(134, 181, 229),
                new Color(211, 236, 248),
                new Color(241, 233, 191),
                new Color(248, 201,  95),
                new Color(255, 170,   0),
                new Color(204, 128,   0),
                new Color(153,  87,   0),
                new Color(106,  52,   3)
                
        });
    
    public InterpolatedColorMap(Color[] colors)
    {
        this.colors = colors;
    }
    

    /**
     * Returns a color based on ix;
     * TODO fix banding issue 
     */
    @Override
    public Color getColor(double ix, long max)
    {
        /* lower density seems to make bands less obvious, but still present. */ 
        final float density = .3f; 
        
        /* In the set */
        if(ix == 0d)
            return Color.black;
        
        int indexa = (int) Math.floor(density*ix), indexb = (int) Math.floor(density*ix) + 1; 
        
        Color a = colors[indexa % colors.length], 
              b = colors[indexb % colors.length];
        
        double c = (density*ix) - indexa; // decimal part of ix
        
        return interpolate(c, a, b);
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
    

}
