import org.junit.Before;
import org.junit.Test;

import model.IModel;
import model.Image;
import model.ModelImpl;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotEquals;

/**
 * Represents the tests needed to test the ModelImpl class using a PPM image.
 */
public class ModelImplTest {
  Pixel[][] fiveByFive;
  Pixel[][] threeByThree;
  Pixel[][] fourByFour;
  IModel defaultImages;
  IModel imagesWith2Images;
  IModel imagesWith3Images;


  @Before
  public void init() {
    this.defaultImages = new ModelImpl();

    this.fiveByFive = new Pixel[][]{
            {new Pixel(0, 0, 255),
             new Pixel(255, 0, 0),
             new Pixel(0, 255, 0),
             new Pixel(100, 100, 100),
             new Pixel(0, 0, 0)},
            {new Pixel(5, 5, 5),
             new Pixel(10, 10, 10),
             new Pixel(20, 20, 20),
             new Pixel(30, 30, 30),
             new Pixel(40, 40, 40)},
            {new Pixel(5, 10, 15),
            new Pixel(20, 30, 40),
            new Pixel(40, 50, 60),
            new Pixel(70, 80, 90),
            new Pixel(100, 110, 120)},
            {new Pixel(3, 2, 1),
             new Pixel(6, 5, 4),
             new Pixel(9, 10, 11),
             new Pixel(14, 13, 12),
             new Pixel(17, 16, 15)},
            {new Pixel(10, 30, 50),
             new Pixel(110, 90, 70),
             new Pixel(130, 170, 150),
             new Pixel(125, 255, 125),
             new Pixel(65, 65, 65)}};

    this.threeByThree = new Pixel[][]{
            {new Pixel(100, 150, 200),
             new Pixel(0, 50, 100),
             new Pixel(255, 255, 255)},
            {new Pixel(150, 150, 150),
             new Pixel(100, 30, 60),
             new Pixel(40, 78, 92)},
            {new Pixel(254, 103, 202),
             new Pixel(6, 70, 139),
             new Pixel(79, 42, 100)}};

    this.fourByFour = new Pixel[][]{
            {new Pixel(80, 40, 40),
             new Pixel(4, 2, 0),
             new Pixel(69, 69, 69),
             new Pixel(60, 60, 60)},
            {new Pixel(49, 59, 69),
             new Pixel(56, 68, 10),
             new Pixel(39, 150, 11),
             new Pixel(96, 243, 206)},
            {new Pixel(0, 111, 222),
             new Pixel(22, 33, 66),
             new Pixel(121, 77, 99),
             new Pixel(84, 96, 108)},
            {new Pixel(68, 70, 63),
             new Pixel(163, 33, 235),
             new Pixel(92, 59, 65),
             new Pixel(112, 195, 253)}};

    imagesWith2Images = new ModelImpl();
    imagesWith2Images.addImage("five", fiveByFive);
    imagesWith2Images.addImage("three", threeByThree);

    imagesWith3Images = new ModelImpl();
    imagesWith3Images.addImage("five", fiveByFive);
    imagesWith3Images.addImage("three", threeByThree);
    imagesWith3Images.addImage("four", fourByFour);

  }

  // checks that an exception is thrown when a null 2d pixel array is given to addImage
  @Test(expected = IllegalArgumentException.class)
  public void testAddPixelArrayGetImage() {
    defaultImages.addImage("kate", null);
  }

  // checks that an exception is thrown when an empty string is given for the name in addImage
  @Test(expected = IllegalArgumentException.class)
  public void testAddImageEmptyString() {
    defaultImages.addImage("", fiveByFive);
  }

  // checks that the add Image method works properly
  @Test
  public void testAddImage() {
    try {
      defaultImages.getImage("kate");
      fail();
    } catch (IllegalArgumentException ex) {
      defaultImages.addImage("kate", fiveByFive);
      Image img = defaultImages.getImage("kate");
      assertEquals("P3\n" +
              "# created by kate and neha :)\n" +
              "5 5\n" + "255\n" + "0 0 255\n" + "255 0 0\n" + "0 255 0\n" +
              "100 100 100\n" + "0 0 0\n" + "5 5 5\n" + "10 10 10\n" + "20 20 20\n" +
              "30 30 30\n" + "40 40 40\n" + "5 10 15\n" + "20 30 40\n" + "40 50 60\n" +
              "70 80 90\n" + "100 110 120\n" + "3 2 1\n" + "6 5 4\n" + "9 10 11\n" +
              "14 13 12\n" + "17 16 15\n" + "10 30 50\n" + "110 90 70\n" + "130 170 150\n" +
              "125 255 125\n" + "65 65 65", img.convertToText());
      try {
        defaultImages.getImage("neha");
        fail();
      } catch (IllegalArgumentException e) {
        defaultImages.addImage("neha", threeByThree);
        Image img2 = defaultImages.getImage("neha");
        assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n"
                + "255\n" + "100 150 200\n" + "0 50 100\n" + "255 255 255\n" +
                "150 150 150\n" + "100 30 60\n" + "40 78 92\n" + "254 103 202\n" +
                "6 70 139\n" + "79 42 100", img2.convertToText());
      }
    }
  }

  // checks that visualizeComponent throws an exception when the name given for the image to
  // visualize is not in the hashmap
  @Test(expected = IllegalArgumentException.class)
  public void testNoImageWithNameVisualizeComponent() {
    imagesWith2Images.visualizeComponent("URMOM", IModel.Component.Intensity, "hey");
  }

  // checks that visualizeComponent throws an exception when an empty string is given for the
  // new name of the altered image
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestinationVisualizeComponent() {
    imagesWith2Images.visualizeComponent("five", IModel.Component.Intensity, "");
  }

  // tests visualizeComponent for the red component
  @Test
  public void testVisualizeRed() {
    imagesWith2Images.visualizeComponent("three", IModel.Component.Red, "red!");
    Image red3 = imagesWith2Images.getImage("red!");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "100 100 100\n" + "0 0 0\n" + "255 255 255\n" +
            "150 150 150\n" + "100 100 100\n" + "40 40 40\n" +
            "254 254 254\n" + "6 6 6\n" + "79 79 79", red3.convertToText());

    imagesWith2Images.visualizeComponent("five", IModel.Component.Red, "redder");
    Image red5 = imagesWith2Images.getImage("redder");
    assertEquals("P3\n" +
                    "# created by kate and neha :)\n" + "5 5\n" + "255\n" + "0 0 0\n" +
                    "255 255 255\n" +
                    "0 0 0\n" + "100 100 100\n" + "0 0 0\n" + "5 5 5\n" + "10 10 10\n" +
                    "20 20 20\n" +
                    "30 30 30\n" + "40 40 40\n" + "5 5 5\n" + "20 20 20\n" + "40 40 40\n" +
                    "70 70 70\n" +
                    "100 100 100\n" + "3 3 3\n" + "6 6 6\n" + "9 9 9\n" + "14 14 14\n" +
                    "17 17 17\n" +
                    "10 10 10\n" + "110 110 110\n" + "130 130 130\n" + "125 125 125\n" + "65 65 65",
            red5.convertToText());
  }

  // tests the visualizeComponent method for blue
  @Test
  public void testVisualizeBlue() {
    imagesWith2Images.visualizeComponent("five", IModel.Component.Blue, "bluey");
    Image blue5 = imagesWith2Images.getImage("bluey");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
            "255 255 255\n" + "0 0 0\n" + "0 0 0\n" + "100 100 100\n" + "0 0 0\n" +
            "5 5 5\n" + "10 10 10\n" + "20 20 20\n" + "30 30 30\n" + "40 40 40\n" +
            "15 15 15\n" + "40 40 40\n" + "60 60 60\n" + "90 90 90\n" + "120 120 120\n" +
            "1 1 1\n" + "4 4 4\n" + "11 11 11\n" + "12 12 12\n" + "15 15 15\n" + "50 50 50\n" +
            "70 70 70\n" + "150 150 150\n" + "125 125 125\n" + "65 65 65", blue5.convertToText());

    imagesWith2Images.visualizeComponent("three", IModel.Component.Blue, "BLUEE");
    Image blue3 = imagesWith2Images.getImage("BLUEE");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "200 200 200\n" + "100 100 100\n" + "255 255 255\n" +
            "150 150 150\n" + "60 60 60\n" + "92 92 92\n" +
            "202 202 202\n" + "139 139 139\n" + "100 100 100", blue3.convertToText());
  }

  // tests the visualizeComponent method for green
  @Test
  public void testVisualizeGreen() {
    imagesWith2Images.visualizeComponent("five", IModel.Component.Green, "gReEn");
    Image green5 = imagesWith2Images.getImage("gReEn");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 0\n" + "0 0 0\n" + "255 255 255\n" + "100 100 100\n" + "0 0 0\n" +
                    "5 5 5\n" + "10 10 10\n" + "20 20 20\n" + "30 30 30\n" + "40 40 40\n" +
                    "10 10 10\n" + "30 30 30\n" + "50 50 50\n" + "80 80 80\n" + "110 110 110\n" +
                    "2 2 2\n" + "5 5 5\n" + "10 10 10\n" + "13 13 13\n" + "16 16 16\n" +
                    "30 30 30\n" + "90 90 90\n" + "170 170 170\n" + "255 255 255\n" + "65 65 65",
            green5.convertToText());

    imagesWith2Images.visualizeComponent("three", IModel.Component.Green, "ew");
    Image green3 = imagesWith2Images.getImage("ew");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "150 150 150\n" + "50 50 50\n" + "255 255 255\n" +
            "150 150 150\n" + "30 30 30\n" + "78 78 78\n" +
            "103 103 103\n" + "70 70 70\n" + "42 42 42", green3.convertToText());
  }

  // tests the visualizeComponent method for value
  @Test
  public void visualizeValue() {
    imagesWith2Images.visualizeComponent("five", IModel.Component.Value, "vulva");
    Image value5 = imagesWith2Images.getImage("vulva");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "255 255 255\n" + "255 255 255\n" + "255 255 255\n" + "100 100 100\n" +
                    "0 0 0\n" +
                    "5 5 5\n" + "10 10 10\n" + "20 20 20\n" + "30 30 30\n" + "40 40 40\n" +
                    "15 15 15\n" + "40 40 40\n" + "60 60 60\n" + "90 90 90\n" + "120 120 120\n" +
                    "3 3 3\n" + "6 6 6\n" + "11 11 11\n" + "14 14 14\n" + "17 17 17\n" +
                    "50 50 50\n" + "110 110 110\n" + "170 170 170\n" + "255 255 255\n" + "65 65 65",
            value5.convertToText());

    imagesWith2Images.visualizeComponent("three", IModel.Component.Value, "tree");
    Image value3 = imagesWith2Images.getImage("tree");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "200 200 200\n" + "100 100 100\n" + "255 255 255\n" +
            "150 150 150\n" + "100 100 100\n" + "92 92 92\n" +
            "254 254 254\n" + "139 139 139\n" + "100 100 100", value3.convertToText());
  }

  // tests the visualizeComponent method for intensity
  @Test
  public void visualizeIntensity() {
    imagesWith2Images.visualizeComponent("three", IModel.Component.Intensity, "INTENSE");
    Image intense3 = imagesWith2Images.getImage("INTENSE");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "150 150 150\n" + "50 50 50\n" + "255 255 255\n" +
            "150 150 150\n" + "63 63 63\n" + "70 70 70\n" +
            "186 186 186\n" + "71 71 71\n" + "73 73 73", intense3.convertToText());

    imagesWith2Images.visualizeComponent("five", IModel.Component.Intensity, "RAWR");
    Image intense5 = imagesWith2Images.getImage("RAWR");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "168\n" +
                    "85 85 85\n" + "85 85 85\n" + "85 85 85\n" + "100 100 100\n" + "0 0 0\n" +
                    "5 5 5\n" + "10 10 10\n" + "20 20 20\n" + "30 30 30\n" + "40 40 40\n" +
                    "10 10 10\n" + "30 30 30\n" + "50 50 50\n" + "80 80 80\n" + "110 110 110\n" +
                    "2 2 2\n" + "5 5 5\n" + "10 10 10\n" + "13 13 13\n" + "16 16 16\n" +
                    "30 30 30\n" + "90 90 90\n" + "150 150 150\n" + "168 168 168\n" + "65 65 65",
            intense5.convertToText());
  }

  // tests the visualizeComponent method for luma
  @Test
  public void testVisualizeLuma() {
    imagesWith2Images.visualizeComponent("three", IModel.Component.Luma, "LOOM");
    Image luma3 = imagesWith2Images.getImage("LOOM");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "254\n" +
            "142 142 142\n" + "42 42 42\n" + "254 254 254\n" +
            "150 150 150\n" + "47 47 47\n" + "70 70 70\n" +
            "142 142 142\n" + "61 61 61\n" + "54 54 54", luma3.convertToText());

    imagesWith2Images.visualizeComponent("five", IModel.Component.Luma, "lol");
    Image luma5 = imagesWith2Images.getImage("lol");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "217\n" +
                    "18 18 18\n" + "54 54 54\n" + "182 182 182\n" + "100 100 100\n" + "0 0 0\n" +
                    "4 4 4\n" + "9 9 9\n" + "19 19 19\n" + "30 30 30\n" + "39 39 39\n" +
                    "9 9 9\n" + "28 28 28\n" + "48 48 48\n" + "78 78 78\n" + "108 108 108\n" +
                    "2 2 2\n" + "5 5 5\n" + "9 9 9\n" + "13 13 13\n" + "16 16 16\n" +
                    "27 27 27\n" + "92 92 92\n" + "160 160 160\n" + "217 217 217\n" + "65 65 65",
            luma5.convertToText());
  }

  // checks that flipHorizontally throws an exception when the name given for the image to
  // flip is not in the hashmap
  @Test(expected = IllegalArgumentException.class)
  public void testNotInHashmapFlipHorizontally() {
    imagesWith2Images.flipHorizontally("bleh", "yuh");
  }

  // checks that flipHorizontally throws an exception when the destination name given for the
  // flipped image is an empty string
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestinationFlipHorizontally() {
    imagesWith2Images.flipHorizontally("five", "");
  }

  // checks that flipHorizontally works properly on its own
  @Test
  public void testFlipHorizontally() {
    // tests an odd amount of columns
    imagesWith2Images.flipHorizontally("five", "FLIPPY");
    Image horizon5 = imagesWith2Images.getImage("FLIPPY");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 0\n" + "100 100 100\n" + "0 255 0\n" + "255 0 0\n" + "0 0 255\n" +
                    "40 40 40\n" + "30 30 30\n" + "20 20 20\n" + "10 10 10\n" + "5 5 5\n" +
                    "100 110 120\n" + "70 80 90\n" + "40 50 60\n" + "20 30 40\n" + "5 10 15\n" +
                    "17 16 15\n" + "14 13 12\n" + "9 10 11\n" + "6 5 4\n" + "3 2 1\n" +
                    "65 65 65\n" + "125 255 125\n" + "130 170 150\n" + "110 90 70\n" + "10 30 50",
            horizon5.convertToText());

    // tests an even amount of columns
    imagesWith3Images.flipHorizontally("four", "OH");
    Image horizon4 = imagesWith3Images.getImage("OH");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "253\n" +
                    "60 60 60\n" + "69 69 69\n" + "4 2 0\n" + "80 40 40\n" +
                    "96 243 206\n" + "39 150 11\n" + "56 68 10\n" + "49 59 69\n" +
                    "84 96 108\n" + "121 77 99\n" + "22 33 66\n" + "0 111 222\n" +
                    "112 195 253\n" + "92 59 65\n" + "163 33 235\n" + "68 70 63",
            horizon4.convertToText());
  }

  // checks that flipVertically throws an exception when the name given for the image to
  // flip is not in the hashmap
  @Test(expected = IllegalArgumentException.class)
  public void testNotInHashmapFlipVertically() {
    imagesWith2Images.flipVertically("heyyyy", "pop");
  }

  // checks that flipVertically throws an exception when the destination name given for the flipped
  // image is an empty string
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestinationFlipVertically() {
    imagesWith2Images.flipVertically("five", "");
  }

  // checks that flipVertically works properly on its own
  @Test
  public void testFlipVertically() {
    // tests an even amount of rows
    imagesWith3Images.flipVertically("four", "farts");
    Image vert4 = imagesWith3Images.getImage("farts");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "253\n" +
            "68 70 63\n" + "163 33 235\n" + "92 59 65\n" + "112 195 253\n" +
            "0 111 222\n" + "22 33 66\n" + "121 77 99\n" + "84 96 108\n" +
            "49 59 69\n" + "56 68 10\n" + "39 150 11\n" + "96 243 206\n" +
            "80 40 40\n" + "4 2 0\n" + "69 69 69\n" + "60 60 60", vert4.convertToText());

    // tests with an  odd number of rows
    imagesWith3Images.flipVertically("five", "HEHEHO");
    Image vert5 = imagesWith3Images.getImage("HEHEHO");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "10 30 50\n" + "110 90 70\n" + "130 170 150\n" + "125 255 125\n" +
                    "65 65 65\n" +
                    "3 2 1\n" + "6 5 4\n" + "9 10 11\n" + "14 13 12\n" + "17 16 15\n" +
                    "5 10 15\n" + "20 30 40\n" + "40 50 60\n" + "70 80 90\n" + "100 110 120\n" +
                    "5 5 5\n" + "10 10 10\n" + "20 20 20\n" + "30 30 30\n" + "40 40 40\n" +
                    "0 0 255\n" + "255 0 0\n" + "0 255 0\n" + "100 100 100\n" + "0 0 0",
            vert5.convertToText());
  }


  // tests flipVertical and then flipHorizontal for an odd amount of rows
  @Test
  public void testBothFlipsOdd() {
    // tests the vertical flip
    imagesWith3Images.flipVertically("five", "OOOOO");
    Image vert5 = imagesWith3Images.getImage("OOOOO");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "10 30 50\n" + "110 90 70\n" + "130 170 150\n" + "125 255 125\n" +
                    "65 65 65\n" +
                    "3 2 1\n" + "6 5 4\n" + "9 10 11\n" + "14 13 12\n" + "17 16 15\n" +
                    "5 10 15\n" + "20 30 40\n" + "40 50 60\n" + "70 80 90\n" + "100 110 120\n" +
                    "5 5 5\n" + "10 10 10\n" + "20 20 20\n" + "30 30 30\n" + "40 40 40\n" +
                    "0 0 255\n" + "255 0 0\n" + "0 255 0\n" + "100 100 100\n" + "0 0 0",
            vert5.convertToText());

    // tests the horizontal flip of the vertical flip
    imagesWith3Images.flipHorizontally("OOOOO", "flipyflip");
    Image doubleFlip = imagesWith3Images.getImage("flipyflip");
    assertEquals("P3\n" +
                    "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "65 65 65\n" + "125 255 125\n" + "130 170 150\n" + "110 90 70\n" +
                    "10 30 50\n" +
                    "17 16 15\n" + "14 13 12\n" + "9 10 11\n" + "6 5 4\n" + "3 2 1\n" +
                    "100 110 120\n" + "70 80 90\n" + "40 50 60\n" + "20 30 40\n" + "5 10 15\n" +
                    "40 40 40\n" + "30 30 30\n" + "20 20 20\n" + "10 10 10\n" + "5 5 5\n" +
                    "0 0 0\n" + "100 100 100\n" + "0 255 0\n" + "255 0 0\n" + "0 0 255",
            doubleFlip.convertToText());

    // tests the horizontal flip of 5
    imagesWith3Images.flipHorizontally("five", "horizon");
    Image horizon5 = imagesWith3Images.getImage("horizon");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 0\n" + "100 100 100\n" + "0 255 0\n" + "255 0 0\n" + "0 0 255\n" +
                    "40 40 40\n" + "30 30 30\n" + "20 20 20\n" + "10 10 10\n" + "5 5 5\n" +
                    "100 110 120\n" + "70 80 90\n" + "40 50 60\n" + "20 30 40\n" + "5 10 15\n" +
                    "17 16 15\n" + "14 13 12\n" + "9 10 11\n" + "6 5 4\n" + "3 2 1\n" +
                    "65 65 65\n" + "125 255 125\n" + "130 170 150\n" + "110 90 70\n" + "10 30 50",
            horizon5.convertToText());

    // tests the vertical flip of the horizontal flip
    imagesWith3Images.flipVertically("horizon", "soFlip");
    Image flippedX2 = imagesWith3Images.getImage("soFlip");
    assertEquals("P3\n" +
                    "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "65 65 65\n" + "125 255 125\n" + "130 170 150\n" + "110 90 70\n" +
                    "10 30 50\n" +
                    "17 16 15\n" + "14 13 12\n" + "9 10 11\n" + "6 5 4\n" + "3 2 1\n" +
                    "100 110 120\n" + "70 80 90\n" + "40 50 60\n" + "20 30 40\n" + "5 10 15\n" +
                    "40 40 40\n" + "30 30 30\n" + "20 20 20\n" + "10 10 10\n" + "5 5 5\n" +
                    "0 0 0\n" + "100 100 100\n" + "0 255 0\n" + "255 0 0\n" + "0 0 255",
            flippedX2.convertToText());

    // tests that the order of the flips does not matter and produces the same result
    assertEquals(flippedX2.convertToText(), doubleFlip.convertToText());


  }

  // checks that changeBrightness throws an exception when the name given for the image to
  // brighten is not in the hashmap
  @Test(expected = IllegalArgumentException.class)
  public void testNotInHashChangeBrightness() {
    imagesWith3Images.changeBrightness(10, "sexy", "rwr");
  }

  // checks that changeBrightness throws an exception when the destination name given for the
  // brightened image is an empty string
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestBrightness() {
    imagesWith3Images.changeBrightness(10, "four", "");
  }

  // checks that changeBrightness throws an exception when the increment to change the brightness
  // by is 0
  @Test(expected = IllegalArgumentException.class)
  public void test0IncrementChangeBrightness() {
    imagesWith3Images.changeBrightness(0, "five", "kate");
  }

  // tests when changeBrightness has a positive amount to change by. this tests to ensure that the
  // numbers are capped when the amount added, gets them over 255
  @Test
  public void testBrightenImage() {
    imagesWith2Images.changeBrightness(100, "three", "hAPPY");
    Image bright3 = imagesWith2Images.getImage("hAPPY");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "200 250 255\n" + "100 150 200\n" + "255 255 255\n" +
            "250 250 250\n" + "200 130 160\n" + "140 178 192\n" +
            "255 203 255\n" + "106 170 239\n" + "179 142 200", bright3.convertToText());

    imagesWith3Images.changeBrightness(10, "four", "goCeltics");
    Image bright4 = imagesWith3Images.getImage("goCeltics");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "255\n" +
            "90 50 50\n" + "14 12 10\n" + "79 79 79\n" + "70 70 70\n" +
            "59 69 79\n" + "66 78 20\n" + "49 160 21\n" + "106 253 216\n" +
            "10 121 232\n" + "32 43 76\n" + "131 87 109\n" + "94 106 118\n" +
            "78 80 73\n" + "173 43 245\n" + "102 69 75\n" + "122 205 255", bright4.convertToText());
  }

  // tests when changeBrightness has a negative amount to change by. this tests to ensure that the
  // numbers are stopped at 0 when the amount added (so the num decreases)
  @Test
  public void testDarken() {
    imagesWith3Images.changeBrightness(-200, "four", "lucky");
    Image dark4 = imagesWith3Images.getImage("lucky");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "53\n" +
            "0 0 0\n" + "0 0 0\n" + "0 0 0\n" + "0 0 0\n" +
            "0 0 0\n" + "0 0 0\n" + "0 0 0\n" + "0 43 6\n" +
            "0 0 22\n" + "0 0 0\n" + "0 0 0\n" + "0 0 0\n" +
            "0 0 0\n" + "0 0 35\n" + "0 0 0\n" + "0 0 53", dark4.convertToText());

    imagesWith3Images.changeBrightness(-20, "three", "sleepy");
    Image dark3 = imagesWith3Images.getImage("sleepy");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "235\n" +
            "80 130 180\n" + "0 30 80\n" + "235 235 235\n" +
            "130 130 130\n" + "80 10 40\n" + "20 58 72\n" +
            "234 83 182\n" + "0 50 119\n" + "59 22 80", dark3.convertToText());
  }

  // tests that when brighten and darken the same image by the same amount, you won't get the
  // exact same picture as before since the RGB components get capped at 255
  @Test
  public void testBrightenAndDarken() {
    imagesWith2Images.changeBrightness(100, "three", "BRIGHT");
    Image bright3 = imagesWith2Images.getImage("BRIGHT");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "200 250 255\n" + "100 150 200\n" + "255 255 255\n" +
            "250 250 250\n" + "200 130 160\n" + "140 178 192\n" +
            "255 203 255\n" + "106 170 239\n" + "179 142 200", bright3.convertToText());

    imagesWith2Images.changeBrightness(-100, "BRIGHT", "back?");
    Image normalMaybe = imagesWith2Images.getImage("back?");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "155\n" +
            "100 150 155\n" + "0 50 100\n" + "155 155 155\n" +
            "150 150 150\n" + "100 30 60\n" + "40 78 92\n" +
            "155 103 155\n" + "6 70 139\n" + "79 42 100", normalMaybe.convertToText());

    assertNotEquals(imagesWith2Images.getImage("three").convertToText(),
            normalMaybe.convertToText());
  }

  // tests that getImage throws an exception when the image library does not contain an image
  // with the name given
  @Test(expected = IllegalArgumentException.class)
  public void testExceptionGetImage() {
    imagesWith2Images.getImage("booooo");
  }

  // test that getImage works properly
  @Test
  public void testGetImage() {
    Image three = imagesWith3Images.getImage("three");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "100 150 200\n" + "0 50 100\n" + "255 255 255\n" +
            "150 150 150\n" + "100 30 60\n" + "40 78 92\n" +
            "254 103 202\n" + "6 70 139\n" + "79 42 100", three.convertToText());

    Image four = imagesWith3Images.getImage("four");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "253\n" +
            "80 40 40\n" + "4 2 0\n" + "69 69 69\n" + "60 60 60\n" +
            "49 59 69\n" + "56 68 10\n" + "39 150 11\n" + "96 243 206\n" +
            "0 111 222\n" + "22 33 66\n" + "121 77 99\n" + "84 96 108\n" +
            "68 70 63\n" + "163 33 235\n" + "92 59 65\n" + "112 195 253", four.convertToText());

    Image five = imagesWith3Images.getImage("five");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "255 0 0\n" + "0 255 0\n" + "100 100 100\n" + "0 0 0\n" +
                    "5 5 5\n" + "10 10 10\n" + "20 20 20\n" + "30 30 30\n" + "40 40 40\n" +
                    "5 10 15\n" + "20 30 40\n" + "40 50 60\n" + "70 80 90\n" + "100 110 120\n" +
                    "3 2 1\n" + "6 5 4\n" + "9 10 11\n" + "14 13 12\n" + "17 16 15\n" +
                    "10 30 50\n" + "110 90 70\n" + "130 170 150\n" + "125 255 125\n" + "65 65 65",
            five.convertToText());
  }

  // tests the combination of many operations on the same image
  @Test
  public void testComboOfOperations() {
    // tests visualizing by blue, then by intensity, then flips vertically and then changes
    // brightness
    imagesWith3Images.visualizeComponent("three", IModel.Component.Blue, "blue");
    imagesWith3Images.visualizeComponent("blue", IModel.Component.Intensity, "intenseBlue");
    imagesWith3Images.flipVertically("intenseBlue", "soFlipped");
    imagesWith3Images.changeBrightness(50, "soFlipped", "allDone");
    Image realChanged = imagesWith3Images.getImage("allDone");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "252 252 252\n" + "189 189 189\n" + "150 150 150\n" +
            "200 200 200\n" + "110 110 110\n" + "142 142 142\n" +
            "250 250 250\n" + "150 150 150\n" + "255 255 255", realChanged.convertToText());

    // tests flipping horizontally, brightening by 100, flipping vertically, visualizing by value,
    // and then darkening by 100
    imagesWith3Images.flipHorizontally("three", "HFLIP");
    imagesWith3Images.changeBrightness(100, "HFLIP", "bright!");
    imagesWith3Images.flipVertically("bright!", "V");
    imagesWith3Images.visualizeComponent("V", IModel.Component.Value, "val");
    imagesWith3Images.changeBrightness(-100, "val", "lastOne");
    Image changeChangeChange = imagesWith3Images.getImage("lastOne");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "155\n" +
            "100 100 100\n" + "139 139 139\n" + "155 155 155\n" +
            "92 92 92\n" + "100 100 100\n" + "150 150 150\n" +
            "155 155 155\n" + "100 100 100\n" + "155 155 155", changeChangeChange.convertToText());

  }


}