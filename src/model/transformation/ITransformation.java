package model.transformation;

import model.images.IImageState;

/**
 * The ITransformation interface represents a generic image transformation.
 * Implementing classes must define the run() method to apply the transformation
 * to an IImageState object.
 */
public interface ITransformation {

  /**
   * Applies the transformation to the input state of an image
   * and returns the transformed IImageState.
   *
   * @param image The IImageState object representing the input image.
   * @return The IImageState object representing the transformed image.
   */
  IImageState run(IImageState image);
}
