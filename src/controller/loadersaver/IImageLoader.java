package controller.loadersaver;

import model.images.IImageState;

/**
 * The IImageLoader interface represents a generic image loader.
 */
public interface IImageLoader {

  /**
   * Loads an image and returns its state as an IImageState object.
   *
   * @return The IImageState object representing the state of the loaded image.
   */
  IImageState loadImage();
}
