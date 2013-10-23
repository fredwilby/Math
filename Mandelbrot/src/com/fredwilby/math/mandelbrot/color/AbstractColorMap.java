package com.fredwilby.math.mandelbrot.color;

import java.awt.Color;

public interface AbstractColorMap
{
    public Color getColor(double ix, long max);
}
