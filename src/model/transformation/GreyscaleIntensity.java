package model.transformation;

/**
 * The GreyscaleIntensity class represents a transformation that converts
 * an image to greyscale using the average channel values of each place of
 * the give image when it runs. It extends the AbstractTransformation class
 * and implements the ITransformation interface. The transformation sets the
 * red, green, blue channel values to the average of the channel values of
 * each place of the given image.
 */
public class GreyscaleIntensity extends AbstractTransformation
        implements ITransformation {

  // helper method that adjust red channel values to average of channel values.
  @Override
  protected int changeToValueR(int r, int g, int b) {
    return (r + g + b) / 3;
  }

  // helper method that adjust green channel values to average of channel values.
  @Override
  protected int changeToValueG(int r, int g, int b) {
    return (r + g + b) / 3;
  }

  // helper method that adjust blue channel values to average of channel values.
  @Override
  protected int changeToValueB(int r, int g, int b) {
    return (r + g + b) / 3;
  }
}
