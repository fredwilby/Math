package com.fredwilby.math.mandelbrot.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayDeque;

import javax.swing.JPanel;

public class MPanel_ng extends JPanel implements RenderEventListener, MouseMotionListener, MouseWheelListener, Runnable
{
    private TiledImagePane mandelImage;
    private RenderEvent currentState;
    private ArrayDeque<InputEvent> inputEventQueue;
    private Thread updateThread;
    
    private Point lastDrag = null; 
    
    private final long FPS = 30, delay = 1000 / FPS, nsPerms = 1000000l;
    
    
    public MPanel_ng()
    {
        inputEventQueue = new ArrayDeque<InputEvent>();
        
        // TODO add tiledimagepanel, controlpanel
        // TODO setup layout
        // TODO add event listeners
    }
    
    @Override 
    public void addNotify()
    {
        super.addNotify(); 
        
        updateThread = new Thread(this);
        updateThread.start();        
    }
    
    
    private void doDelay(long minus)
    {
        try {
            Thread.sleep(delay - minus);
        } catch(InterruptedException e) { /* Why bother? */ }
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            long t0 = System.nanoTime();
            
            InputEvent total = new InputEvent(0,0,0);
            
            while(!inputEventQueue.isEmpty())
                total.add(inputEventQueue.pop());
            
            // TODO update state with input event
            
            // TODO send state to tiled imagepanel           
            
            
            long dt = (System.nanoTime() - t0)/nsPerms; 
            doDelay((dt > delay - 10)? (delay - 10) : dt);
        }        
    }
    
    @Override
    public void recieveRenderEvent(RenderEvent r)
    {
        currentState.update(r);
    };

    private class InputEvent 
    {
        InputEvent(int dx,int dy,int dz)
        {
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
        }
        
        void add(InputEvent other)
        {
            dx += other.dx;
            dy += other.dy;
            dz += other.dz;
        }
        
        int dx, dy, dz;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent arg0)
    {
        inputEventQueue.add(new InputEvent(0, 0, arg0.getClickCount()));
    }

    @Override
    public void mouseDragged(MouseEvent arg0)
    {
       if(lastDrag != null)
       {
           int dx = arg0.getX() - lastDrag.x,
               dy = arg0.getY() - lastDrag.y;
           
           inputEventQueue.add(new InputEvent(dx, dy, 0));    
       }
       
       lastDrag = arg0.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent arg0)
    {
        /* We're no longer dragging the mose */
        lastDrag = null;
    }
}
