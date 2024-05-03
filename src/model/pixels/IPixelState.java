package model.pixels;

/**
 * This interface represents operations that can be used to monitor
 * the state of a pixel, without changing it.
 */
public interface IPixelState {

  /**
   * Return the red channel value of this pixel. Valid range is 0 to 255.
   *
   * @return The red channel value of this pixel.
   */
  int getR();

  /**
   * Return the green channel value of this pixel. Valid range is 0 to 255.
   *
   * @return The green channel value of this pixel.
   */
  int getG();

  /**
   * Return the blue channel value of this pixel. Valid range is 0 to 255.
   *
   * @return The blue channel value of this pixel.
   */
  int getB();
}
