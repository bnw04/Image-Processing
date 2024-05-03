package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.commands.CmdBrighten;
import controller.commands.CmdGaussianBlur;
import controller.commands.CmdGreyscale;
import controller.commands.CmdMatrixBlue;
import controller.commands.CmdMatrixGreen;
import controller.commands.CmdMatrixIntensity;
import controller.commands.CmdMatrixLuma;
import controller.commands.CmdMatrixRed;
import controller.commands.CmdMatrixSepia;
import controller.commands.CmdSharpen;
import controller.commands.ICmd;
import controller.loadersaver.ConventionalLoader;
import controller.loadersaver.ConventionalSaver;
import controller.loadersaver.IImageLoader;
import controller.loadersaver.IImageSaver;
import controller.loadersaver.PPMLoader;
import controller.loadersaver.PPMSaver;
import model.IImageDatabase;
import model.images.IImageState;
import model.transformation.GreyscaleBlue;
import model.transformation.GreyscaleGreen;
import model.transformation.GreyscaleIntensity;
import model.transformation.GreyscaleLuma;
import model.transformation.GreyscaleRed;
import model.transformation.GreyscaleValue;
import view.IView;
import view.ViewListener;


/**
 * The ControllerGraphic class implements the IController interface and acts as a controller
 * for the graphical view. It listens to events from the view
 * (implementing the ViewListener interface) and processes the user commands to modify
 * the image using various image processing commands.
 */
public class ControllerGraphic implements IController, ViewListener {

  private final IImageDatabase model;
  private final IView view;
  private final Map<String, ICmd> cmd;
  private String imageID;

  /**
   * Constructor for the ControllerGraphic class.
   *
   * @param model The IImageDatabase representing the image database for image processing.
   * @param view The IView representing the graphical view with the user interface.
   * @throws IllegalArgumentException if either model or view is null.
   */
  public ControllerGraphic(IImageDatabase model, IView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Constructor argument cannot be null.");
    }

    this.model = model;
    this.view = view;

    // adds the valid commands to map
    this.cmd = new HashMap<>();
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
   * Starts the image processing flow and handles user interactions with the view.
   */
  @Override
  public void process() {
    this.view.addListeners(this);
    this.view.setVisible(true);
  }

  // helper method to run the string of commands with commands object
  private void processRun(String commands) {
    Scanner scan = new Scanner(commands);
    while (scan.hasNext()) {
      String command = scan.next();

      ICmd cmdToRun = this.cmd.getOrDefault(command, null);
      if (cmdToRun == null) {
        this.view.showMsg("Invalid command.\n");
        continue;
      }
      cmdToRun.execute(scan, this.model);
    }
  }

  // helper method to show the error message accordingly when running the command
  // and ask this view to show the according image.
  private void handleEvent(String command) {
    try {
      processRun(command);
      this.view.draw(createImage(this.imageID));
    } catch (Exception e) {
      this.view.showMsg(e.getMessage());
    }
  }

  // helper method to create a BufferedImage from the IImageState associated with
  // id of the image in this model
  private BufferedImage createImage(String id) {
    IImageState image = this.model.getImage(id);
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        int r = image.getRedChannel(w, h);
        int g = image.getGreenChannel(w, h);
        int b = image.getBlueChannel(w, h);
        int argb = (r << 16) | (g << 8) | b;
        output.setRGB(w, h, argb);
      }
    }
    return output;
  }

  /**
   * Handles the event when an image is requested to be loaded with the give
   * file path.
   *
   * @param filepath The file path of the image to be loaded.
   * @throws IllegalArgumentException if Null file path.
   */
  @Override
  public void handleLoadEvent(String filepath) {
    if (filepath == null) {
      throw new IllegalArgumentException("No filepath.");
    }

    // initialize the imageID for the current showing image
    // since only one image will be shown on the view, no need to change
    // in following commands
    this.imageID = "image";
    String extension = filepath.substring(filepath.lastIndexOf("."));
    IImageLoader loader;
    try {
      if (extension.equalsIgnoreCase(".ppm")) {
        loader = new PPMLoader(filepath);
      } else {
        loader = new ConventionalLoader(filepath);
      }
      IImageState image = loader.loadImage();
      this.model.addImage(this.imageID, image);
      this.view.draw(createImage(this.imageID));
    } catch (Exception e) {
      this.view.showMsg(e.getMessage());
    }
  }

  /**
   * Handles the event when the brightness of the current showing
   * image on this view is requested to be adjusted.
   *
   * @param increment The increment by which to brighten or darken the image.
   * @throws IllegalArgumentException if Null increment.
   */
  @Override
  public void handleBrightenEvent(String increment) {
    if (increment == null) {
      throw new IllegalArgumentException("No increment.");
    }
    String command = "brighten " + increment + " "
            + this.imageID + " " + this.imageID;
    handleEvent(command);
  }

  /**
   * Handles the event when a specific filter is requested to be
   * applied to current image showing in this view.
   *
   * @param filterName The name of the filter to be applied to the image.
   * @throws IllegalArgumentException if Null filter.
   */
  @Override
  public void handleFiltersEvent(String filterName) {
    if (filterName == null) {
      throw new IllegalArgumentException("No filter.");
    }
    String command = filterName + " " + this.imageID + " " + this.imageID;
    handleEvent(command);
  }

  /**
   * Handles the event when the current showing image on this view
   * is requested to be saved.
   *
   * @param filepath The file path where the image should be saved.
   * @throws IllegalArgumentException if Null file path.
   */
  @Override
  public void handleSaveEvent(String filepath) {
    if (filepath == null) {
      throw new IllegalArgumentException("No filepath.");
    }

    try {
      IImageState image = this.model.getImage(this.imageID);
      String extension = filepath.substring(filepath.lastIndexOf("."));

      IImageSaver saver;
      if (extension.equalsIgnoreCase(".ppm")) {
        saver = new PPMSaver(filepath, image, new StringBuilder());
      } else {
        saver = new ConventionalSaver(filepath, image,
                new ByteArrayOutputStream());
      }
      saver.run();
    } catch (Exception e) {
      this.view.showMsg(e.getMessage());
    }
  }
}
