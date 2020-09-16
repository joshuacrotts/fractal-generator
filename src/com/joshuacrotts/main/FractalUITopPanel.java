package com.joshuacrotts.main;

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class FractalUITopPanel extends JPanel {

  /* Parent JComponents (panel and window). */
  private final FractalGenerator FRACTAL_WINDOW;
  private final FractalDrawPanel FRACTAL_PANEL;
  
  /* UI elements for the panel. */
  private final FractalDrawButton DRAW_FRACTAL_BUTTON;
  private final FractalResetButton RESET_FRACTAL_BUTTON;
  
  public FractalUITopPanel(FractalGenerator parentWindow, FractalDrawPanel drawPanel) {
    this.FRACTAL_WINDOW = parentWindow;
    this.FRACTAL_PANEL = drawPanel;
    
    this.DRAW_FRACTAL_BUTTON = new FractalDrawButton(this.FRACTAL_WINDOW, 
                                                     this.FRACTAL_PANEL);
    
    this.RESET_FRACTAL_BUTTON = new FractalResetButton(this.FRACTAL_WINDOW, 
                                                       this.FRACTAL_PANEL);    
    super.setLayout(new GridLayout(1, 3));
    super.add(this.DRAW_FRACTAL_BUTTON, 0, 0);
    super.add(this.RESET_FRACTAL_BUTTON, 0, 1);
  }
  
  /**
   * Draws all subchildren.
   * @param Graphics g
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
