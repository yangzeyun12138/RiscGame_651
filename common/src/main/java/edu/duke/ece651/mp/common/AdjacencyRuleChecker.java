package edu.duke.ece651.mp.common;
import java.util.*;

public class AdjacencyRuleChecker extends AttackChecker {
  @Override
  /**
   *checkMyRule(): This function is to check whether the source territory is adjacent to the destination territory
   *@param player build the attack!
   *@param src is the name of the source territory
   *@param dest is the name of the dest territory
   *@param numUnit is the number of unit that player wants to do the attack.
   *@param level is the level of unit
   *@return return the error string, and return null if there is no error. 
   */
  protected String checkMyRule(Player player, String src, String dest, int numUnit, ArrayList<Player> players, int level){
    for(Territory curr_t : player.player_terri_set){
      if(curr_t.getName().equals(src)){
        for(Territory neigh : curr_t.getNeigh()){
          if(neigh.getName().equals(dest)){
            return null;
          }
        }
      }
    }
    return new String(player.color + " player. Invalid Attack: The destination territory is not adjacent to the source territory!\n");
  }

  public AdjacencyRuleChecker(AttackChecker next){
    super(next);
  }
}
