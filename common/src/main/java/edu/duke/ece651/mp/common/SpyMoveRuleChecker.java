package edu.duke.ece651.mp.common;
import java.util.*;

public class SpyMoveRuleChecker extends SpyChecker {
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
  protected String checkMyRule(Player player,ArrayList<Player> players, String src, String dest){
    boolean srcBool = false;
    boolean destBool = false;
    for(Territory curr_t : player.player_terri_set){
      if(curr_t.getName().equals(src)){
        srcBool = true;
      }
      if(curr_t.getName().equals(dest)){
        destBool = true;
      }
    }
    if (srcBool && destBool){
      return null;
    }else{
      for(Player p: players){
        for(Territory curr_t : p.player_terri_set){
          if(curr_t.getName().equals(src)){
            for(Territory neigh:curr_t.getNeigh()){
              if (neigh.getName().equals(dest)){
                return null;
              }
            }
          }
        }
      }
    }
    
    return new String(player.color + " player. Invalid Spy Movement: The destination territory is not reachable from Source Territory!\n");
  }

  public SpyMoveRuleChecker(SpyChecker next){
    super(next);
  }
}




