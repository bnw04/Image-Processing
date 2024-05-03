package controller.commands;

import java.util.Scanner;

import model.IImageDatabase;
import model.images.IImageState;
import model.transformation.ITransformation;
import model.transformation.MatrixGreyscale;

/**
 * The AbstractMatrixCmd class is an abstract class that apply matrix-based
 * grayscale transformations to an image in the image database. It implements
 * the ICmd interface.
 */
public abstract class AbstractMatrixCmd implements ICmd {
  private double[][] kernel;

  /**
   * Constructs an AbstractMatrixCmd object with the specified 3x3 grayscale kernel.
   * The kernel is used for matrix-based grayscale transformations on images.
   *
   * @param kernel The 3x3 double array representing the kernel for the
   *     grayscale transformation.
   * @throws IllegalArgumentException if the kernel is null or its dimensions
   *     are not 3x3.
   */
  public AbstractMatrixCmd(double[][] kernel) {
    if (kernel == null || kernel.length != 3 || kernel[0].length != 3) {
      throw new IllegalArgumentException("Invalid grayscale kernel.");
    }
    this.kernel = kernel;
  }

  /**
   * Applies the matrix-based grayscale transformation to the input image and
   * stores the transformed image in the image database with the specified
   * destination identifier.
   *
   * @param scan The scanner used to read command-line input.
   * @param m The image database model to perform the operation on.
   * @throws IllegalArgumentException if either the scanner or the model is null.
   * @throws IllegalStateException if there's an issue during the execution
   *     of the command.
   */
  @Override
  public void execute(Scanner scan, IImageDatabase m) {
    if (scan == null || m == null) {
      throw new IllegalArgumentException("Null model/command line.");
    }

    try {
      String idImage = scan.next();
      String dest = scan.next();
      IImageState image = m.getImage(idImage);
      ITransformation matrixGray = new MatrixGreyscale(this.kernel);
      IImageState newImage = matrixGray.run(image);
      m.addImage(dest, newImage);
    } catch (Exception e) {
      throw new IllegalStateException("Cannot use kernel to grayscale the image.\n");
    }
  }
}
