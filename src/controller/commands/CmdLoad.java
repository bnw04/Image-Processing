package controller.commands;

import java.util.Scanner;

import controller.loadersaver.ConventionalLoader;
import controller.loadersaver.IImageLoader;
import controller.loadersaver.PPMLoader;
import model.IImageDatabase;
import model.images.IImageState;

/**
 * This class implements the ICmd interface to load an image in the image file
 * into the image database.
 */
public class CmdLoad implements ICmd {

  /**
   * Executes the command to load an image in image file into the image database.
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
      String extension = filename.substring(filename.lastIndexOf("."));
      IImageLoader loader;
      if (extension.equalsIgnoreCase(".ppm")) {
        loader = new PPMLoader(filename);
      } else {
        loader = new ConventionalLoader(filename);
      }
      IImageState image = loader.loadImage();
      m.addImage(idImage, image);
    } catch (Exception e) {
      throw new IllegalStateException("Cannot load the image.\n");
    }
  }
}
