package com.fredwilby.math.mandelbrot.nui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
    /* The aspect ratio of the fractal. Set to default Mandelbrot view 
     * ((-2.5, -1), (1, 1))
     */
    private static final double FRACTAL_AR = 3.5/2.0;
    
    /* The width & height of the fractal pane in pixels */
    private static final int FRACTAL_WIDTH = 1100, 
                             FRACTAL_HEIGHT = (int) (FRACTAL_WIDTH/FRACTAL_AR),
                            
                             /* width & height of the control pane in pixels */
                             CONTROL_WIDTH = 250,
                             CONTROL_HEIGHT = FRACTAL_HEIGHT;
    
    private JScrollPane controlScroll;
    private ControlPane controls;
    private FractalPane fractal;
    private JPanel container;
    
    public FractalWindow()
    {
        super("JFr");
        
        // TODO events / linkages
        
        /* Create controls, enable vertical scrolling */
        controls = new ControlPane(CONTROL_WIDTH, CONTROL_HEIGHT);
        controlScroll = new JScrollPane(controls, 
                                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        /* Setup fractal pane */
        fractal = new FractalPane(FRACTAL_WIDTH, FRACTAL_HEIGHT);

        /* Add components to border layout */
        container = new JPanel(new BorderLayout());
        container.add(controlScroll, BorderLayout.LINE_START);
        container.add(fractal, BorderLayout.CENTER);

        /* Add Border layout panel to window */
        getContentPane().add(container);
        
        /* Setup window and display */
        pack();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
    }
    
    

    public static void main(String[] args)
    {
        new FractalWindow();
    }

}
