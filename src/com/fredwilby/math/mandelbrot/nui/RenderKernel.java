package com.fredwilby.math.mandelbrot.nui;

/**
 * Represents a generic algorithm used to render a fractal.  
 *
 */
public interface RenderKernel<DataType>
{
    /**
     * Start rendering the fractal over the given inputs. 
     */
    public void startRender(DataType[][] input, View<DataType> view);
    
    public double getProgress();

    public DataType[][] getData();
}
