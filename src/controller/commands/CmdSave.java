package controller.commands;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import controller.loadersaver.ConventionalSaver;
import controller.loadersaver.IImageSaver;
import controller.loadersaver.PPMSaver;
import model.IImageDatabase;
import model.images.IImageState;

/**
 * This class implements the ICmd interface to save an image in the PPM or
 * conventional format.
 */
public class CmdSave implements ICmd {

  /**
   * Executes the command to save an image in the PPM or conventional format.
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
      String filename = scan.next();
      String idImage = scan.next();
      IImageState image = m.getImage(idImage);
      String extension = filename.substring(filename.lastIndexOf("."));

      IImageSaver saver;
      if (extension.equalsIgnoreCase(".ppm")) {
        saver = new PPMSaver(filename, image, new StringBuilder());
      } else {
        saver = new ConventionalSaver(filename, image,
                new ByteArrayOutputStream());
      }
      saver.run();
    } catch (Exception e) {
      throw new IllegalStateException("Cannot save the image.\n");
    }
  }
}
