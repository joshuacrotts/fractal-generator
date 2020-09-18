package com.joshuacrotts.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Stack;

import com.revivedstandards.util.StandardComplexNumber;
import com.revivedstandards.util.StdOps;

public class Fractal {

  /* Threads for multi-threaded rendering. */
  private final Thread[] THREADS;

  /* Parent window instance. */
  private final FractalGenerator FRACTAL_WINDOW;

  /*
   * Stack of FractalSerializerObject that we write to when generating the
   * fractal. We always render the top image on the stack.
   */
  private final Stack<FractalSerializerObject> FRACTAL_IMAGES;

  /* To generate a different fractal, the +c has to be modified. */
  private StandardComplexNumber complexNumber;

  /* BufferedImage that each thread writes to. */
  private BufferedImage fractal;

  /* Color map for the edges of the fractal. */
  private final int[] colorMap;

  /* Max number of threads to use for parallelization. */
  private final int MAX_THREADS = 50;

  /* Equation information. */
  private int exponent = 2;

  /* Number of iterations. */
  private int maxIterations;

  public Fractal(FractalGenerator window, int iterations) {
    this(window, iterations, null);
  }

  public Fractal(FractalGenerator window, int iterations, StandardComplexNumber c) {
    this.FRACTAL_WINDOW = window;
    this.FRACTAL_IMAGES = new Stack<>();
    this.THREADS = new Thread[MAX_THREADS];

    this.complexNumber = c;
    this.maxIterations = iterations;
    this.colorMap = generateColorMap(this.maxIterations);

    this.fractal = new BufferedImage(this.FRACTAL_WINDOW.getWidth(), this.FRACTAL_WINDOW.getHeight(),
        BufferedImage.TYPE_INT_RGB);
  }

  /**
   * 
   * @param minComplexX
   * @param maxComplexX
   * @param minComplexY
   * @param maxComplexY
   */
  public void createFractal(double minComplexX, double maxComplexX, double minComplexY, double maxComplexY) {
    // Spin up the threads.
    for (int i = 0; i < this.THREADS.length; i++) {
      this.THREADS[i] = new FractalThread(this.FRACTAL_WINDOW, this, i, MAX_THREADS, minComplexX, maxComplexX,
          minComplexY, maxComplexY);
      this.THREADS[i].start();
    }

    // Join the threads (force them to wait) if they are not done.
    this.joinThreads();
    
    // Saves the previous fractal data so we can use it if we want to
    // undo.
    this.FRACTAL_IMAGES.push(new FractalSerializerObject(
        this.FRACTAL_WINDOW.getMinComplexX(), this.FRACTAL_WINDOW.getMaxComplexX(), 
        this.FRACTAL_WINDOW.getMinComplexY(), this.FRACTAL_WINDOW.getMaxComplexY(), 
        FractalUtils.deepCopyImage(fractal)));
  }

  /**
   * Removes the top fractal from the stack.
   */
  public FractalSerializerObject popTopFractal() {
    return this.FRACTAL_IMAGES.pop();
  }

  /**
   * If the threads are still active, we don't proceed.
   * This is a slightly faster version of Thread.join();
   * 
   * @param void.
   * 
   * @return void.
   */
  private synchronized void joinThreads() {
    boolean join = false;
    do {
      join = true;
      for (int i = 0; i < this.THREADS.length; i++) {
        if (this.THREADS[i].isAlive()) {
          join = false;
          break;
        }
      }
    } while (!join);
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

  public FractalSerializerObject getTopFractal() {
    return this.FRACTAL_IMAGES.peek();
  }

  public Stack<FractalSerializerObject> getFractals() {
    return this.FRACTAL_IMAGES;
  }

  public boolean hasFractals() {
    return !this.FRACTAL_IMAGES.isEmpty();
  }

  /**
   * This class spawns a thread for the fractal to use during the generation.
   */
  private class FractalThread extends Thread {
    
    /* Parent window and fractal objects. */
    private final FractalGenerator FRACTAL_WINDOW;
    private final Fractal FRACTAL;

    /* The rows that the iterator will start, and end at. */
    private final int MIN_ROW;
    private final int MAX_ROW;

    /* Edges of the canvas for the fractal. */
    private double minComplexX, maxComplexX, minComplexY, maxComplexY;

    public FractalThread(FractalGenerator fractalWindow, Fractal fractal, int id, int maxThreads, double minX,
        double maxX, double minY, double maxY) {
      this.FRACTAL_WINDOW = fractalWindow;
      this.FRACTAL = fractal;
      this.MIN_ROW = ((id * this.FRACTAL_WINDOW.getHeight()) / maxThreads);
      this.MAX_ROW = (((id + 1) * this.FRACTAL_WINDOW.getHeight()) / maxThreads);
      this.minComplexX = minX;
      this.maxComplexX = maxX;
      this.minComplexY = minY;
      this.maxComplexY = maxY;
    }

    @Override
    public void run() {
      this.generateFractal(minComplexX, maxComplexX, minComplexY, maxComplexY);
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
    private synchronized void generateFractal(double minComplexX, double maxComplexX, 
                                              double minComplexY, double maxComplexY) {
      final int WIDTH = this.FRACTAL_WINDOW.getWidth();
      final int HEIGHT = this.FRACTAL_WINDOW.getHeight();

      for (int x = MIN_ROW; x < MAX_ROW; x++) {
        for (int y = 0; y < HEIGHT; y++) {
          double xc = StdOps.normalize(x, 0, WIDTH, minComplexX, maxComplexX);
          double yc = StdOps.normalize(y, 0, HEIGHT, minComplexY, maxComplexY);

          StandardComplexNumber z = new StandardComplexNumber(xc, yc);
          StandardComplexNumber c = this.FRACTAL.complexNumber;

          // If c is null, then use the default mandelbrot constant.
          if (c == null) {
            c = new StandardComplexNumber(xc, yc);
          }

          int iterations = this.generateFractalHelper(z, c);

          // If we are beyond the number of iterations, we color it
          // from our array of colors.
          if (iterations < this.FRACTAL.maxIterations) {
            fractal.setRGB(x, y, this.FRACTAL.colorMap[iterations]);
          } else {
            fractal.setRGB(x, y, Color.BLACK.getRGB());
          }
        }
      }
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
    private int generateFractalHelper(StandardComplexNumber z, StandardComplexNumber c) {
      int iterations = 0;
      while (z.getReal() * z.getReal() + z.getImaginary() * z.getImaginary() <= 4.0
          && iterations < this.FRACTAL.maxIterations) {
        z = z.pow(this.FRACTAL.exponent).add(c);
        iterations++;
      }

      return iterations;
    }
  }
}
