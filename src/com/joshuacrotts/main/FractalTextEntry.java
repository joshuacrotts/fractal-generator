package com.joshuacrotts.main;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import com.revivedstandards.util.StandardComplexNumber;

public class FractalTextEntry extends JTextField implements ActionListener {

  /* Parent containers. */
  private final FractalGenerator FRACTAL_WINDOW;
  private final FractalDrawPanel FRACTAL_PANEL;

  public FractalTextEntry(FractalGenerator parentWindow, FractalDrawPanel drawPanel) {
    super("Enter Complex Number (c):");

    this.FRACTAL_WINDOW = parentWindow;
    this.FRACTAL_PANEL = drawPanel;

    super.addActionListener(this);
  }

  /**
   * 
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String text = super.getText();
    
    if (text != null && !text.equals("")) {
      StandardComplexNumber c = StandardComplexNumber.parseComplexNumber(text);
      this.FRACTAL_PANEL.getFractal().setComplexNumber(c);      
    } else {
      this.FRACTAL_PANEL.getFractal().setComplexNumber(null);
    }
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
