package com.joshuacrotts.main;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.revivedstandards.util.StandardComplexNumber;
import com.revivedstandards.util.StdOps;

public class Fractal {

  /* Parent window instance. */
  private final FractalGenerator FRACTAL_WINDOW;
  
  /* BufferedImage that we write to when generating the fractal. */
  private BufferedImage fractalImage;
  
  /* To generate a different fractal, the +c has to be modified. */
  private StandardComplexNumber complexNumber;
  
  /* Number of iterations. */
  private final int MAX_ITERATIONS;
  
  public Fractal(FractalGenerator window, int iterations) {
    this(window, iterations, null);
  }
  
  public Fractal(FractalGenerator window, int iterations, StandardComplexNumber c) {
    this.FRACTAL_WINDOW = window;
    this.complexNumber = c;
    this.fractalImage = null;
    
    this.MAX_ITERATIONS = iterations;
  }
  
  /**
   * 
   * @param n
   * @return
   */
  public void mandelbrot(double minComplexX, double maxComplexX, 
                         double minComplexY, double maxComplexY) {
    int[] colorMap = generateColorMap(this.MAX_ITERATIONS);

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
        
        // If it's null, then use the mandelbrot fractal. 
        if (c == null) {
          c = new StandardComplexNumber(xc, yc);
        }

        while (z.abs().getReal() <= 2.0 && iterations < this.MAX_ITERATIONS) {
          z = z.pow(2).add(c);
          iterations++;
        }

        if (iterations < this.MAX_ITERATIONS) {
          fractal.setRGB(x, y, colorMap[iterations]);
        } else {
          fractal.setRGB(x, y, Color.BLACK.getRGB());
        }
      }
    }

    this.fractalImage = fractal;
  }

  /**
   * 
   * @param n
   * @return
   */
  private int[] generateColorMap(int n) {
    int[] colorMap = new int[n];

    for (int i = 0; i < n; i++) {
      colorMap[i] = Color.HSBtoRGB(i / 256f, 1, i / (i + 8f));
    }

    return colorMap;
  }
  
  public int getMaxIterations() {
    return this.MAX_ITERATIONS;
  }
  
  public void setComplexNumber(StandardComplexNumber scn) {
    this.complexNumber = scn;
  }
  
  public BufferedImage getImage() {
    return this.fractalImage;
  }
}
