package com.fredwilby.math.mandelbrot.standalone_render;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.BoundedRangeModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ar.com.hjg.pngj.IImageLineFactory;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngWriter;

import com.fredwilby.math.mandelbrot.calc.MCalcarapi.MandelKernel;
import com.fredwilby.math.mandelbrot.calc.ViewConverter;
import com.fredwilby.math.mandelbrot.color.ColorModel;
import com.fredwilby.math.mandelbrot.ui_legacy.RDEvent;

public class LBLRenderer implements BoundedRangeModel
{
    private File pngFile;
    private PngWriter output;
    private IImageLineFactory<ImageLineInt> factory;
    private ImageInfo pngInfo;
    private final int rows_at_a_time = 50;
    private ArrayList<ChangeListener> cls;
    
    private volatile ColorModel colorer; 
    
    private volatile int rows_done = 0;
    private int bit_depth = 16, shift = (1<<bit_depth)-1;
    private long max_it;
    private boolean done = false;
    private ViewConverter vc;
    
    
    public LBLRenderer(RDEvent param, String filename) throws FileNotFoundException
    {
        pngFile = new File(filename);
        pngInfo = new ImageInfo(param.pixel_size.width, 
                                param.pixel_size.height, bit_depth, false);
        
        output = new PngWriter(pngFile, pngInfo);
        
        max_it = param.iterations;
        
        vc = new ViewConverter(param);
        
        cls = new ArrayList<ChangeListener>();
        
        this.colorer = param.model;
        
        factory = ImageLineInt.getFactory(pngInfo);
    }
    
    public void writeLines()
    {
        for(int y = 0; y <= pngInfo.rows/rows_at_a_time; y++)
        { 
            ImageLineInt[] rows;
            
            if(y != pngInfo.rows/rows_at_a_time)
                rows = new ImageLineInt[rows_at_a_time];
            else
            {
                int rowstodo = pngInfo.rows - rows_at_a_time*(pngInfo.rows/rows_at_a_time);
                rows = new ImageLineInt[rowstodo];
            }
            
            for(int x = 0; x < rows.length; x++)
                rows[x] = factory.createImageLine(pngInfo);
            
            double[] inputs = new double[pngInfo.cols*2*rows.length];
            
            for(int x = 0; x < pngInfo.cols*rows.length; x++)
            {
                Point2D.Double thePt = vc.convert(x % pngInfo.cols, y*rows_at_a_time + x/pngInfo.cols);
                inputs[2*x] = thePt.x;
                inputs[2*x+1] = thePt.y;
            }
            
            MandelKernel rk = new MandelKernel(inputs, max_it);
            rk.execute(inputs.length/2);
            
            double[] result = rk.getResults();
            
            for(int l = 0; l < rows.length; l++)
            {
                int[] curLine = rows[l].getScanline();
                
                for(int x = 0; x < pngInfo.cols; x++)
                {
                    Color color = colorer.getColor(result[l*pngInfo.cols+x]);

                    int red,green,blue;
                    
                    if(bit_depth == 8)
                    {
                        red = color.getRed();
                        green = color.getGreen(); 
                        blue = color.getBlue();
                    } else
                    {
                        red   = (int)(shift*color.getRed()/255d);
                        green = (int)(shift*color.getGreen()/255d); 
                        blue  = (int)(shift*color.getBlue()/255d);
                    }
                    
                    curLine[3*(x%(result.length/rows.length))    ] = red;
                    curLine[3*(x%(result.length/rows.length)) + 1] = green;
                    curLine[3*(x%(result.length/rows.length)) + 2] = blue;
                }

                output.writeRow(rows[l]);
            }
                        
            rk.dispose();
            
            rows_done += rows.length;
            for(ChangeListener  cl : cls)
            {
                cl.stateChanged(new ChangeEvent(this));
            }
        
            
        }
        
        
        output.end();
        rows_done = 0;
        for(ChangeListener  cl : cls)
        {
            cl.stateChanged(new ChangeEvent(this));
        }
        
    }

    @Override
    public void addChangeListener(ChangeListener arg0)
    {
        cls.add(arg0);        
    }

    @Override
    public int getExtent()
    {
        return 1;
    }

    @Override
    public int getMaximum()
    {
        return pngInfo.rows;
    }

    @Override
    public int getMinimum()
    {
        return 0;
    }

    @Override
    public int getValue()
    {
        return rows_done;
    }

    @Override
    public boolean getValueIsAdjusting()
    {
        return !done;
    }

    @Override
    public void removeChangeListener(ChangeListener arg0)
    {
        cls.remove(arg0);
    }

    @Override
    public void setExtent(int arg0)
    {
    }

    @Override
    public void setMaximum(int arg0)
    {
    }

    @Override
    public void setMinimum(int arg0)
    {
    }

    @Override
    public void setRangeProperties(int arg0, int arg1, int arg2, int arg3, boolean arg4)
    {
    }

    @Override
    public void setValue(int arg0)
    {
    }

    @Override
    public void setValueIsAdjusting(boolean arg0)
    {
    }
}
