package model.images;

/**
 * This interface represents the operations offered by images. It extends
 * IImageState interface and offers all operations mandated by that.
 * Image object has width and height and red, green, blue
 * channel values of each position stored.
 */
public interface IImage extends IImageState {

  /**
   * Set the red, green, blue channel value to the given value of the given
   * position of this image. Valid range of width index and height index is 0
   * to the width/height of the image minus 1. And the valid range for channel
   * values are from 0 to 255.
   *
   * @param w The width index of the position to set the channel values.
   * @param h The red channel value to set to.
   * @param r The red channel value to set to.
   * @param g The green channel value to set to.
   * @param b The blue channel value to set to.
   * @throws IllegalArgumentException if any given value not in the valid
   *     range. And when the channel value is null.
   */
  void setPixel(int w, int h, int r, int g, int b);
}
