package edu.duke.ece651.mp.common;

import java.util.*;
public class SpyCostRuleChecker extends SpyChecker {
  @Override
  /**
   *checkMyRule(): This function is to check whether player has enough cost to do the attack
   *@param player build the attack!
   *@param src is the name of the source territory
   *@param dest is the name of the dest territory
   *@param numUnit is the number of unit that player wants to do the attack.
   *@param level of Unit
   *@return return the error string, and return null if there is no error. 
*/
  protected String checkMyRule(Player player, ArrayList<Player>players, String src ,String dest){
    int SpyMoveCost = 20;
    if(player.getFood() < SpyMoveCost){
      return new String(player.color + " player. Invalid Spy Movement: The cost of the Spy movement is higher than the food of the player!\n");
    } 
    return null;
  }

  public SpyCostRuleChecker(SpyChecker next){
    super(next);
  }
}
