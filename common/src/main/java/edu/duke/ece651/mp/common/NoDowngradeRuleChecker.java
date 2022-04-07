package edu.duke.ece651.mp.common;
import java.util.*;

public class NoDowngradeRuleChecker extends UpgradeChecker {
  @Override
  /**
   *checkMyRule(): This function is to check whether the unit is downgraded
   *@param player does the upgrade
   *@param src is the name of the source territory
   *@param numUnit is the number of unit that player wants to do the attack.
   *@param level of the unit want to be upgraded
   *@param newlevel of the unit want to be upgraded to
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
