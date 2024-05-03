import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import controller.loadersaver.ConventionalLoader;
import controller.loadersaver.ConventionalSaver;
import controller.loadersaver.IImageLoader;
import controller.loadersaver.IImageSaver;
import model.images.IImageState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A JUnit test class for the ConventionalSaver and ConventionalLoader class.
 * Load a three by three image and test for all channel values at each pixel
 * of the image. And test the saver with the loaded image. JPEG format is
 * lossy compression so test with image not null and message.
 */
public class ConventionalSaverLoaderTest {

  @Test(expected = IllegalArgumentException.class)
  public void testLoaderNullFilePath() {
    new ConventionalLoader(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaverNullFilePath() {
    IImageLoader loader = new ConventionalLoader("testImage/three.png");
    IImageState png = loader.loadImage();
    new ConventionalSaver(null, png, new ByteArrayOutputStream());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaverNullImage() {
    new ConventionalSaver("testImage/three.png", null,
            new ByteArrayOutputStream());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaverNullOut() {
    IImageLoader loader = new ConventionalLoader("testImage/three.png");
    IImageState png = loader.loadImage();
    new ConventionalSaver("testImage/three.png", png, null);
  }

  // jpg is lossy compression, test with image created and save
  // with saver's out has image saved message(cannot test pixels)
  @Test
  public void testLoaderSaveJPG() {
    IImageLoader loader = new ConventionalLoader("testImage/three.jpg");
    IImageState jpg = loader.loadImage();
    assertNotNull(jpg);

    OutputStream log = new ByteArrayOutputStream();
    IImageSaver saver = new ConventionalSaver("testImage/three-save.jpg", jpg, log);
    saver.run();
    String[] lines = log.toString().split("\n");
    assertEquals(10, lines.length);
    assertEquals("Image testImage/three-save.jpg is saved.", lines[lines.length - 1]);
  }

  @Test
  public void testLoaderSavePNG() {
    IImageLoader loader = new ConventionalLoader("testImage/three.png");
    IImageState png = loader.loadImage();

    assertEquals(3, png.getHeight());
    assertEquals(3, png.getWidth());
    assertEquals(0, png.getRedChannel(0, 0));
    assertEquals(0, png.getGreenChannel(0, 0));
    assertEquals(0, png.getBlueChannel(0, 0));

    assertEquals(255, png.getRedChannel(1, 0));
    assertEquals(255, png.getGreenChannel(1, 0));
    assertEquals(255, png.getBlueChannel(1, 0));

    assertEquals(255, png.getRedChannel(2, 0));
    assertEquals(0, png.getGreenChannel(2, 0));
    assertEquals(0, png.getBlueChannel(2, 0));

    assertEquals(0, png.getRedChannel(0, 1));
    assertEquals(255, png.getGreenChannel(0, 1));
    assertEquals(0, png.getBlueChannel(0, 1));

    assertEquals(0, png.getRedChannel(1, 1));
    assertEquals(0, png.getGreenChannel(1, 1));
    assertEquals(255, png.getBlueChannel(1, 1));

    assertEquals(100, png.getRedChannel(2, 1));
    assertEquals(1, png.getGreenChannel(2, 1));
    assertEquals(254, png.getBlueChannel(2, 1));

    assertEquals(156, png.getRedChannel(0, 2));
    assertEquals(156, png.getGreenChannel(0, 2));
    assertEquals(156, png.getBlueChannel(0, 2));

    assertEquals(10, png.getRedChannel(1, 2));
    assertEquals(200, png.getGreenChannel(1, 2));
    assertEquals(255, png.getBlueChannel(1, 2));

    assertEquals(45, png.getRedChannel(2, 2));
    assertEquals(23, png.getGreenChannel(2, 2));
    assertEquals(12, png.getBlueChannel(2, 2));

    OutputStream log = new ByteArrayOutputStream();
    IImageSaver saver = new ConventionalSaver("testImage/three-save.png", png, log);
    saver.run();
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
            "Image testImage/three-save.png is saved.", log.toString());
  }

  @Test
  public void testLoaderSaveBMP() {
    IImageLoader loader = new ConventionalLoader("testImage/three.bmp");
    IImageState bmp = loader.loadImage();

    assertEquals(3, bmp.getHeight());
    assertEquals(3, bmp.getWidth());
    assertEquals(0, bmp.getRedChannel(0, 0));
    assertEquals(0, bmp.getGreenChannel(0, 0));
    assertEquals(0, bmp.getBlueChannel(0, 0));

    assertEquals(255, bmp.getRedChannel(1, 0));
    assertEquals(255, bmp.getGreenChannel(1, 0));
    assertEquals(255, bmp.getBlueChannel(1, 0));

    assertEquals(255, bmp.getRedChannel(2, 0));
    assertEquals(0, bmp.getGreenChannel(2, 0));
    assertEquals(0, bmp.getBlueChannel(2, 0));

    assertEquals(0, bmp.getRedChannel(0, 1));
    assertEquals(255, bmp.getGreenChannel(0, 1));
    assertEquals(0, bmp.getBlueChannel(0, 1));

    assertEquals(0, bmp.getRedChannel(1, 1));
    assertEquals(0, bmp.getGreenChannel(1, 1));
    assertEquals(255, bmp.getBlueChannel(1, 1));

    assertEquals(100, bmp.getRedChannel(2, 1));
    assertEquals(1, bmp.getGreenChannel(2, 1));
    assertEquals(254, bmp.getBlueChannel(2, 1));

    assertEquals(156, bmp.getRedChannel(0, 2));
    assertEquals(156, bmp.getGreenChannel(0, 2));
    assertEquals(156, bmp.getBlueChannel(0, 2));

    assertEquals(10, bmp.getRedChannel(1, 2));
    assertEquals(200, bmp.getGreenChannel(1, 2));
    assertEquals(255, bmp.getBlueChannel(1, 2));

    assertEquals(45, bmp.getRedChannel(2, 2));
    assertEquals(23, bmp.getGreenChannel(2, 2));
    assertEquals(12, bmp.getBlueChannel(2, 2));

    OutputStream log = new ByteArrayOutputStream();
    IImageSaver saver = new ConventionalSaver("testImage/three-save.bmp", bmp, log);
    saver.run();
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
            "Image testImage/three-save.bmp is saved.", log.toString());
  }
}
