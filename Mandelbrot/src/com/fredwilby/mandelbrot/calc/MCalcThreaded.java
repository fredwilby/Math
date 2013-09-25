package com.fredwilby.mandelbrot.calc;

import java.awt.geom.Point2D;

import com.fredwilby.mandelbrot.ui.RDEvent;

public class MCalcThreaded implements MCalc 
{
	private static int NUM_THREADS = 4; 
	
	private ViewConverter vc;
	
	private long max_it;
	private int nthreads;
	
	public MCalcThreaded()
	{
		this(NUM_THREADS);
	}
	
	public MCalcThreaded(int n)
	{
		nthreads = n;
	}
	
	/** 
	 * Calculates the normalized iteration counts for the given redraw event. 
	 * Returns a 2d array of doubles of dimension redraw.pixel_size.
	 * 
	 */
	@Override
	public double[][] normalizedIterationValues(RDEvent redraw)
	{
		double[][][] parts = normalizedIterationValuesParts(redraw);
		double[][] result = new double[redraw.pixel_size.width][redraw.pixel_size.height];
		
		for(int x = 0; x < redraw.pixel_size.width; x++)
			for(int y = 0; y < redraw.pixel_size.height; y++)
			{
				int i = x * nthreads / redraw.pixel_size.width;
				result[x][y] = parts[i][x - i*(redraw.pixel_size.width/nthreads)][y];
			}
		
		return result;
	}
	
	/**
	 * Calculates the normalized iteration count for the given redraw event. 
	 * Returns nthreads arrays of size [width / nthreads][height]; 
	 * using this method directly saves the memory bottleneck of copying the 
	 * individual threads' work to a single array.
	 * 
	 */
	public double[][][] normalizedIterationValuesParts(RDEvent redraw) 
	{
		vc = new ViewConverter(redraw.pixel_size, redraw.tl, redraw.br);
		
		max_it = redraw.iterations;
		
		RenderThread[] rt = new RenderThread[nthreads];

		for(int i = 0; i < nthreads; i++)
		{
			rt[i] = new RenderThread(i, redraw.pixel_size.width/nthreads, redraw.pixel_size.height);
			new Thread(rt[i]).start();
		}
		
		/* Wait for render threads to complete */
		boolean fin = false;		
		while(!fin)
		{
			fin = true;
			
			for(RenderThread rtt : rt)
				if(!rtt.isDone()) fin = false;
			
			Thread.yield();
		}
		
		/* Get work from individual render threads. */
		double[][][] result = new double[rt.length][redraw.pixel_size.width/nthreads][redraw.pixel_size.height];
		
		int i = 0;
		for(RenderThread rtt : rt)
			result[i++] = rtt.getWork();
		
		return result;
	}
	
	/* Hopefully speed up the computation... */
	final double log4 = Math.log(4.0), log2 = Math.log(2.0);
	
	public double normalizedIterationCount(Point2D.Double z)
	{
		double i = 0d, j = 0d;
		long it = 0; 
		
		while(i*i+j*j < 4 && it++ < max_it)
		{
			double t = i*i-j*j + z.x;
			j = 2*i*j + z.y;
			i = t;
		}
		
		if(it >= max_it)
			return 0;
		
		return (double) it - Math.log((Math.log(i*i+j*j)/log4))/log2;
		
	}
	
	class RenderThread implements Runnable 
	{
		private int offset, w, h;
		private boolean done;
		private double[][] workload;
		
		public RenderThread(int i, int w, int h)
		{
			this.w = w;
			this.h = h;
			
			offset = i * w;
			done = false;
			workload = new double[w][h];
		}
		
		public boolean isDone()
		{
			return done;
		}
		
		public double[][] getWork()
		{
			if(!done)
				return null;
			else
				return workload;
		}
		
		
		public void run()
		{
			for(int x = offset; x < offset+w; x++)
			{
				for(int y = 0; y < h; y++)
				{
					Point2D.Double pt = vc.convert(x,y);
					workload[x-offset][y] = normalizedIterationCount(pt);
				}
			}
			
			done = true;
		}
	
	};

}
