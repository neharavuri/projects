package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;

import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

/**
 * Represents the view for the graphical user interface. It allows for the user to interact with
 * the program not through the console or through files. In this view, the image the user is
 * working on can be seen as well as the histogram for the current user. The user can click on
 * a drop-down menu to change the image in specific ways. There is a button to load an image
 * and to save an image. The save button will save whatever current image is being worked on and
 * the user can use a finder view to determine where they want to save it. A similar finder view
 * is used to find which image the user wants to load into the program.
 */
public class GUIView extends JFrame implements EnhancedView {

  // the load button
  private final JButton load;
  // the save button
  private final JButton save;
  // the panel in which the image being worked on is visualized
  private final JLabel imageLabel;
  // the entire panel being focused on
  private final JPanel mainPanel;
  // the button to cause a transformation of an image
  private final JButton perform;
  // a drop-down of the different options the user can pick for to modify their image
  private final JComboBox<String> comboBox;
  // the scroll bar on the image
  private final JScrollPane imageScroll;
  // the panel showing the histogram
  private final JPanel histoPanel;

  /**
   * Represents the constructor for the GUIView which initializes all the different components
   * to be in the right spot and look the correct way.
   */
  public GUIView() {
    super();
    this.setTitle("Image Processing Program!");
    this.setSize(1500, 1000);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout());
    JPanel bottomPanel = new JPanel();

    // sets up the main panel to have scroll bars around it
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    JScrollPane mainScroll = new JScrollPane(mainPanel);
    add(mainScroll);


    // sets up the load button
    this.load = new JButton("Load new image");
    topPanel.add(load);

    // sets up the save button
    this.save = new JButton("Save current image");
    topPanel.add(save);

    // the image being worked on with a scrollbar
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Current Image"));
    imagePanel.setMaximumSize(new Dimension(550, 550));
    topPanel.add(imagePanel);
    imageLabel = new JLabel();
    imageScroll = new JScrollPane(imageLabel);
    imageScroll.setPreferredSize(new Dimension(550, 550));
    imagePanel.add(imageScroll);

    // the panel for the histograms
    this.histoPanel = new JPanel();
    histoPanel.setLayout(new BoxLayout(histoPanel, BoxLayout.X_AXIS));
    histoPanel.setPreferredSize(new Dimension(540, 400));
    histoPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    JPanel graphPanel = new JPanel();
    graphPanel.setLayout(new BorderLayout());
    graphPanel.add(histoPanel, BorderLayout.CENTER);
    JLabel label = new JLabel("0" + this.addSpaces() + "255");
    graphPanel.add(label,BorderLayout.PAGE_END);
    topPanel.add(graphPanel);


    // combo box with the different commands that can be chosen
    JPanel options = new JPanel();
    options.setBorder(BorderFactory.createTitledBorder("Image Processing Tools"));
    bottomPanel.add(options);

    JLabel optionsDisplay = new JLabel("Select an operation to perform on your image.");
    options.add(optionsDisplay);
    String[] tools = {"Brighten", "Flip horizontally", "Flip vertically", "Visualize red",
        "Visualize green", "Visualize blue", "Visualize intensity", "Visualize value",
        "Visualize luma", "Blur", "Sharpen", "Apply greyscale filter", "Apply sepia filter",
        "Downsize"};
    comboBox = new JComboBox<String>();
    for (int i = 0; i < tools.length; i++) {
      comboBox.addItem(tools[i]);
    }
    options.add(comboBox);
    perform = new JButton("Perform operation");
    options.add(perform);
    mainPanel.add(topPanel);
    mainPanel.add(bottomPanel);

    add(mainScroll);
  }

  private String addSpaces() {
    String output = "";
    for (int i = 0; i < 124; i++) {
      output = output + " ";
    }
    return output;
  }


  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void updateImage(BufferedImage image) {
    imageLabel.setIcon(new ImageIcon(image));
    imageScroll.revalidate();
    imageScroll.repaint();
  }

  @Override
  public void addFeatures(Features features) {
    // the load action listener sets up a file chooser and then once the user chooses a file,
    // then it tells the controller to deal with the load giving it the filepath of the file to be
    // loaded
    load.addActionListener(e -> {
      final JFileChooser fileChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM, JPG, PNG & BMP" +
              "images", "jpg", "ppm", "png", "bmp");
      fileChooser.setFileFilter(filter);
      int returnVal = fileChooser.showOpenDialog(mainPanel);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        features.load(file.getAbsolutePath());
      }
    });

    // the perform action listener first checks to make sure there is an image to be manipulated
    // and if not, it sends the user a message. Then if the command was brighten, it asks the user
    // to input an extra number for how much to brighten by, it then sends the controller that the
    // user wants to brighten with how much they want it brightened by. This same process happens
    // if the command is to downsize the image but instead it gives two popups to ask for how much
    // the height and width should be now. Otherwise, it just tells the controller that the user
    // wants to perform one of the other commands.
    perform.addActionListener(e -> {
      if (imageLabel.getIcon() == null) {
        sendNoImage();
      } else {
        String command = (String) comboBox.getSelectedItem();
        if (command.equals("Brighten")) {
          String scale = JOptionPane.showInputDialog("Please enter a non zero number as a scale " +
                  "to " + "brighten your" + " image. To darken the" + " image instead, please " +
                  "input a negative scale.");
          features.brighten(scale);
        } else if (command.equals("Downsize")) {
          String height = JOptionPane.showInputDialog("Please enter a non zero number as a " +
                  "height to downsize");
          String width = JOptionPane.showInputDialog("Please enter a non zero number as a " +
                  "width to downsize");
          features.downsize(height, width);
        } else {
          features.modifyImage(command);
        }
      }
    });
    //  the save action listener first checks if there is an image to save and if not, the user is
    // notified of this. Otherwise, the user picks where to save the image and what to save it as.
    // If it is an approved option, then the controller is told to save the current image and
    // where to save it to.
    save.addActionListener(e -> {
      if (imageLabel.getIcon() == null) {
        sendNoImage();
      } else {
        final JFileChooser fileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM, JPG, PNG & " +
                "BMP images", "jpg", "ppm", "png", "bmp");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showSaveDialog(mainPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          features.save(file.getAbsolutePath());
        }
      }
    });

  }

  @Override
  public void drawHistogram(HashMap<Integer, Integer> red, HashMap<Integer, Integer> green,
                            HashMap<Integer, Integer> blue, HashMap<Integer, Integer> intensity) {
    int max = 0;
    // determines the max bar in order to scale the histogram properly.
    for (int i = 0; i < 256; i++) {
      int local = Math.max(red.get(i), Math.max(green.get(i),
              Math.max(blue.get(i), intensity.get(i))));
      max = Math.max(local, max);
    }
    double scale = 400.0 / (double) max;
    // takes off all the old bars for the past histogram off the screen.
    this.histoPanel.removeAll();
    this.histoPanel.revalidate();
    this.histoPanel.repaint();
    // draws all the bars for this current histogram.
    for (int i = 0; i < 256; i++) {
      this.histoPanel.add(new DrawRectangle(red.get(i), green.get(i), blue.get(i),
              intensity.get(i), scale));
    }
    this.histoPanel.revalidate();
    this.histoPanel.repaint();
  }

  /**
   * Sends the user the message if there is no image loaded.
   */
  private void sendNoImage() {
    try {
      renderMessage("No image loaded :(");
    } catch (IOException ex) {
      throw new IllegalStateException("image loading failed");
    }
  }


  @Override
  public void renderMessage(String message) throws IOException {
    JOptionPane.showMessageDialog(this, message, "", JOptionPane.PLAIN_MESSAGE);
  }
}
