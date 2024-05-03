package controller.commands;

/**
 * The CmdMatrixSepia class represents a command that applies a matrix-based
 * sepia-toned transformation to an image in the image database. It extends
 * the AbstractMatrixCmd class.
 */
public class CmdMatrixSepia extends AbstractMatrixCmd implements ICmd {

  /**
   * Constructs a CmdMatrixSepia object with the specific 3x3 kernel
   * that applies the sepia-toned transformation.
   */
  public CmdMatrixSepia() {
    super(new double[][] {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    });
  }
}
