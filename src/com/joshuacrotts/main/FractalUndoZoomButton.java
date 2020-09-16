package com.joshuacrotts.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.JButton;

public class FractalUndoZoomButton extends JButton implements ActionListener {

  /* Parent containers. */
  private final FractalGenerator FRACTAL_WINDOW;
  private final FractalDrawPanel FRACTAL_PANEL;

  public FractalUndoZoomButton(FractalGenerator fractalWindow, FractalDrawPanel drawPanel) {
    super("Undo Zoom");
    
    this.FRACTAL_WINDOW = fractalWindow;
    this.FRACTAL_PANEL = drawPanel;
    
    this.addActionListener(this);
  }
  
  /**
   * 
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    Fractal fractal = this.FRACTAL_PANEL.getFractal();
    
    if (fractal.hasFractals()) {
      FractalSerializerObject topFractal = fractal.popTopFractal();
      
      this.FRACTAL_WINDOW.setMinComplexX(topFractal.getMinComplexX());
      this.FRACTAL_WINDOW.setMaxComplexX(topFractal.getMaxComplexX());
      this.FRACTAL_WINDOW.setMinComplexY(topFractal.getMinComplexY());
      this.FRACTAL_WINDOW.setMaxComplexY(topFractal.getMaxComplexY());
    }
  }
}
