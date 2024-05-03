package controller.commands;

/**
 * The CmdMatrixLuma class represents a command that applies a matrix-based
 * grayscale transformation to the luma component of an image in the image
 * database. It extends the AbstractMatrixCmd class.
 */
public class CmdMatrixLuma extends AbstractMatrixCmd implements ICmd {

  /**
   * Constructs a CmdMatrixLuma object with the specific 3x3 kernel.
   */
  public CmdMatrixLuma() {
    super(new double[][] {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    });
  }
}
