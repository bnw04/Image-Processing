package model.images;

import model.pixels.IPixel;
import model.pixels.Pixel;

/**
 * This class represents an Image and implementation of the IImage interface.
 * It offers all the operations mandated by the IImage interface.
 */
public class ImageImpl implements IImage {
  private final IPixel[][] data;
  private final int width;
  private final int height;

  /**
   * Initializes the Image with given value of width and height of the image.
   * Valid width and height are positive integers.
   *
   * @param width The width of the image.
   * @param height The height of the image.
   * @throws IllegalArgumentException if given value not in the valid.
   */
  public ImageImpl(int width, int height) throws IllegalArgumentException {
    if (width < 1 || height < 1) {
      throw new IllegalArgumentException("Image size invalid.");
    }
    this.data = new IPixel[width][height];
    this.width = width;
    this.height = height;
  }

  @Override
  public void setPixel(int wIndex, int hIndex, int r, int g, int b)
          throws IllegalArgumentException {
    checkBounds(wIndex, hIndex);
    this.data[wIndex][hIndex] = new Pixel(r, g, b);
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  // helper method
  private void checkBounds(int w, int h) throws IllegalArgumentException {
    if ( w < 0 || w >= this.width || h < 0 || h >= this.height ) {
      throw new IllegalArgumentException("Invalid position.");
    }
  }

  @Override
  public int getRedChannel(int w, int h) throws IllegalStateException {
    checkBounds(w, h);
    try {
      return this.data[w][h].getR();
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Image of this position " +
              "has no red value.");
    }
  }

  @Override
  public int getGreenChannel(int w, int h) throws IllegalStateException {
    checkBounds(w, h);
    try {
      return this.data[w][h].getG();
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Image of this position " +
              "has no green value.");
    }
  }

  @Override
  public int getBlueChannel(int w, int h) throws IllegalStateException {
    checkBounds(w, h);
    try {
      return this.data[w][h].getB();
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Image of this position " +
              "has no blue value.");
    }
  }
}
