package edu.duke.ece651.mp.common;

import java.util.HashSet;

public class UnitRuleChecker extends MoveChecker{
  /**
     the method returns the boolean that check whether the number unit of the player want to move from one src territory
     is less than the existing number of units within the src territory
     @param: player, the player is making the move:
     @param: src, the name of the source territory;
     @param: dest, the name of the destination territory;
     @param: numUnit, the number of units the player want to move
     @return: String if there is Violation of rule, null if there is none
   */
  @Override
  protected String checkMyRule(Player player,String src,String dest,int numUnit){
    for (Territory t: player.player_terri_set){
      if (t.getName().equals(src)){
        if (t.countUnit() < numUnit){
          return new String(player.color + " player. The movement is Invalid: the number of unit within the territory is less than the number you want to move!\n");
        }
        if(numUnit < 0){
          return new String(player.color + " player. The movement is Invalid: the Unit to move cannot be negative!\n");
        }
      }
    }
    return null;
  }

  public UnitRuleChecker(MoveChecker next){
    super(next);
  }
  
}
