package com.joshuacrotts.main;

import java.awt.BorderLayout;

import com.revivedstandards.platform.StandardSwingApplication;

public class FractalGenerator extends StandardSwingApplication {
  
  /* Default number of iterations. */
  private static final int INITIAL_ITERATIONS = 2000;
  
  /* JComponents and panels for drawing and interacting. */
  private final FractalDrawPanel FRACTAL_PANEL;
  private final FractalUITopPanel FRACTAL_UI_TOP_PANEL;
  private final FractalUIBottomPanel FRACTAL_UI_BOTTOM_PANEL;
  
  /* Fractal instance. */
  private Fractal fractal;

  /* Initial scale for the frame if we want to reset our zoom factor. */
  private final double INITIAL_MIN_COMPLEX = -2.0;
  private final double INITIAL_MAX_COMPLEX = 2.0;
  
  /* Scale of the complex numbers. */
  private double minComplexX = -2.0;
  private double maxComplexX = 2.0;
  private double minComplexY = -2.0;
  private double maxComplexY = 2.0;
  
  public FractalGenerator(int width, int height, int fps, String title) {
    super(width, height, fps, title);
    this.fractal = new Fractal(this, INITIAL_ITERATIONS);
    
    this.FRACTAL_PANEL = new FractalDrawPanel(this, fractal);
    this.FRACTAL_UI_TOP_PANEL = new FractalUITopPanel(this, this.FRACTAL_PANEL);
    this.FRACTAL_UI_BOTTOM_PANEL = new FractalUIBottomPanel(this, this.FRACTAL_PANEL);
    
    super.setFrameLayout(new BorderLayout());
    super.addComponent(this.FRACTAL_UI_TOP_PANEL, BorderLayout.NORTH);
    super.addComponent(this.FRACTAL_UI_BOTTOM_PANEL, BorderLayout.SOUTH);
    super.addComponent(this.FRACTAL_PANEL, BorderLayout.CENTER);

    super.packComponents();
    super.setVisible(true);
  }

  @Override
  public void run() {

  }
  
  /**
   * 
   */
  public void resetZoom() {
    this.minComplexX = this.INITIAL_MIN_COMPLEX;
    this.maxComplexX = this.INITIAL_MAX_COMPLEX;
    
    this.minComplexY = this.INITIAL_MIN_COMPLEX;
    this.maxComplexY = this.INITIAL_MAX_COMPLEX;
    
    this.fractal.setMaxIterations(INITIAL_ITERATIONS);
  }
  
// =================== GETTERS AND SETTERS ====================//
  
  public int getFrameWidth() {
    return super.getFrame().getPreferredSize().width;
  }
  
  public int getFrameHeight() {
    return super.getFrame().getPreferredSize().height;
  }
  
  public int getDrawPanelWidth() {
    return this.FRACTAL_PANEL.getPreferredSize().width;
  }
  
  public int getDrawPanelHeight() {
    return this.FRACTAL_PANEL.getPreferredSize().height;
  }
  
  public double getMinComplexX() {
    return minComplexX;
  }

  public void setMinComplexX(double minComplexX) {
    this.minComplexX = minComplexX;
  }

  public double getMaxComplexX() {
    return maxComplexX;
  }

  public void setMaxComplexX(double maxComplexX) {
    this.maxComplexX = maxComplexX;
  }

  public double getMinComplexY() {
    return minComplexY;
  }

  public void setMinComplexY(double minComplexY) {
    this.minComplexY = minComplexY;
  }

  public double getMaxComplexY() {
    return maxComplexY;
  }

  public void setMaxComplexY(double maxComplexY) {
    this.maxComplexY = maxComplexY;
  }
}
