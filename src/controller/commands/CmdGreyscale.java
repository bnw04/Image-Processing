package controller.commands;

import java.util.Scanner;

import model.IImageDatabase;
import model.images.IImageState;
import model.transformation.ITransformation;

/**
 * This class implements the ICmd interface to apply a greyscale
 * transformation to an image.
 */
public class CmdGreyscale implements ICmd {
  private final ITransformation s;

  /**
   * Constructs a CmdGreyscale object with the provided transformation.
   *
   * @param s The greyscale transformation to be applied to the image.
   * @throws IllegalArgumentException if transformation is null.
   */
  public CmdGreyscale(ITransformation s) {
    if (s == null) {
      throw new IllegalArgumentException("Constructor cannot be null.");
    }
    this.s = s;
  }

  /**
   * Executes the command to apply a greyscale transformation to an image and add it to
   * the image database.
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
      IImageState newImage = this.s.run(image);
      m.addImage(dest, newImage);
    } catch (Exception e) {
      throw new IllegalStateException("Cannot greyscale the image.\n");
    }
  }
}
