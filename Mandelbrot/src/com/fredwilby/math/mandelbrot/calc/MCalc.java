package com.fredwilby.math.mandelbrot.calc;

import com.fredwilby.math.mandelbrot.ui.RDEvent;

public interface MCalc 
{
	double[][] normalizedIterationValues(RDEvent redraw);
}
