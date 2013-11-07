package com.fredwilby.math.misc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class SpiralWindow
{
    private BufferedImage spiralData;
    private int sideLength;
    
    private final Point [] directions = new Point[] 
    {
            new Point( 1,  0), // right
            new Point( 0, -1), // up
            new Point(-1,  0), // left
            new Point( 0,  1)  // down
    };
    
    /**
     * Starting indexes for spiral.
     */
    private int originx, originy; 
    
    public SpiralWindow(int sideLength)
    {
        this.sideLength = sideLength;
        spiralData = new BufferedImage(sideLength, sideLength,
                                                    BufferedImage.TYPE_INT_RGB);
        
        originx = (int) Math.ceil((double) sideLength / 2d)-1;
        originy = sideLength/2;
        
        
        genSpiral();
    }
    
    /**
     * Returns the color of the given element on the spiral.
     */
    public abstract Color getPoint(int pos);    

    /**
     * Returns the sidelength of the spiral.
     */
    public int getSideLength()
    {
        return sideLength;
    }
    
    /**
     * Returns the number of elements in the spiral (its length).
     */
    public int getSpiralLength()
    {
        return sideLength * sideLength;
    }
    
    /**
     * Generates the spiral
     */
    private void genSpiral()
    {
        int x = originx, y = originy;
        
        int val = 1, cd = 0;


        for(int i = 1; i <= sideLength; i++)
        {
            for(int di = 0; di < ((i == sideLength)? 1 : 2); di++)
            {
                for(int t = i; t > 0; t--)
                {
                    try {
                    spiralData.setRGB(x, y, getPoint(val++).getRGB());
                    } catch(ArrayIndexOutOfBoundsException e)
                    {
                        System.out.println(x+"\t"+y);
                        e.printStackTrace();
                        System.exit(1);
                    }
                    
                    
                    x += directions[cd % directions.length].x;
                    y += directions[cd % directions.length].y;
                }
                
                cd++;
            }
        }
        
    }
    
    public BufferedImage getImage()
    {
        return spiralData;
    }
    
    public static void Show(SpiralWindow toShow)
    {   
        JFrame window = new JFrame("Ulam Spiral");
     
        BufferedImage img = toShow.getImage();
        
        JPanel imp = new ImagePanel(img, 
                                    new Dimension(img.getWidth(), 
                                                  img.getHeight()));

        window.getContentPane().add(imp);
        window.pack();
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
    
    static class ImagePanel extends JPanel
    {
        
        BufferedImage image;
        Dimension size;
        
        public ImagePanel(BufferedImage image, Dimension size)
        {
            super();
            this.image = image;
            this.size = size;
            setPreferredSize(size);
        }
        
        @Override
        public void paint(Graphics G)
        {
            G.drawImage(image, 0, 0, size.width, size.height, null);
        }
        
    }
}
