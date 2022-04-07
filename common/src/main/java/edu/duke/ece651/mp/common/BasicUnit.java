package edu.duke.ece651.mp.common;

import java.util.ArrayList;
import java.util.HashMap;

public class BasicUnit implements Unit, java.io.Serializable {
  private static final long serialVersionUID = 1L;
  private int level;
  private int bonus;
  private int cost;
  /**
   * BasicUnit constructor: initialize level, bonus, and cost to 0
   */
  public BasicUnit(){
    this.level = 0;
    this.bonus = 0;
    this.cost = 0;
  };
  /**
   * BasicUnit constructor: initialize level, bonus, and cost according to the level
   * @param: the level of Unit want to be set
   */ 
  public BasicUnit(int level){
    LevelInfo info = new LevelInfo();
    this.level = level;
    this.bonus = info.getBonus(level);
    this.cost = info.getCost(level);
  };
  /**
   *UpgradeBasicUnit:
   *@param: the level of unit want to be upgraded to
   */
  @Override
  public void UpgradeBasicUnit(int newLevel){
    LevelInfo Info = new LevelInfo();
    this.level = newLevel;
    this.bonus = Info.getBonus(newLevel);
    this.cost = Info.getCost(newLevel);
  }
  /**
   *getLevel: get the level of the basicUnit
   *@return: the level of the unit as an integer
   */
  @Override
  public int getLevel(){
    return this.level;
  }
  /**
   *getBonus: get the bonus of the basicUnit
   *@return: the bonus of the unit as an integer
   */
  @Override
  public int getBonus(){
    return this.bonus;
  }
  /**
   *getCost: get the cost of the basicUnit
   *@return: the cost of the unit as an integer
   */
  @Override
  public int getCost(){
    return this.cost;
  }
}
