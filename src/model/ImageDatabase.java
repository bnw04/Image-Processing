package model;

import java.util.HashMap;
import java.util.Map;

import model.images.IImageState;

/**
 * The ImageDatabase class represents a database to store and manage images
 * identified by unique IDs. It implements the IImageDatabase
 * interface, providing methods to add and get images.
 */
public class ImageDatabase implements IImageDatabase {
  private final Map<String, IImageState> imageMap;

  /**
   * Constructs an ImageDatabase object with an internal HashMap to
   * store the images. The keys in the map represent the image IDs,
   * and the values are instances of IImageState.
   */
  public ImageDatabase() {
    this.imageMap = new HashMap<>();
  }

  @Override
  public void addImage(String idImage, IImageState image)
          throws IllegalArgumentException {
    if (idImage == null || image == null) {
      throw new IllegalArgumentException("Cannot load null image.");
    }
    this.imageMap.put(idImage, image);
  }

  @Override
  public IImageState getImage(String idImage)
          throws IllegalStateException {
    if (idImage == null) {
      throw new IllegalArgumentException("Image id cannot be null");
    }
    IImageState image = this.imageMap.get(idImage);
    if (image == null) {
      throw new IllegalStateException("No image with this id.");
    }
    return image;
  }
}
