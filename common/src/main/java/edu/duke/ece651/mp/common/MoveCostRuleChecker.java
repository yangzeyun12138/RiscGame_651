package edu.duke.ece651.mp.common;
import java.util.*;

public class MoveCostRuleChecker extends MoveChecker{
  /**
     the method returns the boolean that check whether the player has enough food to do the movement
     @param: player, the player is making the move:
     @param: src, the name of the source territory;
     @param: dest, the name of the destination territory;
     @param: numUnit, the number of units the player want to move
     @param: level of the unit
     @return: String if there is Violation of rule, null if there is none
   */
  @Override
  protected String checkMyRule(Player player,String src,String dest,int numUnit, int level){
    int min_path = findMinPath(player, src, dest);
    if(min_path * numUnit > player.getFood()){
      return new String(player.color + " player. The movement is Invalid: the cost for the minimum path is higher than the number of the food!\n");
    }
    return null;
  }
  /**
     findMinPath: return the minimum cost from src territory to the destination territory
     @param: player who does the movement
     @param: src is the name of the source territory
     @param: dest is the name of the destination territory
     @return: the number of minimum total size of territory in the path, -1 if something goes wrong
   */
  public int findMinPath(Player player, String src, String dest){
    HashSet<String> t_set = new HashSet<String>();
    for(Territory curr_t : player.player_terri_set){
      if(curr_t.getName().equals(src)){
        t_set.add(curr_t.getName());
        int min_size = minTotalSize(curr_t, dest,t_set);
        return min_size;
          //- curr_t.getSize();
      }
    }
    return -1;
  }
  //recursive function that uses dfs (useit or loseit) to find minimum total size
  public int minTotalSize(Territory curr_t, String dest, HashSet<String> t_set){
    if(curr_t.getName().equals(dest)){
      return 0;
    }
    String color = curr_t.getColor();
    HashSet<String> new_t_set = new HashSet<String>();
    for(String t: t_set){
      new_t_set.add(t);
    }
   
    ArrayList<Integer> sizeList = new ArrayList<Integer>();
    int counter = 0;
    for(Territory neigh : curr_t.getNeigh()){
      if(neigh.getColor().equals(color) && !new_t_set.contains(neigh.getName())){
        counter++;
        new_t_set.add(neigh.getName());
        sizeList.add(neigh.getSize() + minTotalSize(neigh, dest, new_t_set));
        
      }
    }
    if(counter == 0 && curr_t.getNeigh().size() > 0){
      return 300000;
    }else{
      return getMin(sizeList);
    }
    
  }

  public int getMin(ArrayList<Integer> arr_list){
    int min = Integer.MAX_VALUE;
    for(int a : arr_list){
      if (a < min){
        min = a;
      }
    }
    return min;
  }

  public MoveCostRuleChecker(MoveChecker next){
    super(next);
  }
  
}
