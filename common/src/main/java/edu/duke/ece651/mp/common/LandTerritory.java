package edu.duke.ece651.mp.common;
import java.io.*;
import java.util.ArrayList;
import java.util.*;
import java.io.*;
public class LandTerritory implements Territory, java.io.Serializable {
  private static final long serialVersionUID = 1L;
  protected String name;
  public ArrayList<Territory> neighbors;
  protected String color;
  protected ArrayList<Unit> units;
  protected final int size;
  public ArrayList<Spy> spyList;
  

  /**Land Territory Constructor: initialize name, color, units, and neighbors
   *@param: Name: name of the LandTerritory; Color: Color of the LandTerritory;
   */
  public LandTerritory(String Name, String Color, final int size){
    this.name = Name;
    this.neighbors = new ArrayList<Territory>();
    this.color = Color;
    this.units = new ArrayList<Unit>();
    this.size = size;
    this.spyList = new ArrayList<Spy>();
  }

  /**
   *equals function: checks whether the name of the territory is equal
   *@param: object o (another LandTerritory)
   *@return: true if this LandTerritory and o has the same name; false if this LandTerritory and o has different name
   */
  @Override
  public boolean equals(Object o){
    if (o.getClass().equals(getClass())){
      LandTerritory t = (LandTerritory) o;
      
      return this.name.equals(t.name);
    }
    return false;
  }
  
  /*@Override
  public int hashCode() {
    return toString().hashCbode();
  }

  @Override
  public String toString() {
    return ;
  }*/

  /**
   *getname funtion of LandTerritory
   *@return: the name of this LandTerritory
   */
  @Override
  public String getName(){
    return this.name;
  }
  /**
   *getcolor funtion of LandTerritory
   *@return: the color of this LandTerritory
   */
  @Override
  public String getColor(){
    return this.color;
  }
  /**
   *addNeigh funtion of LandTerritory, add Territory neigh to the ArrayList of neighbors
   *@param: another Territory that is the neighbour of this Land Territory
   */
  @Override
  public void addNeigh(Territory neigh){
    this.neighbors.add(neigh);
  }
  /**
   *getneigh funtion of LandTerritory
   *@param: the Arraylist of neighbor Territories of this LandTerritory
   */
  @Override
  public ArrayList<Territory> getNeigh(){
    return this.neighbors;
  }

  
  /**
   *addUnit: Add a unit to the arrayList of units in this territory
   *@param: a Unit u
   *@return: a boolean that shows the Unit u is successfully added
   */
  @Override
  public boolean addUnit(Unit u){
    this.units.add(u);
    return true;
  }
  /**
   * addBasicUnit: add multiple BasicUnits into the LandTerritory, and the difference between addBasicUnit() and setBasicUnit is that addBasicUnit can be used after initialization of LandTerritory.
   *@param: numUnit: the number of BasicUnits you wanna add.
   *@return: a boolean that shows the BasicUnit is successfully added.
   */

  @Override
  public boolean addBasicUnit(int numUnit){
    for (int i = 0; i < numUnit; i++){
      Unit u = new BasicUnit();
      boolean thisadd = this.addUnit(u);
    }
    return true;
  }

  /**
   * loseUnit: lose multiple Units from the LandTerritory.
   *@param: numUnit: the number of Units you wanna remove.
   *@return: a boolean that shows the Unit is successfully deleted.
   */
  @Override
  public boolean loseUnits(int numUnit){
    for(int i = 0; i < numUnit; i++){
      boolean thislose = this.loseUnit();
      if(!thislose){
        return false;
      }
    }
    return true;
  }
  /**
   *countUnit: Count how many Units in this LandTerritory
   *@return: int of how many Units in this LandTerritory
   */
  @Override
  public int countUnit(){
    return this.units.size();
  }
  /**
   *countLevelUnit: count the units within the territory with specified level
   *@param: level: the level of unit as an integer
   *@return: count: the number of unit with the level as an integer
   */
  @Override
  public int countLevelUnit(int level){
    int count = 0;
    for(Unit curr_unit : units){
      if(curr_unit.getLevel() == level){
        count++;
      }
    }
    return count;
  }

  @Override
  /**
   * sort from minimum to maximum according to unit level
   */
  public void sortUnit(){
    if(units.size() > 0){
      for(int i = 0; i < units.size() - 1; i++){
        for(int j = i + 1; j < units.size(); j++){
          if(units.get(i).getLevel() > units.get(j).getLevel()){
            switchUnit(units, i, j);
          }
        }
      }
    }
  }
  /**
   *swithUnit: Switch two units within the arraylist of unit given their indexs
   *@param: unit_arr: the arraylist of unit that want to be changed
   *@param: a: the index of the first element want to be switched as an integer;
   *@param: b: the index of the second element want to be switched as an integer;
   */
  
  public void switchUnit(ArrayList<Unit> unit_arr, int a, int b){
    Unit A_copy = unit_arr.get(a);
    unit_arr.set(a, unit_arr.get(b));
    unit_arr.set(b, A_copy);
  }
  /**
   *getLevelFromPosition: get the level within the Units at the given position
   *@param: position: index of units arraylist as integer
   *@return: the level of the unit at the given position
   */
  public int getLevelFromPosition(int position){
    return units.get(position).getLevel();
  }
  /**
   *upgradeUnit: upgrade several units given the level, new level, and the number of units
   *@param: numUnit: the number of units want to be upgraded as an integer
   *@param: level: the level of units want to be upgraded
   *@param: newlevel: the level of units want to be upgraded to
   *@return: the successfulness of upgrade: true if upgrade successful
   */
  
  @Override
  public boolean upgradeUnit(int numUnit, int level, int newLevel){
    if(this.countLevelUnit(level) < numUnit){
      return false;
    }
    for(int i = 0; i < numUnit; i++){
      for(Unit u : units){
        if(u.getLevel() == level){
          u.UpgradeBasicUnit(newLevel);
          break;
        }
      }
    }
    return true;
  }
  
  /**
   *setUnit: Initially Setting of number of Units within this Territory, Only used in placement Phase
   *@param: int number of Units want to be set with
   */
  @Override
  public void setBasicUnit(int numUnit){
    /*
    if(this.units.size() != 0){
      throw new IllegalArgumentException("Can only set units in placement Phase, but this LandTerritory already has this number of units: " + this.units.size());
    }
    */

    for (int i = 0; i<numUnit; i++){
      Unit u = new BasicUnit();
      this.addUnit(u);
    }
  }
  /**
   *loseUnit: lose one Unit from the list
   *@param: int number of Units want to be set with
   *@return: true if there are more than 0 units to lose; false if there is no units to lose;
   */
  @Override
  public boolean loseUnit(){
    if(this.countUnit() > 0){
      this.units.remove(0);
      return true;
    }
    return false;
  }

  /**
   *changeColor: change to color of the territory to the given color
   *@param: Color: the color that want to be changed to
   */
  @Override
  public void changeColor(String Color){
    this.color = Color;
  }

  /**
   *getSize(): return the size of the territory as an integer
   *@return: the size of the territory
   */
  
  @Override
  public int  getSize(){
    return this.size;
  }

  /**
   *loseUnit: lose Unit which has the specified level
   *@param: the level of Unit as an integer
   *@return: the successfulness of losing the unit: true if the loseUnit is successful
   */
  @Override
  public boolean loseUnit(int level) {
    Unit toRemove = null;
    if(this.countLevel(level) > 0){
      for (Unit u : units) {
        if(u.getLevel() == level) {
          toRemove = u;
          break;
        }
      }
      units.remove(toRemove);
      return true;
    }
    return false;
  }
  /**
   *loseUnit: lose specified number of Unit which has the specified level
   *@param: the level of Unit as an integer
   *@param: the number of Unit want to be lost
   *@return: the successfulness of losing the unit: true if the loseUnit is successful
   */
  @Override
  public boolean loseUnits(int numUnit, int level){
    if(this.countLevel(level) < numUnit){
      return false;
    }
    
    for(int i = 0; i < numUnit; i++){
      boolean thislose = this.loseUnit(level);
      if(!thislose){
        return false;
      }

    }
    return true;
  }

  /**
   *loseUnitAt: lose specified Unit At the specified index
   *@param: the index of array
   */
  @Override
  public void loseUnitsAt(int idx){
    this.units.remove(idx);
  }

  /**
   *getUnitsFromIndex: get the unit from Units at given index
   *@param: the index of unit as integer
   *@return: the unit at the given index
   */
  public Unit getUnitsFromIndex(int index) {
    return units.get(index);
  }
  /**
   *countLevel: Count the number of Unit at given level
   *@param: level of Unit as an integer
   *@return: the number of units at the given level
   */
  @Override
  public int countLevel(int level) {
    int res = 0;
    for (Unit unit : units) {
      if (unit.getLevel() == level) {
        res++;
      }
    }
    return res;

  }

  /**
   *toString: encode the info the the territory to a String
   */
  @Override
  public String toString() {
    String res = "";
    for (int i = 0; i <= 6; i++) {
      res += "-- Level " + i + " unit: " + this.countLevel(i) + "\n";
    }
    return  res;
  }
  /**
   *deep_copy: make a deep copy of territory
   *@return a territory which has the same info as this one
   */
  @Override
  public Territory deep_copy() {
      Territory deep_copy = null;
      try {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          ObjectOutputStream oos = new ObjectOutputStream(baos);
          oos.writeObject(this);

          ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
          ObjectInputStream ois = new ObjectInputStream(bais);
          deep_copy = (Territory) ois.readObject();
      } catch (ClassNotFoundException | IOException e) {
          e.printStackTrace();
      }
      return deep_copy;
  }
  /**
   *addSpy: add a spy which belongs to the Color 
   *@param Color: input Color as a string
   */
  @Override
  public void addSpy(String Color){
    Spy s = new Spy(Color);
    s.setCanMove(false);
    spyList.add(s);
    System.out.println("#########In addSpy add a " + Color + " spy to " + name);
  }
  /**
   *loseSpy: lost a spy which has the color of Color 
   *@param Color: input Color as a String 
   *@return successfulness of loseSpy: true if successful lost of Spy
   */
  @Override
  public boolean loseSpy(String Color){
    for (int i=0; i<this.spyList.size(); i++){
      if (spyList.get(i).getColor().equals(Color) && spyList.get(i).getCanMove()){
        spyList.remove(i);
        return true;
      }
    }
    return false;
  }
  /**
   *getSpyList:
   *@return the spyList of this territory
   */
  @Override
  public ArrayList<Spy> getSpyList(){
    return this.spyList;
  }

  /**
   *resetAllSpies: reset all spies in this territory to movable state 
   */
  @Override
  public void resetAllSpies(){
    for (Spy s : spyList){
      s.setCanMove(true);
    }
  }

  @Override
  public int countSpy(String color) {
    System.out.println("############In count spy");
    System.out.println("#############spyList size = " + spyList.size());
    int count = 0;
    for (Spy s : spyList) {
      if (s.getColor().equals(color)) {
        count++;
      }
    }
    return count;
  }

  @Override
  public void addInitSpy(String Color){
    Spy s = new Spy(Color);
    spyList.add(s);
  }
}
