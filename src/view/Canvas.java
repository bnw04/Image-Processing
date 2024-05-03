package view;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * The Canvas class is a custom JPanel used for displaying images. It extends the
 * JPanel class to provide a canvas on which the BufferedImage can be drawn.
 */
public class Canvas extends JPanel implements ICanvas {

  private BufferedImage image;

  /**
   * Constructor for the Canvas class. Sets the preferred size of the canvas to
   * a default value of 600x600 pixels.
   */
  public Canvas() {
    this.setPreferredSize(new Dimension(600, 600));
  }

  /**
   * Sets the image to be displayed on the canvas. This method updates the canvas
   * size based on the dimensions of the provided image and triggers a repaint.
   *
   * @param image The BufferedImage to be displayed on the canvas.
   * @throws IllegalArgumentException if the image is null.
   */
  public void setImage(BufferedImage image) {
    if (image == null) {
      throw new IllegalArgumentException("No image.");
    }
    this.image = image;
    this.setPreferredSize(new Dimension(this.image.getWidth(),
            this.image.getHeight()));
    revalidate();
    repaint();
  }

  /**
   * Paints the component and draws the image on the canvas.
   * This method is called internally and should not be called directly.
   *
   * @param g The Graphics object to draw the image on the canvas.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(this.image, 0, 0, null);
  }
}
