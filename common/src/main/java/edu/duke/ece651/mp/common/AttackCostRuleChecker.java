package edu.duke.ece651.mp.common;

import java.util.*;
public class AttackCostRuleChecker extends AttackChecker {
  @Override
  /**
   *checkMyRule(): This function is to check whether the source territory and the destination territory are the same faction.
   *@param player build the attack!
   *@param src is the name of the source territory
   *@param dest is the name of the dest territory
   *@param numUnit is the number of unit that player wants to do the attack.
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
