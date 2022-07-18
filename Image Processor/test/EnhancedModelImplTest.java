
import org.junit.Before;
import org.junit.Test;

import model.EnhancedModel;
import model.EnhancedModelImpl;
import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Represents the tests for the enhanced model.
 */
public class EnhancedModelImplTest {
  Pixel[][] fiveByFive;
  Pixel[][] threeByThree;
  Pixel[][] fourByFour;
  Pixel[][] one;
  EnhancedModel defaultImages;
  EnhancedModel imagesWith2Images;
  EnhancedModel imagesWith4Images;


  @Before
  public void init() {
    this.defaultImages = new EnhancedModelImpl();

    this.fiveByFive = new Pixel[][]{
            {new Pixel(0, 0, 255),
             new Pixel(255, 0, 0),
             new Pixel(0, 255, 0),
             new Pixel(100, 100, 100),
             new Pixel(0, 0, 0)},
            {new Pixel(5,5,5),
             new Pixel(10, 10, 10),
             new Pixel(20, 20, 20),
             new Pixel(30, 30, 30),
             new Pixel(40, 40, 40)},
            {new Pixel(5, 10, 15),
             new Pixel(20, 30, 40),
             new Pixel(40, 50, 60),
             new Pixel(70, 80, 90),
             new Pixel(100, 110, 120)},
            {new Pixel(3,2,1),
             new Pixel(6,5,4),
             new Pixel(9,10,11),
             new Pixel(14,13,12),
             new Pixel(17,16,15)},
            {new Pixel(10,30,50),
             new Pixel(110,90,70),
             new Pixel(130,170,150),
             new Pixel(125,255,125),
             new Pixel(65,65,65)}};

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
            {new Pixel(80,40,40),
             new Pixel(4,2,0),
             new Pixel(69,69,69),
             new Pixel(60,60,60)},
            {new Pixel(49,59,69),
             new Pixel(56,68,10),
             new Pixel(39,150,11),
             new Pixel(96,243,206)},
            {new Pixel(0,111,222),
             new Pixel(22,33,66),
             new Pixel(121,77,99),
             new Pixel(84,96,108)},
            {new Pixel(68,70,63),
             new Pixel(163,33,235),
             new Pixel(92,59,65),
             new Pixel(112,195,253)}};

    one = new Pixel[][]{{new Pixel(100,100,100)}};


    imagesWith2Images = new EnhancedModelImpl();
    imagesWith2Images.addImage("five", fiveByFive);
    imagesWith2Images.addImage("three", threeByThree);

    imagesWith4Images = new EnhancedModelImpl();
    imagesWith4Images.addImage("five", fiveByFive);
    imagesWith4Images.addImage("three", threeByThree);
    imagesWith4Images.addImage("four", fourByFour);
    imagesWith4Images.addImage("one", one);

  }

  // tests the working blur method
  @Test
  public void testBlur() {
    imagesWith2Images.blur("three", "blur");
    Image blur = imagesWith2Images.getImage("blur");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "122\n" +
            "50 64 85\n" + "68 81 104\n" + "75 81 91\n" +
            "94 80 110\n" + "92 85 122\n" + "64 67 89\n" +
            "89 55 90\n" + "67 53 95\n" + "31 30 57", blur.convertToText());

    imagesWith2Images.blur("five", "floof");
    Image blur5 = imagesWith2Images.getImage("floof");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "96\n" +
            "33 1 65\n" + "66 34 34\n" + "49 81 17\n" + "32 64 32\n" + "19 19 19\n" +
            "20 5 38\n" + "42 29 31\n" + "42 61 31\n" + "45 63 50\n" + "36 38 40\n"
            + "5 8 10\n" + "14 19 23\n" + "28 33 38\n" + "45 50 55\n" + "43 47 50\n"
            + "11 13 15\n" + "30 34 33\n" + "46 60 51\n" + "52 73 57\n" + "38 48 41\n"
            + "17 19 21\n" + "46 48 43\n" + "64 88 64\n" + "59 96 61\n" + "34 50 34",
            blur5.convertToText());

    imagesWith4Images.blur("one", "UNO");
    Image blur1 = imagesWith4Images.getImage("UNO");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "1 1\n" + "25\n" +
            "25 25 25", blur1.convertToText());
  }

  // tests blurring an image twice
  @Test
  public void testDoubleBlur() {
    imagesWith2Images.blur("three", "blur");
    Image blur1 = imagesWith2Images.getImage("blur");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "122\n" +
            "50 64 85\n" + "68 81 104\n" + "75 81 91\n" +
            "94 80 110\n" + "92 85 122\n" + "64 67 89\n" +
            "89 55 90\n" + "67 53 95\n" + "31 30 57", blur1.convertToText());

    imagesWith2Images.blur("blur", "extraBlur");
    Image blur2 = imagesWith2Images.getImage("extraBlur");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "100\n" +
            "38 41 55\n" + "54 58 75\n" + "41 44 54\n" +
            "60 53 77\n" + "74 70 100\n" + "49 49 68\n" +
            "48 35 55\n" + "53 43 69\n" + "29 27 44", blur2.convertToText());
  }

  // checks that blur throws an exception when the name given for the image to
  // blur is not in the hashmap
  @Test(expected = IllegalArgumentException.class)
  public void testNotInHashmapBlur() {
    imagesWith2Images.blur("sunny", "yuh");
  }

  // checks that blur throws an exception when the destination name given for the
  // blurred image is an empty string
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestinationBlur() {
    imagesWith2Images.blur("three", "");
  }

  // tests the working sharpen method
  @Test
  public void testWorkingSharpen() {
    imagesWith2Images.sharpen("three", "SHARP");
    Image sharp3 = imagesWith2Images.getImage("SHARP");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "83 139 179\n" + "118 188 234\n" + "216 230 219\n" +
            "218 203 255\n" + "255 254 255\n" + "87 139 186\n" +
            "255 93 195\n" + "117 113 220\n" + "20 0 59", sharp3.convertToText());

    imagesWith2Images.sharpen("five", "sharp finger");
    Image sharp5 = imagesWith2Images.getImage("sharp finger");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "56 0 241\n" + "230 35 30\n" + "68 254 0\n" + "60 151 82\n" + "13 0 6\n" +
                    "67 0 73\n" + "67 72 77\n" + "126 132 42\n" + "81 183 126\n" + "102 74 110\n" +
                    "0 0 0\n" + "0 0 0\n" + "0 0 0\n" + "34 29 96\n" + "81 39 100\n" +
                    "14 10 13\n" + "53 51 66\n" + "99 141 102\n" + "123 173 144\n" + "76 106 79\n" +
                    "14 20 34\n" + "115 89 81\n" + "154 214 148\n" + "140 255 140\n" + "60 83 49",
            sharp5.convertToText());

    imagesWith4Images.sharpen("one", "EEK");
    Image sharp1 = imagesWith4Images.getImage("EEK");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "1 1\n" + "100\n" +
            "100 100 100", sharp1.convertToText());
  }

  // checks that sharpen throws an exception when the name given for the image to
  // sharpen is not in the hashmap
  @Test(expected = IllegalArgumentException.class)
  public void testNotInHashmapSharpen() {
    imagesWith2Images.sharpen("sharpify", "eyes");
  }

  // checks that sharpen throws an exception when the destination name given for the
  // sharpened image is an empty string
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestinationSharpen() {
    imagesWith2Images.sharpen("five", "");
  }

  // tests sharpening an image twice
  @Test
  public void testDoubleSharpen() {
    imagesWith2Images.sharpen("three", "sharp");
    Image sharp1 = imagesWith2Images.getImage("sharp");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "83 139 179\n" + "118 188 234\n" + "216 230 219\n" +
            "218 203 255\n" + "255 254 255\n" + "87 139 186\n" +
            "255 93 195\n" + "117 113 220\n" + "20 0 59", sharp1.convertToText());

    imagesWith2Images.sharpen("sharp", "sharper");
    Image sharp2 = imagesWith2Images.getImage("sharper");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "143 228 255\n" + "255 255 255\n" + "244 255 255\n" +
            "255 255 255\n" + "255 255 255\n" + "199 255 255\n" +
            "255 148 255\n" + "255 215 255\n" + "23 19 89", sharp2.convertToText());
  }

  // test that the greyscale method works properly
  @Test
  public void testGreyscale() {
    imagesWith2Images.greyscale("three", "grey");
    Image grey3 = imagesWith2Images.getImage("grey");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "254\n" +
            "142 142 142\n" + "42 42 42\n" + "254 254 254\n" +
            "150 150 150\n" + "47 47 47\n" + "70 70 70\n" +
            "142 142 142\n" + "61 61 61\n" + "54 54 54", grey3.convertToText());

    imagesWith2Images.greyscale("five", "drab");
    Image grey5 = imagesWith2Images.getImage("drab");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "217\n" +
                    "18 18 18\n" + "54 54 54\n" + "182 182 182\n" + "100 100 100\n" + "0 0 0\n" +
                    "4 4 4\n" + "9 9 9\n" + "19 19 19\n" + "30 30 30\n" + "39 39 39\n" +
                    "9 9 9\n" + "28 28 28\n" + "48 48 48\n" + "78 78 78\n" + "108 108 108\n" +
                    "2 2 2\n" + "5 5 5\n" + "9 9 9\n" + "13 13 13\n" + "16 16 16\n" +
                    "27 27 27\n" + "92 92 92\n" + "160 160 160\n" + "217 217 217\n" + "65 65 65",
            grey5.convertToText());
  }

  // checks that greyscale throws an exception when the name given for the image to
  // greyscaled is not in the hashmap
  @Test(expected = IllegalArgumentException.class)
  public void testNotInHashmapGreyscale() {
    imagesWith2Images.greyscale("grreeeyyyyy", "eyes");
  }

  // checks that greyscale throws an exception when the destination name given for the
  // greyscaled image is an empty string
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestinationGreyscale() {
    imagesWith2Images.greyscale("five", "");
  }

  // tests that the sepia method properly creates a new image with the correct values for the pixels
  @Test
  public void testWorkingSepia() {
    imagesWith2Images.sepia("three", "SEEEEPpP");
    Image sepia3 = imagesWith2Images.getImage("SEEEEPpP");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "192 171 133\n" + "57 51 39\n" + "255 255 238\n" +
            "202 180 140\n" + "73 65 51\n" + "93 82 64\n" +
            "217 193 150\n" + "82 73 57\n" + "82 73 57", sepia3.convertToText());

    imagesWith2Images.sepia("five", "brownish");
    Image sepia5 = imagesWith2Images.getImage("brownish");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "48 42 33\n" + "100 88 69\n" + "196 174 136\n" + "135 120 93\n" + "0 0 0\n" +
                    "6 6 4\n" + "13 12 9\n" + "27 24 18\n" + "40 36 28\n" + "54 48 37\n" +
                    "12 11 8\n" + "38 34 26\n" + "65 58 45\n" + "106 94 73\n" + "146 130 101\n" +
                    "2 2 2\n" + "6 6 4\n" + "13 11 9\n" + "17 15 12\n" + "21 19 15\n" +
                    "36 32 25\n" + "125 111 87\n" + "210 187 145\n" + "255 239 186\n" + "87 78 60",
            sepia5.convertToText());
  }

  // checks that sepia throws an exception when the name given for the image to
  // sepiaify is not in the hashmap
  @Test(expected = IllegalArgumentException.class)
  public void testNotInHashmapSepia() {
    imagesWith2Images.sepia("sEpIaImAgE", "warm");
  }

  // checks that sepia throws an exception when the destination name given for the
  // sepiaify image is an empty string
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestinationSepia() {
    imagesWith2Images.sepia("five", "");
  }

  // tests blurring, then sepia, then sharpening and then greyscale all on the same image
  @Test
  public void testAllAtOnce() {
    imagesWith2Images.blur("three", "blur");
    Image blur = imagesWith2Images.getImage("blur");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "122\n" +
            "50 64 85\n" + "68 81 104\n" + "75 81 91\n" +
            "94 80 110\n" + "92 85 122\n" + "64 67 89\n" +
            "89 55 90\n" + "67 53 95\n" + "31 30 57", blur.convertToText());

    imagesWith2Images.sepia("blur", "sepia");
    Image sepia = imagesWith2Images.getImage("sepia");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "124\n" +
            "84 75 58\n" + "108 96 75\n" + "108 97 75\n" +
            "119 106 82\n" + "124 110 86\n" + "93 83 64\n" +
            "94 83 65\n" + "85 75 58\n" + "46 40 31", sepia.convertToText());

    imagesWith2Images.sharpen("sepia", "sharp");
    Image sharp = imagesWith2Images.getImage("sharp");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "255\n" +
            "118 105 82\n" + "211 189 147\n" + "135 121 94\n" +
            "211 188 146\n" + "255 255 213\n" + "173 154 119\n" +
            "121 106 83\n" + "166 147 114\n" + "57 49 38", sharp.convertToText());

    imagesWith2Images.greyscale("sharp", "grey");
    Image greyscale = imagesWith2Images.getImage("grey");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "3 3\n" + "251\n" +
            "106 106 106\n" + "190 190 190\n" + "122 122 122\n" +
            "189 189 189\n" + "251 251 251\n" + "155 155 155\n" +
            "107 107 107\n" + "148 148 148\n" + "49 49 49", greyscale.convertToText());
  }

  //tests downscaling an image with invalid height and width
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDownscale() {
    imagesWith2Images.downsize("three", "down", 1000, 1000);
  }

  //tests downscaling an image with invalid height and width
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDownscale2() {
    imagesWith2Images.downsize("three", "down", 0, 1000);
  }

  //tests downscaling an image with invalid height and width
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDownscale3() {
    imagesWith2Images.downsize("three", "down", 1000, -1000);
  }

  //tests downscaling with same ratio of width to height as the original
  @Test
  public void testDownscale() {
    //tests downscaling with same ratio of width to height as the original
    imagesWith2Images.downsize("three", "down", 2, 2);
    Image downsize = imagesWith2Images.getImage("down");
    assertEquals("P3\n" +
            "# created by kate and neha :)\n" +
            "2 2\n" +
            "202\n" +
            "100 150 200\n" +
            "127 152 177\n" +
            "202 126 176\n" +
            "56 55 97", downsize.convertToText());
    //tests downscaling with different ratio of width to height
    imagesWith2Images.downsize("three", "moreDown", 1, 2);
    Image unevenDownsize = imagesWith2Images.getImage("moreDown");
    assertEquals("P3\n" +
            "# created by kate and neha :)\n" +
            "2 1\n" +
            "200\n" +
            "100 150 200\n" +
            "0 50 100", unevenDownsize.convertToText());
  }
}
