package com.joshuacrotts.main;

import java.awt.image.BufferedImage;

public class FractalSerializerObject {

  /* Image that has the image data for the fractal. */
  private final BufferedImage image;
  
  /* Coordinates of the top-left and bottom-right of the fractal. */
  private final double MIN_COMPLEX_X;
  private final double MIN_COMPLEX_Y;
  private final double MAX_COMPLEX_X;
  private final double MAX_COMPLEX_Y;
  
  public FractalSerializerObject(double minComplexX, double maxComplexX,
                                 double minComplexY, double maxComplexY,
                                 BufferedImage image) {
    this.image = image;
    this.MIN_COMPLEX_X = minComplexX;
    this.MIN_COMPLEX_Y = minComplexY;
    this.MAX_COMPLEX_X = maxComplexX;
    this.MAX_COMPLEX_Y = maxComplexY;
  }
  
// =================== GETTERS AND SETTERS ====================//  
  
  public BufferedImage getImage() {
    return this.image;
  }

  public double getMinComplexX() {
    return MIN_COMPLEX_X;
  }

  public double getMinComplexY() {
    return MIN_COMPLEX_Y;
  }

  public double getMaxComplexX() {
    return MAX_COMPLEX_X;
  }

  public double getMaxComplexY() {
    return MAX_COMPLEX_Y;
  }
}
