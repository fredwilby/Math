package com.fredwilby.math.mandelbrot.nui;

import java.awt.Dimension;

import javax.swing.JPanel;

public class ControlPane extends JPanel
{
    private Dimension size; 
    
    public ControlPane(int width, int height)
    {
        super();
        
        size = new Dimension(width, height);
        
        setPreferredSize(size);
        
    }
    
}
