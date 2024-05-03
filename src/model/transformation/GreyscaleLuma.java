package model.transformation;

/**
 * The GreyscaleLuma class represents a transformation that converts
 * an image to greyscale using the luma calculation channel values of each
 * place of the give image when it runs. It extends the AbstractTransformation
 * class and implements the ITransformation interface. The transformation
 * sets the red, green, blue channel values to the luma calculation of the
 * channel values of each place of the given image.
 */
public class GreyscaleLuma extends AbstractTransformation
        implements ITransformation {

  // helper method that adjust red channel values to luma result.
  @Override
  protected int changeToValueR(int r, int g, int b) {
    return (int) Math.round(0.2126 * r + 0.7152 * g + 0.0722 * b);
  }

  // helper method that adjust green channel values to luma result.
  @Override
  protected int changeToValueG(int r, int g, int b) {
    return (int) Math.round(0.2126 * r + 0.7152 * g + 0.0722 * b);
  }

  // helper method that adjust blue channel values to luma result.
  @Override
  protected int changeToValueB(int r, int g, int b) {
    return (int) Math.round(0.2126 * r + 0.7152 * g + 0.0722 * b);
  }
}
