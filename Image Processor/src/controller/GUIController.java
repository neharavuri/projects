package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.Function;

import controller.command.Blur;
import controller.command.Brighten;
import controller.command.Downscale;
import controller.command.FlipH;
import controller.command.FlipV;
import controller.command.Greyscale;
import controller.command.ICommand;
import controller.command.Load;
import controller.command.Save;
import controller.command.Sepia;
import controller.command.Sharpen;
import controller.command.VisualizeComp;
import model.EnhancedModel;
import model.IModel;
import view.EnhancedView;

/**
 * Represents the controller for the GUI view. The controller acts as the intermediary between the
 * model and the view.
 */
public class GUIController implements Features, IController {
  // the model being used
  private final EnhancedModel model;
  // the view being used
  private final EnhancedView view;
  // the counter of how many images have been processed in order to have names for the images
  private int counter;
  // the possible to occur on this image. The load, brighten, downsize and save commands are not
  // included here since they are dealt with separately.
  private final HashMap<String, Function<Integer, ICommand>> commands;

  /**
   * The constructor for this controller.
   *
   * @param model the model being worked with.
   * @param view  the view being worked with.
   * @throws IllegalArgumentException if the view or model are null.
   */
  public GUIController(EnhancedModel model, EnhancedView view) throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Neither the model or the view can be null");
    }
    this.model = model;
    this.view = view;
    counter = 0;
    this.commands = new HashMap<>();
    commands.put("Flip vertically", i -> new FlipV(Integer.toString(i), Integer.toString(i + 1),
            this.view));
    commands.put("Flip horizontally", i -> new FlipH(Integer.toString(i),
            Integer.toString(i + 1), this.view));
    commands.put("Visualize value", i -> new VisualizeComp(Integer.toString(i),
            IModel.Component.Value,
            Integer.toString(i + 1), this.view));
    commands.put("Visualize luma", i -> new VisualizeComp(Integer.toString(i),
            IModel.Component.Luma,
            Integer.toString(i + 1), this.view));
    commands.put("Visualize intensity", i -> new VisualizeComp(Integer.toString(i),
            IModel.Component.Intensity, Integer.toString(i + 1), this.view));
    commands.put("Visualize green", i -> new VisualizeComp(Integer.toString(i),
            IModel.Component.Green,
            Integer.toString(i + 1), this.view));
    commands.put("Visualize red", i -> new VisualizeComp(Integer.toString(i),
            IModel.Component.Red,
            Integer.toString(i + 1), this.view));
    commands.put("Visualize blue", i -> new VisualizeComp(Integer.toString(i),
            IModel.Component.Blue,
            Integer.toString(i + 1), this.view));
    commands.put("Blur", i -> new Blur(Integer.toString(i), Integer.toString(i + 1), this.view));
    commands.put("Sharpen", i -> new Sharpen(Integer.toString(i), Integer.toString(i + 1),
            this.view));
    commands.put("Apply greyscale filter", i -> new Greyscale(Integer.toString(i),
            Integer.toString(i + 1), this.view));
    commands.put("Apply sepia filter", i -> new Sepia(Integer.toString(i),
            Integer.toString(i + 1), this.view));
  }

  @Override
  public void startProgram() {
    view.makeVisible();
    view.addFeatures(this);
  }

  @Override
  public void load(String filepath) {
    counter = counter + 1;
    Load load = new Load(filepath, "" + counter, view);
    try {
      load.runCommand(model);
      view.updateImage(model.getImage(Integer.toString(counter)).convertToBufferedImage("png"));
      this.drawHistogram();
    } catch (IllegalArgumentException exception) {
      sendMessage("Failure loading image.");
    }
  }

  @Override
  public void save(String filepath) {
    Save save = new Save(filepath, "" + counter, view);
    try {
      save.runCommand(model);
    } catch (IllegalArgumentException exception) {
      sendMessage("Failure saving image.");
    }
  }

  @Override
  public void modifyImage(String action) {
    Function<Integer, ICommand> command = commands.getOrDefault(action, null);
    if (command != null) {
      ICommand c = command.apply(counter);
      try {
        c.runCommand(model);
        counter = counter + 1;
        view.updateImage(model.getImage(Integer.toString(counter)).convertToBufferedImage("png"));
        this.drawHistogram();
      } catch (IllegalArgumentException e) {
        sendMessage("Failure altering image");
      }
    } else {
      // this will never be reached
      sendMessage("Invalid command");
    }
  }

  @Override
  public void brighten(String scale) {
    ICommand brighten = new Brighten(scale, Integer.toString(counter), Integer.toString(counter + 1)
            , view);
    try {
      brighten.runCommand(model);
      try {
        if (Integer.parseInt(scale) != 0) {
          counter = counter + 1;
          view.updateImage(model.getImage(Integer.toString(counter)).convertToBufferedImage("png"));
          this.drawHistogram();
        }
      } catch (NumberFormatException n) {
        //this exception is dealt with in our runCommand --> we just need this statement
        // to make sure we are not updating the counter if nothing is changing
      }
    } catch (IllegalArgumentException exception) {
      sendMessage("Brightening failed.");
    }
  }

  @Override
  public void downsize(String height, String width) {
    try {
      ICommand donwnsize = new Downscale(width, height, Integer.toString(counter),
              Integer.toString(counter + 1), view);
      donwnsize.runCommand(model);
      try {
        if (!(Integer.parseInt(height) <= 0 || Integer.parseInt(width) <= 0)) {
          counter = counter + 1;
          view.updateImage(model.getImage(Integer.toString(counter)).convertToBufferedImage("png"));
          this.drawHistogram();
        }
      } catch (NumberFormatException n) {
        //this exception is dealt with in our runCommand --> we just need this statement
        // to make sure we are not updating the counter if nothing is changing
      }
    } catch (IllegalArgumentException exception) {
      sendMessage("Downsizing failed. Width and height must be positive integers");
    }
  }

  /**
   * Tells the view to draw the histograms for this specific image.
   */
  private void drawHistogram() {
    HashMap<Integer, Integer> red = model.getHistogram("" + counter, IModel.Component.Red);
    HashMap<Integer, Integer> green = model.getHistogram("" + counter, IModel.Component.Green);
    HashMap<Integer, Integer> blue = model.getHistogram("" + counter, IModel.Component.Blue);
    HashMap<Integer, Integer> intensity = model.getHistogram("" + counter,
            IModel.Component.Intensity);
    view.drawHistogram(red, green, blue, intensity);
  }

  /**
   * Sends a message to the user when the user needs to be notified of something.
   *
   * @param message the message to be sent to the user.
   * @throws IllegalStateException if the message is not able to be properly sent.
   */
  private void sendMessage(String message) throws IllegalStateException {
    try {
      view.renderMessage(message);
    } catch (IOException ex) {
      throw new IllegalStateException("Failure sending message");
    }
  }

}
