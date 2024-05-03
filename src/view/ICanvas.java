package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * The ICanvas interface represents canvas used for displaying images.
 */
public interface ICanvas {

  /**
   * Paints the component and draws the image on the canvas.
   * This method is called internally and should not be called directly.
   *
   * @param g The Graphics object to draw the image on the canvas.
   */
  void paintComponent(Graphics g);

  /**
   * Sets the image to be displayed on the canvas. This method updates the canvas
   * size based on the dimensions of the provided image and triggers a repaint.
   *
   * @param image The BufferedImage to be displayed on the canvas.
   */
  void setImage(BufferedImage image);
}
