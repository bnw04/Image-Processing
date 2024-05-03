import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.Scanner;

import controller.commands.CmdBrighten;
import controller.commands.CmdGreyscale;
import controller.commands.CmdLoad;
import controller.commands.CmdSave;
import controller.commands.ICmd;
import controller.loadersaver.PPMLoader;
import model.IImageDatabase;
import model.ImageDatabase;
import model.images.IImageState;
import model.transformation.GreyscaleBlue;
import model.transformation.GreyscaleGreen;
import model.transformation.GreyscaleIntensity;
import model.transformation.GreyscaleLuma;
import model.transformation.GreyscaleRed;
import model.transformation.GreyscaleValue;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the all command classes.
 * Test load, brighter, greyscale commands by getting the image and tests
 * all channel values at each pixel of the image. Test save command by
 * loading the saved image and tests all channel values at each pixel of the image.
 */
public class CmdTest {
  private IImageDatabase m;
  private ICmd loadCmd;
  private ICmd saveCmd;
  private ICmd brightenCmd;
  private ICmd redGreyCmd;

  @Before
  public void setUp() {
    m = new ImageDatabase();
    IImageState image = new PPMLoader("testImage/three.ppm").loadImage();
    m.addImage("three", image);

    loadCmd = new CmdLoad();
    saveCmd = new CmdSave();
    brightenCmd = new CmdBrighten();
    redGreyCmd = new CmdGreyscale(new GreyscaleRed());
  }

  @Test(expected = IllegalStateException.class)
  public void testLoadCmdNoSecondArg() {
    Readable in = new StringReader("load ");
    Scanner scan = new Scanner(in);
    assertEquals("load", scan.next());
    loadCmd.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testLoadCmdNoThirdArg() {
    Readable in = new StringReader("load testImage/three.ppm");
    Scanner scan = new Scanner(in);
    assertEquals("load", scan.next());
    loadCmd.execute(scan, m);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadCmdNullScan() {
    loadCmd.execute(null, m);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadCmdNullModel() {
    Readable in = new StringReader("l");
    Scanner scan = new Scanner(in);
    loadCmd.execute(scan, null);
  }

  @Test
  public void testValidLoadPPMCmd() {
    Readable inLoad = new StringReader("load testImage/three.ppm new");
    Scanner scanLoad = new Scanner(inLoad);
    assertEquals("load", scanLoad.next());

    // in controller, when the cmd word matches, we pass the scanner to
    // the load cmd class run method
    loadCmd.execute(scanLoad, m);

    // then the image "three.ppm" should be added to the model
    // use getImage to test for the loaded image
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

  @Test(expected = IllegalStateException.class)
  public void testSaveCmdNoSecondArg() {
    Readable in = new StringReader("save ");
    Scanner scan = new Scanner(in);
    assertEquals("save", scan.next());
    loadCmd.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testSaveCmdNoThirdArg() {
    Readable in = new StringReader("save images/three.ppm ");
    Scanner scan = new Scanner(in);
    assertEquals("save", scan.next());
    loadCmd.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testSaveCmdNoIdInModel() {
    Readable in = new StringReader("save images/three.ppm idd");
    Scanner scan = new Scanner(in);
    assertEquals("save", scan.next());
    saveCmd.execute(scan, m);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveCmdNullScan() {
    saveCmd.execute(null, m);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveCmdNullModel() {
    Readable in = new StringReader("save testImage/three.ppm three");
    Scanner scan = new Scanner(in);
    saveCmd.execute(scan, null);
  }

  @Test
  public void testValidSavePPMCmd() {
    Readable inSave = new StringReader("save testImage/new.ppm three");
    Scanner scanSave = new Scanner(inSave);
    assertEquals("save", scanSave.next());
    saveCmd.execute(scanSave, m);

    IImageState saved = new PPMLoader("testImage/new.ppm").loadImage();
    assertEquals(3, saved.getHeight());
    assertEquals(3, saved.getWidth());
    assertEquals(0, saved.getRedChannel(0, 0));
    assertEquals(0, saved.getGreenChannel(0, 0));
    assertEquals(0, saved.getBlueChannel(0, 0));

    assertEquals(255, saved.getRedChannel(1, 0));
    assertEquals(255, saved.getGreenChannel(1, 0));
    assertEquals(255, saved.getBlueChannel(1, 0));

    assertEquals(255, saved.getRedChannel(2, 0));
    assertEquals(0, saved.getGreenChannel(2, 0));
    assertEquals(0, saved.getBlueChannel(2, 0));

    assertEquals(0, saved.getRedChannel(0, 1));
    assertEquals(255, saved.getGreenChannel(0, 1));
    assertEquals(0, saved.getBlueChannel(0, 1));

    assertEquals(0, saved.getRedChannel(1, 1));
    assertEquals(0, saved.getGreenChannel(1, 1));
    assertEquals(255, saved.getBlueChannel(1, 1));

    assertEquals(100, saved.getRedChannel(2, 1));
    assertEquals(1, saved.getGreenChannel(2, 1));
    assertEquals(254, saved.getBlueChannel(2, 1));

    assertEquals(156, saved.getRedChannel(0, 2));
    assertEquals(156, saved.getGreenChannel(0, 2));
    assertEquals(156, saved.getBlueChannel(0, 2));

    assertEquals(10, saved.getRedChannel(1, 2));
    assertEquals(200, saved.getGreenChannel(1, 2));
    assertEquals(255, saved.getBlueChannel(1, 2));

    assertEquals(45, saved.getRedChannel(2, 2));
    assertEquals(23, saved.getGreenChannel(2, 2));
    assertEquals(12, saved.getBlueChannel(2, 2));
  }

  @Test(expected = IllegalStateException.class)
  public void testBrightenCmdNoInt() {
    Readable in = new StringReader("brighten ten three newId");
    Scanner scan = new Scanner(in);
    assertEquals("brighten", scan.next());
    brightenCmd.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testBrightCmdNoSecondArg() {
    Readable in = new StringReader("brighten 10");
    Scanner scan = new Scanner(in);
    assertEquals("brighten", scan.next());
    brightenCmd.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testBrightCmdNoThirdArg() {
    Readable in = new StringReader("brighten 10 three");
    Scanner scan = new Scanner(in);
    assertEquals("brighten", scan.next());
    brightenCmd.execute(scan, m);
  }

  @Test
  public void testValidBrightenCmd() {
    Readable inBright = new StringReader("brighten 100 three three-brighter");

    Scanner scanBright = new Scanner(inBright);
    assertEquals("brighten", scanBright.next());
    brightenCmd.execute(scanBright, m);
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
  public void testValidDarkenCmd() {
    Readable inDark = new StringReader("brighten -100 three three-darker");
    Scanner scanDark = new Scanner(inDark);
    assertEquals("brighten", scanDark.next());
    brightenCmd.execute(scanDark, m);

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

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleCmdNoSecondArg() {
    Readable in = new StringReader("red-component ");
    Scanner scan = new Scanner(in);
    assertEquals("red-component", scan.next());
    redGreyCmd.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleCmdNoThirdArg() {
    Readable in = new StringReader("red-component three ");
    Scanner scan = new Scanner(in);
    assertEquals("red-component", scan.next());
    redGreyCmd.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleCmdNoIdInModel() {
    Readable in = new StringReader("red-component idd three-red");
    Scanner scan = new Scanner(in);
    assertEquals("red-component", scan.next());
    redGreyCmd.execute(scan, m);
  }

  @Test
  public void testValidRedGreyCmd() {
    Readable inRed = new StringReader("red-component three three-red");

    Scanner scanRed = new Scanner(inRed);
    assertEquals("red-component", scanRed.next());
    redGreyCmd.execute(scanRed, m);

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

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleGCmdNoSecondArg() {
    Readable in = new StringReader("green-component ");
    Scanner scan = new Scanner(in);
    assertEquals("green-component", scan.next());
    ICmd green = new CmdGreyscale(new GreyscaleGreen());
    green.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleGCmdNoThirdArg() {
    Readable in = new StringReader("green-component three ");
    Scanner scan = new Scanner(in);
    ICmd green = new CmdGreyscale(new GreyscaleGreen());
    green.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleGCmdNoIdInModel() {
    Readable in = new StringReader("green-component idd three-green");
    Scanner scan = new Scanner(in);
    assertEquals("green-component", scan.next());
    ICmd green = new CmdGreyscale(new GreyscaleGreen());
    green.execute(scan, m);
  }

  @Test
  public void testValidGreenGreyCmd() {
    ICmd greenGreyCmd = new CmdGreyscale(new GreyscaleGreen());
    Readable inGreen = new StringReader("green-component three three-green");
    Scanner scanGreen = new Scanner(inGreen);
    assertEquals("green-component", scanGreen.next());
    greenGreyCmd.execute(scanGreen, m);

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

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleBCmdNoSecondArg() {
    Readable in = new StringReader("blue-component ");
    Scanner scan = new Scanner(in);
    assertEquals("blue-component", scan.next());
    ICmd blue = new CmdGreyscale(new GreyscaleBlue());
    blue.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleBCmdNoThirdArg() {
    Readable in = new StringReader("blue-component three ");
    Scanner scan = new Scanner(in);
    assertEquals("blue-component", scan.next());
    ICmd blue = new CmdGreyscale(new GreyscaleBlue());
    blue.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleBCmdNoIdInModel() {
    Readable in = new StringReader("blue-component idd three-blue");
    Scanner scan = new Scanner(in);
    assertEquals("blue-component", scan.next());
    ICmd blue = new CmdGreyscale(new GreyscaleBlue());
    blue.execute(scan, m);
  }

  @Test
  public void testValidBlueGreyCmd() {
    ICmd blueGreyCmd = new CmdGreyscale(new GreyscaleBlue());
    Readable inBlue = new StringReader("blue-component three three-blue");
    Scanner scanBlue = new Scanner(inBlue);
    assertEquals("blue-component", scanBlue.next());
    blueGreyCmd.execute(scanBlue, m);

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

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleVCmdNoSecondArg() {
    Readable in = new StringReader("value-component ");
    Scanner scan = new Scanner(in);
    assertEquals("value-component", scan.next());
    ICmd value = new CmdGreyscale(new GreyscaleValue());
    value.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleVCmdNoThirdArg() {
    Readable in = new StringReader("value-component three ");
    Scanner scan = new Scanner(in);
    assertEquals("value-component", scan.next());
    ICmd value = new CmdGreyscale(new GreyscaleValue());
    value.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleVCmdNoIdInModel() {
    Readable in = new StringReader("value-component idd three-value");
    Scanner scan = new Scanner(in);
    assertEquals("value-component", scan.next());
    ICmd value = new CmdGreyscale(new GreyscaleValue());
    value.execute(scan, m);
  }

  @Test
  public void testValidValueGreyCmd() {
    ICmd valueGreyCmd = new CmdGreyscale(new GreyscaleValue());
    Readable inValue = new StringReader("value-component three three-value");
    Scanner scanValue = new Scanner(inValue);
    assertEquals("value-component", scanValue.next());
    valueGreyCmd.execute(scanValue, m);

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

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleICmdNoSecondArg() {
    Readable in = new StringReader("intensity-component ");
    Scanner scan = new Scanner(in);
    assertEquals("intensity-component", scan.next());
    ICmd intensity = new CmdGreyscale(new GreyscaleIntensity());
    intensity.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleICmdNoThirdArg() {
    Readable in = new StringReader("intensity-component three ");
    Scanner scan = new Scanner(in);
    assertEquals("intensity-component", scan.next());
    ICmd intensity = new CmdGreyscale(new GreyscaleIntensity());
    intensity.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleICmdNoIdInModel() {
    Readable in = new StringReader("intensity-component idd three-value");
    Scanner scan = new Scanner(in);
    assertEquals("intensity-component", scan.next());
    ICmd intensity = new CmdGreyscale(new GreyscaleIntensity());
    intensity.execute(scan, m);
  }

  @Test
  public void testValidIntensityGreyCmd() {
    ICmd intensityGreyCmd = new CmdGreyscale(new GreyscaleIntensity());
    Readable inIntensity = new StringReader("intensity-component three three-intensity");
    Scanner scanValue = new Scanner(inIntensity);
    assertEquals("intensity-component", scanValue.next());
    intensityGreyCmd.execute(scanValue, m);

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

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleLumaCmdNoSecondArg() {
    Readable in = new StringReader("luma-component ");
    Scanner scan = new Scanner(in);
    assertEquals("luma-component", scan.next());
    ICmd luma = new CmdGreyscale(new GreyscaleLuma());
    luma.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleLumaCmdNoThirdArg() {
    Readable in = new StringReader("luma-component three ");
    Scanner scan = new Scanner(in);
    assertEquals("luma-component", scan.next());
    ICmd luma = new CmdGreyscale(new GreyscaleLuma());
    luma.execute(scan, m);
  }

  @Test(expected = IllegalStateException.class)
  public void testGreyscaleLumaCmdNoIdInModel() {
    Readable in = new StringReader("luma-component idd three-luma");
    Scanner scan = new Scanner(in);
    assertEquals("luma-component", scan.next());
    ICmd luma = new CmdGreyscale(new GreyscaleLuma());
    luma.execute(scan, m);
  }

  @Test
  public void testValidLumaGreyCmd() {
    ICmd lumaGreyCmd = new CmdGreyscale(new GreyscaleLuma());
    Readable inLuma = new StringReader("luma-component three three-luma");
    Scanner scanLuma = new Scanner(inLuma);
    assertEquals("luma-component", scanLuma.next());
    lumaGreyCmd.execute(scanLuma, m);

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
