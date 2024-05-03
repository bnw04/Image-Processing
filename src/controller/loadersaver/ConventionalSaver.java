package controller.loadersaver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import model.images.IImageState;

/**
 * The ConventionalSaver class represents an image saver that saves an image
 * in a conventional format, such as PNG, JPEG, or BMP.
 */
public class ConventionalSaver implements IImageSaver {

  private final String pathToSave;
  private final IImageState image;
  private final OutputStream out;

  /**
   * Constructs a ConventionalSaver object with the provided parameters.
   *
   * @param pathToSave The path where the image file will be saved.
   * @param image The IImageState object containing pixel data to be saved.
   * @param out The OutputStream to output the pixel values and message
   *            during saving.
   * @throws IllegalArgumentException if any of the parameters is null.
   */
  public ConventionalSaver(String pathToSave, IImageState image, OutputStream out) {
    if (pathToSave == null || image == null || out == null) {
      throw new IllegalArgumentException("Constructor cannot be null.");
    }
    this.out = out;
    this.pathToSave = pathToSave;
    this.image = image;
  }

  /**
   * Saves the image data in a conventional format to the specified destination.
   *
   * @throws IllegalStateException if there's an issue while saving the image.
   */
  @Override
  public void run() throws IllegalStateException {
    int width = this.image.getWidth();
    int height = this.image.getHeight();
    BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        int r = image.getRedChannel(w, h);
        int g = image.getGreenChannel(w, h);
        int b = image.getBlueChannel(w, h);
        int argb = (r << 16) | (g << 8) | b;
        output.setRGB(w, h, argb);
        writeToHelper(r + " " + g + " " + b + "\n");
      }
    }
    String extension = this.pathToSave.substring(this.pathToSave.indexOf(".") + 1);
    try {
      if (!ImageIO.write(output, extension, new File(this.pathToSave))) {
        throw new IllegalStateException("Cannot save the image.\n");
      }
      writeToHelper("Image " + this.pathToSave + " is saved.");
    } catch (IOException e) {
      throw new IllegalStateException("Cannot save the image.\n");
    }
  }

  // helper
  private void writeToHelper(String content) {
    try {
      this.out.write(content.getBytes());
    } catch (IOException e) {
      throw new IllegalStateException("Fail to append message.");
    }
  }
}
