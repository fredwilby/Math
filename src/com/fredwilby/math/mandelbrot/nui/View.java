package com.fredwilby.math.mandelbrot.nui;

import java.awt.Point;

/**
 * Represents a mapping between a given coordinate system and pixel values.
 * @param <T> the type of coordinate to convert to (eg Point2D.Double)
 */
public abstract class View<T>
{
    private T min, max;
    private Point pixel;
    
    public View(T minPt, T maxPt, Point maxPixels)
    {
        min = minPt;
        max = maxPt;
        
        pixel = maxPixels;
    }
    
    public abstract T convert(Point src);
    public abstract Point convert(T src);
}
