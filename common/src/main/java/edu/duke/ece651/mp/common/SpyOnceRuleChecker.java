package edu.duke.ece651.mp.common;
import java.util.*;

public class SpyOnceRuleChecker extends SpyChecker {
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

    for(Territory curr_t : player.player_terri_set){
      if(curr_t.getName().equals(src)){
        for(Spy s : curr_t.getSpyList()){
          if (s.getColor().equals(player.color) && s.getCanMove() == true){
            return null;
          }
        }
      }
    }
    
    return new String(player.color + " player. Invalid Spy Movement: The Source territory does not have movable Spy belongs to the player!\n");
  }

  public SpyOnceRuleChecker(SpyChecker next){
    super(next);
  }
}




