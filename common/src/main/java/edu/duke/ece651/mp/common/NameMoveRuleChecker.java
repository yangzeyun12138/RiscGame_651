package edu.duke.ece651.mp.common;

import java.util.HashSet;
import java.util.*;

public class NameMoveRuleChecker extends MoveChecker{
  /**
     the method returns the String that check whether the src territory and dest territory exist a path
     @param: player, the player is making the move:
     @param: src, the name of the source territory;
     @param: dest, the name of the destination territory;
     @param: numUnit, the number of units the player want to move
     @return: String if there is Violation of rule, null if there is none
   */
  @Override
  protected String checkMyRule(Player player,String src,String dest,int numUnit, int level){
    boolean src_check = false;
    boolean dest_check = false;
    for(Territory t : player.player_terri_set){
      if(t.getName().equals(src)){
        src_check = true;
      }
      if(t.getName().equals(dest)){
        dest_check = true;
      }
    }
    
    if(src_check && dest_check){
      return null;
    }
    else {
      return new String(player.color + " player. The movement is Invalid: The name of the source territory or the destination territory is invalid\n");
    }     
  }

 

  public NameMoveRuleChecker(MoveChecker next){
    super(next);
  }
  
}
