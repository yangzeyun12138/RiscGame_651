package edu.duke.ece651.mp.common;

import java.util.HashSet;
import java.util.*;

public class NameUpgradeRuleChecker extends UpgradeChecker{
  /**
   *checkMyRule(): This function is to check whether the source territory exist in player
   *@param player does the upgrade
   *@param src is the name of the source territory
   *@param numUnit is the number of unit that player wants to do the attack.
   *@param level of the unit want to be upgraded
   *@param newlevel of the unit want to be upgraded to
   *@return return the error string, and return null if there is no error. 
   */
  @Override
  protected String checkMyRule(Player player,String src,int numUnit, int level, int newLevel){
    boolean src_check = false;

    for(Territory t : player.player_terri_set){
      if(t.getName().equals(src)){
        src_check = true;
      }
    }
    
    if(src_check){
      return null;
    }
    else {
      return new String(player.color + " player. Invalid Upgrade: The name of the source territory is invalid\n");
    }     
  }

 

  public NameUpgradeRuleChecker(UpgradeChecker next){
    super(next);
  }
  
}
