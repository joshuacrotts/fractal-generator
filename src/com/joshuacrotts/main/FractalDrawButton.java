package com.joshuacrotts.main;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class FractalDrawButton extends JButton implements ActionListener {

  /* Parent containers. */
  private final FractalGenerator FRACTAL_WINDOW;
  private final FractalDrawPanel FRACTAL_PANEL;

  public FractalDrawButton(FractalGenerator parentWindow, FractalDrawPanel drawPanel) {
    super("Draw Fractal");

    this.FRACTAL_WINDOW = parentWindow;
    this.FRACTAL_PANEL = drawPanel;

    super.addActionListener(this);
  }

  /**
   * 
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    this.FRACTAL_PANEL.getFractal().mandelbrot(this.FRACTAL_WINDOW.getMinComplexX(),
        this.FRACTAL_WINDOW.getMaxComplexX(), this.FRACTAL_WINDOW.getMinComplexY(),
        this.FRACTAL_WINDOW.getMaxComplexY());
  }

  /**
   * 
   * @param Graphics g
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
