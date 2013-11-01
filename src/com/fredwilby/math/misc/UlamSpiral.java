package com.fredwilby.math.misc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UlamSpiral
{
    private Color[][] spiralData;
    private int[][] spiralNumbers;
    
    private boolean[] allPrimes;
    private int sideLength;
    
    public UlamSpiral(int sideLength)
    {
        this.sideLength = sideLength;
        spiralData = new Color[sideLength][sideLength];
        spiralNumbers = new int[sideLength][sideLength];
        
        allPrimes = getPrimesBelow(sideLength*sideLength + 1);
        
        
        genSpiral();
    }
    
    private void genSpiral()
    {
        final int originx = (int)Math.ceil((double) sideLength / 2d)-1,
                  originy = sideLength/2; 
        
        int x = originx, y = originy;
        
        int val = 1, cd = 0;
        
        Point [] directions = new Point[] {
                
                new Point( 1,  0), // right
                new Point( 0, -1), // up
                new Point(-1,  0), // left
                new Point( 0,  1)  // down
                
        };

        for(int i = 1; i <= sideLength; i++)
        {
            for(int di = 0; di < ((i == sideLength)? 1 : 2); di++)
            {
                for(int t = i; t > 0; t--)
                {
                    spiralNumbers[x][y] = val++; 
                    
                    x += directions[cd % directions.length].x;
                    y += directions[cd % directions.length].y;
                }
                
                cd++;
            }
        }
        
        final Color center = new Color(249, 49, 49),
                    edge = new Color(229, 139, 29);
        
        for(int xx = 0; xx < sideLength; xx++)
        {
            for(int yy = 0; yy < sideLength; yy++)
            {
                if(isPrime(spiralNumbers[xx][yy]))
                {                    
                    Color pixel = interpolate((double)spiralNumbers[xx][yy]/
                                              ((double)sideLength*sideLength),
                                              center, edge);
                    
                   spiralData[xx][yy] = pixel;
                } else
                {
                    spiralData[xx][yy] = Color.white;
                }
            }
        }
        
    }
    
    private int interpolate(double pos, int a, int b)
    {
        return (int) ((1d - pos) * a + pos * b);
    }
    
    private Color interpolate(double pos, Color a, Color b)
    {
        Color result = new Color(interpolate(pos, a.getRed(),   b.getRed()),
                                 interpolate(pos, a.getGreen(), b.getGreen()),
                                 interpolate(pos, a.getBlue(),  b.getBlue()));
        
        return result;        
    }
    
    private boolean isPrime(int num)
    {
        return allPrimes[num];
    }
    
    public Color getColor(int x, int y)
    {
        return spiralData[x][y];
    }
    
    public int getValue(int x, int y)
    {
        return spiralNumbers[x][y];
    }
    
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        
        for(int x = 0; x < sideLength; x++)
        {
            for(int y = 0; y < sideLength; y++)
            {
                if(spiralNumbers[x][y] >= 10)
                    result.append(spiralNumbers[x][y] + " ");
                else
                    result.append(" " + spiralNumbers[x][y] +" ");
            }
            result.append("\n");
        }
        
        return result.toString();
    }
    
    /**
     * Returns a upperb-large array of boolean that satisfy
     * prime[x] == true iff x is prime. Uses seive of eratosthenes.
     * 
     */
    public boolean[] getPrimesBelow(int upperb)
    {
        boolean[] ints = new boolean[upperb];
        
        /* initialize all values to true */
        for(int x = 0; x < upperb; x++)
        {
            ints[x] = true;
        }
        
        /* 0, 1 are not prime */
        ints[0] = false;
        ints[1] = false; 
        
        /* eliminate all composites */ 
        for(int x = 2; x < Math.sqrt(upperb); x++)
        {
            if(ints[x])
            {
                for(int j = x*x; j < upperb; j += x)
                    ints[j] = false;
            }
        }
        
        return ints;
    }
    
    public static void main(String[] args)
    {
        final int pxs = 2, size = 500; 
        
        UlamSpiral us = new UlamSpiral(size); 
        
        BufferedImage bfi = new BufferedImage(size*pxs, size*pxs, BufferedImage.TYPE_INT_RGB);
        
        Graphics G = bfi.getGraphics();        
        G.setColor(Color.white);
        G.fillRect(0, 0, size*pxs, size*pxs);
        
        G.setColor(Color.black);
        
        for(int x = 0; x < size; x++)
        {
            for(int y = 0; y < size; y++)
            {
                    G.setColor(us.getColor(x, y));
                    G.fillRect(x*pxs, y*pxs, pxs, pxs);
            }
        }
        
        JFrame window = new JFrame("Ulam Spiral");
        
        JPanel imp = new ImagePanel(bfi, new Dimension(1000,1000));

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
