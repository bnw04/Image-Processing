import org.junit.Before;
import org.junit.Test;

import controller.loadersaver.IImageLoader;
import controller.loadersaver.IImageSaver;
import controller.loadersaver.PPMLoader;
import controller.loadersaver.PPMSaver;
import model.images.IImageState;
import model.transformation.BrighterDarker;
import model.transformation.GreyscaleBlue;
import model.transformation.GreyscaleGreen;
import model.transformation.GreyscaleIntensity;
import model.transformation.GreyscaleLuma;
import model.transformation.GreyscaleRed;
import model.transformation.GreyscaleValue;
import model.transformation.ITransformation;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the transformation classes except BlurSharpen
 * and Matrix. Test rgb values at each pixel of the image created.
 * Have tests for loader and saver in another class, use the loader for
 * getting an image and also used the saver's Appendable to test
 * the imager after it's been brightened or filtered.
 */
public class TransformationTest {
  private IImageState three;

  @Before
  public void setUp() {

    // three.ppm is a three by three ppm image
    IImageLoader loader = new PPMLoader("testImage/three.ppm");
    three = loader.loadImage();
  }

  @Test
  public void testShowOriginalImage() {

    StringBuilder log = new StringBuilder();
    IImageSaver saver = new PPMSaver("testImage/three.ppm", three, log);
    saver.run();

    // this is the channel values for original image, use to compare for different
    // filters result.
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n", log.toString());
  }

  @Test
  public void testRedGrey() {
    ITransformation red = new GreyscaleRed();

    // red-component image
    IImageState redImage = red.run(three);
    assertEquals(3, redImage.getHeight());
    assertEquals(3, redImage.getWidth());
    assertEquals(0, redImage.getRedChannel(0, 0));
    assertEquals(0, redImage.getGreenChannel(0, 0));
    assertEquals(0, redImage.getBlueChannel(0, 0));

    assertEquals(255, redImage.getRedChannel(1, 0));
    assertEquals(255, redImage.getGreenChannel(1, 0));
    assertEquals(255, redImage.getBlueChannel(1, 0));

    assertEquals(255, redImage.getRedChannel(2, 0));
    assertEquals(255, redImage.getGreenChannel(2, 0));
    assertEquals(255, redImage.getBlueChannel(2, 0));

    assertEquals(0, redImage.getRedChannel(0, 1));
    assertEquals(0, redImage.getGreenChannel(0, 1));
    assertEquals(0, redImage.getBlueChannel(0, 1));

    assertEquals(0, redImage.getRedChannel(1, 1));
    assertEquals(0, redImage.getGreenChannel(1, 1));
    assertEquals(0, redImage.getBlueChannel(1, 1));

    assertEquals(100, redImage.getRedChannel(2, 1));
    assertEquals(100, redImage.getGreenChannel(2, 1));
    assertEquals(100, redImage.getBlueChannel(2, 1));

    assertEquals(156, redImage.getRedChannel(0, 2));
    assertEquals(156, redImage.getGreenChannel(0, 2));
    assertEquals(156, redImage.getBlueChannel(0, 2));

    assertEquals(10, redImage.getRedChannel(1, 2));
    assertEquals(10, redImage.getGreenChannel(1, 2));
    assertEquals(10, redImage.getBlueChannel(1, 2));

    assertEquals(45, redImage.getRedChannel(2, 2));
    assertEquals(45, redImage.getGreenChannel(2, 2));
    assertEquals(45, redImage.getBlueChannel(2, 2));

    // save the image to see the result
    StringBuilder log = new StringBuilder();
    IImageSaver saver = new PPMSaver("testImage/red.ppm", redImage, log);
    saver.run();
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "0 0 0\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "100 100 100\n" +
            "156 156 156\n" +
            "10 10 10\n" +
            "45 45 45\n", log.toString());
  }

  @Test
  public void testGreenGrey() {
    ITransformation green = new GreyscaleGreen();

    // green-component image
    IImageState greenImage = green.run(three);
    assertEquals(0, greenImage.getRedChannel(0, 0));
    assertEquals(0, greenImage.getGreenChannel(0, 0));
    assertEquals(0, greenImage.getBlueChannel(0, 0));

    assertEquals(255, greenImage.getRedChannel(1, 0));
    assertEquals(255, greenImage.getGreenChannel(1, 0));
    assertEquals(255, greenImage.getBlueChannel(1, 0));

    assertEquals(0, greenImage.getRedChannel(2, 0));
    assertEquals(0, greenImage.getGreenChannel(2, 0));
    assertEquals(0, greenImage.getBlueChannel(2, 0));

    assertEquals(255, greenImage.getRedChannel(0, 1));
    assertEquals(255, greenImage.getGreenChannel(0, 1));
    assertEquals(255, greenImage.getBlueChannel(0, 1));

    assertEquals(0, greenImage.getRedChannel(1, 1));
    assertEquals(0, greenImage.getGreenChannel(1, 1));
    assertEquals(0, greenImage.getBlueChannel(1, 1));

    assertEquals(1, greenImage.getRedChannel(2, 1));
    assertEquals(1, greenImage.getGreenChannel(2, 1));
    assertEquals(1, greenImage.getBlueChannel(2, 1));

    assertEquals(156, greenImage.getRedChannel(0, 2));
    assertEquals(156, greenImage.getGreenChannel(0, 2));
    assertEquals(156, greenImage.getBlueChannel(0, 2));

    assertEquals(200, greenImage.getRedChannel(1, 2));
    assertEquals(200, greenImage.getGreenChannel(1, 2));
    assertEquals(200, greenImage.getBlueChannel(1, 2));

    assertEquals(23, greenImage.getRedChannel(2, 2));
    assertEquals(23, greenImage.getGreenChannel(2, 2));
    assertEquals(23, greenImage.getBlueChannel(2, 2));

    StringBuilder log = new StringBuilder();
    IImageSaver saver = new PPMSaver("testImage/green.ppm", greenImage, log);
    saver.run();
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "0 0 0\n" +
            "255 255 255\n" +
            "0 0 0\n" +
            "255 255 255\n" +
            "0 0 0\n" +
            "1 1 1\n" +
            "156 156 156\n" +
            "200 200 200\n" +
            "23 23 23\n", log.toString());
  }

  @Test
  public void testBlueGrey() {
    ITransformation blue = new GreyscaleBlue();

    // blue-component image
    IImageState blueImage = blue.run(three);
    assertEquals(0, blueImage.getRedChannel(0, 0));
    assertEquals(0, blueImage.getGreenChannel(0, 0));
    assertEquals(0, blueImage.getBlueChannel(0, 0));

    assertEquals(255, blueImage.getRedChannel(1, 0));
    assertEquals(255, blueImage.getGreenChannel(1, 0));
    assertEquals(255, blueImage.getBlueChannel(1, 0));

    assertEquals(0, blueImage.getRedChannel(2, 0));
    assertEquals(0, blueImage.getGreenChannel(2, 0));
    assertEquals(0, blueImage.getBlueChannel(2, 0));

    assertEquals(0, blueImage.getRedChannel(0, 1));
    assertEquals(0, blueImage.getGreenChannel(0, 1));
    assertEquals(0, blueImage.getBlueChannel(0, 1));

    assertEquals(255, blueImage.getRedChannel(1, 1));
    assertEquals(255, blueImage.getGreenChannel(1, 1));
    assertEquals(255, blueImage.getBlueChannel(1, 1));

    assertEquals(254, blueImage.getRedChannel(2, 1));
    assertEquals(254, blueImage.getGreenChannel(2, 1));
    assertEquals(254, blueImage.getBlueChannel(2, 1));

    assertEquals(156, blueImage.getRedChannel(0, 2));
    assertEquals(156, blueImage.getGreenChannel(0, 2));
    assertEquals(156, blueImage.getBlueChannel(0, 2));

    assertEquals(255, blueImage.getRedChannel(1, 2));
    assertEquals(255, blueImage.getGreenChannel(1, 2));
    assertEquals(255, blueImage.getBlueChannel(1, 2));

    assertEquals(12, blueImage.getRedChannel(2, 2));
    assertEquals(12, blueImage.getGreenChannel(2, 2));
    assertEquals(12, blueImage.getBlueChannel(2, 2));

    StringBuilder log = new StringBuilder();
    IImageSaver saver = new PPMSaver("testImage/blue.ppm", blueImage, log);
    saver.run();
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "0 0 0\n" +
            "255 255 255\n" +
            "0 0 0\n" +
            "0 0 0\n" +
            "255 255 255\n" +
            "254 254 254\n" +
            "156 156 156\n" +
            "255 255 255\n" +
            "12 12 12\n", log.toString());
  }

  @Test
  public void testValueGrey() {
    ITransformation value = new GreyscaleValue();

    // value-component image
    IImageState valueImage = value.run(three);
    assertEquals(0, valueImage.getRedChannel(0, 0));
    assertEquals(0, valueImage.getGreenChannel(0, 0));
    assertEquals(0, valueImage.getBlueChannel(0, 0));

    assertEquals(255, valueImage.getRedChannel(1, 0));
    assertEquals(255, valueImage.getGreenChannel(1, 0));
    assertEquals(255, valueImage.getBlueChannel(1, 0));

    assertEquals(255, valueImage.getRedChannel(2, 0));
    assertEquals(255, valueImage.getGreenChannel(2, 0));
    assertEquals(255, valueImage.getBlueChannel(2, 0));

    assertEquals(255, valueImage.getRedChannel(0, 1));
    assertEquals(255, valueImage.getGreenChannel(0, 1));
    assertEquals(255, valueImage.getBlueChannel(0, 1));

    assertEquals(255, valueImage.getRedChannel(1, 1));
    assertEquals(255, valueImage.getGreenChannel(1, 1));
    assertEquals(255, valueImage.getBlueChannel(1, 1));

    assertEquals(254, valueImage.getRedChannel(2, 1));
    assertEquals(254, valueImage.getGreenChannel(2, 1));
    assertEquals(254, valueImage.getBlueChannel(2, 1));

    assertEquals(156, valueImage.getRedChannel(0, 2));
    assertEquals(156, valueImage.getGreenChannel(0, 2));
    assertEquals(156, valueImage.getBlueChannel(0, 2));

    assertEquals(255, valueImage.getRedChannel(1, 2));
    assertEquals(255, valueImage.getGreenChannel(1, 2));
    assertEquals(255, valueImage.getBlueChannel(1, 2));

    assertEquals(45, valueImage.getRedChannel(2, 2));
    assertEquals(45, valueImage.getGreenChannel(2, 2));
    assertEquals(45, valueImage.getBlueChannel(2, 2));

    StringBuilder log = new StringBuilder();
    IImageSaver saver = new PPMSaver("testImage/value.ppm", valueImage, log);
    saver.run();
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "0 0 0\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "255 255 255\n" +
            "254 254 254\n" +
            "156 156 156\n" +
            "255 255 255\n" +
            "45 45 45\n", log.toString());
  }

  @Test
  public void testLumaGrey() {
    ITransformation luma = new GreyscaleLuma();

    // luma-component image
    IImageState lumaImage = luma.run(three);
    assertEquals(0, lumaImage.getRedChannel(0, 0));
    assertEquals(0, lumaImage.getGreenChannel(0, 0));
    assertEquals(0, lumaImage.getBlueChannel(0, 0));

    assertEquals(255, lumaImage.getRedChannel(1, 0));
    assertEquals(255, lumaImage.getGreenChannel(1, 0));
    assertEquals(255, lumaImage.getBlueChannel(1, 0));

    assertEquals(54, lumaImage.getRedChannel(2, 0));
    assertEquals(54, lumaImage.getGreenChannel(2, 0));
    assertEquals(54, lumaImage.getBlueChannel(2, 0));

    assertEquals(182, lumaImage.getRedChannel(0, 1));
    assertEquals(182, lumaImage.getGreenChannel(0, 1));
    assertEquals(182, lumaImage.getBlueChannel(0, 1));

    assertEquals(18, lumaImage.getRedChannel(1, 1));
    assertEquals(18, lumaImage.getGreenChannel(1, 1));
    assertEquals(18, lumaImage.getBlueChannel(1, 1));

    assertEquals(40, lumaImage.getRedChannel(2, 1));
    assertEquals(40, lumaImage.getGreenChannel(2, 1));
    assertEquals(40, lumaImage.getBlueChannel(2, 1));

    assertEquals(156, lumaImage.getRedChannel(0, 2));
    assertEquals(156, lumaImage.getGreenChannel(0, 2));
    assertEquals(156, lumaImage.getBlueChannel(0, 2));

    assertEquals(164, lumaImage.getRedChannel(1, 2));
    assertEquals(164, lumaImage.getGreenChannel(1, 2));
    assertEquals(164, lumaImage.getBlueChannel(1, 2));

    assertEquals(27, lumaImage.getRedChannel(2, 2));
    assertEquals(27, lumaImage.getGreenChannel(2, 2));
    assertEquals(27, lumaImage.getBlueChannel(2, 2));

    StringBuilder log = new StringBuilder();
    IImageSaver saver = new PPMSaver("testImage/luma.ppm", lumaImage, log);
    saver.run();
    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "0 0 0\n" +
            "255 255 255\n" +
            "54 54 54\n" +
            "182 182 182\n" +
            "18 18 18\n" +
            "40 40 40\n" +
            "156 156 156\n" +
            "164 164 164\n" +
            "27 27 27\n", log.toString());
  }

  @Test
  public void testIntensityGrey() {
    ITransformation intensity = new GreyscaleIntensity();

    // intensity-component image
    IImageState intensityImage = intensity.run(three);
    assertEquals(0, intensityImage.getRedChannel(0, 0));
    assertEquals(0, intensityImage.getGreenChannel(0, 0));
    assertEquals(0, intensityImage.getBlueChannel(0, 0));

    assertEquals(255, intensityImage.getRedChannel(1, 0));
    assertEquals(255, intensityImage.getGreenChannel(1, 0));
    assertEquals(255, intensityImage.getBlueChannel(1, 0));

    assertEquals(85, intensityImage.getRedChannel(2, 0));
    assertEquals(85, intensityImage.getGreenChannel(2, 0));
    assertEquals(85, intensityImage.getBlueChannel(2, 0));

    assertEquals(85, intensityImage.getRedChannel(0, 1));
    assertEquals(85, intensityImage.getGreenChannel(0, 1));
    assertEquals(85, intensityImage.getBlueChannel(0, 1));

    assertEquals(85, intensityImage.getRedChannel(1, 1));
    assertEquals(85, intensityImage.getGreenChannel(1, 1));
    assertEquals(85, intensityImage.getBlueChannel(1, 1));

    assertEquals(118, intensityImage.getRedChannel(2, 1));
    assertEquals(118, intensityImage.getGreenChannel(2, 1));
    assertEquals(118, intensityImage.getBlueChannel(2, 1));

    assertEquals(156, intensityImage.getRedChannel(0, 2));
    assertEquals(156, intensityImage.getGreenChannel(0, 2));
    assertEquals(156, intensityImage.getBlueChannel(0, 2));

    assertEquals(155, intensityImage.getRedChannel(1, 2));
    assertEquals(155, intensityImage.getGreenChannel(1, 2));
    assertEquals(155, intensityImage.getBlueChannel(1, 2));

    assertEquals(26, intensityImage.getRedChannel(2, 2));
    assertEquals(26, intensityImage.getGreenChannel(2, 2));
    assertEquals(26, intensityImage.getBlueChannel(2, 2));

    StringBuilder log = new StringBuilder();
    IImageSaver saver = new PPMSaver("testImage/intensity.ppm",
            intensityImage, log);
    saver.run();

    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "0 0 0\n" +
            "255 255 255\n" +
            "85 85 85\n" +
            "85 85 85\n" +
            "85 85 85\n" +
            "118 118 118\n" +
            "156 156 156\n" +
            "155 155 155\n" +
            "26 26 26\n", log.toString());
  }

  @Test
  public void testBrighter() {

    // brighten the image by 100 channel value
    ITransformation brighter = new BrighterDarker(100);
    IImageState brighterImage = brighter.run(three);
    assertEquals(100, brighterImage.getRedChannel(0, 0));
    assertEquals(100, brighterImage.getGreenChannel(0, 0));
    assertEquals(100, brighterImage.getBlueChannel(0, 0));

    assertEquals(255, brighterImage.getRedChannel(1, 0));
    assertEquals(255, brighterImage.getGreenChannel(1, 0));
    assertEquals(255, brighterImage.getBlueChannel(1, 0));

    assertEquals(255, brighterImage.getRedChannel(2, 0));
    assertEquals(100, brighterImage.getGreenChannel(2, 0));
    assertEquals(100, brighterImage.getBlueChannel(2, 0));

    assertEquals(100, brighterImage.getRedChannel(0, 1));
    assertEquals(255, brighterImage.getGreenChannel(0, 1));
    assertEquals(100, brighterImage.getBlueChannel(0, 1));

    assertEquals(100, brighterImage.getRedChannel(1, 1));
    assertEquals(100, brighterImage.getGreenChannel(1, 1));
    assertEquals(255, brighterImage.getBlueChannel(1, 1));

    assertEquals(200, brighterImage.getRedChannel(2, 1));
    assertEquals(101, brighterImage.getGreenChannel(2, 1));
    assertEquals(255, brighterImage.getBlueChannel(2, 1));

    assertEquals(255, brighterImage.getRedChannel(0, 2));
    assertEquals(255, brighterImage.getGreenChannel(0, 2));
    assertEquals(255, brighterImage.getBlueChannel(0, 2));

    assertEquals(110, brighterImage.getRedChannel(1, 2));
    assertEquals(255, brighterImage.getGreenChannel(1, 2));
    assertEquals(255, brighterImage.getBlueChannel(1, 2));

    assertEquals(145, brighterImage.getRedChannel(2, 2));
    assertEquals(123, brighterImage.getGreenChannel(2, 2));
    assertEquals(112, brighterImage.getBlueChannel(2, 2));

    StringBuilder log = new StringBuilder();
    IImageSaver saver = new PPMSaver("testImage/brighterBy100.ppm",
            brighterImage, log);
    saver.run();

    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "100 100 100\n" +
            "255 255 255\n" +
            "255 100 100\n" +
            "100 255 100\n" +
            "100 100 255\n" +
            "200 101 255\n" +
            "255 255 255\n" +
            "110 255 255\n" +
            "145 123 112\n", log.toString());
  }

  @Test
  public void testDarker() {

    // darken the image by 100 channel value
    ITransformation darker = new BrighterDarker(-100);
    IImageState darkerImage = darker.run(three);
    assertEquals(0, darkerImage.getRedChannel(0, 0));
    assertEquals(0, darkerImage.getGreenChannel(0, 0));
    assertEquals(0, darkerImage.getBlueChannel(0, 0));

    assertEquals(155, darkerImage.getRedChannel(1, 0));
    assertEquals(155, darkerImage.getGreenChannel(1, 0));
    assertEquals(155, darkerImage.getBlueChannel(1, 0));

    assertEquals(155, darkerImage.getRedChannel(2, 0));
    assertEquals(0, darkerImage.getGreenChannel(2, 0));
    assertEquals(0, darkerImage.getBlueChannel(2, 0));

    assertEquals(0, darkerImage.getRedChannel(0, 1));
    assertEquals(155, darkerImage.getGreenChannel(0, 1));
    assertEquals(0, darkerImage.getBlueChannel(0, 1));

    assertEquals(0, darkerImage.getRedChannel(1, 1));
    assertEquals(0, darkerImage.getGreenChannel(1, 1));
    assertEquals(155, darkerImage.getBlueChannel(1, 1));

    assertEquals(0, darkerImage.getRedChannel(2, 1));
    assertEquals(0, darkerImage.getGreenChannel(2, 1));
    assertEquals(154, darkerImage.getBlueChannel(2, 1));

    assertEquals(56, darkerImage.getRedChannel(0, 2));
    assertEquals(56, darkerImage.getGreenChannel(0, 2));
    assertEquals(56, darkerImage.getBlueChannel(0, 2));

    assertEquals(0, darkerImage.getRedChannel(1, 2));
    assertEquals(100, darkerImage.getGreenChannel(1, 2));
    assertEquals(155, darkerImage.getBlueChannel(1, 2));

    assertEquals(0, darkerImage.getRedChannel(2, 2));
    assertEquals(0, darkerImage.getGreenChannel(2, 2));
    assertEquals(0, darkerImage.getBlueChannel(2, 2));

    StringBuilder log = new StringBuilder();
    IImageSaver saver = new PPMSaver("testImage/darkerBy100.ppm",
            darkerImage, log);
    saver.run();

    assertEquals("P3\n" +
            "3 3\n" +
            "255\n" +
            "0 0 0\n" +
            "155 155 155\n" +
            "155 0 0\n" +
            "0 155 0\n" +
            "0 0 155\n" +
            "0 0 154\n" +
            "56 56 56\n" +
            "0 100 155\n" +
            "0 0 0\n", log.toString());
  }
}
