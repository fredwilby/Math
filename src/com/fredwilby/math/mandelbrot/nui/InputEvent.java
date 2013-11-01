package com.fredwilby.math.mandelbrot.nui;

/**
 * Represents an event involving mouse input to the FractalPane. dx represents 
 * the distance the image was dragged in the x direction, dy represents the 
 * distance the image was dragged in the y direction, and dz represents the
 * number of clicks on the scrollwheel the image was zoomed.
 *
 */
public class InputEvent
{
    public static final InputEvent ZERO = new InputEvent(0, 0, 0);
    
    private int dx, dy;
    private double dz;
    
    /**
     * Creates a new InputEvent.
     * @param dx the pixels image was dragged in x direction
     * @param dy the pixels image was dragged in y direction
     * @param dz the clicks on scrollwheel image was zoomed
     */
    public InputEvent(int dx, int dy, double dz)
    {
        this.dx = dx; 
        this.dy = dy;
        this.dz = dz;
        
    }
    
    /** 
     * Adds the values of another input event to this one's. 
     */
    public void add(InputEvent other)
    {
        dx += other.dx;
        dy += other.dy;
        dz += other.dz;
    }

    public int getDX()
    {
        return dx;
    }

    public int getDY()
    {
        return dy;
    }

    public double getDZ()
    {
        return dz;
    }

}
