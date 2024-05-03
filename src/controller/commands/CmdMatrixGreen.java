package controller.commands;

/**
 * The CmdMatrixGreen class represents a command that applies a matrix-based
 * grayscale transformation to the green component of an image in the image
 * database. It extends the AbstractMatrixCmd class.
 */
public class CmdMatrixGreen extends AbstractMatrixCmd implements ICmd {

  /**
   * Constructs a CmdMatrixGreen object with the specific 3x3 kernel.
   */
  public CmdMatrixGreen() {
    super(new double[][] {
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0}
    });
  }
}
