import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.Scanner;

import controller.commands.CmdGaussianBlur;
import controller.commands.CmdLoad;
import controller.commands.CmdMatrixBlue;
import controller.commands.CmdMatrixGreen;
import controller.commands.CmdMatrixIntensity;
import controller.commands.CmdMatrixLuma;
import controller.commands.CmdMatrixRed;
import controller.commands.CmdMatrixSepia;
import controller.commands.CmdSharpen;
import controller.commands.ICmd;
import controller.loadersaver.ConventionalLoader;
import model.IImageDatabase;
import model.ImageDatabase;
import model.images.IImageState;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the additional command classes.
 * Test blur, sharpen, matrix grayscale commands by getting the image and tests
 * all channel values at each pixel of the image. Test load command by
 * with jpg/bmp/png file format.
 */
public class AdditionalCmdTest {
  private IImageDatabase m;

  @Before
  public void setUp() {
    m = new ImageDatabase();
    IImageState image = new ConventionalLoader("testImage/three.png").loadImage();
    m.addImage("three", image);
  }


  @Test(expected = IllegalStateException.class)
  public void testLoadCmdNotSupported() {
    Readable inLoad = new StringReader("load testImage/three.txt new");
    Scanner scanLoad = new Scanner(inLoad);
    assertEquals("load", scanLoad.next());

    ICmd load = new CmdLoad();
    load.execute(scanLoad, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testSaveCmdNotSupported() {
    Readable inLoad = new StringReader("save testImage/three.txt three");
    Scanner scanLoad = new Scanner(inLoad);
    assertEquals("save", scanLoad.next());

    ICmd load = new CmdLoad();
    load.execute(scanLoad, m);
  }

  @Test
  public void testValidLoadPNGCmd() {
    Readable inLoad = new StringReader("load testImage/three.png new");
    Scanner scanLoad = new Scanner(inLoad);
    assertEquals("load", scanLoad.next());

    ICmd load = new CmdLoad();
    load.execute(scanLoad, m);

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
  public void testValidLoadBMPCmd() {
    Readable inLoad = new StringReader("load testImage/three.bmp new");
    Scanner scanLoad = new Scanner(inLoad);
    assertEquals("load", scanLoad.next());

    ICmd load = new CmdLoad();
    load.execute(scanLoad, m);

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
  public void testValidLoadJPGCmd() {
    Readable inLoad = new StringReader("load testImage/three.jpg new");
    Scanner scanLoad = new Scanner(inLoad);
    assertEquals("load", scanLoad.next());

    ICmd load = new CmdLoad();
    load.execute(scanLoad, m);

    IImageState loaded = m.getImage("new");
    assertEquals(3, loaded.getHeight());
    assertEquals(3, loaded.getWidth());
  }

  @Test(expected = IllegalStateException.class)
  public void testBlurCmdNoSecondArg() {
    Readable in = new StringReader("blur ");
    Scanner scan = new Scanner(in);
    assertEquals("blur", scan.next());
    ICmd blur = new CmdGaussianBlur();
    blur.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testBlurCmdNoThirdArg() {
    Readable in = new StringReader("blur three ");
    Scanner scan = new Scanner(in);
    assertEquals("blur", scan.next());
    ICmd blur = new CmdGaussianBlur();
    blur.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testBlurCmdNoIdInModel() {
    Readable in = new StringReader("blur idd three-blur");
    Scanner scan = new Scanner(in);
    assertEquals("blur", scan.next());
    ICmd blur = new CmdGaussianBlur();
    blur.execute(scan, m);
  }

  @Test
  public void testValidBlurCmd() {
    ICmd blurCmd = new CmdGaussianBlur();
    Readable inBlur = new StringReader("blur three three-blur");
    Scanner scanBlur = new Scanner(inBlur);
    assertEquals("blur", scanBlur.next());
    blurCmd.execute(scanBlur, m);

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

  @Test(expected = IllegalStateException.class)
  public void testSharpenCmdNoSecondArg() {
    Readable in = new StringReader("sharpen ");
    Scanner scan = new Scanner(in);
    assertEquals("sharpen", scan.next());
    ICmd sharpen = new CmdSharpen();
    sharpen.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testSharpenCmdNoThirdArg() {
    Readable in = new StringReader("sharpen three ");
    Scanner scan = new Scanner(in);
    assertEquals("sharpen", scan.next());
    ICmd sharpen = new CmdSharpen();
    sharpen.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testSharpenCmdNoIdInModel() {
    Readable in = new StringReader("sharpen idd three-sharpen");
    Scanner scan = new Scanner(in);
    assertEquals("sharpen", scan.next());
    ICmd sharpen = new CmdSharpen();
    sharpen.execute(scan, m);
  }

  @Test
  public void testValidSharpenCmd() {
    ICmd sharpCmd = new CmdSharpen();
    Readable inSharp = new StringReader("sharpen three three-sharp");
    Scanner scanSharp = new Scanner(inSharp);
    assertEquals("sharpen", scanSharp.next());
    sharpCmd.execute(scanSharp, m);

    IImageState sharpImage = m.getImage("three-sharp");
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
  public void testValidLumaCmd() {
    ICmd lumaCmd = new CmdMatrixLuma();
    Readable inLuma = new StringReader("luma-grayscale three three-luma");
    Scanner scanLuma = new Scanner(inLuma);
    assertEquals("luma-grayscale", scanLuma.next());
    lumaCmd.execute(scanLuma, m);

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

  @Test
  public void testValidSepiaCmd() {
    ICmd sepiaCmd = new CmdMatrixSepia();
    Readable inSepia = new StringReader("sepia-grayscale three three-sepia");
    Scanner scanSepia = new Scanner(inSepia);
    assertEquals("sepia-grayscale", scanSepia.next());
    sepiaCmd.execute(scanSepia, m);

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
  public void testValidIntensityCmd() {
    ICmd intensityCmd = new CmdMatrixIntensity();
    Readable in = new StringReader("intensity-grayscale three three-intensity");
    Scanner scan = new Scanner(in);
    assertEquals("intensity-grayscale", scan.next());
    intensityCmd.execute(scan, m);

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
  public void testValidRedCmd() {
    ICmd redCmd = new CmdMatrixRed();
    Readable in = new StringReader("red-grayscale three three-red");
    Scanner scan = new Scanner(in);
    assertEquals("red-grayscale", scan.next());
    redCmd.execute(scan, m);

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
  public void testValidGreenCmd() {
    ICmd greenCmd = new CmdMatrixGreen();
    Readable in = new StringReader("green-grayscale three three-green");
    Scanner scan = new Scanner(in);
    assertEquals("green-grayscale", scan.next());
    greenCmd.execute(scan, m);

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
  public void testValidBlueCmd() {
    ICmd blueCmd = new CmdMatrixBlue();
    Readable in = new StringReader("blue-grayscale three three-blue");
    Scanner scan = new Scanner(in);
    assertEquals("blue-grayscale", scan.next());
    blueCmd.execute(scan, m);

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
}
