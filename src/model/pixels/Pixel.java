package model.pixels;

/**
 * This class represents a pixel and implementation of the IPixel interface.
 * It offers all the operations mandated by the IPixel interface.
 */
public class Pixel implements IPixel {

  private int red;
  private int green;
  private int blue;

  /**
   * Initializes the pixel with given value of red, green and blue channel.
   * Valid range for all channel values are from 0 to 255.
   *
   * @param red The red channel value of this pixel.
   * @param green The green channel value of this pixel.
   * @param blue The blue channel value of this pixel.
   * @throws IllegalArgumentException if given value not in the valid range.
   */
  public Pixel(int red, int green, int blue) {
    if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
      throw new IllegalArgumentException("Channel value invalid.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getR() {
    return this.red;
  }

  @Override
  public int getG() {
    return this.green;
  }

  @Override
  public int getB() {
    return this.blue;
  }

  @Override
  public void setR(int r) throws IllegalArgumentException {
    if (r < 0 || r > 255) {
      throw new IllegalArgumentException("Invalid red channel value.");
    }
    this.red = r;
  }

  @Override
  public void setG(int g) throws IllegalArgumentException {
    if (g < 0 || g > 255) {
      throw new IllegalArgumentException("Invalid green channel value.");
    }
    this.green = g;
  }

  @Override
  public void setB(int b) throws IllegalArgumentException {
    if (b < 0 || b > 255) {
      throw new IllegalArgumentException("Invalid blue channel value.");
    }
    this.blue = b;
  }
}
