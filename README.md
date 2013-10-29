Math
====

Mandelbrot
----------

This is the eclipse project containing my Mandelbrot viewer and a few other miscillaneous math programs. 

The mandelbrot viewer (started with `com.fredwilby.math.mandelbrot.ui_legacy.MFrame`) is functional though the AI isn't
very pretty and it's missing a few features (like being able to select the color model for one thing). Rendering is also
somewhat unpolished and in its current state may cause system hangs (because of OpenCL on all processing units). 

A UI rewrite is currently underway that will introduce a more responsive, fully-featured interface with drag-to-pan and 
mousewheel scroll zooming.

Aparapi is currently used for rendering, and the program is untested on systems with non-AMD graphics cards, so performance
and functionality may vary.

These programs are provided under the FreeBSD License, a copy of which is provided in LICENSE.txt.

This program references the following (included) libraries:

Aparapi (with native binaries for Windows 32/64 bit, Linux 34/64 bit, and Mac OSX 64bit)
APFloat
MigLayout 4.0
JAITools



 
