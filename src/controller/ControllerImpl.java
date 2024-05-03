package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.commands.CmdBrighten;
import controller.commands.CmdGaussianBlur;
import controller.commands.CmdGreyscale;
import controller.commands.CmdLoad;
import controller.commands.CmdMatrixBlue;
import controller.commands.CmdMatrixGreen;
import controller.commands.CmdMatrixIntensity;
import controller.commands.CmdMatrixLuma;
import controller.commands.CmdMatrixRed;
import controller.commands.CmdMatrixSepia;
import controller.commands.CmdSave;
import controller.commands.CmdSharpen;
import controller.commands.ICmd;
import model.IImageDatabase;
import model.transformation.GreyscaleBlue;
import model.transformation.GreyscaleGreen;
import model.transformation.GreyscaleIntensity;
import model.transformation.GreyscaleLuma;
import model.transformation.GreyscaleRed;
import model.transformation.GreyscaleValue;
import view.IView;

/**
 * This class implements the IController interface to process user commands
 * and interact with the image database model and the view.
 */
public class ControllerImpl implements IController {
  private final Readable in;
  private final IImageDatabase model;
  private final IView view;
  private final Map<String, ICmd> cmd;

  /**
   * Constructs a ControllerImpl object with the provided image database model,
   * view, and input source. And update a valid command map with valid command
   * names.
   *
   * @param model The image database model to be used for managing image data.
   * @param view The view to display information and messages to the user.
   * @param in The input source where to read user commands from.
   * @throws IllegalArgumentException if any of the constructor arguments is null.
   */
  public ControllerImpl(IImageDatabase model, IView view, Readable in) {
    if (model == null || view == null || in == null) {
      throw new IllegalArgumentException("Constructor argument cannot be null.");
    }

    this.model = model;
    this.view = view;
    this.in = in;

    this.cmd = new HashMap<>();
    this.cmd.putIfAbsent("save", new CmdSave());
    this.cmd.putIfAbsent("load", new CmdLoad());
    this.cmd.putIfAbsent("brighten", new CmdBrighten());
    this.cmd.putIfAbsent("value-component", new CmdGreyscale(new GreyscaleValue()));
    this.cmd.putIfAbsent("luma-component", new CmdGreyscale(new GreyscaleLuma()));
    this.cmd.putIfAbsent("intensity-component", new CmdGreyscale(new GreyscaleIntensity()));
    this.cmd.putIfAbsent("red-component", new CmdGreyscale(new GreyscaleRed()));
    this.cmd.putIfAbsent("green-component", new CmdGreyscale(new GreyscaleGreen()));
    this.cmd.putIfAbsent("blue-component", new CmdGreyscale(new GreyscaleBlue()));
    this.cmd.putIfAbsent("blur", new CmdGaussianBlur());
    this.cmd.putIfAbsent("sharpen", new CmdSharpen());
    this.cmd.putIfAbsent("red-grayscale", new CmdMatrixRed());
    this.cmd.putIfAbsent("green-grayscale", new CmdMatrixGreen());
    this.cmd.putIfAbsent("blue-grayscale", new CmdMatrixBlue());
    this.cmd.putIfAbsent("intensity-grayscale", new CmdMatrixIntensity());
    this.cmd.putIfAbsent("luma-grayscale", new CmdMatrixLuma());
    this.cmd.putIfAbsent("sepia-grayscale", new CmdMatrixSepia());
  }

  /**
   * Processes user commands and performs corresponding actions on the image database.
   * The processing continues until the user decides to exit. And append any information
   * to view when there's command executed or error occurred.
   */
  @Override
  public void process() {
    Scanner scan = new Scanner(this.in);
    while (scan.hasNext()) {

      String command = scan.next();

      if (command.equalsIgnoreCase("exit")) {
        this.view.showMsg("Quitting.");
        return;
      }

      ICmd cmdToRun = this.cmd.getOrDefault(command, null);
      if (cmdToRun == null) {
        this.view.showMsg("Invalid command.\n");
        continue;
      }

      try {
        cmdToRun.execute(scan, this.model);
        this.view.showMsg("Command executed.\n");
      } catch (Exception e) {
        this.view.showMsg(e.getMessage());
      }
    }
  }
}
