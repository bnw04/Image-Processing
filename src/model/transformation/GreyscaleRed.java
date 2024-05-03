package model.transformation;

/**
 * The GreyscaleRed class represents a transformation that converts
 * an image to greyscale using the red channel values when it runs. It
 * extends the AbstractTransformation class and implements the
 * ITransformation interface. The transformation sets the red, green,
 * blue channel values to the red channel values of each place of the
 * given image.
 */
public class GreyscaleRed extends AbstractTransformation
        implements ITransformation {

  // helper method that adjust red channel values to red channel value.
  @Override
  protected int changeToValueR(int r, int g, int b) {
    return r;
  }

  // helper method that adjust green channel values to red channel value.
  @Override
  protected int changeToValueG(int r, int g, int b) {
    return r;
  }

  // helper method that adjust blue channel values to red channel value.
  @Override
  protected int changeToValueB(int r, int g, int b) {
    return r;
  }
}
