This README is for the Image Manipulation and Enhancement Program.
The GUI contains buttons "Load", "Save", "Exit", "Blur", "Sharpen", "Brighten/Darker", "Sepia-tone" buttons menu and submenus for  "File", "Filter", "Color Transform", and "Grayscale". Every action is accessible through these submenus. Click on the button or menu items to load image, filter or save the showing image.

See below the screen shot for buttons and each menu:
- Buttons that supports load an image file, save the showing image, exit the program and blur, sharpen, brightened/darken, sepia tone the showing image.
![buttons](res/buttons.PNG)
- File Menu supports load an image file and save the showing image.
![file menu](res/file_menu.png)
- Filter Menu supports blur, sharpen and brighten/darken the showing image.
![filter menu](res/filter_menu.png)
- Color transformation Menu supports sepia-tone, luma, red, green, blue, and intensity matrices gray-scale the showing image.
![transformation menu](res/color_menu.png)
- Grayscale Menu supports value, intensity, luma, red, green, blue component gray-scale the showing image.
![grayscale menu](res/grayscale_menu.png)

Each Action Note/Error message example:
- Load: You may load image from any folder of your computer.

![load](res/load_image.PNG)
When loading image, you will see this window for you to select the file, the default image file types are ppm/png/jpg/bmp files. If you are loading JAVA ImageIO supported other types of files, please change the File of Type to all files. If the selected file type is not supported, you will see the following error message.

![load error](res/load_error.PNG) 

Also, you will need load a image before saving, filtering an image.     
![no load1](res/save_error.png)

You will see following error message if save/filter with no current showing image.
![no load2](res/filter_error.png)

After loading an image, if the image is larger than the window size, scroll bars will show up, see example:
![load image](res/loaded.png)
- Save:

![save](res/save_image.PNG)
When saving a showing image, you will see this window for you to enter the name and extension for the image. The supported image file types are ppm/png/jpg/bmp and other ImageIO supported types. If the type is not supported, error message will show. The default saving folder is the current folder but you may change to other directory.
![save error](res/save_error2.PNG)

- Brighten/Darken

When brighten/darken the showing image, a pop up window will show and ask for the increment of brighten(positive integer) or darken(negative integer). 
![brighten input](res/brighten_input.png)

If entering input is not an integer, the following error message will show.

![brighten error](res/brighten_no_integer.png)

- All other filter/color transformation/grayscale
Click on the menu items/button to apply the according filter/color transformation/grayscale on the current showing image. The blur/sharpen/brighten can be applied multiple times, all other may have the same result.

- Click on Exit button OR click on top right to close the window to exit the program.