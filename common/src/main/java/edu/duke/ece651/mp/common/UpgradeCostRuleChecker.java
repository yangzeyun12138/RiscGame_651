package edu.duke.ece651.mp.common;
import java.util.*;

public class UpgradeCostRuleChecker extends UpgradeChecker {
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
    LevelInfo info = new LevelInfo();
    int totalCost = numUnit * (info.getCost(newLevel) - info.getCost(level));
    if(player.getFood() < totalCost){
      return new String(player.color + " player. Invalid Upgrade: The player has insufficient food for Upgrade!\n");
    }
    return null;
  }

  public UpgradeCostRuleChecker(UpgradeChecker next){
    super(next);
  }
}
