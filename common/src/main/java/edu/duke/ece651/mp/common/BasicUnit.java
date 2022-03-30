package edu.duke.ece651.mp.common;

import java.util.ArrayList;

public class BasicUnit implements Unit, java.io.Serializable {
  private static final long serialVersionUID = 1L;
  private int level;
  private int bonus;
  private int cost;
  public BasicUnit(){
    this.level = 0;
    this.bonus = 0;
    this.cost = 0;
  };

  public BasicUnit(int level){
    LevelInfo info = new LevelInfo();
    this.level = level;
    this.bonus = info.getBonus(level);
    this.cost = info.getCost(level);
  };


  @Override
  public void UpgradeBasicUnit(int newLevel){
    LevelInfo Info = new LevelInfo();
    this.level = newLevel;
    this.bonus = Info.getBonus(newLevel);
    this.cost = Info.getCost(newLevel);
  }

  @Override
  public int getLevel(){
    return this.level;
  }

  @Override
  public int getBonus(){
    return this.bonus;
  }

  @Override
  public int getCost(){
    return this.cost;
  }
}
