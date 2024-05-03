package controller.commands;

import java.util.Scanner;

import model.IImageDatabase;
import model.images.IImageState;
import model.transformation.BlurSharpen;
import model.transformation.ITransformation;

/**
 * The AbstractKernelFilterCmd class represents an abstract command that applies a kernel-based
 * filter, blur or sharpen, to an image in the image database. It implements the ICmd
 * interface.
 */
public abstract class AbstractKernelFilterCmd implements ICmd {

  private double[][] kernel;

  /**
   * Constructs an AbstractKernelFilterCmd object with the specified kernel.
   *
   * @param kernel The 2D double array representing the kernel to be used for the filter.
   * @throws IllegalArgumentException if the kernel is null or has invalid dimensions.
   */
  public AbstractKernelFilterCmd(double[][] kernel) {
    if (kernel == null || kernel.length < 3 || kernel.length % 2 == 0) {
      throw new IllegalArgumentException("Invalid filter kernel.");
    }
    this.kernel = kernel;
  }

  /**
   * Executes the command to apply a kernel-based filter to an image in the image database.
   * and add a new IImageState object to model
   *
   * @param scan The scanner used to read command-line input.
   * @param m The image database model to perform the operation on.
   * @throws IllegalArgumentException if either the scanner or the model is null.
   * @throws IllegalStateException if there's an issue during the execution of the command.
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
      ITransformation blurSharpen = new BlurSharpen(this.kernel);
      IImageState newImage = blurSharpen.run(image);
      m.addImage(dest, newImage);
    } catch (Exception e) {
      throw new IllegalStateException("Cannot use kernel to filter the image.\n");
    }
  }
}
