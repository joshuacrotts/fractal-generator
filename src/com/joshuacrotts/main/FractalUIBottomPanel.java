package com.joshuacrotts.main;

import java.awt.Graphics;

import javax.swing.JPanel;

public class FractalUIBottomPanel extends JPanel {

  /* Parent JComponents (panel and window). */
  private final FractalGenerator FRACTAL_WINDOW;
  private final FractalDrawPanel FRACTAL_PANEL;
  
  /* UI elements for the panel. */
  private final FractalEquationTextEntry FRACTAL_EQUATION_TEXT_ENTRY;
  private final FractalIterationTextEntry FRACTAL_ITERATION_TEXT_ENTRY;
  
  public FractalUIBottomPanel(FractalGenerator parentWindow, FractalDrawPanel drawPanel) {
    this.FRACTAL_WINDOW = parentWindow;
    this.FRACTAL_PANEL = drawPanel;
    this.FRACTAL_EQUATION_TEXT_ENTRY = new FractalEquationTextEntry(parentWindow, drawPanel);
    this.FRACTAL_ITERATION_TEXT_ENTRY = new FractalIterationTextEntry(parentWindow, drawPanel);
    
    super.add(this.FRACTAL_EQUATION_TEXT_ENTRY);
    super.add(this.FRACTAL_ITERATION_TEXT_ENTRY);
  }
  
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
