package edu.duke.ece651.mp.common;

import java.util.*;
public class AttackCostRuleChecker extends AttackChecker {
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
  protected String checkMyRule(Player player, String src, String dest, int numUnit, ArrayList<Player> players, int level){
    if(player.getFood() < numUnit){
      return new String(player.color + " player. Invalid Attack: The cost of the attack is higher than the food of the player!\n");
    } 
    return null;
  }

  public AttackCostRuleChecker(AttackChecker next){
    super(next);
  }
}
