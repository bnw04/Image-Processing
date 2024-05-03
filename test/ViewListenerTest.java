import org.junit.Before;
import org.junit.Test;

import controller.ControllerGraphic;
import model.IImageDatabase;
import model.ImageDatabase;
import view.IView;
import view.View;
import view.ViewListener;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the ViewListener/Controller class.
 * Using text based View object to test the graphic controller
 */
public class ViewListenerTest {

  private StringBuilder log;
  private ViewListener c;

  @Before
  public void setUp() {
    IImageDatabase m = new ImageDatabase();
    log = new StringBuilder();
    IView view = new View(log);
    c = new ControllerGraphic(m, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullLoad() {
    c.handleLoadEvent(null);
  }

  @Test
  public void testNoExtensionLoad() {
    c.handleLoadEvent("./testImage/three new");
    assertEquals("Cannot load the image.\n", log.toString());
  }

  @Test
  public void testNoValidExtensionLoad() {
    c.handleLoadEvent("./testImage/three.txt new");
    assertEquals("Cannot load the image.\n", log.toString());
  }

  @Test
  public void testHandleLoadPPM() {
    c.handleLoadEvent("./testImage/three.ppm");
    assertEquals("0 0 0\n" +
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
  public void testHandleLoadPNG() {
    c.handleLoadEvent("./testImage/three.png");
    assertEquals("0 0 0\n" +
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
  public void testHandleLoadBMP() {
    c.handleLoadEvent("./testImage/three.bmp");
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n", log.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullBrighten() {
    c.handleBrightenEvent(null);
  }

  @Test
  public void testNoLoadBrighten() {
    c.handleBrightenEvent("100");
    assertEquals("Cannot brighten/darken the image.\n", log.toString());
  }

  @Test
  public void testNotIntegerBrighten() {
    c.handleLoadEvent("./testImage/three.bmp");
    c.handleBrightenEvent("a1");
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
            "Brighten/Darken increment should be an integer.\n", log.toString());
  }

  @Test
  public void testHandleBrighten() {
    c.handleLoadEvent("./testImage/three.bmp");
    c.handleBrightenEvent("100");

    // loaded pixels followed by the brightened pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
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
  public void testHandleDarkened() {
    c.handleLoadEvent("./testImage/three.bmp");
    c.handleBrightenEvent("-100");

    // loaded pixels followed by the darkened pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
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

  @Test(expected = IllegalArgumentException.class)
  public void testNullFilter() {
    c.handleFiltersEvent(null);
  }

  @Test
  public void testNoLoadFilter1() {
    c.handleFiltersEvent("luma-grayscale");
    assertEquals("Cannot use kernel to grayscale the image.\n", log.toString());
  }

  @Test
  public void testNoLoadFilter2() {
    c.handleFiltersEvent("red-component");
    assertEquals("Cannot greyscale the image.\n", log.toString());
  }

  @Test
  public void testNoLoadFilter3() {
    c.handleFiltersEvent("blur");
    assertEquals("Cannot use kernel to filter the image.\n", log.toString());
  }

  @Test
  public void testBlurFilter() {
    c.handleLoadEvent("./testImage/three.bmp");
    c.handleFiltersEvent("blur");

    // loaded pixels followed by the blur pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
            "32 64 48\n" +
            "102 80 112\n" +
            "108 32 80\n" +
            "36 112 83\n" +
            "74 100 170\n" +
            "79 32 129\n" +
            "40 96 87\n" +
            "34 88 133\n" +
            "25 31 83\n", log.toString());
  }

  @Test
  public void testSharpenFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("sharpen");

    // loaded pixels followed by the sharpen pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
            "0 80 43\n" +
            "255 255 255\n" +
            "255 0 138\n" +
            "55 255 197\n" +
            "205 223 255\n" +
            "222 69 255\n" +
            "77 235 218\n" +
            "22 255 255\n" +
            "0 0 152\n", log.toString());
  }

  @Test
  public void testRedFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("red-component");

    // loaded pixels followed by the red component grayscale pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
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
  public void testGreenFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("green-component");

    // loaded pixels followed by the green component grayscale pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
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
  public void testBlueFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("blue-component");

    // loaded pixels followed by the blue component grayscale pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
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
  public void testValueFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("value-component");

    // loaded pixels followed by the value component grayscale pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
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
  public void testIntensityFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("intensity-component");

    // loaded pixels followed by the intensity component grayscale pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
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
  public void testLumaFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("luma-component");

    // loaded pixels followed by the intensity component grayscale pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
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
  public void testSepiaFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("sepia-grayscale");

    // loaded pixels followed by the sepia toned pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
            "0 0 0\n" +
            "255 255 239\n" +
            "100 89 69\n" +
            "196 175 136\n" +
            "48 43 33\n" +
            "88 78 61\n" +
            "211 188 146\n" +
            "206 184 143\n" +
            "38 33 26\n", log.toString());
  }

  @Test
  public void testRedMatrixFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("red-grayscale");

    // loaded pixels followed by the green grayscale matrix pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
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
  public void testGreenMatrixFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("green-grayscale");

    // loaded pixels followed by the red grayscale matrix pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
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
  public void testBlueMatrixFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("blue-grayscale");

    // loaded pixels followed by the blue grayscale matrix pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
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
  public void testIntensityMatrixFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("intensity-grayscale");

    // loaded pixels followed by the intensity grayscale matrix pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
            "0 0 0\n" +
            "255 255 255\n" +
            "85 85 85\n" +
            "85 85 85\n" +
            "85 85 85\n" +
            "118 118 118\n" +
            "156 156 156\n" +
            "155 155 155\n" +
            "27 27 27\n", log.toString());
  }

  @Test
  public void testLumaMatrixFilter() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("luma-grayscale");

    // loaded pixels followed by the luma grayscale matrix pixels
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
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
  public void testSaveInvalidExtension() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleSaveEvent("testImage/saved.pn");
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
            "Cannot save the image.\n", log.toString());
  }

  @Test
  public void testSave() {
    c.handleLoadEvent("./testImage/three.png");
    c.handleFiltersEvent("luma-grayscale");
    c.handleSaveEvent("testImage/saved.png");

    // load back the saved image
    // the string contains first loaded image, luma-grayscale image
    // and re-loaded luma-grayscale image pixels
    c.handleLoadEvent("./testImage/saved.png");
    assertEquals("0 0 0\n" +
            "255 255 255\n" +
            "255 0 0\n" +
            "0 255 0\n" +
            "0 0 255\n" +
            "100 1 254\n" +
            "156 156 156\n" +
            "10 200 255\n" +
            "45 23 12\n" +
            "0 0 0\n" +
            "255 255 255\n" +
            "54 54 54\n" +
            "182 182 182\n" +
            "18 18 18\n" +
            "40 40 40\n" +
            "156 156 156\n" +
            "164 164 164\n" +
            "27 27 27\n" +
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
}
