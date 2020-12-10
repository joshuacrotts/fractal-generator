package com.joshuacrotts.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Stack;

import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.util.StandardComplexNumber;
import com.revivedstandards.util.StdOps;

public class Fractal {

  /**
   * Parent window instance. 
   */
  private final FractalGenerator FRACTAL_WINDOW;

  /**
   * Stack of FractalSerializerObject that we write to when generating the
   * fractal. We always render the top image on the stack.
   */
  private final Stack<FractalSerializerObject> FRACTAL_IMAGES;

  /** 
   * To generate a different fractal, the +c has to be modified. 
   */
  private StandardComplexNumber complexNumber;

  /** 
   * Equation information. 
   */
  private int exponent = 2;

  /** 
   * Number of iterations. 
   */
  private int maxIterations;

  public Fractal(FractalGenerator window, int iterations) {
    this(window, iterations, null);
  }

  public Fractal(FractalGenerator window, int iterations, StandardComplexNumber c) {
    this.FRACTAL_WINDOW = window;
    this.FRACTAL_IMAGES = new Stack<>();

    this.complexNumber = c;
    this.maxIterations = iterations;
  }

  /**
   * Removes the top fractal from the stack.
   */
  public FractalSerializerObject popTopFractal() {
    return this.FRACTAL_IMAGES.pop();
  }

  /**
   * Generates the fractal with the given interval [-x, x], [-y, y]. If the
   * constant c is null, the mandelbrot constant is used.
   * 
   * @param minComplexX
   * @param maxComplexX
   * @param minComplexY
   * @param minComplexY
   */
  public void createFractal(double minComplexX, double maxComplexX, double minComplexY, double maxComplexY) {
    int[] colorMap = generateColorMap(this.maxIterations);

    final int WIDTH = this.FRACTAL_WINDOW.getDrawPanelWidth();
    final int HEIGHT = this.FRACTAL_WINDOW.getDrawPanelWidth();

    BufferedImage fractal = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < WIDTH; x++) {
      for (int y = 0; y < HEIGHT; y++) {
        double xc = StdOps.normalize(x, 0, WIDTH, minComplexX, maxComplexX);
        double yc = StdOps.normalize(y, 0, HEIGHT, minComplexY, maxComplexY);

        StandardComplexNumber z = new StandardComplexNumber(xc, yc);
        StandardComplexNumber c = this.complexNumber;

        // If c is null, then use the default mandelbrot constant.
        if (c == null) {
          c = new StandardComplexNumber(xc, yc);
        }

        int iterations = this.createFractalHelper(z, c);

        // If we are beyond the number of iterations, we color it
        // from our array of colors.
        if (iterations < this.maxIterations) {
          fractal.setRGB(x, y, colorMap[iterations]);
        } else {
          fractal.setRGB(x, y, StandardDraw.BARN_RED.getRGB());
        }
      }
    }

    // Once we generate an image, push it to the stack of previous
    // images. We need to use the old set of complex coordinates.
    this.FRACTAL_IMAGES
        .push(new FractalSerializerObject(this.FRACTAL_WINDOW.getMinComplexX(), this.FRACTAL_WINDOW.getMaxComplexX(),
            this.FRACTAL_WINDOW.getMinComplexY(), this.FRACTAL_WINDOW.getMaxComplexY(), fractal));
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
  private int createFractalHelper(StandardComplexNumber z, StandardComplexNumber c) {
    int iterations = 0;
    while (z.abs().getReal() <= 2.0 && iterations < this.maxIterations) {
      z = z.pow(this.exponent).add(c);
      iterations++;
    }

    return iterations;
  }

  /**
   * Generates n colors to use for detecting "closeness" to the center of the
   * fractal.
   * 
   * @param n
   * @return int[] array of colors.
   */
  private int[] generateColorMap(int n) {
    int[] colorMap = new int[n];

    for (int i = 0; i < n; i++) {
      colorMap[i] = Color.HSBtoRGB(i / 256f, 1.f, i / (i + 8f));
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

  public FractalSerializerObject getTopFractal() {
    return this.FRACTAL_IMAGES.peek();
  }

  public Stack<FractalSerializerObject> getFractals() {
    return this.FRACTAL_IMAGES;
  }

  public boolean hasFractals() {
    return !this.FRACTAL_IMAGES.isEmpty();
  }
}
