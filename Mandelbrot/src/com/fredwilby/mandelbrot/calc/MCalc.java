package com.fredwilby.mandelbrot.calc;

import com.fredwilby.mandelbrot.ui.RDEvent;

public interface MCalc 
{
	double[][] normalizedIterationValues(RDEvent redraw);
}
