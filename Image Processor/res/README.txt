Design Explanation:

Model Package:
Image Interface: Our image interface represents any image that we are trying to represent in our image processor. The methods we have in our image interface are broad enough so that any image represented by a 2d array of pixels (which is all of them) is able to be properly represented. In this interface there are all the methods to edit individual images which includes the ones from assignment 4 (brighten/darken, visualizing components, and flipping), as well as the ones from assignment 5 (blur, sharpen, sepia and grayscale). We chose to put the new operations directly into this interface instead of extending the interface because if we extended the interface, then every instance of Image we had in our code so far would need to be changed in order to allow for this additional functionality.
IModel Interface: The IModel interface represents the set of functions we would want to perform on any model we could potentially have for this assignment. This interface supports all the functions that we were required to support, as well as an additional getter and setter that eliminates the need for the client to have access to the specific fields in the model class. The methods to save and load are not included in this interface because that is the responsibility of the controller not the model. We made the design decision that one cannot brighten an image by 0 as that will not change the image at all.
ModelImpl Class: The ModelImpl class is our implementation of the IModel interface for this assignment. It is the same class from the last assignment, however, we renamed it as we realized that all of the images should be stored in the same image library, no matter their type. It stores a hashmap of image names to images to keep track of any images that are loaded and modified via the processor. It has a host of functions that mirror the commands the program supports. However, instead of carrying out these functions in the implementation class itself, we have a static inner class that does these for us. This inner class is an implementation of the Image interface described above. In this class, the inner class represents a concrete image. The reason this separate class was necessary is so that the images themselves were processed separately in their own class. It allows for more functionality and components to be added to the images themselves without having to change the methods in the model implementation. The reason this class is static is so that we could keep it private. Although this class makes our implementation more efficient, the client has no need for access to this class so we hid it inside our model. We did not want to make it possible for the client to modify the images without going through the ModelImpl itself.
EnhancedModel Interface: EnhancedModel is an extension of the IModel interface. In this interface, there are all of the methods to update images that were new to assignment 5. We chose to extend the IModel interface instead of adding these methods directly to the IModel due to the closed for modification, open for extension principle. The IModel interface methods were carefully tested before and by creating a new interface, we allow for the possibility that people could use our image processor without implementing all of the image processing tools. It allows for the client to pick what abilities they want their model to have.
EnhancedModelImp class: EnhancedModelImp is an extension of the ModelImpl class. We chose to extend the ModelImpl so it can have the same abilities as the ModelImpl but also have the new abilities specific to assignment 5.
Pixel Class: Our pixel class is a very vague representation of a pixel in any image. It is not necessarily specific to a pixel in a PPM image, but rather any pixel we would try to represent. Each pixel we represent will have a respective field that stores the red, green, blue, and alpha components of the pixel as well as the value, intensity and luma components. Separating the pixels themselves from the image gives us the ability to add more components to a pixel without having to edit our other classes too much. For assignment 5, the alpha channel was added in to account for the PNG having the ability to be transparent. All pixels had an alpha value and if they were inputted as an image type that did not have an alpha channel then the alpha value was automatically initialized to be 255, which allows for PNG to be created from PPM, BMP and JPG files.

View Package
IView interface: The IView interface represents the view interface for our image processor. It contains all the methods that would be needed to enhance the view the client would see. Right now, it only has a renderMessage method, but as we expand our view, this interface will grow. Similarly, different implementations of this interface can represent different types of views.
ImageTextView class: The ImageTextView class represents our implementation of the IView class. This represents the text view we are using for our image processor.
Controller Package

Command Package: this package contains all the commands that we currently support in our image processing program. Thus, we have the brighten class, flipH class, flipV class, load class, save class and visualizeComp class in this package to represent each command. For assignment 5, new commands got added for the additional features. There are also blur, sharpen, sepia, and greyscale commands. For assignment 5, the load and save commands did need to be changed in order to account for the dealing with the new file formats (JPG, PNG, and BMP). These changes were necessary since PPM is a text based image while JPG, PNG, and BMP are not.
ICommand interface: This is the interface that each of the commands needs to implement. This interface keeps extension of commands simple because they are all required to implement a go method, which is called in the controller. Thus, adding a new command won’t require much change in the controller code, which makes extension simple. The go method simply delegates to the model, which keeps our controller separate from the functionality of the model.
IController interface: This interface represents the interface for controllers in this image processor. The controller currently only has a startProgram() method that basically runs the game when called. Depending on the type of controller we are implementing, the code for starting the program will differ but they all need to have this method
ControllerImpl class: This is our implementation of the controller interface. The controller currently stores the different commands we have in a hashmap that can be called by string keys. This keeps extension of commands simple because they just need to be put in the hashmap in order to be supported. It also simplifies our code and reduces the need to add a switch case everytime we add more functionality.  Once the input is read, the controller calls the go method of the command, which executes the specified command. For assignment 5, the changes made to the new ControllerImpl class were to add the new commands to the Hashmap of possible commands, update the menu to include the new commands, and change the type of the model to be an EnhancedModel since that includes the new features for this assignment. This could easily be switched to an IModel if a user just wanted to have the old features.
ImageUtil class: This class is a utility class that is used to read a file and return a 2d array of pixels that represents the image in the file. Since the reading of a file could be helpful for a variety of things, it is kept in a utility class and not necessarily embedded into our controller implementation.
Main method: The main method helps us run our code through the console. This helps us see the live interaction between the user and the program to fix any issues we see. For this assignment, we updated the main method so that the client can give a script and run that script completely through.

Running Our Script
In the console, you will be able to input the commands you want to use the image processor. The script can also be run through the command line where you can give it a .txt file in order to run the entire program through.
To run a txt file as a script from the command line, use -file followed by the file path.

Command Line functionality this application supports:
 1. To load a script of script commands into the program when it is being ran
    on the command line, use the -file command followed by the path of the script
    that is being run.
    *Example usage: java -jar realIP.jar -file script.txt*
    A sample script for this application can be run with the above command
    while the jar file is being run.
 2. To open the program in interactive text mode without a predetermined script file
    use the -text command after the jar command. Commands can then be typed in interactively
    one line at a time
    *Example usage: java -jar realIP.jar -text*
 3. To open the program with the graphical user interface, don't include any commands
    after calling the jar command.
    *Example usage: java -jar realIP.jar*

Design changes that were made to support the downsize features:
In order to implement downsize, we added a command to the command package called Downsize.
When this command was run, it called on a new method in the model interface called downsize.
This method in the model interface got the image that we were trying to downsize, downsized it by
delegating to the image class, and then saved this new image as a separate instance in the
hashmap. The image class has the true functionality of downsizing which follows the provided
algorithm to shrink the image. This followed our design thus far because we just had
to add a new command to the command package and add a method in the model that this command
can call on. This functionality was also added to the features interface so the callback
for this function was possible.

Citation for images used:
The beach photo is from: https://unsplash.com/photos/JP23z_-dA74. It is free to use under the Unsplash License. License link: https://unsplash.com/license

The Kermit, Pink Panther photo is from: https://pixabay.com/photos/the-pink-panter-plush-toys-kermit-1996281/. It is free to use under the PixaBay license. Terms of usage for PixaBay images: https://pixabay.com/service/license/

The Paris photo is a screenshot of one section of this photo: https://pixabay.com/photos/eiffel-tower-paris-france-3349075/. It is free to use under the PixaBay license. Terms of usage for PixaBay images: https://pixabay.com/service/license/

One image we used is called flower.ppm. This image is a screenshot of a section of a photo I, Kate Stuntz, took. I authorize it to be used for this assignment.

Other images used in this assignment were sample.ppm, fakePPM.ppm, sample.ppm, blueYellow.png, red.jpg, and redBlue.bmp. All of these images were created by me, Kate Stuntz, and I authorize them to be used in this assignment.

