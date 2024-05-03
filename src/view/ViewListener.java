package view;

/**
 * The ViewListener interface defines the contract for classes that wish to listen
 * to actions and events from the view. Implementing classes should handle specific
 * events such as image loading, image brightening, applying filters, and saving images.
 * These events are triggered by the view and processed by the implementing class.
 */
public interface ViewListener {

  /**
   * Handles the event when an image is requested to be loaded from the give file path.
   *
   * @param filepath The file path of the image to be loaded.
   */
  void handleLoadEvent(String filepath);

  /**
   * Handles the event when the brightness of the current image shown on the view
   * is requested to be adjusted.
   *
   * @param increment The increment by which to brighten or darken the image.
   */
  void handleBrightenEvent(String increment);

  /**
   * Handles the event when a specific filter is requested to be applied
   * to the current shown image on the view.
   *
   * @param filterName The name of the filter to be applied to the image.
   */
  void handleFiltersEvent(String filterName);

  /**
   * Handles the event when the image shown on the view is requested to be saved.
   *
   * @param filepath The file path where the modified image should be saved.
   */
  void handleSaveEvent(String filepath);

}
