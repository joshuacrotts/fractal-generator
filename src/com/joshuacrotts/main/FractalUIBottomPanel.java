package com.joshuacrotts.main;

import java.awt.Graphics;

import javax.swing.JPanel;

public class FractalUIBottomPanel extends JPanel {

  /* Parent JComponents (panel and window). */
  private final FractalGenerator FRACTAL_WINDOW;
  private final FractalDrawPanel FRACTAL_PANEL;
  
  /* UI elements for the panel. */
  private final FractalTextEntry FRACTAL_TEXT_ENTRY;
  
  public FractalUIBottomPanel(FractalGenerator parentWindow, FractalDrawPanel drawPanel) {
    this.FRACTAL_WINDOW = parentWindow;
    this.FRACTAL_PANEL = drawPanel;
    this.FRACTAL_TEXT_ENTRY = new FractalTextEntry(parentWindow, drawPanel);
    
    super.add(this.FRACTAL_TEXT_ENTRY);
  }
  
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
