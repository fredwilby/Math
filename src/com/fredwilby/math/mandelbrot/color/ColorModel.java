package com.fredwilby.math.mandelbrot.color;

import java.awt.Color;

/**
 * Represents an algorithm to return a color based on a fractal value at a given
 * pixel. 
 *
 */
public interface ColorModel
{
    public Color getColor(double val);
}
