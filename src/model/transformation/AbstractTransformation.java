package model.transformation;

import model.images.IImage;
import model.images.IImageState;
import model.images.ImageImpl;

/**
 * The AbstractTransformation class provides a basic structure
 * for image transformations. Implementing classes must extend this class
 * and implement the abstract methods that transforms the image to perform
 * the specific transformation. See each class for specific transformation
 * description.
 */
public abstract class AbstractTransformation implements ITransformation {

  /**
   * Applies the image transformation to the input image and
   * returns the transformed IImageState.
   *
   * @param image The IImageState object representing the input image.
   * @return The IImageState object representing the transformed image.
   */
  public IImageState run(IImageState image) {
    IImage newImage = new ImageImpl(image.getWidth(), image.getHeight());

    for (int h = 0; h < image.getHeight(); h++) {
      for (int w = 0; w < image.getWidth(); w++) {
        int r = image.getRedChannel(w, h);
        int g = image.getGreenChannel(w, h);
        int b = image.getBlueChannel(w, h);
        int newR = clamp(changeToValueR(r, g, b));
        int newG = clamp(changeToValueG(r, g, b));
        int newB = clamp(changeToValueB(r, g, b));
        newImage.setPixel(w, h, newR, newG, newB);
      }
    }
    return newImage;
  }

  // Transforms the red channel value based on the implementation in the subclass.
  // return The transformed red channel value.
  protected abstract int changeToValueR(int r, int g, int b);

  // Transforms the green channel value based on the implementation in the subclass.
  // return The transformed green channel value.
  protected abstract int changeToValueG(int r, int g, int b);

  // Transforms the blue channel value based on the implementation in the subclass.
  // return The transformed blue channel value.
  protected abstract int changeToValueB(int r, int g, int b);

  // helper method to clamp the channel value of the image.
  protected int clamp(int value) {
    return Math.min(Math.max(0, value), 255);
  }

}
