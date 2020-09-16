package com.joshuacrotts.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

import com.revivedstandards.util.StandardComplexNumber;
import com.revivedstandards.util.StdOps;

public class Fractal {

  /* Parent window instance. */
  private final FractalGenerator FRACTAL_WINDOW;
  
  /* BufferedImage that we write to when generating the fractal. */
  private BufferedImage fractalImage;
  
  /* Equation information. */
  private int exponent;

  /* To generate a different fractal, the +c has to be modified. */
  private StandardComplexNumber complexNumber;
  
  /* Number of iterations. */
  private int maxIterations;
  
  public Fractal(FractalGenerator window, int iterations) {
    this(window, iterations, null);
  }
  
  public Fractal(FractalGenerator window, int iterations, StandardComplexNumber c) {
    this.FRACTAL_WINDOW = window;
    this.complexNumber = c;
    this.fractalImage = null;
    this.exponent = 2;
    
    this.maxIterations = iterations;
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
    int[] colorMap = generateColorMap(this.maxIterations);

    final int WIDTH = this.FRACTAL_WINDOW.getWidth();
    final int HEIGHT = this.FRACTAL_WINDOW.getHeight();
        
    BufferedImage fractal = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    
    for (int x = 0; x < WIDTH; x++) {
      for (int y = 0; y < HEIGHT; y++) {
        int iterations = 0;

        double xc = StdOps.normalize(x, 0, WIDTH, minComplexX, maxComplexX);
        double yc = StdOps.normalize(y, 0, HEIGHT, minComplexY, maxComplexY);

        StandardComplexNumber z = new StandardComplexNumber(xc, yc);
        StandardComplexNumber c = this.complexNumber;
        
        // If c is null, then use the default mandelbrot constant.
        if (c == null) {
          c = new StandardComplexNumber(xc, yc);
        }

        // Make sure we're still inside the interval for the fractal.
        while (z.getReal() * z.getReal() + z.getImaginary() * z.getImaginary() <= 4.0 && iterations < this.maxIterations) {
          z = z.pow(this.exponent).add(c);
          iterations++;
        }
        
        // If we are beyond the number of iterations, we color it
        // from our array of colors.
        if (iterations < this.maxIterations) {
          fractal.setRGB(x, y, colorMap[iterations]);
        } else {
          fractal.setRGB(x, y, Color.BLACK.getRGB());
        }
      }
    }

    this.fractalImage = fractal;
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
  
  public BufferedImage getImage() {
    return this.fractalImage;
  }
}
