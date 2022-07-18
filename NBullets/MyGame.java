import java.awt.Color;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldcanvas.WorldCanvas;
import javalib.worldimages.CircleImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import javalib.worldimages.WorldImage;
import tester.Tester;
import tester.*;


//interface of constants
interface IConstants {
  int WIDTH = 500;
  int HEIGHT = 300;
  int TICK = 28;
  Color BULLETCOLOR = Color.pink;
  Color SHIPCOLOR = Color.cyan;
  Color TEXTCOLOR = Color.black;
  int BULLETSPEED = 8;
  int SHIPRADIUS = (HEIGHT / 30);
  int SHIPSPEED = (BULLETSPEED / 2);
}

//represents the game
class MyGame extends World {
  int bullets; // number of bullets available
  int currentTick; // current tick
  ILoGamePiece listOfPiece; // list of game pieces
  int score; // current score

  // constructor
  MyGame(int bullets, ILoGamePiece listOfPiece, int currentTick, int score) {
    if (bullets < 0) {
      throw new IllegalArgumentException("Not a valid number of bullets");
    }
    else {
      this.bullets = bullets;
      this.listOfPiece = listOfPiece;
      this.currentTick = currentTick;
      this.score = score;
    }
  }

  // client constructor that only requires the number of bullets to be inputed
  MyGame(int bullets) {
    this(bullets, new MtLoGamePiece(), 0, 0);
  }

  // draws the scene of the game
  @Override
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(IConstants.WIDTH, IConstants.HEIGHT);
    WorldImage score = new TextImage("Score: " + Integer.toString(this.score), Color.black);
    WorldScene sceneWScore = scene.placeImageXY(score, 30, 10);
    WorldImage bulletsLeft = new TextImage("Bullets left: " + Integer.toString(this.bullets),
        Color.black);
    WorldScene sceneWBoth = sceneWScore.placeImageXY(bulletsLeft, 44, 30);
    return this.listOfPiece.drawList(sceneWBoth);
  }

  // executes commands on every tick
  @Override
  public World onTick() {
    return new MyGame(this.bullets,
        this.listOfPiece.addShips(currentTick).moveList().explode().remove(), this.currentTick + 1,
        this.listOfPiece.addShips(currentTick).moveList().addScore(this.score));
  }

  // executes commands every time a certain key is pressed
  @Override
  public World onKeyEvent(String key) {
    if (key.equals(" ") && this.bullets > 0) {
      return new MyGame(this.bullets - 1, this.listOfPiece.addBullet(), this.currentTick,
          this.score);
    }
    else {
      return this;
    }
  }

  // specifies when the game ends --> when we run out of bullets
  @Override
  public WorldEnd worldEnds() {
    if (this.listOfPiece.seperateBullets(new MtLoGamePiece()).endHelper() && this.bullets <= 0) {
      return new WorldEnd(true, this.makeEndScene());
    }
    else {
      return new WorldEnd(false, this.makeEndScene());
    }
  }

  // draws the ending scene
  public WorldScene makeEndScene() {
    WorldScene scene = new WorldScene(IConstants.WIDTH, IConstants.HEIGHT);
    WorldImage endMessage = new TextImage("Game Over!", Color.blue);
    WorldScene sceneWEnd = scene.placeImageXY(endMessage, IConstants.WIDTH / 2,
        IConstants.HEIGHT / 2);
    WorldImage score = new TextImage("Score: " + Integer.toString(this.score), Color.black);
    WorldScene sceneWScore = sceneWEnd.placeImageXY(score, (IConstants.WIDTH / 2),
        (IConstants.HEIGHT / 2) + 30);

    return sceneWScore;
  }
}

class ExamplesMyGame {

  AGamePiece ship1 = new Ship(new Posn(200, 20), 0);
  AGamePiece ship2 = new Ship(new Posn(210, 30), 0);
  AGamePiece bullet1 = new Bullet(2, new Posn(10, 10), (3 * Math.PI) / 2);
  AGamePiece bullet2 = new Bullet(2, new Posn(200, 20), (3 * Math.PI) / 2);
  AGamePiece bullet3 = new Bullet(4, new Posn(10, 10), (3 * Math.PI) / 2);
  AGamePiece bulletOff = new Bullet(3, new Posn(400000, 40000), (3 * Math.PI) / 2);
  ILoGamePiece list1 = new ConsLoGamePiece(this.ship1,
      new ConsLoGamePiece(this.ship2, new ConsLoGamePiece(this.bullet1, new MtLoGamePiece())));
  ILoGamePiece list2 = new ConsLoGamePiece(this.ship1, new ConsLoGamePiece(this.ship2,
      new ConsLoGamePiece(this.bullet1, new ConsLoGamePiece(this.bullet2, new MtLoGamePiece()))));
  ILoGamePiece list4 = new ConsLoGamePiece(this.bulletOff, this.list2);

  boolean testDraw(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(list1.addBullet().drawList(s));
  }

  // tests the checkShipOverlap method
  // will only be called when the argument is a bullet
  boolean testCheckShipOverlap(Tester t) {
    return t.checkExpect(this.ship1.checkShipOverlap(this.bullet2), true)
        && t.checkExpect(this.bullet1.checkShipOverlap(this.bullet3), false)
        && t.checkExpect(this.ship2.checkShipOverlap(this.bullet2), false);
  }

  // tests seperateHelper1
  boolean seperateHelper1(Tester t) {
    return t.checkExpect(this.ship1.seperateHelper1(this.list1), this.list1)
        && t.checkExpect(this.bullet1.seperateHelper1(this.list1),
            new ConsLoGamePiece(this.bullet1, this.list1))
        && t.checkExpect(this.ship1.seperateHelper1(new MtLoGamePiece()), new MtLoGamePiece())
        && t.checkExpect(this.bullet1.seperateHelper1(new MtLoGamePiece()),
            new ConsLoGamePiece(this.bullet1, new MtLoGamePiece()));
  }

  // tests seperateHelper2
  boolean seperateHelper2(Tester t) {
    return t.checkExpect(this.bullet1.seperateHelper2(this.list1), this.list1)
        && t.checkExpect(this.ship1.seperateHelper2(this.list1),
            new ConsLoGamePiece(this.ship1, this.list1))
        && t.checkExpect(this.bullet1.seperateHelper1(new MtLoGamePiece()), new MtLoGamePiece())
        && t.checkExpect(this.ship1.seperateHelper1(new MtLoGamePiece()),
            new ConsLoGamePiece(this.ship1, new MtLoGamePiece()));
  }

  // tests the explodeBullets method
  boolean testExpBullet(Tester t) {
    return t.checkExpect(this.ship1.explodeBullets(0, 0, 0), new MtLoGamePiece())
        && t.checkExpect(this.bullet2.explodeBullets(200, 20, 3),
            this.bullet1.explodeBulletsHelper(200, 20, 3, 3, new MtLoGamePiece()));
  }

  // tests the explode bullets helper
  boolean testExpBullHelper(Tester t) {
    return t.checkExpect(this.bullet2.explodeBulletsHelper(200, 20, 3, 4, new MtLoGamePiece()),
        new ConsLoGamePiece(new Bullet(3, new Posn(200, 20), 1 * ((2 * Math.PI) / 3)),
            new ConsLoGamePiece(new Bullet(3, new Posn(200, 20), 2 * ((2 * Math.PI) / 3)),
                new ConsLoGamePiece(new Bullet(3, new Posn(200, 20), 3 * ((2 * Math.PI) / 3)),
                    new ConsLoGamePiece(new Bullet(3, new Posn(200, 20), 4 * ((2 * Math.PI) / 3)),
                        new MtLoGamePiece())))))
        && t.checkExpect(this.ship1.explodeBulletsHelper(200, 20, 3, 4, new MtLoGamePiece()),
            new MtLoGamePiece());
  }

  // tests the explode helper
  boolean testExpHelper(Tester t) {
    return t.checkExpect(this.bullet1.explodeHelper(), this.bullet1.explodeBullets(10, 10, 3))
        && t.checkExpect(this.ship1.explodeHelper(), new MtLoGamePiece());
  }

  // tests the sumRadii method
  boolean testRadii(Tester t) {
    return t.checkExpect(this.bullet1.sumRadii(), 14) && t.checkExpect(this.ship1.sumRadii(), 0);
  }

  // tests the hyp method
  boolean testHyp(Tester t) {
    return t.checkExpect(this.ship1.Hyp(20, 40), 0.0)
        && t.checkExpect(this.bullet1.Hyp(20, 20), 10 * Math.sqrt(2));
  }

  // tests the add bullet method
  boolean testAddBullet(Tester t) {
    return t.checkExpect(new MtLoGamePiece().addBullet(),
        new ConsLoGamePiece(new Bullet(1, (new Posn(250, 300)), (3 * Math.PI) / 2),
            new MtLoGamePiece()))
        && t.checkExpect(this.list1.addBullet(), new ConsLoGamePiece(
            new Bullet(1, (new Posn(250, 300)), (3 * Math.PI) / 2), this.list1));
  }

  // tests the remove method
  boolean testRemove(Tester t) {
    return t.checkExpect(new MtLoGamePiece().remove(), new MtLoGamePiece())
        && t.checkExpect(this.list1.remove(), this.list1)
        && t.checkExpect(this.list4.remove(), this.list2);
  }

  // test explode method
  boolean testExplode(Tester t) {
    return t
        .checkExpect(this.list2.explode(), new ConsLoGamePiece(this.ship2, new ConsLoGamePiece(
            this.bullet1,
            new ConsLoGamePiece(new Bullet(3, new Posn(200, 20), 1 * ((2 * Math.PI) / 3)),
                new ConsLoGamePiece(new Bullet(3, new Posn(200, 20), 2 * ((2 * Math.PI) / 3)),
                    new ConsLoGamePiece(new Bullet(3, new Posn(200, 20), 3 * ((2 * Math.PI) / 3)),
                        new MtLoGamePiece()))))))
        && t.checkExpect(new MtLoGamePiece().explode(), new MtLoGamePiece());
  }

  // tests seperateShips
  boolean testSeperateShips(Tester t) {
    return t.checkExpect(list2.seperateShips(new MtLoGamePiece()),
        new ConsLoGamePiece(this.ship2, new ConsLoGamePiece(this.ship1, new MtLoGamePiece())))
        && t.checkExpect(new MtLoGamePiece().seperateShips(new MtLoGamePiece()),
            new MtLoGamePiece());
  }

  // tests seperateBullets
  boolean testSeperateBullets(Tester t) {
    return t.checkExpect(list2.seperateBullets(new MtLoGamePiece()),
        new ConsLoGamePiece(this.bullet2, new ConsLoGamePiece(this.bullet1, new MtLoGamePiece())))
        && t.checkExpect(new MtLoGamePiece().seperateBullets(new MtLoGamePiece()),
            new MtLoGamePiece());
  }

  // tests the checkOverlap method
  boolean testCheckOverlap(Tester t) {
    return t.checkExpect(
        this.list2.seperateBullets(new MtLoGamePiece())
            .checkOverlap(this.list2.seperateShips(new MtLoGamePiece()), new MtLoGamePiece()),
        new ConsLoGamePiece(this.bullet2, new MtLoGamePiece()))
        && t.checkExpect(new MtLoGamePiece().checkOverlap(new MtLoGamePiece(), new MtLoGamePiece()),
            new MtLoGamePiece())
        && t.checkExpect(new MtLoGamePiece()
            .checkOverlap(this.list2.seperateShips(new MtLoGamePiece()), new MtLoGamePiece()),
            new MtLoGamePiece())
        && t.checkExpect(this.list2.seperateBullets(new MtLoGamePiece())
            .checkOverlap(new MtLoGamePiece(), new MtLoGamePiece()), new MtLoGamePiece());
  }

  // tests the checkOverlap helper
  boolean checkOverlapHelper(Tester t) {
    return t.checkExpect(
        this.list2.seperateShips(new MtLoGamePiece()).checkOverlapHelper(this.bullet2), true)
        && t.checkExpect(
            this.list1.seperateShips(new MtLoGamePiece()).checkOverlapHelper(this.bullet2), false)
        && t.checkExpect(new MtLoGamePiece().checkOverlapHelper(this.bullet2), false);
  }

  // TEST EXPLODE EACH

  // tests the append method
  boolean testAppend(Tester t) {
    return t.checkExpect(
        new ConsLoGamePiece(this.ship1,
            new MtLoGamePiece()
                .appendTogether(new ConsLoGamePiece(this.bullet1, new MtLoGamePiece()))),
        new ConsLoGamePiece(this.ship1, new ConsLoGamePiece(this.bullet1, new MtLoGamePiece())))
        && t.checkExpect(
            this.list2.seperateBullets(new MtLoGamePiece())
                .appendTogether(this.list2.seperateShips(new MtLoGamePiece())),
            new ConsLoGamePiece(this.ship2,
                new ConsLoGamePiece(this.ship1, new ConsLoGamePiece(this.bullet2,
                    new ConsLoGamePiece(this.bullet1, new MtLoGamePiece())))));
  }

  // tests the append helper
  boolean testAppendHelper(Tester t) {
    return t
        .checkExpect(
            new ConsLoGamePiece(this.bullet1,
                new MtLoGamePiece()
                    .appendHelper(new ConsLoGamePiece(this.ship1, new MtLoGamePiece()))),
            new ConsLoGamePiece(this.bullet1, new ConsLoGamePiece(this.ship1, new MtLoGamePiece())))
        && t.checkExpect(
            new MtLoGamePiece()
                .appendHelper(new ConsLoGamePiece(this.bullet1, new MtLoGamePiece())),
            new ConsLoGamePiece(this.bullet1, new MtLoGamePiece()));
  }

  // tests remove overlapB
  boolean testRemoveOverlapB(Tester t) {
    return t.checkExpect(
        this.list2.seperateBullets(new MtLoGamePiece())
            .removeOverlapB(this.list2.seperateShips(new MtLoGamePiece())),
        new ConsLoGamePiece(this.bullet1, new MtLoGamePiece()))
        && t.checkExpect(
            this.list1.seperateBullets(new MtLoGamePiece())
                .removeOverlapB(this.list1.seperateShips(new MtLoGamePiece())),
            new ConsLoGamePiece(bullet1, new MtLoGamePiece()));
  }

  // tests the removeOverlapS
  boolean testRemoveOverlapS(Tester t) {
    return t.checkExpect(
        this.list2.seperateBullets(new MtLoGamePiece())
            .removeOverlapS(this.list2.seperateShips(new MtLoGamePiece())),
        new ConsLoGamePiece(this.ship2, new MtLoGamePiece()))
        && t.checkExpect(
            this.list1.seperateBullets(new MtLoGamePiece())
                .removeOverlapS(this.list1.seperateShips(new MtLoGamePiece())),
            this.list1.seperateShips(new MtLoGamePiece()));
  }

  // tests the remove helper
  boolean testRemoveHelper(Tester t) {
    return t.checkExpect(new MtLoGamePiece().removeHelper(this.bullet1), new MtLoGamePiece())
        && t.checkExpect(new MtLoGamePiece().removeHelper(this.ship1), new MtLoGamePiece())
        && t.checkExpect(this.list2.seperateShips(new MtLoGamePiece()).removeHelper(this.bulletOff),
            this.list2.seperateShips(new MtLoGamePiece()))
        && t.checkExpect(this.list2.seperateShips(new MtLoGamePiece()).removeHelper(this.bullet2),
            new ConsLoGamePiece(this.ship2, new MtLoGamePiece()));
  }

  // tests the end helper
  boolean testEnd(Tester t) {
    return t.checkExpect(this.list2.endHelper(), false)
        && t.checkExpect(new MtLoGamePiece().endHelper(), true);
  }

  boolean testAddScore(Tester t) {
    return t.checkExpect(list2.addScore(0), 1) && t.checkExpect(list1.addScore(0), 0)
        && t.checkExpect(list2.addScore(4), 5) && t.checkExpect(list1.addScore(4), 4);
  }

  // tests the count method
  boolean testCount(Tester t) {
    return t.checkExpect(list2.count(), 4) && t.checkExpect(list1.count(), 3)
        && t.checkExpect(new MtLoGamePiece().count(), 0);
  }

  // tests the constructor exceptions
  boolean testConstructorException(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Not a valid number of bullets"), "MyGame", -5)
        && t.checkConstructorException(
            new IllegalArgumentException("Not a valid number of bullets"), "MyGame", -9);

  }

  boolean testBigBang(Tester t) {
    MyGame game = new MyGame(10);
    return game.bigBang(500, 500, 0.035);
  }

}
