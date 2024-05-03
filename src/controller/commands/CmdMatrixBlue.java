package controller.commands;

/**
 * The CmdMatrixBlue class represents a command that applies a matrix-based
 * grayscale transformation to the blue component of an image in the image
 * database. It extends the AbstractMatrixCmd class.
 */
public class CmdMatrixBlue extends AbstractMatrixCmd implements ICmd {

  /**
   * Constructs a CmdMatrixBlue object with the specific 3x3 kernel.
   */
  public CmdMatrixBlue() {
    super(new double[][] {
            {0, 0, 1},
            {0, 0, 1},
            {0, 0, 1}
    });
  }
}
