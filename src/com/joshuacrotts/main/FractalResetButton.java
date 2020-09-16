package com.joshuacrotts.main;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class FractalResetButton extends JButton implements ActionListener {

  /* Parent containers. */
  private final FractalGenerator FRACTAL_WINDOW;
  private final FractalDrawPanel FRACTAL_PANEL;

  public FractalResetButton(FractalGenerator parentWindow, FractalDrawPanel drawPanel) {
    super("Reset Zoom");

    this.FRACTAL_WINDOW = parentWindow;
    this.FRACTAL_PANEL = drawPanel;

    super.addActionListener(this);
  }

  /**
   * After pressing the button, the zoom status is reset.
   * To redraw the fractal at the default camera view,
   * press the draw button again.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    this.FRACTAL_WINDOW.resetZoom();
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
