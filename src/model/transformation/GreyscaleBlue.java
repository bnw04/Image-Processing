package model.transformation;

/**
 * The GreyscaleBlue class represents a transformation that converts
 * an image to greyscale using the blue channel values of each place of
 * the give image when it runs. It extends the AbstractTransformation
 * class and implements the ITransformation interface. The transformation
 * sets the red and green channel values to the blue channel values.
 */
public class GreyscaleBlue extends AbstractTransformation
        implements ITransformation {

  // helper method that adjust red channel values to blue channel value.
  @Override
  protected int changeToValueR(int r, int g, int b) {
    return b;
  }

  // helper method that adjust green channel values to blue channel value.
  @Override
  protected int changeToValueG(int r, int g, int b) {
    return b;
  }

  // helper method that adjust blue channel values to blue channel value.
  @Override
  protected int changeToValueB(int r, int g, int b) {
    return b;
  }
}
