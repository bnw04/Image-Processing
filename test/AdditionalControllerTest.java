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
 * A JUnit test class for the additional Controller class tests.
 * Test all new added valid commands by getting the image and tests
 * all channel values at each pixel of the image. Test save command by
 * loading the saved image and tests all channel values at each pixel of the image.
 */
public class AdditionalControllerTest {

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

  @Test
  public void testNoExtensionLoad() {
    IController c = new ControllerImpl(m, view,
            new StringReader("load testImage/three new"));
    c.process();
    assertEquals("Cannot load the image.\n", log.toString());
  }

  @Test
  public void testNotSupportedLoad() {
    IController c = new ControllerImpl(m, view,
            new StringReader("load testImage/three.txt new"));
    c.process();
    assertEquals("Cannot load the image.\n", log.toString());
  }

  @Test
  public void testNoExtensionSave() {
    IController c = new ControllerImpl(m, view,
            new StringReader("save testImage/three new"));
    c.process();
    assertEquals("Cannot save the image.\n", log.toString());
  }

  @Test
  public void testNotSupportedSave() {
    IController c = new ControllerImpl(m, view,
            new StringReader("save testImage/three.txt new"));
    c.process();
    assertEquals("Cannot save the image.\n", log.toString());
  }

  @Test
  public void testValidLoadPNG() {
    IController c = new ControllerImpl(m, view,
            new StringReader("load testImage/three.png new"));
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
  public void testValidLoadBMP() {
    IController c = new ControllerImpl(m, view,
            new StringReader("load testImage/three.bmp new"));
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
  public void testValidLoadJPG() {
    IController c = new ControllerImpl(m, view,
            new StringReader("load testImage/three.jpg new"));
    c.process();
    assertEquals("Command executed.\n", log.toString());

    IImageState loaded = m.getImage("new");
    assertEquals(3, loaded.getHeight());
    assertEquals(3, loaded.getWidth());
  }

  @Test
  public void testSaveValidPNG() {
    IController c = new ControllerImpl(m, view,
            new StringReader("save testImage/new.png three"));
    c.process();
    assertEquals("Command executed.\n", log.toString());
  }

  @Test
  public void testSaveValidBMP() {
    IController c = new ControllerImpl(m, view,
            new StringReader("save testImage/new.bmp three"));
    c.process();
    assertEquals("Command executed.\n", log.toString());
  }

  @Test
  public void testSaveValidJPG() {
    IController c = new ControllerImpl(m, view,
            new StringReader("save testImage/new.jpg three"));
    c.process();
    assertEquals("Command executed.\n", log.toString());
  }

  @Test
  public void testValidLoadPNGSaveToPPM() {
    IController c = new ControllerImpl(m, view,
            new StringReader("load testImage/three.png three" +
                    "\nsave testImage/new.ppm three\nload testImage/new.ppm new"));
    c.process();
    assertEquals("Command executed.\n" +
            "Command executed.\nCommand executed.\n", log.toString());

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
  public void testValidLoadPPMSaveToPNG() {
    IController c = new ControllerImpl(m, view,
            new StringReader("load testImage/three.ppm three" +
                    "\nsave testImage/new.png three\nload testImage/new.png new"));
    c.process();
    assertEquals("Command executed.\n" +
            "Command executed.\nCommand executed.\n", log.toString());

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
  public void testMatrixMissingArg1() {
    IController c = new ControllerImpl(m, view,
            new StringReader("luma-grayscale"));
    c.process();
    assertEquals("Cannot use kernel to grayscale the image.\n", log.toString());
  }

  @Test
  public void testMatrixMissingArg2() {
    IController c = new ControllerImpl(m, view,
            new StringReader("sepia-grayscale three"));
    c.process();
    assertEquals("Cannot use kernel to grayscale the image.\n", log.toString());
  }

  @Test
  public void testMatrixImageNotInModel() {
    IController c = new ControllerImpl(m, view,
            new StringReader("sepia-grayscale new three-blur"));
    c.process();
    assertEquals("Cannot use kernel to grayscale the image.\n", log.toString());
  }

  @Test
  public void testBlurSharpenMissingArg1() {
    IController c = new ControllerImpl(m, view,
            new StringReader("blur"));
    c.process();
    assertEquals("Cannot use kernel to filter the image.\n", log.toString());
  }

  @Test
  public void testBlurSharpenMissingArg2() {
    IController c = new ControllerImpl(m, view,
            new StringReader("sharpen three"));
    c.process();
    assertEquals("Cannot use kernel to filter the image.\n", log.toString());
  }

  @Test
  public void testBlurSharpenImageNotInModel() {
    IController c = new ControllerImpl(m, view,
            new StringReader("blur new three-blur"));
    c.process();
    assertEquals("Cannot use kernel to filter the image.\n", log.toString());
  }

  @Test
  public void testValidBlur() {
    IController c = new ControllerImpl(m, view,
            new StringReader("blur three three-blur"));
    c.process();
    assertEquals("Command executed.\n", log.toString());

    IImageState blurImage = m.getImage("three-blur");
    assertEquals(3, blurImage.getHeight());
    assertEquals(3, blurImage.getWidth());

    assertEquals(32, blurImage.getRedChannel(0, 0));
    assertEquals(64, blurImage.getGreenChannel(0, 0));
    assertEquals(48, blurImage.getBlueChannel(0, 0));

    assertEquals(102, blurImage.getRedChannel(1, 0));
    assertEquals(80, blurImage.getGreenChannel(1, 0));
    assertEquals(112, blurImage.getBlueChannel(1, 0));

    assertEquals(108, blurImage.getRedChannel(2, 0));
    assertEquals(32, blurImage.getGreenChannel(2, 0));
    assertEquals(80, blurImage.getBlueChannel(2, 0));

    assertEquals(36, blurImage.getRedChannel(0, 1));
    assertEquals(112, blurImage.getGreenChannel(0, 1));
    assertEquals(83, blurImage.getBlueChannel(0, 1));

    assertEquals(74, blurImage.getRedChannel(1, 1));
    assertEquals(100, blurImage.getGreenChannel(1, 1));
    assertEquals(170, blurImage.getBlueChannel(1, 1));

    assertEquals(79, blurImage.getRedChannel(2, 1));
    assertEquals(32, blurImage.getGreenChannel(2, 1));
    assertEquals(129, blurImage.getBlueChannel(2, 1));

    assertEquals(40, blurImage.getRedChannel(0, 2));
    assertEquals(96, blurImage.getGreenChannel(0, 2));
    assertEquals(87, blurImage.getBlueChannel(0, 2));

    assertEquals(34, blurImage.getRedChannel(1, 2));
    assertEquals(88, blurImage.getGreenChannel(1, 2));
    assertEquals(133, blurImage.getBlueChannel(1, 2));

    assertEquals(25, blurImage.getRedChannel(2, 2));
    assertEquals(31, blurImage.getGreenChannel(2, 2));
    assertEquals(83, blurImage.getBlueChannel(2, 2));
  }

  @Test
  public void testValidBlurTwice() {
    IController c = new ControllerImpl(m, view,
            new StringReader("blur three three-blur\n" +
                    "blur three-blur three-blur-twice"));
    c.process();
    assertEquals("Command executed.\nCommand executed.\n", log.toString());

    IImageState blurredTwice = m.getImage("three-blur-twice");
    assertEquals(3, blurredTwice.getHeight());
    assertEquals(3, blurredTwice.getWidth());

    assertEquals(30, blurredTwice.getRedChannel(0, 0));
    assertEquals(46, blurredTwice.getGreenChannel(0, 0));
    assertEquals(47, blurredTwice.getBlueChannel(0, 0));

    assertEquals(59, blurredTwice.getRedChannel(1, 0));
    assertEquals(54, blurredTwice.getGreenChannel(1, 0));
    assertEquals(79, blurredTwice.getBlueChannel(1, 0));

    assertEquals(54, blurredTwice.getRedChannel(2, 0));
    assertEquals(28, blurredTwice.getGreenChannel(2, 0));
    assertEquals(61, blurredTwice.getBlueChannel(2, 0));

    assertEquals(36, blurredTwice.getRedChannel(0, 1));
    assertEquals(71, blurredTwice.getGreenChannel(0, 1));
    assertEquals(74, blurredTwice.getBlueChannel(0, 1));

    assertEquals(63, blurredTwice.getRedChannel(1, 1));
    assertEquals(78, blurredTwice.getGreenChannel(1, 1));
    assertEquals(118, blurredTwice.getBlueChannel(1, 1));

    assertEquals(54, blurredTwice.getRedChannel(2, 1));
    assertEquals(39, blurredTwice.getGreenChannel(2, 1));
    assertEquals(89, blurredTwice.getBlueChannel(2, 1));

    assertEquals(23, blurredTwice.getRedChannel(0, 2));
    assertEquals(55, blurredTwice.getGreenChannel(0, 2));
    assertEquals(59, blurredTwice.getBlueChannel(0, 2));

    assertEquals(33, blurredTwice.getRedChannel(1, 2));
    assertEquals(59, blurredTwice.getGreenChannel(1, 2));
    assertEquals(89, blurredTwice.getBlueChannel(1, 2));

    assertEquals(25, blurredTwice.getRedChannel(2, 2));
    assertEquals(29, blurredTwice.getGreenChannel(2, 2));
    assertEquals(64, blurredTwice.getBlueChannel(2, 2));
  }

  @Test
  public void testValidSharpen() {
    IController c = new ControllerImpl(m, view,
            new StringReader("sharpen three three-sharpen"));
    c.process();
    assertEquals("Command executed.\n", log.toString());
    IImageState sharpImage = m.getImage("three-sharpen");
    assertEquals(3, sharpImage.getHeight());
    assertEquals(3, sharpImage.getWidth());

    assertEquals(3, sharpImage.getHeight());
    assertEquals(3, sharpImage.getWidth());
    assertEquals(0, sharpImage.getRedChannel(0, 0));
    assertEquals(80, sharpImage.getGreenChannel(0, 0));
    assertEquals(43, sharpImage.getBlueChannel(0, 0));

    assertEquals(255, sharpImage.getRedChannel(1, 0));
    assertEquals(255, sharpImage.getGreenChannel(1, 0));
    assertEquals(255, sharpImage.getBlueChannel(1, 0));

    assertEquals(255, sharpImage.getRedChannel(2, 0));
    assertEquals(0, sharpImage.getGreenChannel(2, 0));
    assertEquals(138, sharpImage.getBlueChannel(2, 0));

    assertEquals(55, sharpImage.getRedChannel(0, 1));
    assertEquals(255, sharpImage.getGreenChannel(0, 1));
    assertEquals(197, sharpImage.getBlueChannel(0, 1));

    assertEquals(205, sharpImage.getRedChannel(1, 1));
    assertEquals(223, sharpImage.getGreenChannel(1, 1));
    assertEquals(255, sharpImage.getBlueChannel(1, 1));

    assertEquals(222, sharpImage.getRedChannel(2, 1));
    assertEquals(69, sharpImage.getGreenChannel(2, 1));
    assertEquals(255, sharpImage.getBlueChannel(2, 1));

    assertEquals(77, sharpImage.getRedChannel(0, 2));
    assertEquals(235, sharpImage.getGreenChannel(0, 2));
    assertEquals(218, sharpImage.getBlueChannel(0, 2));

    assertEquals(22, sharpImage.getRedChannel(1, 2));
    assertEquals(255, sharpImage.getGreenChannel(1, 2));
    assertEquals(255, sharpImage.getBlueChannel(1, 2));

    assertEquals(0, sharpImage.getRedChannel(2, 2));
    assertEquals(0, sharpImage.getGreenChannel(2, 2));
    assertEquals(152, sharpImage.getBlueChannel(2, 2));
  }

  @Test
  public void testValidSharpenTwice() {
    IController c = new ControllerImpl(m, view,
            new StringReader("sharpen three three-sharpen\n" +
                    "sharpen three-sharpen three-sharpen-twice"));
    c.process();
    assertEquals("Command executed.\nCommand executed.\n", log.toString());
    IImageState sharpenedTwice = m.getImage("three-sharpen-twice");
    assertEquals(3, sharpenedTwice.getHeight());
    assertEquals(3, sharpenedTwice.getWidth());

    assertEquals(57, sharpenedTwice.getRedChannel(0, 0));
    assertEquals(193, sharpenedTwice.getGreenChannel(0, 0));
    assertEquals(93, sharpenedTwice.getBlueChannel(0, 0));

    assertEquals(255, sharpenedTwice.getRedChannel(1, 0));
    assertEquals(255, sharpenedTwice.getGreenChannel(1, 0));
    assertEquals(255, sharpenedTwice.getBlueChannel(1, 0));

    assertEquals(255, sharpenedTwice.getRedChannel(2, 0));
    assertEquals(34, sharpenedTwice.getGreenChannel(2, 0));
    assertEquals(221, sharpenedTwice.getBlueChannel(2, 0));

    assertEquals(135, sharpenedTwice.getRedChannel(0, 1));
    assertEquals(255, sharpenedTwice.getGreenChannel(0, 1));
    assertEquals(255, sharpenedTwice.getBlueChannel(0, 1));

    assertEquals(255, sharpenedTwice.getRedChannel(1, 1));
    assertEquals(255, sharpenedTwice.getGreenChannel(1, 1));
    assertEquals(255, sharpenedTwice.getBlueChannel(1, 1));

    assertEquals(255, sharpenedTwice.getRedChannel(2, 1));
    assertEquals(181, sharpenedTwice.getGreenChannel(2, 1));
    assertEquals(255, sharpenedTwice.getBlueChannel(2, 1));

    assertEquals(56, sharpenedTwice.getRedChannel(0, 2));
    assertEquals(255, sharpenedTwice.getGreenChannel(0, 2));
    assertEquals(255, sharpenedTwice.getBlueChannel(0, 2));

    assertEquals(98, sharpenedTwice.getRedChannel(1, 2));
    assertEquals(255, sharpenedTwice.getGreenChannel(1, 2));
    assertEquals(255, sharpenedTwice.getBlueChannel(1, 2));

    assertEquals(32, sharpenedTwice.getRedChannel(2, 2));
    assertEquals(34, sharpenedTwice.getGreenChannel(2, 2));
    assertEquals(237, sharpenedTwice.getBlueChannel(2, 2));
  }

  @Test
  public void testValidMatrixSepiaGreyscale() {
    IController c = new ControllerImpl(m, view,
            new StringReader("sepia-grayscale three three-sepia"));
    c.process();
    assertEquals("Command executed.\n", log.toString());

    IImageState sepiaImage = m.getImage("three-sepia");
    assertEquals(3, sepiaImage.getHeight());
    assertEquals(3, sepiaImage.getWidth());
    assertEquals(0, sepiaImage.getRedChannel(0, 0));
    assertEquals(0, sepiaImage.getGreenChannel(0, 0));
    assertEquals(0, sepiaImage.getBlueChannel(0, 0));

    assertEquals(255, sepiaImage.getRedChannel(1, 0));
    assertEquals(255, sepiaImage.getGreenChannel(1, 0));
    assertEquals(239, sepiaImage.getBlueChannel(1, 0));

    assertEquals(100, sepiaImage.getRedChannel(2, 0));
    assertEquals(89, sepiaImage.getGreenChannel(2, 0));
    assertEquals(69, sepiaImage.getBlueChannel(2, 0));

    assertEquals(196, sepiaImage.getRedChannel(0, 1));
    assertEquals(175, sepiaImage.getGreenChannel(0, 1));
    assertEquals(136, sepiaImage.getBlueChannel(0, 1));

    assertEquals(48, sepiaImage.getRedChannel(1, 1));
    assertEquals(43, sepiaImage.getGreenChannel(1, 1));
    assertEquals(33, sepiaImage.getBlueChannel(1, 1));

    assertEquals(88, sepiaImage.getRedChannel(2, 1));
    assertEquals(78, sepiaImage.getGreenChannel(2, 1));
    assertEquals(61, sepiaImage.getBlueChannel(2, 1));

    assertEquals(211, sepiaImage.getRedChannel(0, 2));
    assertEquals(188, sepiaImage.getGreenChannel(0, 2));
    assertEquals(146, sepiaImage.getBlueChannel(0, 2));

    assertEquals(206, sepiaImage.getRedChannel(1, 2));
    assertEquals(184, sepiaImage.getGreenChannel(1, 2));
    assertEquals(143, sepiaImage.getBlueChannel(1, 2));

    assertEquals(38, sepiaImage.getRedChannel(2, 2));
    assertEquals(33, sepiaImage.getGreenChannel(2, 2));
    assertEquals(26, sepiaImage.getBlueChannel(2, 2));
  }

  @Test
  public void testValidMatrixGreyscaleRed() {
    IController c = new ControllerImpl(m, view,
            new StringReader("red-grayscale three three-red"));
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
  public void testValidMatrixGreyscaleGreen() {
    IController c = new ControllerImpl(m, view,
            new StringReader("green-grayscale three three-green"));
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
  public void testValidMatrixGreyscaleBlue() {
    IController c = new ControllerImpl(m, view,
            new StringReader("blue-grayscale three three-blue"));
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
  public void testValidGreyscaleIntensity() {
    IController c = new ControllerImpl(m, view,
            new StringReader("intensity-grayscale three three-intensity"));
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

    assertEquals(27, intensityImage.getRedChannel(2, 2));
    assertEquals(27, intensityImage.getGreenChannel(2, 2));
    assertEquals(27, intensityImage.getBlueChannel(2, 2));
  }

  @Test
  public void testValidGreyscaleLuma() {
    IController c = new ControllerImpl(m, view,
            new StringReader("luma-grayscale three three-luma"));
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
