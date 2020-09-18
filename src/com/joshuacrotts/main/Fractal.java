package com.joshuacrotts.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Stack;

import com.revivedstandards.util.StandardComplexNumber;
import com.revivedstandards.util.StdOps;

public class Fractal {

  /* Parent window instance. */
  private final FractalGenerator FRACTAL_WINDOW;
  
  /* Stack of FractalSerializerObject that we write to when generating the fractal.
   * We always render the top image on the stack. */
  private final Stack<FractalSerializerObject> FRACTAL_IMAGES;

  /* To generate a different fractal, the +c has to be modified. */
  private StandardComplexNumber complexNumber;
  
  /* Equation information. */
  private int exponent = 2;
  
  /* Number of iterations. */
  private int maxIterations;
  
  private Thread[] threads;
  
  private int maxThreads = 60;
  private BufferedImage fractal;
  public Fractal(FractalGenerator window, int iterations) {
    this(window, iterations, null);
    threads = new Thread[maxThreads];
  }
  
  public Fractal(FractalGenerator window, int iterations, StandardComplexNumber c) {
    this.FRACTAL_WINDOW = window;
    this.FRACTAL_IMAGES = new Stack<>();
    
    this.complexNumber = c;
    this.maxIterations = iterations;
    fractal = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
  }
  
  public void mandelbrot(double minComplexX, double maxComplexX, 
          double minComplexY, double maxComplexY) {
	  
	  for (int i = 0; i < threads.length; i++) {
		  threads[i] = new FractalThread(this.FRACTAL_WINDOW, this, i, maxThreads,
				  					     minComplexX, maxComplexX, minComplexY, maxComplexY);
		  threads[i].start();
	  }
	  
	 boolean join = false;
	 do {
		 join = true;
		 for (int i = 0; i < threads.length; i++) {
			 if (threads[i].isAlive()) {
				 join = false;
				 break;
			 }
		 }
	 } while (!join);
	 
	    this.FRACTAL_IMAGES.push(new FractalSerializerObject(
		        this.FRACTAL_WINDOW.getMinComplexX(),
		        this.FRACTAL_WINDOW.getMaxComplexX(), 
		        this.FRACTAL_WINDOW.getMinComplexY(),
		        this.FRACTAL_WINDOW.getMaxComplexY(), fractal));
  }
  
  /** 
   * Removes the top fractal from the stack. 
   */
  public FractalSerializerObject popTopFractal() {
    return this.FRACTAL_IMAGES.pop();
  }
  
  
// =================== GETTERS AND SETTERS ====================//
  
  public int getMaxIterations() {
    return this.maxIterations;
  }
  
  public void setMaxIterations(int iterations) {
    this.maxIterations = iterations;
  }
  
  public void setComplexNumber(StandardComplexNumber scn) {
    this.complexNumber = scn;
  }
  
  public void setExponent(int exp) {
	  this.exponent = exp;
  }
  
  public FractalSerializerObject getTopFractal() {
    return this.FRACTAL_IMAGES.peek();
  }

  public Stack<FractalSerializerObject> getFractals() {
    return this.FRACTAL_IMAGES;
  }
  
  public boolean hasFractals() {
    return !this.FRACTAL_IMAGES.isEmpty();
  }
  
  private class FractalThread extends Thread {
	  int minRow;
	  int maxRow;
	  
	  private final FractalGenerator FRACTAL_WINDOW;
	  private final Fractal FRACTAL;
	  
	  private double minComplexX, maxComplexX, minComplexY, maxComplexY;
	  
	  public FractalThread(FractalGenerator fractalWindow, Fractal fractal, int id, int maxThreads,
			  			   double minX, double maxX, double minY, double maxY) {
		  this.FRACTAL_WINDOW = fractalWindow;
		  this.FRACTAL = fractal;
		  this.minRow = ((id * this.FRACTAL_WINDOW.getHeight()) / maxThreads);
		  this.maxRow = (((id + 1) * this.FRACTAL_WINDOW.getHeight()) / maxThreads);
		  this.minComplexX = minX;
		  this.maxComplexX = maxX;
		  this.minComplexY = minY;
		  this.maxComplexY = maxY;
	  }
	  
	  @Override
	  public void run() {
		  this.mandelbrot(minComplexX, maxComplexX, minComplexY, maxComplexY);
	  }
	  
	  /**
	   * Generates the fractal with the given interval [-x, x],
	   * [-y, y]. If the constant c is null, the mandelbrot constant
	   * is used.
	   * 
	   * @param minComplexX
	   * @param maxComplexX
	   * @param minComplexY
	   * @param minComplexY
	   */
	  public void mandelbrot(double minComplexX, double maxComplexX, 
	                         double minComplexY, double maxComplexY) {
	    int[] colorMap = generateColorMap(this.FRACTAL.maxIterations);

	    final int WIDTH = this.FRACTAL_WINDOW.getWidth();
	    final int HEIGHT = this.FRACTAL_WINDOW.getHeight();
	        

	    
	    for (int x = minRow; x < maxRow; x++) {
	      for (int y = 0; y < HEIGHT; y++) {
	        double xc = StdOps.normalize(x, 0, WIDTH, minComplexX, maxComplexX);
	        double yc = StdOps.normalize(y, 0, HEIGHT, minComplexY, maxComplexY);

	        StandardComplexNumber z = new StandardComplexNumber(xc, yc);
	        StandardComplexNumber c = this.FRACTAL.complexNumber;
	        
	        // If c is null, then use the default mandelbrot constant.
	        if (c == null) {
	          c = new StandardComplexNumber(xc, yc);
	        }

	        int iterations = this.fractalHelper(z, c);
	        
	        // If we are beyond the number of iterations, we color it
	        // from our array of colors.
	        if (iterations < this.FRACTAL.maxIterations) {
	          fractal.setRGB(x, y, colorMap[iterations]);
	        } else {
	          fractal.setRGB(x, y, Color.BLACK.getRGB());
	        }
	      }
	    }

//	    // Once we generate an image, push it to the stack of previous
//	    // images. We need to use the old set of complex coordinates.
	  }
	  
	  /**
	   * Generates the number of iterations. Makes sure that we stay inside the 
	   * fractal set of points.
	   * 
	   * @param z
	   * @param c
	   * @param n
	   * @return 
	   */
	  private int fractalHelper(StandardComplexNumber z, StandardComplexNumber c) {
	    int iterations = 0;
	    while (z.getReal() * z.getReal() + z.getImaginary() * z.getImaginary() <= 4.0 
	            && iterations < this.FRACTAL.maxIterations) {
	      z = z.pow(this.FRACTAL.exponent).add(c);
	      iterations++;
	    }
	    
	    return iterations;
	  }

	  /**
	   * Generates n colors to use for detecting "closeness" to the
	   * center of the fractal. 
	   * 
	   * @param n
	   * @return int[] array of colors.
	   */
	  private int[] generateColorMap(int n) {
	    int[] colorMap = new int[n];

	    for (int i = 0; i < n; i++) {
	      colorMap[i] = Color.HSBtoRGB(i / 256f, 1, i / (i + 8f));
	    }

	    return colorMap;
	  }
  }
}
