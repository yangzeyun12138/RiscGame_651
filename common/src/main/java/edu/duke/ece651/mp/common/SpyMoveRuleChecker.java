package edu.duke.ece651.mp.common;
import java.util.*;

public class SpyMoveRuleChecker extends SpyChecker {
/**
   *checkMyRule: check the rules before move a spy, make sure it can move through its own territory and can move only to neighbor territories when in enemy territory
   *@param: player: the player who wants to move the spy
   *@param: players: the list of all players
   *@param: src: the name of the source territory
   *@param: dest: the name of the destination territory
   *@return: result: null if there is no errors; Error message as a String
   */
  @Override
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




