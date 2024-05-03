package controller.commands;

/**
 * The CmdMatrixRed class represents a command that applies a matrix-based
 * grayscale transformation to the red component of an image in the image
 * database. It extends the AbstractMatrixCmd class.
 */
public class CmdMatrixRed extends AbstractMatrixCmd implements ICmd {

  /**
   * Constructs a CmdMatrixRed object with the specific 3x3 kernel.
   */
  public CmdMatrixRed() {
    super(new double[][] {
            {1, 0, 0},
            {1, 0, 0},
            {1, 0, 0}
    });
  }
}
