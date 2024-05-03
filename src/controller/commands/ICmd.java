package controller.commands;

import java.util.Scanner;

import model.IImageDatabase;

/**
 * This interface represents a command that can be executed to perform specific operations
 * on an image database using a given scanner and an image database model.
 */
public interface ICmd {

  /**
   * Executes the command to perform a specific operation on the image database.
   *
   * @param scan The scanner used to read command-line input.
   * @param model The image database model to perform the operation on.
   * @throws IllegalArgumentException if either the scanner or the model is null.
   * @throws IllegalStateException if there's an issue during the execution of the command.
   */
  void execute(Scanner scan, IImageDatabase model);
}
