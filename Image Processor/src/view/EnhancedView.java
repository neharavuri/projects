package view;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import controller.Features;

/**
 * Represents the operations necessary for the enhanced view which is used for the GUI view.
 */
public interface EnhancedView extends IView {
  /**
   * This method makes the entire frame visible to the user.
   */
  void makeVisible();

  /**
   * Updates the image in the image panel to be this new image.
   *
   * @param image the image that should be visualized.
   */
  void updateImage(BufferedImage image);

  /**
   * Sets up all the action listeners for the different components that can be interacted with.
   *
   * @param features represents the controller that should be called when the view needs
   *                 to interact with the rest of the program.
   */
  void addFeatures(Features features);

  /**
   * Draws the histogram for the current image. All 4 of the histograms (one for each color and one
   * for intensity) are drawn on the same axis.
   *
   * @param red       the histogram for the red component represented by a Hashmap.
   * @param green     the histogram for the green component represented by a Hashmap.
   * @param blue      the histogram for the blue component represented by a Hashmap.
   * @param intensity the histogram for the intensity component represented by a Hashmap.
   */
  void drawHistogram(HashMap<Integer, Integer> red, HashMap<Integer, Integer> green,
                     HashMap<Integer, Integer> blue, HashMap<Integer, Integer> intensity);
}
