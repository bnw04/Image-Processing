import org.junit.Before;
import org.junit.Test;

import controller.loadersaver.IImageLoader;
import controller.loadersaver.IImageSaver;
import controller.loadersaver.PPMLoader;
import controller.loadersaver.PPMSaver;
import model.images.IImageState;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the PPMSaver and PPMLoader class.
 * Load a three by three image and test for all channel values at each pixel
 * of the image. And test the saver with the loaded image.
 */
public class PPMSaverLoaderTest {

  private IImageLoader loader;

  @Before
  public void setUp() {

    // three.ppm is a three by three ppm image
    loader = new PPMLoader("testImage/three.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoaderNullFilePath() {
    new PPMLoader(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testFileNotFound() {
    IImageLoader loaderTest = new PPMLoader("testImage/notThere.ppm");
    loaderTest.loadImage();
  }

  @Test(expected = IllegalStateException.class)
  public void testLoaderNotP3File() {
    IImageLoader loaderTest = new PPMLoader("testImage/ppmInvalidNotP3.ppm");
    loaderTest.loadImage();
  }

  @Test
  public void testLoader() {
    IImageState three = loader.loadImage();

    // test for each channel's value
    assertEquals(3, three.getHeight());
    assertEquals(3, three.getWidth());
    assertEquals(0, three.getRedChannel(0, 0));
    assertEquals(0, three.getGreenChannel(0, 0));
    assertEquals(0, three.getBlueChannel(0, 0));

    assertEquals(255, three.getRedChannel(1, 0));
    assertEquals(255, three.getGreenChannel(1, 0));
    assertEquals(255, three.getBlueChannel(1, 0));

    assertEquals(255, three.getRedChannel(2, 0));
    assertEquals(0, three.getGreenChannel(2, 0));
    assertEquals(0, three.getBlueChannel(2, 0));

    assertEquals(0, three.getRedChannel(0, 1));
    assertEquals(255, three.getGreenChannel(0, 1));
    assertEquals(0, three.getBlueChannel(0, 1));

    assertEquals(0, three.getRedChannel(1, 1));
    assertEquals(0, three.getGreenChannel(1, 1));
    assertEquals(255, three.getBlueChannel(1, 1));

    assertEquals(100, three.getRedChannel(2, 1));
    assertEquals(1, three.getGreenChannel(2, 1));
    assertEquals(254, three.getBlueChannel(2, 1));

    assertEquals(156, three.getRedChannel(0, 2));
    assertEquals(156, three.getGreenChannel(0, 2));
    assertEquals(156, three.getBlueChannel(0, 2));

    assertEquals(10, three.getRedChannel(1, 2));
    assertEquals(200, three.getGreenChannel(1, 2));
    assertEquals(255, three.getBlueChannel(1, 2));

    assertEquals(45, three.getRedChannel(2, 2));
    assertEquals(23, three.getGreenChannel(2, 2));
    assertEquals(12, three.getBlueChannel(2, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaverNullFilePath() {
    IImageSaver saver = new PPMSaver(null,
            loader.loadImage(), new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaverNullImage() {
    IImageSaver saver = new PPMSaver("testImage/new.ppm",
            null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaverNullOut() {
    IImageSaver saver = new PPMSaver("testImage/new.ppm",
            loader.loadImage(), null);
  }

  @Test
  public void testSaver() {
    IImageState three = loader.loadImage();
    StringBuilder log = new StringBuilder();

    // save the loaded image, all channel values should be the same as the result shows
    // in the testLoader();
    IImageSaver saver = new PPMSaver("testImage/new.ppm", three, log);
    saver.run();

    // using the appendable to test for the saved image
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
}
