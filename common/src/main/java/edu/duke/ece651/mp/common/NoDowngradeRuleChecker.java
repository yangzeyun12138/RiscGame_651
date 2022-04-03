package edu.duke.ece651.mp.common;
import java.util.*;

public class NoDowngradeRuleChecker extends UpgradeChecker {
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
    if(level > newLevel){
      return new String(player.color + " player. Invalid Upgrade: The new level should be higher than the current level!\n");
    }
    return null;
  }

  public NoDowngradeRuleChecker(UpgradeChecker next){
    super(next);
  }
}
