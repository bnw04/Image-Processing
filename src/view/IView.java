package view;

import java.awt.image.BufferedImage;

/**
 * The IView interface represents a view for render messages.
 * View components has an output to an Appendable object for now.
 */
public interface IView {

  /**
   * The View object will show the given message.
   *
   * @param msg The message to be rendered to this view.
   */
  void showMsg(String msg);

  /**
   * The View object will be seen if it's graphic view.
   *
   * @param value When setting to true, graphic view will be seen.
   */
  void setVisible(boolean value);

  /**
   * If there's a listener object to this view, add it to this view.
   *
   * @param listener The ViewListener object to add to listen to
   *                 the actions of this view.
   */

  void addListeners(ViewListener listener);

  /**
   * To show the image on the view object.
   *
   * @param image The BufferedImage to be shown on this view.
   */
  void draw(BufferedImage image);
}
