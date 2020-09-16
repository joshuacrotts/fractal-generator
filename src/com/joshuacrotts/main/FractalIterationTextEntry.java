package com.joshuacrotts.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class FractalIterationTextEntry extends JTextField implements ActionListener {

  /* Parent containers. */
  private final FractalGenerator FRACTAL_WINDOW;
  private final FractalDrawPanel FRACTAL_PANEL;
  
  /* Dimension controls. */
  private final int MAX_HEIGHT = 30;
  private final int MAX_WIDTH = 280;

  public FractalIterationTextEntry(FractalGenerator parentWindow, FractalDrawPanel drawPanel) {
    super("Enter the number of iterations (default=2000)");

    this.FRACTAL_WINDOW = parentWindow;
    this.FRACTAL_PANEL = drawPanel;

    super.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
    super.addActionListener(this);
  }

  /**
   * Retrieves the iterations from the textbox that the user
   * enters after pressing enter. These are set in the Fractal object.
   * 
   * @param ActionEvent e.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String text = super.getText();
    
    int iterations = Integer.parseInt(text);
    
    this.FRACTAL_PANEL.getFractal().setMaxIterations(iterations);
  }

  /**
   * Paints all subchild components.
   * @param Graphics g
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
