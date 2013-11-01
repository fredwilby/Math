package com.fredwilby.math.mandelbrot.nui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayDeque;

import javax.swing.JPanel;

import com.fredwilby.math.mandelbrot.color.ColorModel;

public class FractalPane extends JPanel implements Runnable, MouseMotionListener, MouseWheelListener
{
    private static final RenderKernel defaultKernel = null; // TODO create default kernel
    private static final ColorModel defaultColorModel = null; // TODO create default colorModel
    
    private ArrayDeque<InputEvent> inputQueue;
    private RenderKernel renderer;
    private ColorModel  colorGen;
    private Dimension size; 
    
    private Point lastDrag; 
    
    public FractalPane(int width, int height)
    {
        this(width, height, defaultKernel, defaultColorModel);
    }
    
    public FractalPane(int width, int height, RenderKernel drawer, ColorModel colorer)
    {
        super(); // why not?
        
        size = new Dimension(width, height);
        
        renderer = drawer; 
        colorGen = colorer; 
        
        inputQueue = new ArrayDeque<InputEvent>();
        lastDrag = null;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent arg0)
    {
        inputQueue.add(new InputEvent(0, 0, arg0.getPreciseWheelRotation()));
    }

    @Override
    public void mouseDragged(MouseEvent arg0)
    {
        if(lastDrag == null)
        {
            lastDrag = arg0.getPoint();
        } else
        {
            inputQueue.add(new InputEvent(arg0.getX()-lastDrag.x,
                                          arg0.getY()-lastDrag.y, 0));
            
            lastDrag = arg0.getPoint();
        }
        
    }

    @Override
    public void mouseMoved(MouseEvent arg0)
    { 
        lastDrag = null;  // we are not dragging
    }

    @Override
    public void run()
    {
        while(true)
        {
            
        }
    }

}
