package controller.commands;

/**
 * The CmdGaussianBlur class represents a command that applies a Gaussian blur
 * filter to an image in the image database. It extends the AbstractKernelFilterCmd class,
 * which provides the kernel-based filtering functionality.
 */
public class CmdGaussianBlur extends AbstractKernelFilterCmd implements ICmd {

  /**
   * Constructs a CmdGaussianBlur object with the pre-defined Gaussian blur kernel.
   * The kernel is a 3x3 double array with specific values to achieve the
   * Gaussian blur effect.
   */
  public CmdGaussianBlur() {
    super(new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    });
  }
}
