package edu.duke.ece651.mp.common;
import java.util.*;

public class LevelRuleChecker extends UpgradeChecker {
  @Override
  /**
   *checkMyRule(): This function is to check whether the source territory is adjacent to the destination territory
   *@param player build the attack!
   *@param src is the name of the source territory
   *@param dest is the name of the dest territory
   *@param numUnit is the number of unit that player wants to do the attack.
   *@return return the error string, and return null if there is no error. 
   */
  protected String checkMyRule(Player player, String src, int numUnit, int level, int newLevel){
    for(Territory curr_t : player.player_terri_set){
      if(curr_t.getName().equals(src)){
        if(curr_t.countLevelUnit(level) >= numUnit){
          return null;        
        }
      }
    }
    return new String(player.color + " player. Invalid Upgrade: The number of Unit on the specific territory is not enough!\n");
  }

  public LevelRuleChecker(UpgradeChecker next){
    super(next);
  }
}
