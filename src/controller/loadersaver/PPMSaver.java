package controller.loadersaver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import model.images.IImageState;

/**
 * This class implements the IImageSaver interface to save an image in the PPM format.
 */
public class PPMSaver implements IImageSaver {
  private final String pathToSave;
  private final IImageState image;
  private final Appendable out;

  /**
   * Constructs a PPMSaver object with the provided parameters.
   *
   * @param pathToSave The path where the PPM file will be saved.
   * @param image The image state containing pixel data to be saved to the path.
   * @param out An Appendable object to output of the image pixels values.
   * @throws IllegalArgumentException if any of the parameters is null.
   */
  public PPMSaver(String pathToSave, IImageState image, Appendable out) {
    if (pathToSave == null || image == null || out == null) {
      throw new IllegalArgumentException("Constructor cannot be null.");
    }
    this.out = out;
    this.pathToSave = pathToSave;
    this.image = image;
  }

  /**
   * Saves the image data in the PPM format to the specified destination.
   *
   * @throws IllegalStateException if there's an issue while saving the image.
   */
  @Override
  public void run() throws IllegalStateException {
    BufferedWriter writer;
    try {
      writer = new BufferedWriter(new FileWriter(this.pathToSave));
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }

    try {
      int height = this.image.getHeight();
      int width = this.image.getWidth();
      String start = "P3\n" + width + " " + height + "\n255\n";
      writer.write(start);
      this.out.append(start);
      for (int h = 0; h < height; h++) {
        for (int w = 0; w < width; w++) {
          int r = image.getRedChannel(w, h);
          int g = image.getGreenChannel(w, h);
          int b = image.getBlueChannel(w, h);
          String pixel = r + " " + g + " " + b + "\n";
          writer.write(pixel);
          this.out.append(pixel);
        }
      }
      writer.close();
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
