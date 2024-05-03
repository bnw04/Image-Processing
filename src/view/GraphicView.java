package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * The GraphicView class is a graphical view that implements the IView and ActionListener
 * interface to graphically display images and show messages accordingly.
 * It extends the JFrame class to provide a graphical user interface.
 */
public class GraphicView extends JFrame implements IView, ActionListener {

  /**
   * The canvas object used to display the images.
   */
  private Canvas canvas;

  /**
   * A list of ViewListener objects that listen to the actions of this view.
   */
  private final List<ViewListener> listeners;

  /**
   * A set of valid image transformation names that can be applied to the loaded image.
   */
  private HashSet<String> validTransforms;

  /**
   * A boolean flag indicating whether an image has been loaded.
   */
  private boolean imageLoaded;

  /**
   * Constructor for the GraphicView class. Initializes the graphical user interface
   * components, sets up the menu bar, adds buttons and image display area.
   * And create a valid transformation set with all valid filter transformation names.
   */
  public GraphicView() {
    super();
    this.listeners = new ArrayList<>();
    this.setBackground(Color.GRAY);
    this.setTitle("Beini's IME graphical interface");
    this.setSize(1200, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    addFeatures();
    addValidTransforms();

    this.pack();

    // initially no image is loaded
    this.imageLoaded = false;
  }

  // helper method to start the graphical setup for this view
  private void addFeatures() {
    addMenuBar();
    addButton();
    addImageDisplay();
  }

  // add buttons to this view
  private void addButton() {
    JPanel features = new JPanel();
    BoxLayout boxlayout = new BoxLayout(features, BoxLayout.Y_AXIS);
    features.setLayout(boxlayout);
    features.setBounds(10, 10, 20, 100);
    features.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
    this.add(features, BorderLayout.WEST);

    JButton save = new JButton("Save");
    JButton load = new JButton("Load");
    JButton exit = new JButton("Exit");

    JLabel filters = new JLabel("Filters");
    JButton blur = new JButton("Blur");
    JButton sharpen = new JButton("Sharpen");
    JButton brighten = new JButton("Brighten/Darker");
    JButton sepia = new JButton("Sepia-tone");

    JLabel note = new JLabel("See more transformations in menu");

    features.add(load);
    features.add(save);
    features.add(exit);
    features.add(filters);
    features.add(blur);
    features.add(sharpen);
    features.add(brighten);
    features.add(sepia);
    features.add(note);

    save.setActionCommand("save");
    load.setActionCommand("load");
    blur.setActionCommand("blur");
    sharpen.setActionCommand("sharpen");
    brighten.setActionCommand("brighten");
    sepia.setActionCommand("sepia-grayscale");

    save.addActionListener(this);
    load.addActionListener(this);
    blur.addActionListener(this);
    sharpen.addActionListener(this);
    brighten.addActionListener(this);
    sepia.addActionListener(this);

    exit.addActionListener((ActionEvent e) -> System.exit(0));
  }

  // add image display panel to this view
  private void addImageDisplay() {
    this.canvas = new Canvas();
    JScrollPane display = new JScrollPane(this.canvas);
    display.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
    this.add(display, BorderLayout.CENTER);
  }

  // add menu bar to this view
  private void addMenuBar() {
    JMenuBar bar = new JMenuBar();
    this.setJMenuBar(bar);
    JMenu file = new JMenu("File");
    JMenu filter = new JMenu("Filter");
    JMenu colorTrans = new JMenu("Color Transformation");
    JMenu grayscale = new JMenu("Grayscale");
    bar.add(file);
    bar.add(filter);
    bar.add(colorTrans);
    bar.add(grayscale);

    JMenuItem loader = new JMenuItem("Load");
    JMenuItem saver = new JMenuItem("Save");
    file.add(loader);
    file.add(saver);
    saver.setActionCommand("save");
    loader.setActionCommand("load");
    saver.addActionListener(this);
    loader.addActionListener(this);

    JMenuItem blur = new JMenuItem("Blur");
    JMenuItem sharpen = new JMenuItem("Sharpen");
    JMenuItem brighterDarker = new JMenuItem("Brighten/Darken");
    filter.add(blur);
    filter.add(sharpen);
    filter.add(brighterDarker);
    blur.setActionCommand("blur");
    sharpen.setActionCommand("sharpen");
    brighterDarker.setActionCommand("brighten");
    blur.addActionListener(this);
    sharpen.addActionListener(this);
    brighterDarker.addActionListener(this);

    JMenuItem sepia = new JMenuItem("Sepia-tone");
    JMenuItem lumaM = new JMenuItem("Luma-Matrix Grayscale");
    JMenuItem redM = new JMenuItem("Red-Matrix Grayscale");
    JMenuItem greenM = new JMenuItem("Green-Matrix Grayscale");
    JMenuItem blueM = new JMenuItem("Blue-Matrix Grayscale");
    JMenuItem intensityM = new JMenuItem("Intensity-Matrix Grayscale");
    colorTrans.add(sepia);
    colorTrans.add(lumaM);
    colorTrans.add(redM);
    colorTrans.add(blueM);
    colorTrans.add(greenM);
    colorTrans.add(intensityM);
    sepia.setActionCommand("sepia-grayscale");
    redM.setActionCommand("red-grayscale");
    greenM.setActionCommand("green-grayscale");
    blueM.setActionCommand("blue-grayscale");
    intensityM.setActionCommand("intensity-grayscale");
    lumaM.setActionCommand("luma-grayscale");
    sepia.addActionListener(this);
    redM.addActionListener(this);
    greenM.addActionListener(this);
    blueM.addActionListener(this);
    intensityM.addActionListener(this);
    lumaM.addActionListener(this);

    JMenuItem red = new JMenuItem("Red-component");
    JMenuItem green = new JMenuItem("Green-component");
    JMenuItem blue = new JMenuItem("Blue-component");
    JMenuItem value = new JMenuItem("Value-component");
    JMenuItem intensity = new JMenuItem("Intensity-component");
    JMenuItem luma = new JMenuItem("Luma-component");
    grayscale.add(red);
    grayscale.add(green);
    grayscale.add(blue);
    grayscale.add(luma);
    grayscale.add(value);
    grayscale.add(intensity);
    red.setActionCommand("red-component");
    green.setActionCommand("green-component");
    blue.setActionCommand("blue-component");
    value.setActionCommand("value-component");
    intensity.setActionCommand("intensity-component");
    luma.setActionCommand("luma-component");
    red.addActionListener(this);
    green.addActionListener(this);
    blue.addActionListener(this);
    value.addActionListener(this);
    intensity.addActionListener(this);
    luma.addActionListener(this);
  }

  // build the set for all valid filters names for this view.
  private void addValidTransforms() {
    String[] validT = { "blur", "sharpen", "sepia-grayscale", "red-grayscale",
      "green-grayscale", "blue-grayscale", "luma-grayscale", "intensity-grayscale",
      "red-component", "green-component", "blue-component",  "value-component",
      "intensity-component", "luma-component" };
    this.validTransforms = new HashSet<>();
    this.validTransforms.addAll(Arrays.asList(validT));
  }

  /**
   * Adds a ViewListener to this view. The listener will be notified of actions
   * performed on this view.
   *
   * @param listener The ViewListener object to be added.
   * @throws IllegalArgumentException if Null listener.
   */
  @Override
  public void addListeners(ViewListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Null listener.");
    }
    this.listeners.add(listener);
  }

  /**
   * This method creates a pop-out message for handling exceptions thrown through the view.
   *
   * @param msg the message that is thrown.
   * @throws IllegalArgumentException if Null message.
   */
  @Override
  public void showMsg(String msg) {
    if (msg == null) {
      throw new IllegalArgumentException("Null message.");
    }
    JOptionPane.showMessageDialog(this, msg, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Sets the image to be displayed on the canvas of this view.
   *
   * @param image The BufferedImage to be displayed on the canvas.
   * @throws IllegalArgumentException if Null image.
   */
  @Override
  public void draw(BufferedImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Null Image.");
    }
    this.canvas.setImage(image);
  }

  /**
   * Handles actionPerformed events from buttons and menu items.
   * This method identifies the action command and emits the corresponding event.
   *
   * @param e The ActionEvent object representing the action.
   * @throws IllegalArgumentException if action is invalid.
   */
  @Override
  public void actionPerformed(ActionEvent e) {

    String commandName = e.getActionCommand();

    if (commandName.equals("load")) {
      emitLoadEvent();
    } else if (commandName.equals("save")) {
      emitSaveEvent();
    } else if (commandName.equals("brighten")) {
      emitBrightenEvent();

    // else if the filter is in valid filter set
    } else if (this.validTransforms.contains(commandName)) {
      emitFiltersEvent(commandName);
    } else {
      throw new IllegalArgumentException("Invalid Action.");
    }
  }

  // emit the load event, set jpg, png, bmp, ppm as the preferred
  // image file types for users to select from.
  private void emitLoadEvent() {
    JFileChooser loader =
            new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    FileFilter images = new FileNameExtensionFilter("Image files",
            "png", "jpg", "bmp", "ppm");
    loader.setFileFilter(images);

    int returnValue = loader.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      String filepath = loader.getSelectedFile().toString();

      for ( ViewListener listener : this.listeners ) {
        listener.handleLoadEvent(filepath);
      }
      this.imageLoaded = true;
    }
  }

  // emit the save event, if no image has been loaded, show error message
  private void emitSaveEvent() {
    if (!this.imageLoaded) {
      showMsg("No image to be saved.");
      return;
    }
    JFileChooser saver = new JFileChooser(".");
    int returnValue = saver.showSaveDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      String filepath = saver.getSelectedFile().getAbsolutePath();

      for ( ViewListener listener : this.listeners ) {
        listener.handleSaveEvent(filepath);
      }
    }
  }

  // emit the bright event, if no image has been loaded, show error message
  private void emitBrightenEvent() {
    if (!this.imageLoaded) {
      showMsg("No image to be brightened/darkened.");
      return;
    }

    String increment = (String) JOptionPane.showInputDialog(this,
            "Brighten/Darken increment: ",
            "Brighten/Darken (integer)", JOptionPane.PLAIN_MESSAGE, null,
            null, "1");

    for ( ViewListener listener : this.listeners ) {
      if (increment != null) {
        listener.handleBrightenEvent(increment);
      }
    }
  }

  // emit the filters event, if no image has been loaded, show error message
  private void emitFiltersEvent(String filterName) {
    if (!this.imageLoaded) {
      showMsg("No image to be filtered.");
      return;
    }
    for ( ViewListener listener : this.listeners ) {
      listener.handleFiltersEvent(filterName);
    }
  }
}
