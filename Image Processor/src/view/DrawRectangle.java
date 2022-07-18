package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Represents an extension of JPanel in which each rectangle represents one bar of the histogram.
 * In this bar, all 4 of the values for that number is drawn (aka the red, blue, green and
 * intensity bars all overlap each other).
 */
public class DrawRectangle extends JPanel {
  // how tall the red bar should be
  private final int redHeight;
  // how tall the green bar should be
  private final int greenHeight;
  // how tall the blue bar should be
  private final int blueHeight;
  // how tall the intensity bar should be
  private final int intenseHeight;
  // the scale in which the bars should be drawn
  private final double scale;

  /**
   * The constructor for the rectangle.
   *
   * @param redHeight     how tall the red bar should be.
   * @param greenHeight   how tall the green bar should be.
   * @param blueHeight    how tall the blue bar should be.
   * @param intenseHeight how tall the intensity bar should be.
   * @param scale         the scale of the bars so they can fit on the screen.
   * @throws IllegalArgumentException if any of the values given are less that 0.
   */
  public DrawRectangle(int redHeight, int greenHeight, int blueHeight, int intenseHeight,
                       double scale) throws IllegalArgumentException {
    if (redHeight < 0 || greenHeight < 0 || blueHeight < 0 || intenseHeight < 0 || scale <= 0) {
      throw new IllegalArgumentException("None of the arguments can be less that zero");
    }
    this.redHeight = redHeight;
    this.greenHeight = greenHeight;
    this.blueHeight = blueHeight;
    this.intenseHeight = intenseHeight;
    this.scale = scale;
  }

  /**
   * overrides the provided getPreferredSize method to account for the maximum
   * frequency height we are trying to represent.
   * @return a new Dimension object with the updated values.
   */
  @Override
  public Dimension getPreferredSize() {
    int max = Math.max(redHeight, Math.max(greenHeight, Math.max(blueHeight, intenseHeight)));
    return new Dimension(1, (int) (max * scale));
  }

  /**
   * overrides the paintComponent method to draw the histograms
   * based on the bar heights that were given to this class in its constructor.
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    super.paintComponent(g2d);
    g2d.setColor(new Color(0, 0, 0, 128));
    g2d.fillRect(0, 400 - (int) (intenseHeight * scale), 1, (int) (intenseHeight * scale));
    g2d.setColor(new Color(255, 0, 0, 128));
    g2d.fillRect(0, 400 - (int) (redHeight * scale), 1, (int) (redHeight * scale));
    g2d.setColor(new Color(0, 255, 50, 128));
    g2d.fillRect(0, 400 - (int) (greenHeight * scale), 1, (int) (greenHeight * scale));
    g2d.setColor(new Color(0, 50, 250, 128));
    g2d.fillRect(0, 400 - (int) (blueHeight * scale), 1, (int) (blueHeight * scale));

  }
}
