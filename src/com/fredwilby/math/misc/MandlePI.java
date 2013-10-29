package com.fredwilby.math.misc;

import java.math.MathContext;
import java.math.RoundingMode;

import org.apfloat.Apcomplex;
import org.apfloat.ApcomplexMath;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

/**
 * Uses the conjecture that the number of iterations the point (0.25+10^-n, 0) takes to diverge = sqrt(n) digits of pi.   
 * @author Fred Wilby
 *
 *On my computer(Phenom II 955, so fairly modern processor): 4 correct digits (ie 3.141) takes 421 seconds 
 *and the time required afterwards increases exponentially. Basically use BBP for anything.   
 *
 */
public class MandlePI 
{

	private static long itCount(double z0, double z1)
	{
		double i = 0, j = 0;
		long it = 0;
		
		while(i*i+j*j < 4)
		{
			double t = i*i - j*j + z0;
			j = 2*i*j + z1;
			i = t;
			
			it++;
		}
		
		return it;
	}
	
	private static long genPi(MathContext mc)
	{

		double X = Math.pow(10, -mc.getPrecision()),
			   x = .25 + X,
			   y = 0.0;
		
		long it = itCount(x, y);
		
		
		return it;
	}
	
	private static Apfloat itCount(Apcomplex c)
	{
		Apfloat[] z = new Apfloat[] { new Apfloat(0,c.precision()), new Apfloat(0,c.precision()) };
		
		Apfloat it = new Apfloat(0, c.precision()), four = new Apfloat(4, c.precision()), two = new Apfloat(2, c.precision());
		
		Apfloat z0z = z[0].multiply(z[0]), z1z = z[1].multiply(z[1]); 
		
		while(z0z.add(z1z).compareTo(four) < 0)
		{
			Apfloat t = z0z.subtract(z1z).add(c.real());
			z[1] = z[0].multiply(z[1]).multiply(two).add(c.imag());
			z[0] = t;
			
			it = it.add(Apfloat.ONE);
			
			z0z = z[0].multiply(z[0]);
			z1z = z[1].multiply(z[1]);
		}
		
		return it;		
	}

	private static Apfloat genPi(int digits, int prec)
	{
		Apfloat X = ApfloatMath.pow(new Apfloat("10", prec), new Apfloat(1-digits, prec)),
				x = new Apfloat(".25", prec).add(X),
				y = new Apfloat("0",prec);
		
		Apcomplex i = new Apcomplex(x,y);
		Apfloat it = itCount(i);
		
		return ApfloatMath.sqrt(X).multiply(it);
	}
	
	public static void main(String[] args) 
	{
		int[] val = new int [] { 9, 10, 11, 12, 13, 14, 15, 16 };
		
		for(int x : val)
		{
			long t0 = System.nanoTime();
			
			Apfloat pi = genPi(x, 64);
		
			double dt = (double)(System.nanoTime()-t0) / 1000000000d;
			System.out.println(x+"\t"+dt +"\t"+ pi);
		}

	}

}
