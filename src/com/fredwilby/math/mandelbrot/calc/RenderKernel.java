package com.fredwilby.math.mandelbrot.calc;

import com.fredwilby.math.mandelbrot.nui.View;

/**
 * Represents a generic algorithm used to render a fractal.  
 *
 */
public interface RenderKernel<DataType, PointType>
{
    /**
     * Start rendering the fractal over the given inputs. 
     */
    public void startRender(PointType[] input, View<PointType> view);
    
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
