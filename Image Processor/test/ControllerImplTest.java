import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import controller.ControllerImpl;
import controller.IController;
import model.EnhancedModel;
import model.EnhancedModelImpl;
import view.IView;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * represents the test class for the ControllerImpl
 * implementation of the controller interface.
 */
public class ControllerImplTest {

  Readable in1;
  Readable in2;
  Readable in3;
  Readable in4;
  Readable in5;
  Readable in6;
  Readable in7;
  Readable in8;
  Readable in9;
  Readable in10;
  Readable in11;
  Readable in12;
  Readable in13;
  Readable in14;
  Readable in15;
  Readable in16;
  Readable in17;
  Readable in18;
  Readable in19;
  Readable in20;
  Readable in21;
  Readable in22;
  Readable in23;
  Readable in24;
  Readable in25;
  Readable in26;
  Readable in27;
  Readable in28;
  Readable in29;
  Readable in30;
  Readable in31;
  Readable in32;
  Readable in33;
  Readable in34;
  Readable in35;

  EnhancedModel model1;
  EnhancedModel model2;
  IView view1;
  Appendable appendable1;

  @Before
  public void init() {
    //tests just loading an image
    this.in1 = new InputStreamReader(
            new ByteArrayInputStream("load res/flower.ppm koala\n q\n".getBytes()));
    //tests just loading and saving an image
    this.in2 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n " +
                    "save res/newFlower.ppm koala\n q\n").getBytes()));
    //loads and saves an image
    this.in3 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n " +
                    "save res/newFlower.ppm koala\n q\n").getBytes()));
    //loads, visualizes the red component and then saves
    this.in4 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n " +
                    "red-component koala newKoala\n" +
                    "save res/newFlower.ppm newKoala\n q\n").getBytes()));
    //does literally every function to the koala
    this.in5 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n " +
                    "red-component koala newKoala\n" +
                    "blue-component newKoala newKoala1\n" +
                    "green-component newKoala1 newKoala2\n" +
                    "value-component newKoala2 newKoala3\n" +
                    "intensity-component newKoala3 newKoala4\n" +
                    "luma-component newKoala4 newKoala5\n" +
                    "brighten 100 newKoala5 newKoala6\n" +
                    "brighten -100 newKoala6 newKoala7\n" +
                    "horizontal-flip newKoala7 newKoala8\n" +
                    "vertical-flip newKoala8 newKoala9\n" +
                    "save res/newFlower.ppm koala\n q\n").getBytes()));
    //invalid input #1: load takes in an invalid file
    this.in6 = new InputStreamReader(
            new ByteArrayInputStream(("load res/fake.ppm koala\n " +
                    "brighten 100 koala newKoala\n q\n").getBytes()));
    //invalid input #2: load doesn't have a specified destination name
    this.in7 = new InputStreamReader(
            new ByteArrayInputStream("load res/flower.ppm\n".getBytes()));
    //invalid input #3: try to perform a function on something that hasn't been loaded
    this.in8 = new InputStreamReader(
            new ByteArrayInputStream("brighten 100 koala newKoala\n q\n".getBytes()));
    //invalid input: brighten but not other inputs
    this.in9 = new InputStreamReader(
            new ByteArrayInputStream("brighten\n q\n".getBytes()));
    //invalid input #4: brighten with an invalid name --> doesn't exist in hashmap
    this.in10 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "brighten 100 none newNone\n q\n").getBytes()));
    //invalid input: brighten with no integer as the scale
    this.in11 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "brighten ?? koala newNone\n q\n").getBytes()));
    //invalid input: brighten with only a scale
    this.in12 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "brighten 100\n").getBytes()));
    //invalid input: brighten with only a scale and name
    this.in13 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "brighten 100 none\n").getBytes()));

    //invalid input: vertical-flip with no other inputs
    this.in14 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "vertical-flip \n").getBytes()));
    //invalid input: vertical-flip with an invalid name --> doesn't exist in hashmap
    this.in15 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "vertical-flip invalid newKoala\n q\n").getBytes()));
    //invalid input: vertical-flip with no destination
    this.in16 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "vertical-flip invalid \n").getBytes()));

    //invalid input: horizontal-flip with no other inputs
    this.in17 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "horizontal-flip \n").getBytes()));
    //invalid input: horizontal-flip with an invalid name --> doesn't exist in hashamp
    this.in18 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "horizontal-flip invalid newKoala\n q\n").getBytes()));
    //invalid input: horizontal-flip with no destination
    this.in19 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "horizontal-flip\n").getBytes()));

    //invalid input: component that doesn't exist
    this.in20 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "yellow-component koala newKoala\n q\n").getBytes()));

    //invalid input: red-component with no other inputs
    this.in21 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "red-component\n q\n").getBytes()));
    //invalid input: red-component with an invalid name --> doesn't exist in hashamp
    this.in22 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "red-component invalid newKoala\n q\n").getBytes()));
    //invalid input: red-component with no destination
    this.in23 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "red-component koala\n").getBytes()));

    //invalid input: save an image that doesn't exist in the hashMap
    this.in24 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "red-component koala newKoala\n save invalid.ppm invalid\n q\n").getBytes()));
    //invalid input: command that doesn't exist in the middle of the program
    this.in25 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n " +
                    "red-component koala newKoala\n" +
                    "blue-component newKoala newKoala1\n" +
                    "green-component newKoala1 newKoala2\n" +
                    "value-component newKoala2 newKoala3\n" +
                    "intensity-component newKoala3 newKoala4\n" +
                    "invalid-function newKoala4 invalidKoala\n" +
                    "luma-component newKoala4 newKoala5\n" +
                    "brighten 100 newKoala5 newKoala6\n" +
                    "brighten -100 newKoala6 newKoala7\n" +
                    "horizontal-flip newKoala7 newKoala8\n" +
                    "vertical-flip newKoala8 newKoala9\n" +
                    "save res/newFlower.ppm koala\n q\n").getBytes()));
    this.in26 = new InputStreamReader(new ByteArrayInputStream("".getBytes()));
    this.in27 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n " +
                    "red-component koala newKoala\n" +
                    "blue-component newKoala newKoala1\n" +
                    "green-component newKoala1 newKoala2\n" +
                    "value-component newKoala2 newKoala3\n" +
                    "intensity-component newKoala3 newKoala4\n" +
                    "q\n" +
                    "luma-component newKoala4 newKoala5\n" +
                    "brighten 100 newKoala5 newKoala6\n" +
                    "brighten -100 newKoala6 newKoala7\n" +
                    "horizontal-flip newKoala7 newKoala8\n" +
                    "vertical-flip newKoala8 newKoala9\n" +
                    "save res/newFlower.ppm koala\n q\n").getBytes()));
    this.in28 = new InputStreamReader(
            new ByteArrayInputStream(("q\n load res/flower.ppm koala\n " +
                    "red-component koala newKoala\n" +
                    "blue-component newKoala newKoala1\n" +
                    "green-component newKoala1 newKoala2\n" +
                    "value-component newKoala2 newKoala3\n" +
                    "intensity-component newKoala3 newKoala4\n" +
                    "luma-component newKoala4 newKoala5\n" +
                    "brighten 100 newKoala5 newKoala6\n" +
                    "brighten -100 newKoala6 newKoala7\n" +
                    "horizontal-flip newKoala7 newKoala8\n" +
                    "vertical-flip newKoala8 newKoala9\n" +
                    "save res/newFlower.ppm koala\n q\n").getBytes()));
    this.in29 = new InputStreamReader(new ByteArrayInputStream(("load res/flower.ppm koala\n" +
            "brighten 0 koala newKoala").getBytes()));
    //invalid input: luma-component with an invalid name --> doesn't exist in hashamp
    this.in30 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "luma-component invalid newKoala\n q\n").getBytes()));
    //invalid input: value-component with an invalid name --> doesn't exist in hashamp
    this.in31 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "value-component invalid newKoala\n q\n").getBytes()));
    //invalid input: intensity-component with an invalid name --> doesn't exist in hashamp
    this.in32 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "intensity-component invalid newKoala\n q\n").getBytes()));
    //invalid input: green-component with an invalid name --> doesn't exist in hashamp
    this.in33 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "green-component invalid newKoala\n q\n").getBytes()));
    //invalid input: blue-component with an invalid name --> doesn't exist in hashamp
    this.in34 = new InputStreamReader(
            new ByteArrayInputStream(("load res/flower.ppm koala\n" +
                    "blue-component invalid newKoala\n q\n").getBytes()));
    this.in35 = readFile("test/testScript");
    this.model1 = new EnhancedModelImpl();
    this.appendable1 = new StringBuilder();
    this.model2 = new MockModel(this.appendable1);
    this.view1 = new ImageTextView(this.appendable1);
  }

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

  //tests invalid constructor: null readable
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInit1() {
    IController controller = new ControllerImpl(null,model1,view1);
  }

  //tests invalid constructor: null model
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInit2() {
    IController controller = new ControllerImpl(in1,null,view1);
  }

  //tests invalid constructor: null view
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInit3() {
    IController controller = new ControllerImpl(in1,model1,null);
  }

  //tests invalid constructor: null readable and null model
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInit4() {
    IController controller = new ControllerImpl(null,model1,view1);
  }

  //tests invalid constructor: null readable and null model and null view
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInit5() {
    IController controller = new ControllerImpl(null,null,null);
  }

  //tests invalid constructor: null model and null view
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInit6() {
    IController controller = new ControllerImpl(in1,null,null);
  }

  //tests the output when we just load an image
  //also tests the load function
  @Test
  public void testLoad() {
    IController controller = new ControllerImpl(this.in1,this.model1,this.view1);
    controller.startProgram();
    assertEquals("Welcome to the image processing program! Here are a list of supported " +
            "functions:\n" +
            "1. load 'image-path' 'image-name': loads an image from the specified 'image-path'\n" +
            " into the program.This image can be referenced henceforth in the  program by the " +
            "given" + " 'image-name'\n" +
            "2. save 'image-path' image-name': saves an image with the given 'image-name' in\n" +
            " the program to the specified 'image-path'\n" +
            "3. 'component-name'-component 'image-name' 'dest-image-name': creates a greyscale\n" +
            "image with the 'component-name'(can be red, green, blue, value, intensity or luma)\n" +
            " component of the image specified by'image-name'This new version of the \n" +
            "image can now be referenced in the program by the given 'dest-image-name'. \n" +
            "9. horizontal-flip 'image-name' 'dest-image-name': flips the image specified by \n" +
            "'image-name' horizontally and saves this new version in the program as " +
            "'dest-image-name'\n" +
            "10. vertical-flip 'image-name' 'dest-image-name': flips the image specified by \n" +
            "'image-name' vertically and saves this new version in the program \n" +
            "as 'dest-image-name'\n" +
            "11. brighten 'increment' 'image-name' 'dest-image-name': brighten the image by the\n" +
            "given 'increment' to create a new image, referred to by the given\n" +
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
            "16. q : quits the program\n" +
            "image loaded!\n" +
            "image processor terminated :(\n", this.appendable1.toString());
  }

  //mock version of the input above
  @Test
  public void testMockLoad() {
    IController controller = new ControllerImpl(this.in1, this.model2, this.view1);
    controller.startProgram();
    assertEquals("Welcome to the image processing program! Here are a list of supported " +
            "functions:\n" +
            "1. load 'image-path' 'image-name': loads an image from the specified 'image-path'\n" +
            " into the program.This image can be referenced henceforth in the  program by the " +
            "given" + " 'image-name'\n" +
            "2. save 'image-path' image-name': saves an image with the given 'image-name' in\n" +
            " the program to the specified 'image-path'\n" +
            "3. 'component-name'-component 'image-name' 'dest-image-name': creates a greyscale\n" +
            "image with the 'component-name'(can be red, green, blue, value, intensity or luma)\n" +
            " component of the image specified by'image-name'This new version of the \n" +
            "image can now be referenced in the program by the given 'dest-image-name'. \n" +
            "9. horizontal-flip 'image-name' 'dest-image-name': flips the image specified by \n" +
            "'image-name' horizontally and saves this new version in the program as " +
            "'dest-image-name'\n" +
            "10. vertical-flip 'image-name' 'dest-image-name': flips the image specified by \n" +
            "'image-name' vertically and saves this new version in the program \n" +
            "as 'dest-image-name'\n" +
            "11. brighten 'increment' 'image-name' 'dest-image-name': brighten the image by the\n" +
            "given 'increment' to create a new image, referred to by the given\n" +
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
            "16. q : quits the program\n" +
            "add image: koala 90\n" +
            "image loaded!\n" +
            "image processor terminated :(\n", this.appendable1.toString());
  }

  //tests loading and saving an image
  @Test
  public void testLoadAndSave() {
    IController controller = new ControllerImpl(this.in2, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith("image loaded!\n" +
            "image saved :)\n" +
            "image processor terminated :(\n"));
  }

  //mock version of the input above
  @Test
  public void testMockLoadAndSave() {
    IController controller = new ControllerImpl(this.in2, this.model2, this.view1);
    controller.startProgram();
    assertTrue(this.appendable1.toString().endsWith("add image: koala 90\n" +
            "image loaded!\n" +
            "get image: koala\n" +
            "image saved :)\n" +
            "image processor terminated :(\n"));
  }

  //tests loading, visualizing the red component and then saving
  @Test
  public void testRedComp() {
    IController controller = new ControllerImpl(this.in4, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith("image loaded!\n" +
            "Red component visualized!\n" +
            "image saved :)\n" +
            "image processor terminated :(\n"));
  }

  //mock version of the input above
  @Test
  public void testMockRedComp() {
    IController controller = new ControllerImpl(this.in4, this.model2, this.view1);
    controller.startProgram();
    assertTrue(this.appendable1.toString().endsWith("image loaded!\n" +
            "visualize component: koala Red newKoala\n" +
            "Red component visualized!\n" +
            "get image: newKoala\n" +
            "image saved :)\n" +
            "image processor terminated :(\n"));
  }


  //computes every method on the same image
  @Test
  public void testAll() {
    IController controller = new ControllerImpl(this.in5, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith("image loaded!\n" +
            "Red component visualized!\n" +
            "Blue component visualized!\n" +
            "Green component visualized!\n" +
            "Value component visualized!\n" +
            "Intensity component visualized!\n" +
            "Luma component visualized!\n" +
            "image brightness changed!\n" +
            "image brightness changed!\n" +
            "flipped horizontally!!!!!!\n" +
            "image flipped vertically!\n" +
            "image saved :)\n" +
            "image processor terminated :(\n"));
  }

  //mock version of the input above
  @Test
  public void testMockAllFuncs() {
    IController controller = new ControllerImpl(this.in5, this.model2, this.view1);
    controller.startProgram();
    assertTrue(this.appendable1.toString().endsWith("add image: koala 90\n" +
            "image loaded!\n" +
            "visualize component: koala Red newKoala\n" +
            "Red component visualized!\n" +
            "visualize component: newKoala Blue newKoala1\n" +
            "Blue component visualized!\n" +
            "visualize component: newKoala1 Green newKoala2\n" +
            "Green component visualized!\n" +
            "visualize component: newKoala2 Value newKoala3\n" +
            "Value component visualized!\n" +
            "visualize component: newKoala3 Intensity newKoala4\n" +
            "Intensity component visualized!\n" +
            "visualize component: newKoala4 Luma newKoala5\n" +
            "Luma component visualized!\n" +
            "change brightness: 100 newKoala5 newKoala6\n" +
            "image brightness changed!\n" +
            "change brightness: -100 newKoala6 newKoala7\n" +
            "image brightness changed!\n" +
            "flip horizontally: newKoala7 newKoala8\n" +
            "flipped horizontally!!!!!!\n" +
            "flip vertically: newKoala8 newKoala9\n" +
            "image flipped vertically!\n" +
            "get image: koala\n" +
            "image saved :)\n" +
            "image processor terminated :(\n"));
  }

  //tests output when load takes an invalid file
  @Test
  public void testInvalidFile() {
    IController controller = new ControllerImpl(this.in6, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith("error loading file\n" +
            "this image does not exist in the system\n" +
            "image processor terminated :(\n"));
  }

  //mock version of the input above
  @Test
  public void testMockInvalidFile() {
    IController controller = new ControllerImpl(this.in6, this.model2, this.view1);
    controller.startProgram();
    assertTrue(this.appendable1.toString().endsWith("error loading file\n" +
            "change brightness: 100 koala newKoala\n" +
            "image brightness changed!\n" +
            "image processor terminated :(\n"));
  }


  //tests output when load isn't given a destination name
  @Test
  public void testInvalidDestination() {
    IController controller = new ControllerImpl(this.in7, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith("ran out of inputs!\n"));
  }

  //mock version of the input above
  @Test
  public void testMockInvalidDestination() {
    IController controller = new ControllerImpl(this.in7, this.model2, this.view1);
    controller.startProgram();
    assertTrue(this.appendable1.toString().endsWith("ran out of inputs!\n"));
  }


  //tests output when a function is called on something that hasn't been loaded
  @Test
  public void testNoLoad() {
    IController controller = new ControllerImpl(this.in8, this.model1,this.view1);
    controller.startProgram();
    //check that the brighten method isn't performed
    assertTrue(this.appendable1.toString().endsWith("this image does not exist in the system\n" +
                    "image processor terminated :(\n"));
  }

  //tests output when we call the method but don't give any other necessary inputs
  @Test
  public void testNoInputs() {
    IController controller = new ControllerImpl(this.in9, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith("ran out of inputs!\n"));
  }

  //test brighten with an image not loaded into the system
  @Test
  public void testBrightenNoName() {
    IController controller = new ControllerImpl(this.in10, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith("image loaded!\n" +
            "this image does not exist in the system\n" +
            "image processor terminated :(\n"));
  }

  //test brighten with a non integer as the scale--> program must keep reading until integer
  @Test
  public void testBrightenNoString() {
    IController controller = new ControllerImpl(this.in11, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "brightness constant must be a non zero integer\n" +
                    "image processor terminated :(\n"));
  }

  //test brighten with only a scale
  @Test
  public void testBrightenOnlyScale() {
    IController controller = new ControllerImpl(this.in12, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "ran out of inputs!\n"));
  }

  //test brighten with only a scale and a name
  @Test
  public void testBrightenNoDestination() {
    IController controller = new ControllerImpl(this.in13, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "ran out of inputs!\n"));
  }

  //test vertical flip with only a scale
  @Test
  public void testVerticalFlipNoInputs() {
    IController controller = new ControllerImpl(this.in14, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "ran out of inputs!\n"));
  }

  //test vertical flip with an image that hasn't been loaded
  @Test
  public void testVerticalFlipInvalidImage() {
    IController controller = new ControllerImpl(this.in15, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "this image does not exist in the system\n" +
                    "image processor terminated :(\n"));
  }

  //test vertical flip with no destination
  @Test
  public void testVerticalFlipNoDestination() {
    IController controller = new ControllerImpl(this.in16, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "ran out of inputs!\n"));
  }

  //test horizontal flip with no inputs
  @Test
  public void testHorizontalFlipNoInput() {
    IController controller = new ControllerImpl(this.in17, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "ran out of inputs!\n"));
  }

  //test horizontal flip with an image that hasn't been loaded
  @Test
  public void testHorizontalFlipInvalidImage() {
    IController controller = new ControllerImpl(this.in18, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "this image does not exist in the system\n" +
                    "image processor terminated :(\n"));
  }

  //test horizontal flip with an image that hasn't been loaded
  @Test
  public void testHorizontalFlipNoDestination() {
    IController controller = new ControllerImpl(this.in19, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "ran out of inputs!\n"));
  }

  //test visualizing component with an invalid component
  @Test
  public void testInvalidComponent() {
    IController controller = new ControllerImpl(this.in20, this.model1, this.view1);
    controller.startProgram();
    assertTrue(this.appendable1.toString().endsWith("image loaded!\n" +
            "not a supported function\n" +
            "not a supported function\n" +
            "not a supported function\n" +
            "image processor terminated :(\n"));
  }


  //test red-component  with an image that hasn't been loaded
  @Test
  public void testRedCompNoInputs() {
    IController controller = new ControllerImpl(this.in21, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "ran out of inputs!\n"));
  }

  //test red-component  with an image that hasn't been loaded
  @Test
  public void testRedCompInvalidName() {
    IController controller = new ControllerImpl(this.in22, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "this image does not exist in the system\n" +
                    "image processor terminated :(\n"));
  }

  //test red-component  with no destination
  @Test
  public void testRedCompNoDestination() {
    IController controller = new ControllerImpl(this.in23, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "ran out of inputs!\n"));
  }

  //save an image that doesn't exist in the hashmap
  @Test
  public void testSaveInvalidImage() {
    IController controller = new ControllerImpl(this.in24, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "Red component visualized!\n" +
                    "this image does not exist in the system\n" +
                    "image processor terminated :(\n"));
  }

  //runs the processor with a command that doesn't exist in the middle of multiple
  // valid commands
  @Test
  public void testHiddenInvalidCommand() {
    IController controller = new ControllerImpl(this.in25, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "Red component visualized!\n" +
                    "Blue component visualized!\n" +
                    "Green component visualized!\n" +
                    "Value component visualized!\n" +
                    "Intensity component visualized!\n" +
                    "not a supported function\n" +
                    "not a supported function\n" +
                    "not a supported function\n" +
                    "Luma component visualized!\n" +
                    "image brightness changed!\n" +
                    "image brightness changed!\n" +
                    "flipped horizontally!!!!!!\n" +
                    "image flipped vertically!\n" +
                    "image saved :)\n" +
                    "image processor terminated :(\n"));
  }

  //mock version of the input above
  @Test
  public void testMockHiddenInvalidCommand() {
    IController controller = new ControllerImpl(this.in25, this.model2, this.view1);
    controller.startProgram();
    assertTrue(this.appendable1.toString().endsWith("add image: koala 90\n" +
            "image loaded!\n" +
            "visualize component: koala Red newKoala\n" +
            "Red component visualized!\n" +
            "visualize component: newKoala Blue newKoala1\n" +
            "Blue component visualized!\n" +
            "visualize component: newKoala1 Green newKoala2\n" +
            "Green component visualized!\n" +
            "visualize component: newKoala2 Value newKoala3\n" +
            "Value component visualized!\n" +
            "visualize component: newKoala3 Intensity newKoala4\n" +
            "Intensity component visualized!\n" +
            "not a supported function\n" +
            "not a supported function\n" +
            "not a supported function\n" +
            "visualize component: newKoala4 Luma newKoala5\n" +
            "Luma component visualized!\n" +
            "change brightness: 100 newKoala5 newKoala6\n" +
            "image brightness changed!\n" +
            "change brightness: -100 newKoala6 newKoala7\n" +
            "image brightness changed!\n" +
            "flip horizontally: newKoala7 newKoala8\n" +
            "flipped horizontally!!!!!!\n" +
            "flip vertically: newKoala8 newKoala9\n" +
            "image flipped vertically!\n" +
            "get image: koala\n" +
            "image saved :)\n" +
            "image processor terminated :(\n"));
  }


  //runs the image processor with no inputs
  @Test
  public void testNoInputsAtAll() {
    IController controller = new ControllerImpl(this.in26, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    //since there are no inputs --> nothing should happen after the quit message in the menu
    assertTrue(this.appendable1.toString().endsWith(
            "16. q : quits the program\n"));
  }

  //runs the image processor with quit in the middle
  @Test
  public void testQuitMiddle() {
    IController controller = new ControllerImpl(this.in27, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    //only has function calls up until the q
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "Red component visualized!\n" +
                    "Blue component visualized!\n" +
                    "Green component visualized!\n" +
                    "Value component visualized!\n" +
                    "Intensity component visualized!\n" +
                    "image processor terminated :(\n"));
  }

  //runs the image processor with quit at the beginning
  @Test
  public void testQuitBeginning() {
    IController controller = new ControllerImpl(this.in28, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    //only has function calls up until the q
    assertTrue(this.appendable1.toString().endsWith(
            "image processor terminated :(\n"));
  }

  //tests output when brightness is given a scale of 0
  @Test
  public void testBrightnessZero() {
    IController controller = new ControllerImpl(this.in29, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    //if brightness constant is 0, invalidates the rest of the call
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "brightness constant must be a non zero integer\n"));
  }

  //test luma-component  with an image that hasn't been loaded
  @Test
  public void testLumaCompInvalidName() {
    IController controller = new ControllerImpl(this.in30, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "this image does not exist in the system\n" +
                    "image processor terminated :(\n"));
  }

  //test value-component  with an image that hasn't been loaded
  @Test
  public void testValueCompInvalidName() {
    IController controller = new ControllerImpl(this.in31, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "this image does not exist in the system\n" +
                    "image processor terminated :(\n"));
  }

  //test intensity-component  with an image that hasn't been loaded
  @Test
  public void testIntensityCompInvalidName() {
    IController controller = new ControllerImpl(this.in32, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "this image does not exist in the system\n" +
                    "image processor terminated :(\n"));
  }

  //test green-component  with an image that hasn't been loaded
  @Test
  public void testGreenCompInvalidName() {
    IController controller = new ControllerImpl(this.in33, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "this image does not exist in the system\n" +
                    "image processor terminated :(\n"));
  }

  //test blue-component  with an image that hasn't been loaded
  @Test
  public void testBlueCompInvalidName() {
    IController controller = new ControllerImpl(this.in34, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "this image does not exist in the system\n" +
                    "image processor terminated :(\n"));
  }

  //test script as a readable
  @Test
  public void testScript() {
    IController controller = new ControllerImpl(this.in35, this.model1,this.view1);
    controller.startProgram();
    //doesn't test the beginning since we have already tested the menu being printed above
    assertTrue(this.appendable1.toString().endsWith(
            "image loaded!\n" +
                    "image brightness changed!\n" +
                    "image brightness changed!\n" +
                    "flipped horizontally!!!!!!\n" +
                    "image flipped vertically!\n" +
                    "Green component visualized!\n" +
                    "Blue component visualized!\n" +
                    "Red component visualized!\n" +
                    "Luma component visualized!\n" +
                    "Intensity component visualized!\n" +
                    "Value component visualized!\n" +
                    "image has a sepia filter now!\n" +
                    "image converted to greyscale!\n" +
                    "image blurred!\n" +
                    "image sharpened!\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image saved :)\n" +
                    "image processor terminated :(\n"));
  }

}