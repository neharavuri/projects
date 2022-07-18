package controller.command;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.ImageUtil;
import model.EnhancedModel;
import model.IModel;
import model.Pixel;
import view.IView;

/**
 * represents the load command for the image processor program
 * that adds an image into the system.
 */
public class Load extends controller.command.ACommand {

  /**
   * constructor that takes the filePath we are deriving our image from,
   * where the image is going to runCommand and an instance of the view we are working with.
   *
   * @param filePath the current file path of the image.
   * @param name     what the image will be referred to as in the system.
   * @param view     an instance of the view we are working with.
   */
  public Load(String filePath, String name, IView view) {
    super(name, filePath, view);
  }

  @Override
  public void runCommand(EnhancedModel model) {
    if (destination.endsWith(".ppm")) {
      this.loadPPM(destination, name, model);
    } else {
      try {
        this.loadImage(destination, name, model);
      } catch (IllegalArgumentException e) {
        this.sendMessage("file not found!");
      }
    }
  }

  /**
   * loads a PPM into the image library.
   *
   * @param filename the filepath of this file.
   * @param nickname what the image should be referred to as.
   * @param model    the model in which the image should be placed in.
   */
  private void loadPPM(String filename, String nickname, IModel model) {
    try {
      model.addImage(nickname, ImageUtil.readPPM(filename));
      this.sendMessage("image loaded!");
    } catch (IllegalArgumentException ex) {
      this.sendMessage("error loading file");
    }
  }

  /**
   * helps load the image by reading the file and giving it to the model to store it.
   *
   * @param filename the filepath of this file.
   * @param nickname what the image should be referred to.
   * @param model    the model in which the image should be referred to.
   */
  private void loadImage(String filename, String nickname, IModel model)
          throws IllegalArgumentException {
    if (!filename.endsWith(".png") && !filename.endsWith(".bmp") && !filename.endsWith(".jpg")) {
      this.sendMessage("this is an unsupported file type.");
      return;
    }
    File file = new File(filename);
    try {
      BufferedImage image = ImageIO.read(file);
      int h = image.getHeight();
      int w = image.getWidth();
      Pixel[][] pixels = new Pixel[h][w];
      for (int i = 0; i < h; i = i + 1) {
        for (int j = 0; j < w; j = j + 1) {
          Color color = new Color(image.getRGB(j, i), true);
          int red = color.getRed();
          int blue = color.getBlue();
          int green = color.getGreen();
          int alpha = color.getAlpha();
          pixels[i][j] = new Pixel(red, green, blue, alpha);
        }
      }
      model.addImage(nickname, pixels);
      this.sendMessage("image loaded!");
    } catch (IOException e) {
      this.sendMessage("this file does not exist");
    }
  }
}
