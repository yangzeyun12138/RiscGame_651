package edu.duke.ece651.mp.common;

import java.util.ArrayList;
import java.util.*;

public class LandTerritory implements Territory, java.io.Serializable {
  private static final long serialVersionUID = 1L;
  protected String name;
  public ArrayList<Territory> neighbors;
  protected String color;
  protected ArrayList<Unit> units;

  /**Land Territory Constructor: initialize name, color, units, and neighbors
   *@param: Name: name of the LandTerritory; Color: Color of the LandTerritory;
   */
  public LandTerritory(String Name, String Color){
    this.name = Name;
    this.neighbors = new ArrayList<Territory>();
    this.color = Color;
    this.units = new ArrayList<Unit>();
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
   *countUnit: Count how many Units in this LandTerritory
   *@return: int of how many Units in this LandTerritory
   */
  @Override
  public int countUnit(){
    return this.units.size();
  }
  /**
   *setUnit: Initially Setting of number of Units within this Territory, Only used in placement Phase
   *@param: int number of Units want to be set with
   */
  @Override
  public void setUnit(int numUnit){
    if(this.units.size() != 0){
      throw new IllegalArgumentException("Can only set units in placement Phase, but this LandTerritory already has this number of units: " + this.units.size());
    }
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
}
