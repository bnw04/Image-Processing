package model.pixels;

/**
 * This interface represents the operations offered by the pixels.
 * It extends IPixelState interface and offers all operations mandated by that.
 * One object of the pixels has values for red, green and blue.
 */
public interface IPixel extends IPixelState {

  /**
   * Set the red channel value to the given value of this pixel.
   * Valid range is 0 to 255.
   *
   * @param r The red channel value to set to.
   * @throws IllegalArgumentException if given value not in the valid range.
   */
  void setR(int r);

  /**
   * Set the green channel value to the given value of this pixel.
   * Valid range is 0 to 255.
   *
   * @param g The green channel value to set to.
   * @throws IllegalArgumentException if given value not in the valid range.
   */
  void setG(int g);

  /**
   * Set the blue channel value to the given value of this pixel.
   * Valid range is 0 to 255.
   *
   * @param b The blue channel value to set to.
   * @throws IllegalArgumentException if given value not in the valid range.
   */
  void setB(int b);
}
