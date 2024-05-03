package model.transformation;

import static model.transformation.Channel.BLUE;
import static model.transformation.Channel.GREEN;
import static model.transformation.Channel.RED;

/**
 * The MatrixGreyscale class represents a color transformation that converts
 * an image to greyscale/sepia using a matrix kernel. It extends the
 * AbstractTransformation class and implements the ITransformation interface.
 */
public class MatrixGreyscale extends AbstractTransformation
        implements ITransformation {
  private final double[][] kernel;

  /**
   * Constructs a MatrixGreyscale object with the specified 3x3 matrix kernel.
   *
   * @param kernel The 3x3 matrix kernel used for the greyscale/sepia transformation.
   * @throws IllegalArgumentException if the kernel is null or not of size 3x3.
   */
  public MatrixGreyscale(double[][] kernel) {
    if (kernel == null || kernel.length != 3 || kernel[0].length != 3) {
      throw new IllegalArgumentException("Invalid filter kernel.");
    }
    this.kernel = kernel;
  }

  // helper method using color to get the array number to multiply each color
  // channel value by definition of the matrix color transformation.
  // Red is 0, green is 1, and blue is 2, and multiply each color channel value.
  private int applyMatrix(double[][] kernel, Channel color, int r, int g, int b) {
    int array = getArrayNum(color);
    return (int) Math.round(kernel[array][0] * r + kernel[array][1] * g
            + kernel[array][2] * b);
  }

  // helper method to get the array number to multiply to for the matrix
  private int getArrayNum(Channel color) {
    if (color.equals(RED)) {
      return 0;
    } else if (color.equals(GREEN)) {
      return 1;
    } else if (color.equals(BLUE)) {
      return 2;
    } else {
      throw new IllegalArgumentException("Not valid color");
    }
  }

  @Override
  protected int changeToValueR(int r, int g, int b) {
    return  applyMatrix(this.kernel, RED, r, g, b);
  }

  @Override
  protected int changeToValueG(int r, int g, int b) {
    return applyMatrix(this.kernel, GREEN, r, g, b);
  }

  @Override
  protected int changeToValueB(int r, int g, int b) {
    return applyMatrix(this.kernel, BLUE, r, g, b);
  }
}
