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
    
    /**
     * Returns a double in the range [0,1] indicating progress towards 
     * completion of input 
     */
    public double getProgress();

    /**
     * If the render has completed, returns the result, otherwise returns null.
     */
    public DataType[][] getData();
}
