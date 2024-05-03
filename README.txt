Assignment 10
An image manipulation program, user can start with a swing graphic view or use text-based command/text file with valid commands to load, save, blur, 
	sharpen, sepia tone, grayscale using matrices, brighter, darker, and grayscale ppm and conventional file format(including but not limited to 
	JPEG, PNG and BMP) of images.
Author: Beini Wang

Brief Design explaination:
	The program follows the Model-View-Controller (MVC) design pattern. The IImageDatabase is the model that stores all image files loaded and IView is 
	the view, that is text/swing-graphic based that provides the view of images and messages to the user when running the program. IController is
	the controller that reads the clicked buttons/menu items/text based command lines typed in/in text file by users and executes command by executing 
	the ICmd objects. Load, blur, sharpen grayscale by red, green, blue, value, intensity, luma component(in both regular and matrix calculating way), 
	brighten, darken, sepia-tone and save commands are valid for this program. 
	Load and save command objects use IImageLoad and Save interface that loads and saves image files in formats. Blur and Sharpen, Grayscale, Grayscale
	images by matrix calculation, sepia-tone, brighten and darker commands using ITransformation objects to manipulate the existing image in model and 
	create manipulated images that store in the model. When controller processing the command lines, users will view the text based message accordingly.
	Please see down below for overview of each interface and class.

How to use:
	To start this program using the GUI, run program with no command line argument. The GUI will start right up.
	If you would like to use the previous release of this program (the one without the GUI), you will need to enter -file to run a file or -text
	to type in. You can still follow the rules in previous assignment's USEME.txt to operate the program.
	Please see USEME file to see how to use the GUI.

Citation of the image used:
source.ppm file reference: open link of florida state univeristy: https://people.sc.fsu.edu/~jburkardt/data/ppma/ppma.html, 
	download link: https://people.sc.fsu.edu/~jburkardt/data/ppma/sines.ascii.ppm
	(other format of source images are created based on this one).
three.ppm/three.jpg/three.png/three.bmp files used in tests are created by me and are authorized to use for testing this program.

Design Changes:
	The biggest change is to include a graphical user interface, used as the graphic view of the program. Class GraphicView implements the IView
	interface which has method of draw the image, render message, set visible, add listener methods. The GUI contains "Load", "Save", "Exit", "Blur", 
	"Sharpen", "Brighten/Darker", "Sepia-tone" buttons and a menu and submenus for  "File", "Filter", "Color Transform", and "Grayscale". Every action 
	is accessible through these submenus.
	ViewListener interface has handle all action events methods indicate all classes listens to the view will need to have these methods to execute
	when clicking the according buttons/menu items.
	ControllerGraphic that implements IController and ViewListener interfaces that will respond to the user actions on the view 
	ICanvas interface/Canvas class that represents a canvas(custom JPanel class) used for displaying images. 


Program Design:
Interface: 
	IPixelState: This interface represents operations that can be used to monitor the state of a pixel without changing it.
Interface: 
	IPixel: This interface extends the IPixelState interface and represents the operations offered by a pixel, including the ability to change its 
	state.
Class: 
	Pixel: This class represents a pixel and provides an implementation of the IPixel interface, offering all the operations mandated by the interface. 
	One pixel has values for red, green and blue channels and the value can be set to value in valid channels values from 0 to 255.

Interface: 
	IImageState: This interface represents operations that can be used to monitor the state of an image without changing it.
Interface: 
	IImage:This interface extends the IImageState interface and represents the operations offered by an image, including the ability to change its 
	state (by setting pixel channels values at given place of the image).
Class: 
	ImageImpl: This class represents an Image and provides an implementation of the IImage interface, offering all the operations mandated by the 
	interface. Gives representation of an image with pixel data and associated methods to access and modify pixel channel values. An ImageImpl object
	is initialized with the given width and height and pixels values are updated by setting at each place of the image and a new Pixel object created 
	at that position to store the channel values.

Interface: 
	IImageDatabase: This interface represents the operations offered by an image database. The image database object serves as the model in this 
	Model-View-Controller program and is responsible for storing and managing images identified by unique IDs.
Class: 
	ImageDatabase: This class represents an image database that stores and manages images identified by unique IDs. It implements the IImageDatabase 
	interface and provides methods to add images and retrieve the image as an IImageState object which the state of the image cannot be changed.

Interface: 
	IView: This interface represents a view of an image processing model. View components are responsible for rendering messages and output to an 
	Appendable object. It serves as the view in this Model-View-Controller program.
Class: 
	View: This class represents a view that passes to controller. It implements the IView interface, allowing it to render messages. A textual 
	representation of messages, allowing users to view them.
	Graphic View: The class is a graphical view that implements the IView and ActionListener interface to graphically display images and show messages 
	accordingly. It extends the JFrame class to provide a graphical user interface.

Interface:
	ViewListener: The ViewListener interface defines the contract for classes that wish to listen to actions and events from the view. Implementing 
	classes should handle specific events such as image loading, image brightening, applying filters, and saving images. These events are triggered by 
	the view and processed by the implementing class.

Interface:
	ICanvas: The ICanvas interface represents canvas used for displaying images.
Class:
	Canvas: The Canvas class is a custom JPanel used for displaying images. It extends the JPanel class to provide a canvas on which the BufferedImage
	can be drawn.

Interface:
	ITransformation:This interface represents a generic image transformation. It follows the strategy pattern. Implementing classes must define the 
	run(IImageState object) method to apply the transformation to an IImageState object (representing an image) and return a the transformed image
	(an IImageState object).
	The transformations defined in the classes will be used as strategies that can be executed in the controller of the program when specific commands
	are given to process the images.
Classes:
	AbstractTransformation: This abstract class provides a basic structure for image transformations. Implementing classes must extend this class and 
	implement the abstract methods to perform specific transformations. The class includes a common implementation of the run() method, which 
	delegates the transformation process to the specific helper methods defined in subclasses.
	BrighterDarker: This class represents a transformation that adjusts the brightness of an image when it runs. It extends the AbstractTransformation 
	class and implements the ITransformation interface. The transformation adds the specified value to the RGB values of the image, making the image 
	brighter or darker based on the provided value.
	GreyscaleRed, GreyscaleGreen, GreyscaleBlue, GreyscaleIntensity, GreyscaleLuma, GreyscaleValue: These classes represent various greyscale 
	transformations for images. They extend the AbstractTransformation class and implement the ITransformation interface, each providing a specific 
	method to calculate the new channel values for greyscale transformation by the red, green, blue, value(max of RGB channel value of each pixel), 
	intensity(average of RGB channel value of each pixel), luma(weighted sum of RGB channel value of each pixel: 0.2126 * red + 0.7152 * green + 0.0722 * blue) 
	component accordingly. Each class represents a different approach to converting a image to a greyscale image.
	BlurSharpen: It represents a transformation that converts an image to blurred/sharpened when it runs. It implements the ITransformation interface.
	MatrixGreyscale:  The MatrixGreyscale class represents a color transformation that converts an image to greyscale/sepia using a matrix kernel. 
	It extends the AbstractTransformation class and implements the ITransformation interface.

Interface:
	IImageLoader: This interface represents a generic image loader. Implementing classes must define the loadImage() method to load an image in a 
	specific image format and return its image state as an IImageState object.
	IImageSaver: This interface represents an image saver that is capable of saving images. Implementing classes must define the run() method to save 
	the image data to a specific destination in a specific image file format.
Classes:
	PPMLoader: This class represents an image loader specifically for PPM files. It implements the IImageLoader interface. PPM files should be in 
	P3 format and contain color information in RGB format. The loadImage() method is responsible for reading the PPM file, extracting the image data, 
	and creating an IImage object and return its image state as an IImageState object.
	ConventionalLoader: It implements the IImageLoader interface to load an image in the conventional format (e.g., JPEG, PNG) into the image database.
	It implements IImageLoader interface.
	PPMSaver: This class implements the IImageSaver interface to save an image in the PPM format. The run() method is responsible for writing the 
	pixel data of the IImageState object to a PPM file at the specified destination. The object initialized with an Appendable object for testing
	purpose.
 	ConventionalSaver: It represents an image saver that saves an image in a conventional format, such as PNG, JPEG, or BMP. The object initialized 
	with an OutputStream object for testing purpose.

Interface:
	ICmd: This interface represents a command that can be executed to perform specific operations on an image database using a given scanner and an 
	image database model. Implementing classes must define the execute() method to execute the command. The design follows command pattern.
Classes:
	CmdSave: This class implements the ICmd interface to save an image in the multiple formats. The execute() method is responsible for reading the 
	image unique ID and destination file path from the command-line scanner, retrieving the image state from the image database model, and using the
	PPMSaver/ConventionalSaver object to save the image in the specified format.
	CmdLoad: This class implements the ICmd interface to load an image in multiple formats into the image database model. The execute() method is 
	responsible for reading the image ID and source file path from the command-line scanner, using the PPMLoader/Conventional Loader object to load the
	image from the PPM/conventional image file, and adding the loaded image to the image database model.
	CmdBrighten: This class implements the ICmd interface to apply a brightened or darkened transformation to an image. The execute() method is 
	responsible for reading the transformation value and source image ID and dest-image-name as the id for the transformed image from the command-line 
	scanner, applying the BrighterDarker transformation with the specified value to the image, and adding the transformed image to the image database 
	model with the ID.
	CmdGreyscale: This class implements the ICmd interface to apply a greyscale transformation to an image and the object is initialized with a specific
	ITransfomation greyscale object. This design allows new transformations to be added without modifying existing code. The execute() method is 
	responsible for reading the source image ID and destination image ID from the command-line scanner, applying the greyscale transformation specified
	in the ITransformation object to the image, and adding the transformed image to the image database model with the ID. 
	AbstractKernelFilterCmd: This abstract class represents an abstract command that applies a kernel-based filter, such as blur or sharpen, to an 
	image in the image database. It implements the ICmd interface.  This design allows new transformations to be added without modifying existing code.
	CmdBlur/Sharpen: Classes implement the ICmd interface to apply a blur/sharpen transformation to an image and the object is initialized with a 
	specific kernel.The execute() method is responsible for reading the source image ID and destination image ID from the command-line scanner, applying the greyscale transformation specified
	the BlurSharpen object to the image, and adding the transformed image to the image database model with the ID. 
	AbstractMatrixCmd: This abstract class represents an abstract command that applies a matrix-based grayscale/sepia tone color transformation, 
	such as sepia, luma-component grayscale, to an image in the image database. It implements the ICmd interface. This design allows new matrices
	operation transformations to be added without modifying existing code.
	CmdMatrix classes: Classes extends AbstractMatrixCmd abstract class with specified 3x3 matrices.

Interface:
	IController: This interface represents a controller responsible for processing commands and managing interactions between the image database model, 
	the view, and the user input. The process() method defines the contract for processing user commands.
Class:
	ControllerImpl: This class implements the IController interface to process user commands and interact with the image database model and the view. 
	It is the controller component of the MVC design pattern. It's initialized with a IImageDatabase model, IView object associated with the model and 
	a Readable that reads the user's command lines. When a valid command has been executed, a "Command executed" message will be shown by the Appendable
	of the IView object, if the command starts with unknow command, a "Invalid command" message will be shown, if the command fails to execute, the 
	error message will be shown accordingly. User use "exit" command to quit the program.
	ControllerGraphic:  The ControllerGraphic class implements the IController interface and acts as a controller for the graphical view. It listens to 
	events from the view (implementing the ViewListener interface) and processes the user commands to modify the image using various image processing 
	commands.

Class:
	Main: It serves as the entry point for the image processing program. It contains the main method, which is the starting point for executing the 
	program. The purpose of the Main class is to set up the essential components of the program, such as such as the image database model, the view, 
	and the controller, and then initiate the execution of the image processing program.

Test Classes:
	ViewListnerTest: A JUnit test class for the ControllerGraphic class using the View object that append output to the appendable.
	PixelTest: A JUnit test class for the Pixel class.
	ImageTest: A JUnit test class for the ImageImpl class.
	FallingAppendable: A mock to simulate a failure to write to the Appendable.
	ImageDatabaseViewTest: A JUnit test class for the ImageDatabase and View class. Test using Falling Appendable.
	PPMSaverLoaderTest: A JUnit test class for the PPMSaver and PPMLoader class. Testing by loading the three.ppm file in testImage/ folder, and test 
		for all channel values at each pixel of the image. And test the saver by saving the loaded image and test its Appendable result.
	ConventionalSaverLoaderTest: A JUnit test class for the ConventionalSaver and ConventionalLoader class.
	TransformationTest: A JUnit test class for the transformation classes. Test the transfomed images after executing all different class 
		ITransformation objects for all channel values at each pixel of the transformed image. Also using tested PPM saver to save the transformed
		images and used the saver's Appendable to test the out string after it's been brightened/darkened or greyscaled.
	BlurSharpenTest: A JUnit test class for the BlurSharpen transformation class. 
	MatrixGrayscaleTest: A JUnit test class for the MatrixGrayscale transformation class. 
	CmdTest: A JUnit test class for the command classes. Test load, brighter, darker, all components greyscale commands by getting the image from
		model and tests all channel values at each pixel of the image. Test save command by loading the saved image and tests all channel values 
		at each pixel of the image.
	AdditionalCmdTest:  A JUnit test class for the newly added Commands.
	ControllerTest: A JUnit test class for the Controller class. Test load, brighter, darker, all components greyscale commands by getting the image 
		and tests all channel values at each pixel of the image. Test save command by loading the saved image and tests all channel values at each 
		pixel of the image. Also test for the shown error message/execute message/quitting messages accordingly.
	AdditionalControllerTest:  A JUnit test class for the controller tests that read newly added Commands.
