package model.images;

/**
 * This interface represents operations that can be used to monitor
 * the state of an image, without changing it.
 */
public interface IImageState {

  /**
   * Return the height of this Image.
   *
   * @return The height of this Image.
   */
  int getHeight();

  /**
   * Return the width of this Image.
   *
   * @return The width of this Image.
   */
  int getWidth();

  /**
   * Return the red channel value of the given position of this image.
   * Valid range of width index and height index is 0 to the width/height
   * of the image minus 1.
   *
   * @param w The width index of the position.
   * @param h The height index of the position.
   * @throws IllegalArgumentException if given value not in the valid range.
   * @throws IllegalStateException if no channel value of the given position.
   */
  int getRedChannel(int w, int h);

  /**
   * Return the green channel value of the given position of this image.
   * Valid range of width index and height index is 0 to the width/height
   * of the image minus 1.
   *
   * @param w The width index of the position.
   * @param h The height index of the position.
   * @throws IllegalArgumentException if given value not in the valid range.
   * @throws IllegalStateException if no channel value of the given position.
   */
  int getGreenChannel(int w, int h);

  /**
   * Return the blue channel value of the given position of this image.
   * Valid range of width index and height index is 0 to the width/height
   * of the image minus 1.
   *
   * @param w The width index of the position.
   * @param h The height index of the position.
   * @throws IllegalArgumentException if given value not in the valid range.
   * @throws IllegalStateException if no channel value of the given position.
   */
  int getBlueChannel(int w, int h);
}
