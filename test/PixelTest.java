import org.junit.Before;
import org.junit.Test;

import model.pixels.IPixel;
import model.pixels.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the Pixel class.
 */
public class PixelTest {
  private IPixel red;
  private IPixel green;
  private IPixel blue;
  private IPixel white;
  private IPixel black;
  private IPixel random;
  private IPixel random2;

  @Before
  public void setUp() {
    red = new Pixel(255, 0, 0);
    green = new Pixel(0, 255 , 0);
    blue = new Pixel(0, 0, 255);
    white = new Pixel(255, 255, 255);
    black = new Pixel(0, 0, 0);
    random = new Pixel(1, 254, 123);
    random2 = new Pixel(22, 203, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRedOutOfBounds() {
    new Pixel(-1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreenOutOfBounds() {
    new Pixel(0, 256, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlueOutOfBounds() {
    new Pixel(0, 0, -10);
  }

  @Test
  public void testGetR() {
    assertEquals(255, red.getR());
    assertEquals(0, green.getR());
    assertEquals(0, blue.getR());
    assertEquals(255, white.getR());
    assertEquals(0, black.getR());
    assertEquals(1, random.getR());
    assertEquals(22, random2.getR());
  }

  @Test
  public void testGetG() {
    assertEquals(0, red.getG());
    assertEquals(255, green.getG());
    assertEquals(0, blue.getG());
    assertEquals(255, white.getG());
    assertEquals(0, black.getG());
    assertEquals(254, random.getG());
    assertEquals(203, random2.getG());
  }

  @Test
  public void testGetB() {
    assertEquals(0, red.getB());
    assertEquals(0, green.getB());
    assertEquals(255, blue.getB());
    assertEquals(255, white.getB());
    assertEquals(0, black.getB());
    assertEquals(123, random.getB());
    assertEquals(100, random2.getB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetRedOutOfBounds() {
    red.setR(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetGreenOutOfBounds() {
    green.setG(256);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetBlueOutOfBounds() {
    blue.setB(258);
  }

  @Test
  public void testSetR() {
    red.setR(100);
    assertEquals(100, red.getR());

    white.setR(0);
    assertEquals(0, white.getR());

    random.setR(255);
    assertEquals(255, random.getR());
  }

  @Test
  public void testSetG() {
    green.setG(100);
    assertEquals(100, green.getG());

    black.setG(255);
    assertEquals(255, black.getG());

    random.setG(0);
    assertEquals(0, random.getG());
  }

  @Test
  public void testSetB() {
    blue.setB(100);
    assertEquals(100, blue.getB());

    black.setB(0);
    assertEquals(0, black.getB());

    random.setB(12);
    assertEquals(12, random.getB());

    random.setB(255);
    assertEquals(255, random.getB());
  }
}
