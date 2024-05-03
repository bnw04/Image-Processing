import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import controller.ControllerGraphic;
import controller.ControllerImpl;
import controller.IController;
import model.IImageDatabase;
import model.ImageDatabase;
import view.GraphicView;
import view.View;

/**
 * Main class for Image processing program to start.
 */
public class Main {

  /**
   * The main method that starts the image processing program. If command line argument
   * starts with -file, then run the command line with file name. If command line argument
   * starts with -text, then takes the input from terminal. If command line argument is empty,
   * then run the graphic view of this program.
   *
   * @param args Command-line arguments.
   * @throws IllegalArgumentException if the command line file doesn't exist
   *     or Invalid argument.
   */
  public static void main(String[] args) {
    IImageDatabase m = new ImageDatabase();
    IController c;
    if (args.length == 0) {
      c = new ControllerGraphic(m, new GraphicView());
    } else if (args.length > 1 && args[0].equals("-file")) {
      try {
        FileInputStream file = new FileInputStream(args[1]);
        c = new ControllerImpl(m, new View(System.out), new InputStreamReader(file));
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File not found.");
      }
    } else if (args.length == 1 && args[0].equals("-text")) {
      c = new ControllerImpl(m, new View(System.out), new InputStreamReader(System.in));
    } else {
      throw new IllegalArgumentException("Try again.");
    }
    c.process();
  }
}