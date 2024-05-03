import org.junit.Before;
import org.junit.Test;

import controller.loadersaver.ConventionalLoader;
import controller.loadersaver.IImageLoader;
import controller.loadersaver.PPMLoader;
import model.images.IImageState;
import model.transformation.ITransformation;
import model.transformation.MatrixGreyscale;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the MatrixGreyscale class.
 */
public class MatrixGrayscaleTest {
  private IImageState sourcePPM;
  private IImageState sourcePNG;

  @Before
  public void setUp() {

    IImageLoader loaderPPM = new PPMLoader("testImage/three.ppm");
    sourcePPM = loaderPPM.loadImage();

    // three.png has same pixel channel values with ppm image
    IImageLoader loaderPNG = new ConventionalLoader("testImage/three.png");
    sourcePNG = loaderPNG.loadImage();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullMatrixGreyscale() {
    new MatrixGreyscale(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMatrixNotThreeByThree() {
    new MatrixGreyscale(new double[][]{
            {1.0 / 16, 1.0 / 8},
            {1.0 / 8, 1.0 / 4}
    });
  }

  @Test
  public void testSepiaPPM() {

    ITransformation sepia = new MatrixGreyscale(new double[][] {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}});

    IImageState sepiaPPM = sepia.run(sourcePPM);
    assertEquals(3, sepiaPPM.getHeight());
    assertEquals(3, sepiaPPM.getWidth());
    assertEquals(0, sepiaPPM.getRedChannel(0, 0));
    assertEquals(0, sepiaPPM.getGreenChannel(0, 0));
    assertEquals(0, sepiaPPM.getBlueChannel(0, 0));

    assertEquals(255, sepiaPPM.getRedChannel(1, 0));
    assertEquals(255, sepiaPPM.getGreenChannel(1, 0));
    assertEquals(239, sepiaPPM.getBlueChannel(1, 0));

    assertEquals(100, sepiaPPM.getRedChannel(2, 0));
    assertEquals(89, sepiaPPM.getGreenChannel(2, 0));
    assertEquals(69, sepiaPPM.getBlueChannel(2, 0));

    assertEquals(196, sepiaPPM.getRedChannel(0, 1));
    assertEquals(175, sepiaPPM.getGreenChannel(0, 1));
    assertEquals(136, sepiaPPM.getBlueChannel(0, 1));

    assertEquals(48, sepiaPPM.getRedChannel(1, 1));
    assertEquals(43, sepiaPPM.getGreenChannel(1, 1));
    assertEquals(33, sepiaPPM.getBlueChannel(1, 1));

    assertEquals(88, sepiaPPM.getRedChannel(2, 1));
    assertEquals(78, sepiaPPM.getGreenChannel(2, 1));
    assertEquals(61, sepiaPPM.getBlueChannel(2, 1));

    assertEquals(211, sepiaPPM.getRedChannel(0, 2));
    assertEquals(188, sepiaPPM.getGreenChannel(0, 2));
    assertEquals(146, sepiaPPM.getBlueChannel(0, 2));

    assertEquals(206, sepiaPPM.getRedChannel(1, 2));
    assertEquals(184, sepiaPPM.getGreenChannel(1, 2));
    assertEquals(143, sepiaPPM.getBlueChannel(1, 2));

    assertEquals(38, sepiaPPM.getRedChannel(2, 2));
    assertEquals(33, sepiaPPM.getGreenChannel(2, 2));
    assertEquals(26, sepiaPPM.getBlueChannel(2, 2));
  }

  @Test
  public void testLumaPNG() {

    ITransformation luma = new MatrixGreyscale(new double[][] {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    });

    IImageState lumaImage = luma.run(sourcePNG);
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
  public void testIntensityPNG() {

    ITransformation intensity = new MatrixGreyscale(new double[][] {
            {1.0 / 3, 1.0 / 3, 1.0 / 3},
            {1.0 / 3, 1.0 / 3, 1.0 / 3},
            {1.0 / 3, 1.0 / 3, 1.0 / 3}
    });

    IImageState intensityImage = intensity.run(sourcePNG);
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
  public void testRedPNG() {
    ITransformation red = new MatrixGreyscale(new double[][] {
            {1, 0, 0},
            {1, 0, 0},
            {1, 0, 0}
    });

    IImageState redImage = red.run(sourcePNG);
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
  public void testGreenPNG() {
    ITransformation green = new MatrixGreyscale(new double[][] {
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0}
    });

    IImageState greenImage = green.run(sourcePNG);
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
  public void testBluePNG() {
    ITransformation green = new MatrixGreyscale(new double[][] {
            {0, 0, 1},
            {0, 0, 1},
            {0, 0, 1}
    });

    IImageState blueImage = green.run(sourcePNG);
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
