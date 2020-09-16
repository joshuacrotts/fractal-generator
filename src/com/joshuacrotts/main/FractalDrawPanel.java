package com.joshuacrotts.main;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class FractalDrawPanel extends JPanel {
  
  /* Instance of the parent component. */
  private final FractalGenerator FRACTAL_WINDOW;
  
  /* Fractal object to draw. */
  private Fractal FRACTAL;
  
  /* Fractal zoom control object. */
  private final FractalZoomSelector ZOOM_SELECTOR;
  
  public FractalDrawPanel(FractalGenerator frameWindow, Fractal fractal) {
    this.FRACTAL_WINDOW = frameWindow;
    this.FRACTAL = fractal;
    this.ZOOM_SELECTOR = new FractalZoomSelector(this.FRACTAL_WINDOW, this);
    
    super.addMouseListener(this.ZOOM_SELECTOR);
    super.addMouseMotionListener(this.ZOOM_SELECTOR);
  }
  
  /**
   * 
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    g.drawImage(this.FRACTAL.getImage(), 0, 0, null);
    
    this.ZOOM_SELECTOR.drawRectangle(g);
  }
  
  public Fractal getFractal() {
    return this.FRACTAL;
  }
}
