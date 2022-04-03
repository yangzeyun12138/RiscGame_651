package edu.duke.ece651.mp.common;

import java.util.HashSet;
import java.util.*;

public class PathRuleChecker extends MoveChecker{
  /**
     the method returns the String that check whether the src territory and dest territory exist a path
     @param: player, the player is making the move:
     @param: src, the name of the source territory;
     @param: dest, the name of the destination territory;
     @param: numUnit, the number of units the player want to move
     @return: String if there is Violation of rule, null if there is none
   */
  @Override
  protected String checkMyRule(Player player,String src,String dest,int numUnit, int level){
    System.out.println("in path rule check");
    ArrayList<Territory> SearchQueue = new ArrayList<Territory>();
    HashSet<String> visited = new HashSet<String>();
    String color = player.getColor();
    for (Territory t: player.player_terri_set){
      if (t.getName().equals(src)){
        //This is the source territory
        SearchQueue.add(t);
        while(!SearchQueue.isEmpty()){
          //check this is the dest territory
          Territory terri = SearchQueue.remove(0);
          if (terri.getName().equals(dest)){
            return null;
          }
          //check this territory is visited
          //if not visited, pop and add it to visited, and its neighbors to SeachQueue
          if (!visited.contains(terri.getName())){
            visited.add(terri.getName());
            
            for(Territory t_neigh : terri.getNeigh()){
              // check the neighbors share the same color
              if (t_neigh.getColor().equals(color)){
                SearchQueue.add(t_neigh);
              }
            }
          }
        }

        
      }
    } 
    return new String(player.color + " player. The movement is Invalid: the Path between src and dest Territory cannot be found\n");
  }

 

  public PathRuleChecker(MoveChecker next){
    super(next);
  }
  
}
