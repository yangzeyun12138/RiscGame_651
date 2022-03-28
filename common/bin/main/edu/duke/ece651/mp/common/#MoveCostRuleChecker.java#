package edu.duke.ece651.mp.common;
import java.util.*;

public class MoveCostRuleChecker extends MoveChecker{
  /**
     the method returns the boolean that check whether the number unit of the player want to move from one src territory
     is less than the existing number of units within the src territory
     @param: player, the player is making the move:
     @param: src, the name of the source territory;
     @param: dest, the name of the destination territory;
     @param: numUnit, the number of units the player want to move
     @return: String if there is Violation of rule, null if there is none
   */
  @Override
  protected String checkMyRule(Player player,String src,String dest,int numUnit){
    System.out.println("in unit rule check");
    int min_path = findMinPath(player, src, dest);
    if(min_path * numUnit > player.getFood()){
      return new String(player.color + " player. The movement is Invalid: the cost for the minimum path is higher than the number of the food!\n");
    }
    return null;
  }

  public int findMinPath(Player player, String src, String dest){
    HashSet<String> t_set = new HashSet<String>();
    for(Territory curr_t : player.player_terri_set){
      t_set.add(curr_t.getName());
      int min_size = minTotalSize(curr_t, dest,t_set);
      return min_size;
    }
    return 0;
  }

  public int minTotalSize(Territory curr_t, String dest, HashSet<String> t_set){
    if(curr_t.getName().equals(dest)){
      return curr_t.getSize();
    }
    ArrayList<Integer> sizeList = new ArrayList<Integer>();
    for(Territory neigh : curr_t.getNeigh()){
      if(!t_set.contains(neigh.getName())){
        t_set.add(neigh.getName());
        sizeList.add(neigh.getSize() + minTotalSize(neigh, dest, t_set));
      }
    }
    return getMin(sizeList);
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
