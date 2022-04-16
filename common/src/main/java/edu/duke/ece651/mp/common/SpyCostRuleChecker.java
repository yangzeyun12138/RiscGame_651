package edu.duke.ece651.mp.common;

import java.util.*;
public class SpyCostRuleChecker extends SpyChecker {
/**
   *checkMyRule: check the rules before move a spy, make sure the player has more food than the cost of spy movement
   *@param: player: the player who wants to move the spy
   *@param: players: the list of all players
   *@param: src: the name of the source territory
   *@param: dest: the name of the destination territory
   *@return: result: null if there is no errors; Error message as a String
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
