package model;

import model.images.IImageState;

/**
 * This interface represents the operations offered by image database.
 * Image database object is the model of this program and
 * will store all load images.
 */
public interface IImageDatabase {

  /**
   * Adds a new image to the image database with the specified ID and
   * associated IImageState object.
   *
   * @param idImage The unique identifier for the image to be added.
   * @param image The IImageState object representing the state
   *              of the image to be added.
   * @throws IllegalArgumentException if either the idImage or image
   *     parameter is null.
   */
  void addImage(String idImage, IImageState image);

  /**
   * Get the IImageState object associated with the specified image ID.
   *
   * @param idImage The unique identifier of the image to be retrieved.
   * @return The IImageState object representing the state of
   *     the requested image.
   * @throws IllegalArgumentException if the idImage parameter is null.
   * @throws IllegalStateException if no image is found with
   *     the given idImage.
   */
  IImageState getImage(String idImage);
}
