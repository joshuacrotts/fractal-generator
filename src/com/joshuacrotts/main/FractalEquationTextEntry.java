package com.joshuacrotts.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import com.revivedstandards.util.StandardComplexNumber;

public class FractalEquationTextEntry extends JTextField implements ActionListener {

  /* Parent containers. */
  private final FractalGenerator FRACTAL_WINDOW;
  private final FractalDrawPanel FRACTAL_PANEL;

  /* Dimension controls. */
  private final int MAX_HEIGHT = 30;
  private final int MAX_WIDTH = 280;

  public FractalEquationTextEntry(FractalGenerator parentWindow, FractalDrawPanel drawPanel) {
    super("Enter Equation (z^2 + c), c = a + bi.");

    this.FRACTAL_WINDOW = parentWindow;
    this.FRACTAL_PANEL = drawPanel;

    super.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
    super.addActionListener(this);
  }

  /**
   * Retrieves the text when the user presses enter, and parses the equation.
   * 
   * @param ActionEvent e
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String text = super.getText();

    if (text != null && !text.equals("")) {
      this.parseEquation(text);
    } else {
      throw new IllegalArgumentException("Cannot parse a null or empty equation.");
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

  /**
   * Parses an equation of the form z^2 + c, where c is in the form a + bi. If
   * there is no complex constant, it is evaluated as the mandelbrot constant in
   * the algorithm.
   * 
   * @param String equation.
   * @throws IllegalArgumentException if the equation is not in the format of the
   *                                  regex.
   */
  private void parseEquation(String equation) {
    // Regex for a complex number.
    Pattern pattern = Pattern.compile("z\\^(\\d)\\s?[+]\\s?(-?\\d*(.\\d+)?)\\s?([+-])\\s?(-?[\\d]*(.[\\d]*)?)i");
    Matcher matcher = pattern.matcher(equation);

    if (!matcher.matches()) {
      throw new IllegalArgumentException("Error! Cannot parse this equation.");
    }

    int exponent = Integer.parseInt(matcher.group(1));
    double real = Double.parseDouble(matcher.group(2));
    String sign = matcher.group(4);
    double imaginary = Double.parseDouble(matcher.group(5));

    // If they have a minus sign, then it flips the second operand.
    // Our invariant is maintaining an equation of a + bi.
    if (sign.equals("-")) {
      imaginary *= -1;
    }

    StandardComplexNumber c = new StandardComplexNumber(real, imaginary);

    if (real == 0 && imaginary == 0) {
      c = null;
    }

    this.FRACTAL_PANEL.getFractal().setExponent(exponent);
    this.FRACTAL_PANEL.getFractal().setComplexNumber(c);
  }
}
