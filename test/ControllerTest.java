import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.ControllerImpl;
import controller.IController;
import controller.loadersaver.PPMLoader;
import model.IImageDatabase;
import model.ImageDatabase;
import model.images.IImageState;
import view.IView;
import view.View;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the Controller class.
 * Test all valid commands by getting the image and tests
 * all channel values at each pixel of the image. Test save command by
 * loading the saved image and tests all channel values at each pixel of the image.
 */
public class ControllerTest {

  private IImageDatabase m;
  private IView view;
  private Appendable log;

  @Before
  public void setUp() {
    m = new ImageDatabase();

    // have image three added to the model
    IImageState image = new PPMLoader("testImage/three.ppm").loadImage();
    m.addImage("three", image);

    log = new StringBuilder();
    view = new View(log);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new ControllerImpl(null, view, new StringReader("exit"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    new ControllerImpl(null, view, new StringReader("exit"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadable() {
    new ControllerImpl(m, view, null);
  }

  @Test(expected = IllegalStateException.class)
  public void testFailToAppend() {
    IView viewFail = new View(new FailingAppendable());
    IController c = new ControllerImpl(m, viewFail, new StringReader("exit"));
    c.process();
  }

  @Test
  public void testQuitController() {
    Readable inQ = new StringReader("exit");
    IController c = new ControllerImpl(m, view, inQ);
    c.process();
    assertEquals("Quitting.", log.toString());
  }

  @Test
  public void testInvalidCommand1() {
    Readable invalid = new StringReader("savee");
    IController c = new ControllerImpl(m, view, invalid);
    c.process();
    assertEquals("Invalid command.\n", log.toString());
  }

  @Test
  public void testInvalidCommand2() {
    Readable invalid = new StringReader("invalid");
    IController c = new ControllerImpl(m, view, invalid);
    c.process();
    assertEquals("Invalid command.\n", log.toString());
  }

  @Test
  public void testLoadMissingArg1() {
    IController c = new ControllerImpl(m, view, new StringReader("load "));
    c.process();
    assertEquals("Cannot load the image.\n", log.toString());
  }

  @Test
  public void testLoadMissingArg2() {
    IController c = new ControllerImpl(m, view,
            new StringReader("load testImages/three.ppm"));
    c.process();
    assertEquals("Cannot load the image.\n", log.toString());
  }

  @Test
  public void testLoadImageNotFound() {
    IController c = new ControllerImpl(m, view,
            new StringReader("load testImage/notFound.ppm new"));
    c.process();
    assertEquals("Cannot load the image.\n", log.toString());
  }

  @Test
  public void testValidLoadImage() {
    IController c = new ControllerImpl(m, view,
            new StringReader("load testImage/three.ppm new"));
    c.process();
    assertEquals("Command executed.\n", log.toString());

    IImageState loaded = m.getImage("new");
    assertEquals(3, loaded.getHeight());
    assertEquals(3, loaded.getWidth());
    assertEquals(0, loaded.getRedChannel(0, 0));
    assertEquals(0, loaded.getGreenChannel(0, 0));
    assertEquals(0, loaded.getBlueChannel(0, 0));

    assertEquals(255, loaded.getRedChannel(1, 0));
    assertEquals(255, loaded.getGreenChannel(1, 0));
    assertEquals(255, loaded.getBlueChannel(1, 0));

    assertEquals(255, loaded.getRedChannel(2, 0));
    assertEquals(0, loaded.getGreenChannel(2, 0));
    assertEquals(0, loaded.getBlueChannel(2, 0));

    assertEquals(0, loaded.getRedChannel(0, 1));
    assertEquals(255, loaded.getGreenChannel(0, 1));
    assertEquals(0, loaded.getBlueChannel(0, 1));

    assertEquals(0, loaded.getRedChannel(1, 1));
    assertEquals(0, loaded.getGreenChannel(1, 1));
    assertEquals(255, loaded.getBlueChannel(1, 1));

    assertEquals(100, loaded.getRedChannel(2, 1));
    assertEquals(1, loaded.getGreenChannel(2, 1));
    assertEquals(254, loaded.getBlueChannel(2, 1));

    assertEquals(156, loaded.getRedChannel(0, 2));
    assertEquals(156, loaded.getGreenChannel(0, 2));
    assertEquals(156, loaded.getBlueChannel(0, 2));

    assertEquals(10, loaded.getRedChannel(1, 2));
    assertEquals(200, loaded.getGreenChannel(1, 2));
    assertEquals(255, loaded.getBlueChannel(1, 2));

    assertEquals(45, loaded.getRedChannel(2, 2));
    assertEquals(23, loaded.getGreenChannel(2, 2));
    assertEquals(12, loaded.getBlueChannel(2, 2));
  }

  @Test
  public void testSaveMissingArg1() {
    IController c = new ControllerImpl(m, view, new StringReader("save "));
    c.process();
    assertEquals("Cannot save the image.\n", log.toString());
  }

  @Test
  public void testSaveMissingArg2() {
    IController c = new ControllerImpl(m, view,
            new StringReader("save testImage/new.ppm "));
    c.process();
    assertEquals("Cannot save the image.\n", log.toString());
  }

  @Test
  public void testSaveImageNotFoundInModel() {
    IController c = new ControllerImpl(m, view,
            new StringReader("save testImage/new.ppm new"));
    c.process();
    assertEquals("Cannot save the image.\n", log.toString());
  }

  @Test
  public void testSaveValid() {
    IController c = new ControllerImpl(m, view,
            new StringReader("save testImage/new.ppm three"));
    c.process();
    assertEquals("Command executed.\n", log.toString());
  }

  @Test
  public void testBrightMissingNextInt() {
    IController c = new ControllerImpl(m, view, new StringReader("brighten "));
    c.process();
    assertEquals("Brighten/Darken increment should be an integer.\n", log.toString());
  }

  @Test
  public void testBrightMissingArg1() {
    IController c = new ControllerImpl(m, view,
            new StringReader("brighten 10"));
    c.process();
    assertEquals("Cannot brighten/darken the image.\n", log.toString());
  }

  @Test
  public void testBrightMissingArg2() {
    IController c = new ControllerImpl(m, view,
            new StringReader("brighten 10 three"));
    c.process();
    assertEquals("Cannot brighten/darken the image.\n", log.toString());
  }

  @Test
  public void testBrightImageNotFound() {
    IController c = new ControllerImpl(m, view,
            new StringReader("brighten 10 new three-brighter"));
    c.process();
    assertEquals("Cannot brighten/darken the image.\n", log.toString());
  }

  @Test
  public void testValidBrighterImage() {
    IController c = new ControllerImpl(m, view,
            new StringReader("brighten 100 three three-brighter"));
    c.process();
    assertEquals("Command executed.\n", log.toString());

    IImageState brighterImage = m.getImage("three-brighter");
    assertEquals(3, brighterImage.getHeight());
    assertEquals(3, brighterImage.getWidth());
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
  }

  @Test
  public void testValidDarkerImage() {
    IController c = new ControllerImpl(m, view,
            new StringReader("brighten -100 three three-darker"));
    c.process();
    assertEquals("Command executed.\n", log.toString());

    IImageState darkerImage = m.getImage("three-darker");
    assertEquals(3, darkerImage.getHeight());
    assertEquals(3, darkerImage.getWidth());
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
  }

  @Test
  public void testGreyscaleMissingArg1() {
    IController c = new ControllerImpl(m, view,
            new StringReader("red-component"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleMissingArg2() {
    IController c = new ControllerImpl(m, view,
            new StringReader("red-component three"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleImageNotInModel() {
    IController c = new ControllerImpl(m, view,
            new StringReader("red-component new three-red"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleGMissingArg1() {
    IController c = new ControllerImpl(m, view,
            new StringReader("green-component"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleGMissingArg2() {
    IController c = new ControllerImpl(m, view,
            new StringReader("green-component three"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleGImageNotInModel() {
    IController c = new ControllerImpl(m, view,
            new StringReader("green-component new three-red"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleBMissingArg1() {
    IController c = new ControllerImpl(m, view,
            new StringReader("blue-component"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleBMissingArg2() {
    IController c = new ControllerImpl(m, view,
            new StringReader("blue-component three"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleBImageNotInModel() {
    IController c = new ControllerImpl(m, view,
            new StringReader("blue-component new three-red"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleVMissingArg1() {
    IController c = new ControllerImpl(m, view,
            new StringReader("value-component"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleVMissingArg2() {
    IController c = new ControllerImpl(m, view,
            new StringReader("value-component three"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleVImageNotInModel() {
    IController c = new ControllerImpl(m, view,
            new StringReader("value-component new three-red"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleIMissingArg1() {
    IController c = new ControllerImpl(m, view,
            new StringReader("intensity-component"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleIMissingArg2() {
    IController c = new ControllerImpl(m, view,
            new StringReader("intensity-component three"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleIImageNotInModel() {
    IController c = new ControllerImpl(m, view,
            new StringReader("intensity-component new three-red"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleLMissingArg1() {
    IController c = new ControllerImpl(m, view,
            new StringReader("luma-component"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleLMissingArg2() {
    IController c = new ControllerImpl(m, view,
            new StringReader("luma-component three"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testGreyscaleLImageNotInModel() {
    IController c = new ControllerImpl(m, view,
            new StringReader("luma-component new three-red"));
    c.process();
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testValidGreyscaleRed() {
    IController c = new ControllerImpl(m, view,
            new StringReader("red-component three three-red"));
    c.process();
    assertEquals("Command executed.\n", log.toString());

    IImageState redImage = m.getImage("three-red");
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
  }

  @Test
  public void testValidGreyscaleGreen() {
    IController c = new ControllerImpl(m, view,
            new StringReader("green-component three three-green"));
    c.process();
    assertEquals("Command executed.\n", log.toString());

    IImageState greenImage = m.getImage("three-green");
    assertEquals(3, greenImage.getHeight());
    assertEquals(3, greenImage.getWidth());
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
  }

  @Test
  public void testValidGreyscaleBlue() {
    IController c = new ControllerImpl(m, view,
            new StringReader("blue-component three three-blue"));
    c.process();
    assertEquals("Command executed.\n", log.toString());

    IImageState blueImage = m.getImage("three-blue");
    assertEquals(3, blueImage.getHeight());
    assertEquals(3, blueImage.getWidth());
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
  }

  @Test
  public void testValidGreyscaleValue() {
    IController c = new ControllerImpl(m, view,
            new StringReader("value-component three three-value"));
    c.process();
    assertEquals("Command executed.\n", log.toString());

    IImageState valueImage = m.getImage("three-value");
    assertEquals(3, valueImage.getHeight());
    assertEquals(3, valueImage.getWidth());
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
  }

  @Test
  public void testValidGreyscaleIntensity() {
    IController c = new ControllerImpl(m, view,
            new StringReader("intensity-component three three-intensity"));
    c.process();
    assertEquals("Command executed.\n", log.toString());

    IImageState intensityImage = m.getImage("three-intensity");
    assertEquals(3, intensityImage.getHeight());
    assertEquals(3, intensityImage.getWidth());
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
  }

  @Test
  public void testValidGreyscaleLuma() {
    IController c = new ControllerImpl(m, view,
            new StringReader("luma-component three three-luma"));
    c.process();
    assertEquals("Command executed.\n", log.toString());

    IImageState lumaImage = m.getImage("three-luma");
    assertEquals(3, lumaImage.getHeight());
    assertEquals(3, lumaImage.getWidth());
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
  }
}
