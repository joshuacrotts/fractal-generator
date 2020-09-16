package com.joshuacrotts.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.revivedstandards.util.StdOps;

public class FractalZoomSelector extends MouseAdapter {

  /* Instance of the parent panel. */
  private final FractalDrawPanel FRACTAL_PANEL;
  private final FractalGenerator FRACTAL_WINDOW;

  /* Control variables. */
  private boolean isPressed;

  /* Placement of the rectangle. */
  private int rectX;
  private int rectY;
  private int deltaRectX;
  private int deltaRectY;

  public FractalZoomSelector(FractalGenerator fractalWindow, FractalDrawPanel drawPanel) {
    this.FRACTAL_PANEL = drawPanel;
    this.FRACTAL_WINDOW = fractalWindow;
  }

  /**
   * Draws the rectangle specified by where the user first clicks,
   * to where they are dragging the mouse (this point is the bottom-right
   * of the rectangle).
   * 
   * @param g
   */
  public void drawRectangle(Graphics g) {
    if (this.isPressed) {
      g.setColor(Color.BLUE);
      g.drawRect(this.rectX, this.rectY, this.deltaRectX, this.deltaRectY);
    }
  }

  /**
   * Sets the top-left coordinates of the "drag" rectangle
   * upon pressing the mouse.
   * 
   * @param MouseEvent e
   */
  @Override
  public void mousePressed(MouseEvent e) {
    if (!this.isPressed) {
      this.rectX = e.getX();
      this.rectY = e.getY();
      this.isPressed = true;
    }
  }

  /**
   * Sets the canvas/frame edges to a normalized
   * complex coordinate. Also resets the mouse flags.
   * 
   *  @param MouseEvent e.
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    if (this.isPressed) {
      // Assign dx and dy to the bottom right of our rectangle.
      this.deltaRectX = e.getX();
      this.deltaRectY = e.getY();

      this.normalizeCanvasEdges();
      this.resetMouse();
    }
  }

  /**
   * Not used.
   */
  @Override
  public void mouseMoved(MouseEvent e) {
  }

  /**
   * Calculates the length and width of the rectangle
   * to draw.
   */
  @Override
  public void mouseDragged(MouseEvent e) {
    if (this.isPressed) {
      this.deltaRectX = e.getX() - this.rectX;
      this.deltaRectY = e.getY() - this.rectY;
    }
  }

  /**
   * Normalizes the canvas drawing window starting
   * from the top-left of the rectangle and the bottom-right.
   * These coordinates are normalized to the complex set and 
   * the fractal is re-drawn using these as the min/max values
   * of the canvas (JPanel).
   */
  private void normalizeCanvasEdges() {
    int fw = this.FRACTAL_WINDOW.getWidth();
    int fh = this.FRACTAL_WINDOW.getHeight();

    // Normalize the new edge points of our canvas.
    double newComplexMinX = StdOps.normalize(this.rectX, 0, fw, this.FRACTAL_WINDOW.getMinComplexX(),
        this.FRACTAL_WINDOW.getMaxComplexX());

    double newComplexMaxX = StdOps.normalize(this.deltaRectX, 0, fw, this.FRACTAL_WINDOW.getMinComplexX(),
        this.FRACTAL_WINDOW.getMaxComplexX());

    double newComplexMinY = StdOps.normalize(this.rectY, 0, fh, this.FRACTAL_WINDOW.getMinComplexY(),
        this.FRACTAL_WINDOW.getMaxComplexY());

    double newComplexMaxY = StdOps.normalize(this.deltaRectY, 0, fh, this.FRACTAL_WINDOW.getMinComplexY(),
        this.FRACTAL_WINDOW.getMaxComplexY());

    this.FRACTAL_PANEL.getFractal().mandelbrot(newComplexMinX, newComplexMaxX, newComplexMinY, newComplexMaxY);

    // Set the new edges of our canvas.
    this.FRACTAL_WINDOW.setMinComplexX(newComplexMinX);
    this.FRACTAL_WINDOW.setMaxComplexX(newComplexMaxX);
    this.FRACTAL_WINDOW.setMinComplexY(newComplexMinY);
    this.FRACTAL_WINDOW.setMaxComplexY(newComplexMaxY);
  }
  
  /**
   * Resets all flags on the mouse.
   */
  private void resetMouse() {
    this.isPressed = false;
    this.rectX = 0;
    this.rectY = 0;
    this.deltaRectX = 0;
    this.deltaRectY = 0;
  }
  
  // =================== GETTERS AND SETTERS ====================//
  
  public boolean isPressed() {
    return this.isPressed;
  }
}
