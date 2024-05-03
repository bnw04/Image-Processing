package controller.loadersaver;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.images.IImage;
import model.images.IImageState;
import model.images.ImageImpl;

/**
 * The ConventionalLoader class implements the IImageLoader interface to load
 * an image in the conventional format (e.g., JPEG, PNG) into the image database.
 * It implements IImageLoader interface
 */
public class ConventionalLoader implements IImageLoader {

  private final String filePath;

  /**
   * Constructs a ConventionalLoader object with the specified file path.
   *
   * @param filePath The path of the conventional format file to be loaded.
   * @throws IllegalArgumentException if the filePath parameter is null.
   */
  public ConventionalLoader(String filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("Constructor cannot be null.");
    }
    this.filePath = filePath;
  }

  /**
   * Loads an image from the specified file path and creates an IImageState object to
   * represent the loaded image.
   *
   * @return The IImageState object representing the loaded image.
   * @throws IllegalStateException if there is an error during the image loading process.
   */
  @Override
  public IImageState loadImage() {

    BufferedImage input;
    try {
      input = ImageIO.read(new FileInputStream(this.filePath));
    } catch (IOException e) {
      throw new IllegalStateException("Cannot load the image.\n");
    }

    if (input == null) {
      throw new IllegalStateException("Cannot load the image.\n");
    }

    int width = input.getWidth();
    int height = input.getHeight();
    IImage result = new ImageImpl(width, height);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int argb = input.getRGB(j, i);
        Color c = new Color(argb);
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        result.setPixel(j, i, r, g, b);
      }
    }
    return result;
  }
}
