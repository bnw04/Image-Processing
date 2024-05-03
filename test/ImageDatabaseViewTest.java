import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.images.IImage;
import model.IImageDatabase;
import model.images.IImageState;
import model.ImageDatabase;
import model.images.ImageImpl;
import view.IView;
import view.View;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the ImageDatabase and View class.
 */
public class ImageDatabaseViewTest {

  private IImage large;
  private IImage small;
  private IImageDatabase m;
  private IView view;
  private StringBuilder log;

  @Before
  public void setUp() {
    large = new ImageImpl(768, 1024);
    small = new ImageImpl(1, 2);
    small.setPixel(0, 0, 0, 255, 0);
    small.setPixel(0, 1, 3, 12, 203);

    m = new ImageDatabase();
    log = new StringBuilder();
    view = new View(log);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullId() {
    m.addImage(null, large);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullImage() {
    m.addImage("id", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNullId() {
    m.getImage(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetInvalidId() {
    m.addImage("id", small);
    m.getImage("idd");
  }

  @Test
  public void testAddGetImage() {
    m.addImage("small", small);
    m.addImage("large", large);
    m.addImage("small2", small);

    assertEquals(small, m.getImage("small"));

    // get the image added and get channel values at each pixel
    IImageState getSmall = m.getImage("small");
    assertEquals(1, getSmall.getWidth());
    assertEquals(2, getSmall.getHeight());
    assertEquals(0, getSmall.getRedChannel(0, 0));
    assertEquals(255, getSmall.getGreenChannel(0, 0));
    assertEquals(0, getSmall.getBlueChannel(0, 0));
    assertEquals(3, getSmall.getRedChannel(0, 1));
    assertEquals(12, getSmall.getGreenChannel(0, 1));
    assertEquals(203, getSmall.getBlueChannel(0, 1));
  }

  @Test
  public void testAddGetImage2() {
    IImage three = new ImageImpl(3, 3);
    three.setPixel(0 ,0, 0, 0, 0);
    three.setPixel(0 ,1, 255, 255, 255);
    three.setPixel(0 ,2, 255, 0, 0);
    three.setPixel(1 ,0, 0, 255, 0);
    three.setPixel(1 ,1, 0, 0, 255);
    three.setPixel(1 ,2, 100, 1, 254);
    three.setPixel(2 ,0, 111, 111, 111);
    three.setPixel(2 ,1, 10, 200, 255);
    three.setPixel(2 ,2, 12, 23, 45);
    m.addImage("small", small);
    m.addImage("THREE", three);

    IImageState getThree = m.getImage("THREE");
    assertEquals(three, getThree);

    assertEquals(3, getThree.getHeight());
    assertEquals(3, getThree.getWidth());
    assertEquals(3, getThree.getWidth());
    assertEquals(3, getThree.getHeight());

    assertEquals(0, getThree.getRedChannel(0, 0));
    assertEquals(0, getThree.getGreenChannel(0, 0));
    assertEquals(0, getThree.getBlueChannel(0, 0));

    assertEquals(255, getThree.getRedChannel(0, 1));
    assertEquals(255, getThree.getGreenChannel(0, 1));
    assertEquals(255, getThree.getBlueChannel(0, 1));

    assertEquals(255, getThree.getRedChannel(0, 2));
    assertEquals(0, getThree.getGreenChannel(0, 2));
    assertEquals(0, getThree.getBlueChannel(0, 2));

    assertEquals(0, getThree.getRedChannel(1, 0));
    assertEquals(255, getThree.getGreenChannel(1, 0));
    assertEquals(0, getThree.getBlueChannel(1, 0));

    assertEquals(0, getThree.getRedChannel(1, 1));
    assertEquals(0, getThree.getGreenChannel(1, 1));
    assertEquals(255, getThree.getBlueChannel(1, 1));

    assertEquals(100, getThree.getRedChannel(1, 2));
    assertEquals(1, getThree.getGreenChannel(1, 2));
    assertEquals(254, getThree.getBlueChannel(1, 2));

    assertEquals(111, getThree.getRedChannel(2, 0));
    assertEquals(111, getThree.getGreenChannel(2, 0));
    assertEquals(111, getThree.getBlueChannel(2, 0));

    assertEquals(10, getThree.getRedChannel(2, 1));
    assertEquals(200, getThree.getGreenChannel(2, 1));
    assertEquals(255, getThree.getBlueChannel(2, 1));

    assertEquals(12, getThree.getRedChannel(2, 2));
    assertEquals(23, getThree.getGreenChannel(2, 2));
    assertEquals(45, getThree.getBlueChannel(2, 2));
  }

  @Test
  public void testAddImageSameId() {
    m.addImage("small", small);
    m.addImage("large", large);
    m.addImage("small2", small);

    IImageState getSmall = m.getImage("small");
    IImageState getSmall2 = m.getImage("small2");
    assertEquals(1, getSmall.getWidth());
    assertEquals(2, getSmall.getHeight());
    assertEquals(1, getSmall2.getWidth());
    assertEquals(2, getSmall2.getHeight());

    m.addImage("large", small);

    // get the image added with same id
    // and get channel values at each pixel
    IImageState getLarge = m.getImage("large");
    assertEquals(1, getLarge.getWidth());
    assertEquals(2, getLarge.getHeight());
    assertEquals(0, getLarge.getRedChannel(0, 0));
    assertEquals(255, getLarge.getGreenChannel(0, 0));
    assertEquals(0, getLarge.getBlueChannel(0, 0));
    assertEquals(3, getLarge.getRedChannel(0, 1));
    assertEquals(12, getLarge.getGreenChannel(0, 1));
    assertEquals(203, getLarge.getBlueChannel(0, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullOut() {
    new View(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRenderNull() {
    try {
      view.showMsg(null);
    } catch (IllegalStateException e) {
      Assert.fail("Fail to append.");
    }
  }

  @Test
  public void testViewRenderFailIOException() {
    Appendable failingA = new FailingAppendable();
    IView viewFail = new View(failingA);
    try {
      viewFail.showMsg("Fail to append.");
    } catch (IllegalStateException e) {
      assertEquals("Invalid message.", e.getMessage());
    }
  }

  @Test
  public void testViewRenderMsg() {
    try {
      view.showMsg("render message\n");
    } catch (IllegalStateException e) {
      Assert.fail("Fail to append.");
    }
    assertEquals("render message\n", log.toString());

    try {
      view.showMsg("image\n");
    } catch (IllegalStateException e) {
      Assert.fail("Fail to append.");
    }

    assertEquals("render message\nimage\n", log.toString());
  }
}
