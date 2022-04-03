package edu.duke.ece651.mp.common;
import java.util.*;
public class LevelInfo {
  private final HashMap<Integer, Integer> TechCostMap;
  private final HashMap<Integer, Integer> BonusMap;
  private final HashMap<Integer, Integer> CostMap;

  public LevelInfo(){
    this.TechCostMap = new HashMap<Integer, Integer>();
    this.BonusMap = new HashMap<Integer, Integer>();
    this.CostMap = new HashMap<Integer, Integer>();
    TechCostMap.put(1,0);
    TechCostMap.put(2,50);
    TechCostMap.put(3,125);
    TechCostMap.put(4,250);
    TechCostMap.put(5,450);
    TechCostMap.put(6,750);
    BonusMap.put(0,0);
    BonusMap.put(1,1);
    BonusMap.put(2,3);
    BonusMap.put(3,5);
    BonusMap.put(4,8);
    BonusMap.put(5,11);
    BonusMap.put(6,15);
    CostMap.put(0,0);
    CostMap.put(1,3);
    CostMap.put(2, 11);
    CostMap.put(3,30);
    CostMap.put(4,55);
    CostMap.put(5,90);
    CostMap.put(6,140);
  }

  
  public int getBonus(int level){
    if(level < 0 || level > 6){
      throw new IllegalArgumentException("The number of the Level in BonusMap is invalid!");
    }
    return BonusMap.get(level);
  }

  public int getCost(int level){
    if(level < 0 || level > 6){
      throw new IllegalArgumentException("The number of the Level in CostMap is invalid!");
    }
    return CostMap.get(level);
  }

  public int getTechCost(int level){
    if(level < 1 || level > 6){
    throw new IllegalArgumentException("The number of the Level in TechCostMap is invalid!");
    }
    return TechCostMap.get(level);
  }
  
}
