package com.fredwilby.math.mandelbrot.nui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Main application window. Creates a ControlPane (in a JScrollPane) and a 
 * FractalPane with default parameters.
 * 
 * @author Fred
 *
 */

public class FractalWindow extends JFrame
{
    private JScrollPane controlScroll;
    private ControlPane controls;
    private FractalPane fractal;
    
    
    public FractalWindow()
    {
        super("JFr");
        
        // TODO setup components
        
        pack();
        setVisible(true);
        
        
    }
    
    

    public static void main(String[] args)
    {
        new FractalWindow();
    }

}
