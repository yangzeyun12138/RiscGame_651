package edu.duke.ece651.mp.common;
import java.util.*;

public class NameAttackRuleChecker extends AttackChecker {
  @Override
  /**
   *checkMyRule(): This function is to check whether the source territory is adjacent to the destination territory
   *@param player build the attack!
   *@param src is the name of the source territory
   *@param dest is the name of the dest territory
   *@param numUnit is the number of unit that player wants to do the attack.
   *@return return the error string, and return null if there is no error. 
   */
  protected String checkMyRule(Player player, String src, String dest, int numUnit, ArrayList<Player> players, int level){
    boolean src_check = false;
    boolean dest_check = false;
    for(Territory t : player.player_terri_set){
        if(t.getName().equals(src)){
          src_check = true;
        }
    }
    
    for(Player p : players){
      for(Territory t : p.player_terri_set){
        if(t.getName().equals(dest)){
          dest_check = true;
        }
      }
    }
    if(src_check && dest_check){
      return null;
    }
    else {
      return new String(player.color + " player. The Attack is Invalid: The name of the source territory or the destination territory is invalid\n");
    }    
  }

  public NameAttackRuleChecker(AttackChecker next){
    super(next);
  }
}
