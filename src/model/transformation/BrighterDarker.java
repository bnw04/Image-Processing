package model.transformation;

/**
 * The BrighterDarker class represents a transformation that adjusts
 * the brightness of an image when it runs. It extends the
 * AbstractTransformation class and implements the ITransformation interface.
 * The transformation adds the specified value to the RGB values of the image,
 * making the image brighter or darker based on the provided value.
 */
public class BrighterDarker extends AbstractTransformation
        implements ITransformation {
  private final int value;

  /**
   * Constructs a BrighterDarker object with the specified value.
   * The value determines the amount by which the RGB values of the image is adjusted.
   *
   * @param value The value to adjust the brightness of the image.
   *     Positive values make the image brighter,
   *     and negative values make it darker.
   */
  public BrighterDarker(int value) {
    this.value = value;
  }

  // helper method that adjust red channel values by value amount.
  @Override
  protected int changeToValueR(int r, int g, int b) {
    return (r + this.value);
  }

  // helper method that adjust green channel values by value amount.
  @Override
  protected int changeToValueG(int r, int g, int b) {
    return (g + this.value);
  }

  // helper method that adjust blue channel values by value amount.
  @Override
  protected int changeToValueB(int r, int g, int b) {
    return (b + this.value);
  }
}
