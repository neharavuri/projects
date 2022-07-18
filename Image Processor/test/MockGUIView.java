import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import controller.Features;
import view.EnhancedView;

/**
 * Represents a mock version of the GUI view.
 */
public class MockGUIView implements EnhancedView {
  StringBuilder log;

  public MockGUIView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void makeVisible() {
    log.append("makeVisible\n");
  }

  @Override
  public void updateImage(BufferedImage image) {
    log.append("updateImage\n");
  }

  @Override
  public void addFeatures(Features features) {
    log.append("addFeatures\n");
  }

  @Override
  public void drawHistogram(HashMap<Integer, Integer> red, HashMap<Integer, Integer> green,
                            HashMap<Integer, Integer> blue, HashMap<Integer, Integer> intensity) {
    log.append("drawHistogram\n");
  }

  @Override
  public void renderMessage(String message) throws IOException {
    log.append("renderMessage: " + message + "\n");
  }
}
