package controller.commands;

import java.util.Scanner;

import model.IImageDatabase;
import model.images.IImageState;
import model.transformation.BrighterDarker;
import model.transformation.ITransformation;

/**
 * This class implements the ICmd interface to apply a brightened or darkened
 * transformation to an image.
 */
public class CmdBrighten implements ICmd {

  /**
   * Executes the command to apply a brightened or darken transformation
   * to an image in the image database.
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

    if (!scan.hasNextInt()) {
      throw new IllegalStateException("Brighten/Darken increment should be an integer.\n");
    }
    int value = scan.nextInt();

    try {
      String idImage = scan.next();
      String dest = scan.next();
      IImageState image = m.getImage(idImage);
      ITransformation brighter = new BrighterDarker(value);
      IImageState newImage = brighter.run(image);
      m.addImage(dest, newImage);
    } catch (Exception e) {
      throw new IllegalStateException("Cannot brighten/darken the image.\n");
    }
  }
}
