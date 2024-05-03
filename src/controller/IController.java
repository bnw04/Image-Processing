package controller;

/**
 * This interface represents a controller responsible for processing commands and managing
 * interactions between the image database model, the view, and the user input.
 */
public interface IController {

  /**
   * Processes commands from the user input and performs corresponding
   * actions on the image database. The processing continues until the user
   * decides to exit.
   */
  void process();
}
