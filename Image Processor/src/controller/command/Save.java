package controller.command;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.EnhancedModel;
import model.IModel;
import view.IView;

/**
 * represents the save command for the image processor program. This command
 * takes an image from the system and saves it to a specified file path.
 */
public class Save extends controller.command.ACommand {
  /**
   * constructor that takes in the filePath we are saving the image to,
   * the image that we are saving, and the view we are working with.
   *
   * @param filePath where this image is getting saved.
   * @param name     the image we are saving.
   * @param view     an instance of the view we are dealing with.
   */
  public Save(String filePath, String name, IView view) {
    super(name, filePath, view);
  }

  @Override
  public void runCommand(EnhancedModel model) {
    this.save(this.destination, this.name, model);
    this.sendMessage("image saved :)");
  }


  /**
   * Save saves a particular file from the image collection to the computer.
   *
   * @param filePath where the image should be saved to.
   * @param name     the name of the image in the image collection.
   * @param model    the model that is being used.
   * @throws IllegalArgumentException if the file does not exist (however this is not possible since
   *                                  the file is made in the method).
   * @throws IllegalStateException    if the conversion of the method fails.
   */
  private void save(String filePath, String name, IModel model)
          throws IllegalArgumentException, IllegalStateException {
    File newImage = new File(filePath);
    FileOutputStream output;
    try {
      output = new FileOutputStream(newImage);
    } catch (FileNotFoundException ex) {
      throw new IllegalArgumentException("This is not an existing file.");
    }
    if (filePath.endsWith("ppm")) {
      String imageAsText = model.getImage(name).convertToText();
      try {
        output.write(imageAsText.getBytes());
        output.close();
      } catch (IOException e) {
        throw new IllegalStateException("Conversion failed!");
      }
    } else {
      int fileFormatIndex = filePath.lastIndexOf(".");
      String fileFormat = filePath.substring(fileFormatIndex + 1);
      BufferedImage image = model.convertToBufferedImage(name, fileFormat);
      try {
        ImageIO.write(image, fileFormat, newImage);
      } catch (IOException e) {
        throw new IllegalArgumentException("file saving failed");
      }
    }
  }

}
