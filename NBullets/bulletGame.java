import tester.*;
import javalib.worldimages.*;
import javalib.worldimages.Posn;
import javalib.funworld.*;
import javalib.worldcanvas.WorldSceneBase;

import java.awt.Color;
import java.awt.color.*;
import java.util.Random;

interface IGamePiece {
  // draws the given game piece
  WorldScene draw(WorldScene scene);

  // checks if a ship is overlapping with a bullet
  boolean checkShipOverlap(AGamePiece current);

  // moves the given game piece
  AGamePiece move();

  // helps accumulate the bullets
  ILoGamePiece seperateHelper1(ILoGamePiece acc);

  // helps accumulate the ships
  ILoGamePiece seperateHelper2(ILoGamePiece acc);

  // explodes a given bullet into a given set of bullets
  ILoGamePiece explodeBullets(int x, int y, int num);

  // helper to explode the bullets
  ILoGamePiece explodeBulletsHelper(int x, int y, int num, int exp, ILoGamePiece acc);

  // increments the number of explosions by 1
  ILoGamePiece explodeHelper();

  // returns the sum of the radii of two game pieces
  int sumRadii();

  // returns the distance between the centers of two game pieces
  double Hyp(int x, int y);

}

//abstract class
abstract class AGamePiece implements IGamePiece {
  // position of the game piece
  Posn coord;

  // constructor
  AGamePiece(Posn coord) {
    this.coord = coord;
  }
}

class Ship extends AGamePiece {
  // represents which side the ship comes from (0 for left and 1 for right)
  int direction;

  Ship(Posn coord, int direction) {
    super(coord);
    this.direction = direction;
  }

  // draws a ship
  public WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(
        new CircleImage(IConstants.SHIPRADIUS, OutlineMode.SOLID, IConstants.SHIPCOLOR),
        this.coord.x, this.coord.y);
  }

  // checks if a ship is overlapping with a bullet
  public boolean checkShipOverlap(AGamePiece current) {
    double distBtwnRadii = current.Hyp(this.coord.x, this.coord.y);
    double SumRadii = current.sumRadii();
    return distBtwnRadii < SumRadii;
  }

  // moves a ship
  public AGamePiece move() {
    if (this.direction == 1) {
      return new Ship(new Posn(this.coord.x - IConstants.SHIPSPEED, this.coord.y), this.direction);
    }
    else {
      return new Ship(new Posn(this.coord.x + IConstants.SHIPSPEED, this.coord.y), this.direction);
    }
  }

  // returns the accumulator since the acc only consists of bullets
  public ILoGamePiece seperateHelper1(ILoGamePiece acc) {
    return acc;
  }

  // adds the ship to the accumulated list of ships
  public ILoGamePiece seperateHelper2(ILoGamePiece acc) {
    return new ConsLoGamePiece(this, acc);
  }

  // returns an empty since you can't explode a ship
  public ILoGamePiece explodeBullets(int x, int y, int num) {
    return new MtLoGamePiece();
  }

  // returns an empty since you can't explode a ship
  public ILoGamePiece explodeBulletsHelper(int x, int y, int num, int exp, ILoGamePiece acc) {
    return new MtLoGamePiece();
  }

  // returns an empty since you can't explode a ship
  public ILoGamePiece explodeHelper() {
    return new MtLoGamePiece();
  }

  // returns 0 since we do not care about the distance between two ships
  public int sumRadii() {
    return 0;
  }

  // returns 0 since we do not care about the distance between 2 ships
  public double Hyp(int x, int y) {
    return 0;
  }
}

//represents a bullet
class Bullet extends AGamePiece {
  // number of explosions
  int explosion;
  // angle at which it is being fired
  double theta;

  // constructor
  Bullet(int explosion, Posn coord, double theta) {
    super(coord);
    this.explosion = explosion;
    this.theta = theta;
  }

  // draws a bullet
  public WorldScene draw(WorldScene scene) {
    if (this.explosion <= 4) {
      return scene.placeImageXY(
          new CircleImage(this.explosion * 2, OutlineMode.SOLID, IConstants.BULLETCOLOR),
          this.coord.x, this.coord.y);
    }
    else {
      return scene.placeImageXY(new CircleImage(8, OutlineMode.SOLID, IConstants.BULLETCOLOR),
          this.coord.x, this.coord.y);
    }
  }

  // checks if a bullet is overlapping with a bullet which does not matter
  public boolean checkShipOverlap(AGamePiece current) {
    return false;
  }

  // moves a bullet
  public AGamePiece move() {
    return new Bullet(this.explosion,
        new Posn((int) (this.coord.x + (IConstants.BULLETSPEED * Math.cos(this.theta))),
            (int) (this.coord.y + (IConstants.BULLETSPEED * Math.sin(this.theta)))),
        this.theta);
  }

  // adds the bullet to the accumulated list of bullets
  public ILoGamePiece seperateHelper1(ILoGamePiece acc) {
    return new ConsLoGamePiece(this, acc);
  }

  // returns the accumulated list of ships without adding anything
  public ILoGamePiece seperateHelper2(ILoGamePiece acc) {
    return acc;
  }

  // explodes a given bullet into a number of other bullets
  public ILoGamePiece explodeBullets(int x, int y, int num) {
    return this.explodeBulletsHelper(x, y, num, num, new MtLoGamePiece());
  }

  // returns a list of exploded bullets (helper for explodeBullets)
  public ILoGamePiece explodeBulletsHelper(int x, int y, int num, int exp, ILoGamePiece acc) {
    if (exp == 0) {
      return acc;
    }
    else {
      return this.explodeBulletsHelper(x, y, num, exp - 1, new ConsLoGamePiece(
          new Bullet(num, new Posn(x, y), exp * Math.toRadians(360 / num)), acc));
    }
  }

  // increments the explosion count on the object
  public ILoGamePiece explodeHelper() {
    return this.explodeBullets(this.coord.x, this.coord.y, this.explosion + 1);
  }

  // computes the sum of the radii between the ship and the bullet
  public int sumRadii() {
    return 10 + (this.explosion * 2);
  }

  // computes the distance between a ship and a bullet
  public double Hyp(int x, int y) {
    // the distance between the two x coordinates
    double diffX = Math.abs(this.coord.x - x);
    // the difference between the 2 y coordinates
    double diffY = Math.abs(this.coord.y - y);
    // overall distance between the two points
    return Math.hypot(diffX, diffY);
  }
}

//represents a list of game pieces
interface ILoGamePiece {
  WorldScene drawList(WorldScene scene);

  // removes the game pieces that are not on the scene
  // moves a list of game pieces
  ILoGamePiece moveList();

  // adds random ships to screen
  ILoGamePiece addShips(int tick);

  // helper to add ships to screen
  ILoGamePiece addShipHelper(int acc);

  // chooses which side of the screen the ships come from
  int chooseDirection(int rand);

  // fires a bullet
  ILoGamePiece addBullet();

  // draws a list of game pieces
  ILoGamePiece remove();

  // explodes a list of game pieces
  ILoGamePiece explode();

  // seperates a list of ships from a list of game pieces
  ILoGamePiece seperateShips(ILoGamePiece acc);

  // seperates a list of bullets from a list of game pieces
  ILoGamePiece seperateBullets(ILoGamePiece acc);

  // returns the list of exploded bullets for all overlapping bullets in a list of
  // game pieces
  ILoGamePiece checkOverlap(ILoGamePiece ships, ILoGamePiece collided);

  // helper that determines if overlap exists between a list of pieces and a
  // bullet
  boolean checkOverlapHelper(AGamePiece bullet);

  // explodes each bullet in a list of bullets
  ILoGamePiece explodeEach();

  // appends two lists together
  ILoGamePiece appendTogether(ILoGamePiece other);

  // helper for append
  ILoGamePiece appendHelper(ConsLoGamePiece first);

  // removes the overlapping bullets from the list of bullets
  ILoGamePiece removeOverlapB(ILoGamePiece ships);

  // removes the overlapping ships from the list of ships
  ILoGamePiece removeOverlapS(ILoGamePiece ships);

  // helper for removeOverlapS that reomves the first ship from a list of ships
  ILoGamePiece removeHelper(AGamePiece bullet);

  // returns false if called on a list Game Pieces and true if called on an empty
  // list
  boolean endHelper();

  // computes the user's score
  int addScore(int acc);

  // counts the number of elements in the list
  int count();

}

//represents a list of no game pieces
class MtLoGamePiece implements ILoGamePiece {
  MtLoGamePiece() {
  }

  // returns the scene for an empty list
  public WorldScene drawList(WorldScene scene) {
    return scene;
  }

  // moves an empty list of game pieces
  public ILoGamePiece moveList() {
    return this;
  }

  // adds ships to an empty screen
  public ILoGamePiece addShips(int tick) {
    Random rand = new Random();
    if (tick % IConstants.TICK == 0) {
      return this.addShipHelper(rand.nextInt(4));
    }
    else {
      return this;
    }
  }

  // helper that adds ships to a screen
  // has to be between certain numbers to not be in the top and bottom seventh of
  // the screen
  public ILoGamePiece addShipHelper(int acc) {
    Random rand = new Random();
    if (acc == 0) {
      return this;
    }
    else {
      int direction = rand.nextInt(2);
      return new ConsLoGamePiece(
          new Ship(new Posn(chooseDirection(direction), rand.nextInt(214) + 43), direction),
          this.addShipHelper(acc - 1));
    }
  }

  // returns which x coordinate the ship should start at based off the randomized
  // side
  public int chooseDirection(int rand) {
    if (rand == 1) {
      return 510;
    }
    else {
      return -10;
    }
  }

  // adds a new fired bullet to the empty list of game pieces
  public ILoGamePiece addBullet() {
    return new ConsLoGamePiece(new Bullet(1, (new Posn(250, 300)), (3 * Math.PI) / 2), this);
  }

  // returns empty since there are no game pieces that can be off the screen
  public ILoGamePiece remove() {
    return this;
  }

  // returns empty since there are no bullets to explode
  public ILoGamePiece explode() {
    return this;
  }

  // returns the accumulated list of ships
  public ILoGamePiece seperateShips(ILoGamePiece acc) {
    return acc;
  }

  // returns the accumulated list of bullets
  public ILoGamePiece seperateBullets(ILoGamePiece acc) {
    return acc;
  }

//returns the overlapping bullets
  public ILoGamePiece checkOverlap(ILoGamePiece ships, ILoGamePiece collided) {
    return collided;
  }

  // returns false since there is no overlap when there are no game pieces
  public boolean checkOverlapHelper(AGamePiece bullet) {
    return false;
  }

  // returns empty since there are no bullets to explode
  public ILoGamePiece explodeEach() {
    return this;
  }

  // appends an empty list and a other list
  public ILoGamePiece appendTogether(ILoGamePiece other) {
    return other;
  }

  // checks the type of the other list being appended
  public ILoGamePiece appendHelper(ConsLoGamePiece first) {
    return first;
  }

  // returns the list of ships with the ships that have hit bullets removed
  public ILoGamePiece removeOverlapS(ILoGamePiece ships) {
    return ships;
  }

  // removes the bullets that have been overlapped with a ship
  public ILoGamePiece removeOverlapB(ILoGamePiece ships) {
    return this;
  }

  // helper for remove
  public ILoGamePiece removeHelper(AGamePiece bullet) {
    return this;
  }

  // helper for ending the world
  public boolean endHelper() {
    return true;
  }

  // returns the accumulated number to represent score
  public int addScore(int acc) {
    return acc;
  }

  // returns 0 to represent the lack of elements in an empty list
  public int count() {
    return 0;
  }

}

//represents a list of game pieces
class ConsLoGamePiece implements ILoGamePiece {
  AGamePiece first;
  ILoGamePiece rest;

  ConsLoGamePiece(AGamePiece first, ILoGamePiece rest) {
    this.first = first;
    this.rest = rest;
  }

  // draws a list of game pieces
  public WorldScene drawList(WorldScene scene) {
    return this.rest.drawList(this.first.draw(scene));

  }

  // moves a list of game pieces
  public ILoGamePiece moveList() {
    return new ConsLoGamePiece(this.first.move(), this.rest.moveList());
  }

  // adds randomized ships to a list of game pieces
  public ILoGamePiece addShips(int tick) {
    Random rand = new Random();
    if (tick % 28 == 0) {
      return this.addShipHelper(rand.nextInt(4));
    }
    else {
      return this;
    }
  }

  // helper to add ships to a list of game pieces
  public ILoGamePiece addShipHelper(int acc) {
    Random rand = new Random();
    if (acc == 0) {
      return this;
    }
    else {
      int direction = rand.nextInt(2);
      return new ConsLoGamePiece(
          new Ship(new Posn(chooseDirection(direction), rand.nextInt(214) + 43), direction),
          this.addShipHelper(acc - 1));
    }
  }

  // randomizes the side by which the ship enters the screen
  public int chooseDirection(int rand) {
    if (rand == 1) {
      return 510;
    }
    else {
      return -10;
    }
  }

  // adds a fired bullet to a list of game pieces
  public ILoGamePiece addBullet() {
    return new ConsLoGamePiece(new Bullet(1, (new Posn(250, 300)), (3 * Math.PI) / 2), this);
  }

  // removes game pieces that are not on the screen from a list of game pieces
  public ILoGamePiece remove() {
    if (this.first.coord.x > 510 || this.first.coord.x < -10 || this.first.coord.y > 300
        || this.first.coord.y < 0) {
      return this.rest.remove();
    }
    else {
      return new ConsLoGamePiece(this.first, this.rest.remove());
    }
  }

  // returns a list of game pieces with the overlapping bullets exploded
  public ILoGamePiece explode() {
    // seperate list of ships
    ILoGamePiece ships = this.seperateShips(new MtLoGamePiece());
    // seperate list of bullets
    ILoGamePiece bullets = this.seperateBullets(new MtLoGamePiece());
    // list of exploded bullets
    ILoGamePiece explodedBullets = bullets.checkOverlap(ships, new MtLoGamePiece()).explodeEach();
    // list of left over bullets( the ones that did not explode)
    ILoGamePiece leftOverBullets = bullets.removeOverlapB(ships);
    // list of left over ships
    ILoGamePiece leftOverShips = bullets.removeOverlapS(ships);
    // list of game pieces that are left over
    ILoGamePiece leftOverGP = leftOverBullets.appendTogether(leftOverShips);
    // add the exploded bullets to the left over game pieces
    return explodedBullets.appendTogether(leftOverGP);
  }

  // seperates the ships from a list of ships
  public ILoGamePiece seperateShips(ILoGamePiece acc) {
    return this.rest.seperateShips(this.first.seperateHelper2(acc));
  }

  // seperates the bullets from a list of game pieces
  public ILoGamePiece seperateBullets(ILoGamePiece acc) {
    return this.rest.seperateBullets(this.first.seperateHelper1(acc));
  }

  // returns the list of collided bullets
  public ILoGamePiece checkOverlap(ILoGamePiece ships, ILoGamePiece collided) {
    if (ships.checkOverlapHelper(this.first)) {
      return this.rest.checkOverlap(ships, new ConsLoGamePiece(this.first, collided));
    }
    else {
      return this.rest.checkOverlap(ships, collided);
    }
  }

  // checks if any of the ships collided with the given bullet
  public boolean checkOverlapHelper(AGamePiece bullet) {
    return this.first.checkShipOverlap(bullet) || this.rest.checkOverlapHelper(bullet);
  }

  // explodes each bullet in a list of bullets
  public ILoGamePiece explodeEach() {
    return this.first.explodeHelper().appendTogether(this.rest.explodeEach());
  }

  // appends two lists together
  public ILoGamePiece appendTogether(ILoGamePiece other) {
    return other.appendHelper(this);
  }

  // helper that checks the type of the originally inputted second list
  public ILoGamePiece appendHelper(ConsLoGamePiece first) {
    return new ConsLoGamePiece(this.first, this.rest.appendHelper(first));
  }

  // removes the bullets that have hit a ship from the list
  public ILoGamePiece removeOverlapB(ILoGamePiece ships) {
    if (ships.checkOverlapHelper(this.first)) {
      return this.rest.removeOverlapB(ships);
    }
    else {
      return new ConsLoGamePiece(this.first, this.rest.removeOverlapB(ships));
    }
  }

  // removes ships that have collided from the list
  public ILoGamePiece removeOverlapS(ILoGamePiece ships) {
    if (ships.checkOverlapHelper(this.first)) {
      return this.rest.removeOverlapS(ships.removeHelper(this.first));
    }
    else {
      return this.rest.removeOverlapS(ships);
    }
  }

  // helper for removing the overlapping ships
  public ILoGamePiece removeHelper(AGamePiece bullet) {
    if (this.first.checkShipOverlap(bullet)) {
      return this.rest.removeHelper(bullet);
    }
    else {
      return new ConsLoGamePiece(this.first, this.rest.removeHelper(bullet));
    }
  }

  // helper for ending the world
  public boolean endHelper() {
    return false;
  }

  // counts the number of items in a list of game pieces
  public int count() {
    return 1 + this.rest.count();
  }

  // returns the score of the game
  public int addScore(int acc) {
    ILoGamePiece ships = this.seperateShips(new MtLoGamePiece());
    ILoGamePiece bullets = this.seperateBullets(new MtLoGamePiece());
    ILoGamePiece leftOverShips = bullets.removeOverlapS(ships);
    // the amount of ships we lost is also representative of the score
    int score1 = ships.count() - leftOverShips.count();

    // new points + previous score
    return score1 + acc;
  }

}
