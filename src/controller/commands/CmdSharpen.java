package controller.commands;

/**
 * The CmdSharpen class represents a command that applies a sharpening
 * filter to an image in the image database. It extends the AbstractKernelFilterCmd class
 * which provides the kernel-based filtering functionality.
 */
public class CmdSharpen extends AbstractKernelFilterCmd implements ICmd {

  /**
   * Constructs a CmdSharpen object with the pre-defined sharpening kernel.
   * The kernel is a 5x5 double array with specific values to achieve the
   * sharpening effect.
   */
  public CmdSharpen() {
    super(new double[][] {
            {- 1.0 / 8, - 1.0 / 8, - 1.0 / 8, - 1.0 / 8, - 1.0 / 8},
            {- 1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, - 1.0 / 8},
            {- 1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, - 1.0 / 8},
            {- 1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, - 1.0 / 8},
            {- 1.0 / 8, - 1.0 / 8, - 1.0 / 8, - 1.0 / 8, - 1.0 / 8},
    });
  }
}
