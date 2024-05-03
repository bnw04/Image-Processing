package model.transformation;

import model.images.IImage;
import model.images.IImageState;
import model.images.ImageImpl;

import static model.transformation.Channel.BLUE;
import static model.transformation.Channel.GREEN;
import static model.transformation.Channel.RED;

/**
 * The BlurSharpen class represents a transformation that converts
 * an image to blurred/sharpened when it runs. It implements the
 * ITransformation interface.
 */
public class BlurSharpen implements ITransformation {

  private final double[][] kernel;

  /**
   * Constructs a BlurSharpen object with the specified double 2D array.
   *
   * @param kernel The 2D array representing the kernel used for the
   *     blur/sharpen transformation. The kernel should have odd dimensions
   *     and larger than 3 values.
   * @throws IllegalArgumentException If the kernel is null, has even
   *     dimensions, or has dimensions less than 3.
   */
  public BlurSharpen(double[][] kernel) {
    if (kernel == null || kernel.length % 2 == 0 ||  kernel.length < 3 ) {
      throw new IllegalArgumentException("Kernel invalid dimensions.");
    }
    this.kernel = kernel;
  }

  /**
   * Applies the image transformation blur or sharpen to the input image and
   * returns the transformed IImageState.
   *
   * @param image The IImageState object representing the input image.
   * @return The IImageState object representing the transformed image.
   */
  @Override
  public IImageState run(IImageState image) {
    IImage newImage = new ImageImpl(image.getWidth(), image.getHeight());

    for (int h = 0; h < image.getHeight(); h++) {
      for (int w = 0; w < image.getWidth(); w++) {
        int newR = clamp(applyKernel(image, w, h, RED));
        int newG = clamp(applyKernel(image, w, h, GREEN));
        int newB = clamp(applyKernel(image, w, h, BLUE));
        newImage.setPixel(w, h, newR, newG, newB);
      }
    }
    return newImage;
  }

  // helper method to clamp the channel value of the image.
  private int clamp(int value) {
    return Math.min(Math.max(0, value), 255);
  }

  // helper method to apply the kernel on a channel value of a single pixel
  private int applyKernel(IImageState image,
                               int w, int h, Channel color) {

    int halfSize = this.kernel.length / 2;
    double result = 0;
    for (int y = - halfSize; y <= halfSize; y++) {
      for (int x = - halfSize; x <= halfSize; x++) {
        int value = getChannelValue(image, w + x, h + y, color);
        result += value * this.kernel[y + halfSize][x + halfSize];
      }
    }

    return (int) Math.round(result);
  }

  // helper method that returns the specified channel value
  // at the given place pixel of the image.
  private int getChannelValue(IImageState image, int w, int h, Channel color) {
    int result = 0;
    if (w < 0 || w >= image.getWidth() || h < 0
            || h >= image.getHeight()) {
      return result;
    }

    switch (color) {
      case RED:
        result = image.getRedChannel(w, h);
        break;
      case GREEN:
        result = image.getGreenChannel(w, h);
        break;
      case BLUE:
        result = image.getBlueChannel(w, h);
        break;
      default:
        break;
    }
    return result;
  }
}

