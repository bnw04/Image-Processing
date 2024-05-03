package view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The View class represents a view implements the IView interface,
 * allowing it to render messages for now. The View is responsible
 * for displaying messages and output to a specified Appendable object.
 */
public class View implements IView {
  private final Appendable out;

  /**
   * Constructs a View object with an output Appendable.
   *
   * @param out The Appendable object where messages and output will be rendered.
   * @throws IllegalArgumentException if either the m or out parameter is null.
   */
  public View(Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("Constructor cannot be null.");
    }
    this.out = out;
  }

  /**
   * Renders the given message to the output Appendable.
   *
   * @param msg The message to be rendered to this view.
   * @throws IllegalStateException if an I/O error occurs while appending
   *     the message to the output.
   * @throws IllegalArgumentException if the msg parameter is null.
   */
  @Override
  public void showMsg(String msg)
          throws IllegalStateException {
    if (msg == null) {
      throw new IllegalArgumentException("Invalid message.");
    }
    try {
      this.out.append(msg);
    } catch (IOException e) {
      throw new IllegalStateException("Invalid message.");
    }
  }

  @Override
  public void setVisible(boolean value) {
    throw new IllegalStateException("Text view can not be visible.");
  }

  /**
   * This is a simple view to show info to the Appendable, so no listener.
   * Shows no listener message in this view to the output Appendable.
   *
   * @param listener possible listeners to different views.
   * @throws IllegalStateException if an I/O error occurs while appending
   *     the message to the output.
   */
  @Override
  public void addListeners(ViewListener listener) {
    try {
      this.out.append("No listener to this view.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid message.");
    }
  }

  /**
   * Renders the given image to the output Appendable.
   *
   * @param image The image to be rendered to this view.
   * @throws IllegalArgumentException if Null image.
   */
  @Override
  public void draw(BufferedImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Null Image.");
    }
    int width = image.getWidth();
    int height = image.getHeight();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int argb = image.getRGB(j, i);
        Color c = new Color(argb);
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        try {
          this.out.append(r + " " + g + " " + b + "\n");
        } catch (IOException e) {
          throw new IllegalStateException("I/O error occurs.");
        }
      }
    }
  }
}
