package controller.commands;

/**
 * The CmdMatrixIntensity class represents a command that applies a matrix-based
 * grayscale transformation to the intensity component of an image in the image
 * database. It extends the AbstractMatrixCmd class.
 */
public class CmdMatrixIntensity extends AbstractMatrixCmd implements ICmd {

  /**
   * Constructs a CmdMatrixIntensity object with the specific 3x3 kernel.
   */
  public CmdMatrixIntensity() {
    super(new double[][] {
            {1.0 / 3, 1.0 / 3, 1.0 / 3},
            {1.0 / 3, 1.0 / 3, 1.0 / 3},
            {1.0 / 3, 1.0 / 3, 1.0 / 3}
    });
  }
}
