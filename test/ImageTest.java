import org.junit.Before;
import org.junit.Test;

import model.images.IImage;
import model.images.ImageImpl;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the Image class.
 */
public class ImageTest {
  private IImage three;
  private IImage rec;
  private IImage rec2;
  private IImage large;

  @Before
  public void setUp() {
    three = new ImageImpl(3, 3);
    rec = new ImageImpl(1, 5);
    rec2 = new ImageImpl(6, 1);
    large = new ImageImpl(512, 1024);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWInvalid() {
    new ImageImpl(0, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHInvalid() {
    new ImageImpl(5, -5);
  }

  @Test
  public void testGetConstructor() {
    assertEquals(3, three.getHeight());
    assertEquals(3, three.getWidth());

    assertEquals(1024, large.getHeight());
    assertEquals(512, large.getWidth());

    assertEquals(1, rec2.getHeight());
    assertEquals(6, rec2.getWidth());
  }

  @Test
  public void testGetH() {
    assertEquals(5, rec.getHeight());
    assertEquals(1, rec2.getHeight());
    assertEquals(1024, large.getHeight());
  }

  @Test
  public void testGetW() {
    assertEquals(1, rec.getWidth());
    assertEquals(6, rec2.getWidth());
    assertEquals(512, large.getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRedGetWidthOutOfBounds() {
    large.getRedChannel(512, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRedGetHeightOutOfBounds() {
    large.getRedChannel(0, 1024);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreenGetWidthOutOfBounds() {
    rec.getGreenChannel(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreenGetHeightOutOfBounds() {
    rec.getGreenChannel(0, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlueGetWidthOutOfBounds() {
    rec.getBlueChannel(1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlueGetHeightOutOfBounds() {
    rec.getBlueChannel(0, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRNotSetYet() {
    large.getRedChannel(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGNotSetYet() {
    large.getGreenChannel(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBNotSetYet() {
    large.getBlueChannel(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetPixelWidthOut() {
    three.setPixel(-1, 0, 0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetPixelHeightOut() {
    three.setPixel(0, 3, 0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetPixelRedOut() {
    three.setPixel(0, 0, -1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetPixelGreenOut() {
    three.setPixel(0, 0, 0, 256, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetPixelBlueOut() {
    three.setPixel(0, 0, 0, 0, 256);
  }

  @Test
  public void testSetThreeGetChannel() {
    three.setPixel(0 ,0, 0, 0, 0);
    three.setPixel(0 ,1, 255, 255, 255);
    three.setPixel(0 ,2, 255, 0, 0);
    three.setPixel(1 ,0, 0, 255, 0);
    three.setPixel(1 ,1, 0, 0, 255);
    three.setPixel(1 ,2, 100, 1, 254);
    three.setPixel(2 ,0, 111, 111, 111);
    three.setPixel(2 ,1, 10, 200, 255);
    three.setPixel(2 ,2, 12, 23, 45);

    assertEquals(0, three.getRedChannel(0, 0));
    assertEquals(0, three.getGreenChannel(0, 0));
    assertEquals(0, three.getBlueChannel(0, 0));

    assertEquals(255, three.getRedChannel(0, 1));
    assertEquals(255, three.getGreenChannel(0, 1));
    assertEquals(255, three.getBlueChannel(0, 1));

    assertEquals(255, three.getRedChannel(0, 2));
    assertEquals(0, three.getGreenChannel(0, 2));
    assertEquals(0, three.getBlueChannel(0, 2));

    assertEquals(0, three.getRedChannel(1, 0));
    assertEquals(255, three.getGreenChannel(1, 0));
    assertEquals(0, three.getBlueChannel(1, 0));

    assertEquals(0, three.getRedChannel(1, 1));
    assertEquals(0, three.getGreenChannel(1, 1));
    assertEquals(255, three.getBlueChannel(1, 1));

    assertEquals(100, three.getRedChannel(1, 2));
    assertEquals(1, three.getGreenChannel(1, 2));
    assertEquals(254, three.getBlueChannel(1, 2));

    assertEquals(111, three.getRedChannel(2, 0));
    assertEquals(111, three.getGreenChannel(2, 0));
    assertEquals(111, three.getBlueChannel(2, 0));

    assertEquals(10, three.getRedChannel(2, 1));
    assertEquals(200, three.getGreenChannel(2, 1));
    assertEquals(255, three.getBlueChannel(2, 1));

    assertEquals(12, three.getRedChannel(2, 2));
    assertEquals(23, three.getGreenChannel(2, 2));
    assertEquals(45, three.getBlueChannel(2, 2));
  }
}
