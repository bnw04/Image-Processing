package controller.loadersaver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.images.IImage;
import model.images.ImageImpl;

/**
 * The PPMLoader class represents an image loader specifically for PPM files.
 * It implements the IImageLoader interface, PPM files should be in P3 format
 * and contain color information in RGB format.
 */
public class PPMLoader implements IImageLoader {

  private final String filePath;

  /**
   * Constructs a PPMLoader object with the specified file path.
   *
   * @param filePath The path of the PPM file to be loaded.
   * @throws IllegalArgumentException if the filePath parameter is null.
   */
  public PPMLoader(String filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("Constructor cannot be null.");
    }
    this.filePath = filePath;

  }

  /**
   * Loads a PPM image from the specified file
   * and returns its state as an IImageState object.
   *
   * @return The IImageState object representing the state of the loaded image.
   * @throws IllegalStateException if the file is not found or an error occurs
   *     while reading it or the PPM file is not in P3 format.
   */
  @Override
  public IImage loadImage() {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(this.filePath));
    }
    catch (FileNotFoundException e) {
      throw new IllegalStateException("No such file.");
    }
    StringBuilder builder = new StringBuilder();

    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    IImage result = new ImageImpl(width, height);
    int maxValue = sc.nextInt();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        result.setPixel(j, i, r, g, b);
      }
    }
    return result;
  }

}
