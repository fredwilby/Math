package com.fredwilby.math.mandelbrot.ui;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class TiledImagePane extends JPanel
{
    BufferedImage view,tiles[][]; 
    
    /**
     * Draws the requested view as best as possible with current data while
     * requesting missing data from renderer. 
     * @param newView
     */
    public void requestView(RenderEvent newView)
    {
        
    }
}
