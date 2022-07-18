package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.EnhancedModel;
import model.EnhancedModelImpl;
import view.EnhancedView;
import view.GUIView;
import view.IView;
import view.ImageTextView;

/**
 * A class that is used to start this program.
 */
public class Main {
  /**
   * A method that starts the program. It does not need to take in any inputs in the args to start
   * the program. All the communication with the program takes place in the console.
   *
   * @param args arguments you could put in, but you should not to use this processor properly.
   */
  public static void main(String[] args) {
    Readable read = null;
    IController controller;
    boolean text = false;
    if (args.length > 0 && !args[0].equals("-file") && !args[0].equals("-text")) {
      throw new IllegalArgumentException("This is not a possible way to run this image processor.");
    }
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-file")) {
        try {
          read = readFile(args[i + 1]);
          text = true;
          break;
        } catch (ArrayIndexOutOfBoundsException e) {
          e.printStackTrace();
        }
      }
      if (args[i].equals("-text")) {
        text = true;
        break;
      }
    }
    EnhancedModel model = new EnhancedModelImpl();
    IView textView = new ImageTextView();
    EnhancedView guiView = new GUIView();
    if (read == null && text) {
      controller = new ControllerImpl(new InputStreamReader(System.in), model, textView);
    } else if (text) {
      controller = new ControllerImpl(read, model, textView);
    } else {
      controller = new GUIController(model, guiView);
    }
    controller.startProgram();
  }

  //reads the file given by the fileName and converts it into the form
  //of a readable that the controller can parse.
  private static Readable readFile(String fileName) {
    String script = "";
    try {
      Path path = Paths.get(fileName);
      try {
        script = Files.readString(path);
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (script.length() == 0) {
        return new InputStreamReader(System.in);
      }
    } catch (InvalidPathException e) {
      System.out.println("file not found!");
    }
    return new InputStreamReader(new ByteArrayInputStream(script.getBytes()));
  }
}
