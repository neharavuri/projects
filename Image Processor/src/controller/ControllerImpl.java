package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import controller.command.Blur;
import controller.command.Brighten;
import controller.command.Greyscale;
import controller.command.ICommand;
import controller.command.Load;
import controller.command.Save;
import controller.command.FlipH;
import controller.command.FlipV;
import controller.command.Sepia;
import controller.command.Sharpen;
import controller.command.VisualizeComp;
import model.EnhancedModel;
import model.IModel;
import view.IView;

/**
 * represents the implementation of the IController interface
 * for the image processor we are building. The controller is in charge
 * of reading the inputs from the user and delegating appropriate commands
 * to the model and the view.
 */
public class ControllerImpl implements IController {
  private final Scanner scanner;
  private final EnhancedModel model;
  private final IView view;

  /**
   * represents the constructor for this controller implementation that takes in
   * a readable representing input, an object of the IModel interface and an
   * object of the IView interface. This constructor also initializes the scanner
   * to read the inputs that were provided.
   *
   * @param input the user input.
   * @param model an object of the IModel interface.
   * @param view  an object of the IView interface.
   * @throws IllegalArgumentException if any of the above arguments are null.
   */
  public ControllerImpl(Readable input, EnhancedModel model, IView view)
          throws IllegalArgumentException {
    if (input == null || model == null || view == null) {
      throw new IllegalArgumentException("inputs cannot be null");
    }
    this.scanner = new Scanner(input);
    this.model = model;
    this.view = view;
  }

  /**
   * represents the "runCommand" method for this controller implementation. First, the controller
   * renders a menu that describes the different types of commands that are legal and the syntax
   * of how the user can input them. The commands are as follows:
   * 1. load 'image-path' 'image-name': loads an image from the specified 'image-path'
   * into the program.This image can be referenced henceforth in the  program by the given
   * 'image-name'
   * 2. save 'image-path' image-name': saves an image with the given 'image-name' in
   * the program to the specified 'image-path'
   * 3. 'component-name'-component 'image-name' 'dest-image-name': creates a greyscale
   * image with the 'component-name'(can be red, green, blue, value, intensity or luma)
   * component of the image specified by'image-name'This new version of the
   * image can now be referenced in the program by the given 'dest-image-name'.
   * 9. horizontal-flip 'image-name' 'dest-image-name': flips the image specified by
   * 'image-name' horizontally and saves this new version in the program as 'dest-image-name'
   * 10. vertical-flip 'image-name' 'dest-image-name': flips the image specified by
   * 'image-name' vertically and saves this new version in the program
   * as 'dest-image-name'
   * 11. brighten 'increment' 'image-name' 'dest-image-name': brighten the image by the
   * given positive 'increment' to create a new image, referred to by the given
   * 'dest-image-name'. The increment may be positive (brightening) or negative
   * (darkening). if the increment is 0, the image will not be processed
   * 12. blur 'image-name' 'dest-image-name': blurs the image specified by
   * 'image-name' and saves this new version in the program as 'dest-image-name'. this operation
   * can happen on the same image multiple times to blur it more
   * 13. sharpen 'image-name' 'dest-image-name': sharpens the image specified by 'image-name' and
   * saves this new version in the program as 'dest-image-name'. this operation can happen on the
   * same image multiple times to sharpen it more
   * 14. greyscale 'image-name' 'dest-image-name': produces a greyscale version of image specified
   * by 'image-name' and saves this new version in the program as dest-image-name'.
   * 15. sepia 'image-name' 'dest-image-name': produces a sepia version of "image specified by
   * 'image-name' and saves this new version in the program as 'dest-image-name'.
   * 16. q : quits the program
   * Next, the scanner reads the first input the user gives as the command that we are dealing
   * with. Based on the command type as well as the arguments
   * provided, an object of the respective command class is created. Then, once the object is
   * created, the runCommand method of the command is called to actually execute the command.
   * If at any point, the scanner runs out of inputs, it sends a message saying that it ran out.
   * Similarly, an error message is displayed if the user is trying to perform an action on an
   * image that doesn't exist in the system. This process keeps running until the user presses q,
   * which exits and quits the game.
   */
  @Override
  public void startProgram() {
    this.renderMenu();
    HashMap<String, Function<Scanner, ICommand>> functions = new HashMap<>();
    functions.put("load", s -> new Load(s.next(), s.next(), this.view));
    functions.put("brighten", s -> new Brighten(s.next(), s.next(), s.next(), this.view));
    functions.put("vertical-flip", s -> new FlipV(s.next(), s.next(), this.view));
    functions.put("horizontal-flip", s -> new FlipH(s.next(), s.next(), this.view));
    functions.put("value-component", s -> new VisualizeComp(s.next(), IModel.Component.Value,
            s.next(), this.view));
    functions.put("luma-component", s -> new VisualizeComp(s.next(), IModel.Component.Luma,
            s.next(), this.view));
    functions.put("intensity-component", s -> new VisualizeComp(s.next(),
            IModel.Component.Intensity,
            s.next(), this.view));
    functions.put("green-component", s -> new VisualizeComp(s.next(), IModel.Component.Green,
            s.next(), this.view));
    functions.put("red-component", s -> new VisualizeComp(s.next(), IModel.Component.Red,
            s.next(), this.view));
    functions.put("blue-component", s -> new VisualizeComp(s.next(), IModel.Component.Blue,
            s.next(), this.view));
    functions.put("save", s -> new Save(s.next(), s.next(), this.view));
    functions.put("blur", s -> new Blur(s.next(), s.next(), this.view));
    functions.put("sharpen", s -> new Sharpen(s.next(), s.next(), this.view));
    functions.put("greyscale", s -> new Greyscale(s.next(), s.next(), this.view));
    functions.put("sepia", s -> new Sepia(s.next(), s.next(), this.view));


    while (scanner.hasNext()) {
      ICommand c;
      String in = scanner.next();
      if (in.equals("q")) {
        this.quit();
        return;
      }
      Function<Scanner, ICommand> command = functions.getOrDefault(in, null);
      if (command == null) {
        this.sendMessage("not a supported function");
      } else {
        try {
          c = command.apply(scanner);
          try {
            c.runCommand(this.model);
          } catch (IllegalArgumentException e) {
            this.sendMessage("this image does not exist in the system");
          }
        } catch (NoSuchElementException e) {
          this.sendMessage("ran out of inputs!");
        }
      }
    }
  }

  //quits the program
  private void quit() {
    this.sendMessage("image processor terminated :(");
  }

  //performs the try/catch on an arbitrary message
  private void sendMessage(String message) {
    try {
      this.view.renderMessage(message + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("transmission failed");
    }
  }

  //renders the menu
  private void renderMenu() {
    try {
      this.view.renderMessage("Welcome to the image processing program! " +
              "Here are a list of supported functions:\n" +
              "1. load 'image-path' 'image-name': loads an image from the specified " +
              "'image-path'\n " + "into the program." +
              "This image can be referenced henceforth in the  program by the given " +
              "'image-name'\n" +
              "2. save 'image-path' image-name': saves an image with the given 'image-name' in\n " +
              "the program to the specified 'image-path'\n" +
              "3. 'component-name'-component 'image-name' 'dest-image-name': creates a " +
              "greyscale\n" +
              "image with the 'component-name'(can be red, green, blue, value, intensity or luma)" +
              "\n" +
              " component of the image specified by" + "'image-name'" + "This new version of the " +
              "\n" +
              "image can now be referenced in the program by the given 'dest-image-name'. \n" +
              "9. horizontal-flip 'image-name' 'dest-image-name': flips the image specified by \n" +
              "'image-name' horizontally and saves this new version in the " +
              "program as 'dest-image-name'\n" +
              "10. vertical-flip 'image-name' 'dest-image-name': flips the image specified by \n" +
              "'image-name' vertically and saves this new version in the program \n" +
              "as 'dest-image-name'\n" +
              "11. brighten 'increment' 'image-name' 'dest-image-name': brighten the image by " +
              "the\n" +
              "given 'increment' to create a new image, referred to by the " + "given\n" +
              " 'dest-image-name'. The increment may be positive (brightening) or negative \n" +
              "(darkening). if the increment is 0, the image will not be processed\n" +
              "12. blur 'image-name' 'dest-image-name': blurs the image specified by \n" +
              "'image-name' and saves this new version in the program as 'dest-image-name'. \n" +
              "this operation can happen on the same image multiple times to blur it more \n" +
              "13. sharpen 'image-name' 'dest-image-name': sharpens the image specified by \n" +
              "'image-name' and saves this new version in the program as 'dest-image-name'. \n" +
              "this operation can happen on the same image multiple times to sharpen it more \n" +
              "14. greyscale 'image-name' 'dest-image-name': produces a greyscale version of \n" +
              "image specified by 'image-name' and saves this new version in the program as \n" +
              "'dest-image-name'. \n" +
              "15. sepia 'image-name' 'dest-image-name': produces a sepia version of \n" +
              "image specified by 'image-name' and saves this new version in the program as \n" +
              "'dest-image-name'. \n" +
              "16. q : quits the program\n");
    } catch (IOException e) {
      throw new IllegalStateException("render failed");
    }
  }
}
