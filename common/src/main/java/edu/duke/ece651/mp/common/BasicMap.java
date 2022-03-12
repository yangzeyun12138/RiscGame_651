package edu.duke.ece651.mp.common;

import java.util.ArrayList;
import java.util.HashSet;


public class BasicMap implements GameMap, java.io.Serializable{
  private static final long serialVersionUID = 1L;
  protected String mapName;
  protected ArrayList<String> colorList;
  protected HashSet<Territory> TerritorySet;
  private ArrayList<Player> players;

  /**
   * one room one map
   * @param name is given by external
   * @param colorList given by parse
   * @param TerritorySet given by parse
   */
  public BasicMap(String name, ArrayList<String> colorList, HashSet<Territory> TerritorySet){
    this.mapName = name;
    this.colorList = colorList;
    this.TerritorySet = TerritorySet;
    this.players = new ArrayList<Player>();
  }

  @Override
  public int get_num_players() {
    return colorList.size();
  }

  @Override
  public ArrayList<Player> get_player_list(){
    return players;
  }


  @Override

  /**
   * assign territory set according to this color to one player and
   * add this player to the player list
   * @param color
   */

  public void assign_one(String color) {
    HashSet<Territory> player_terri = new HashSet<Territory>();
    for (Territory t : TerritorySet) {
        if (t.getColor().equals(color)) {
          player_terri.add(t);
        }
    }
    players.add(new Player(color, player_terri));
  }


  @Override

  /**
   * call assign_one method to traverse the colorList
   * for assigning each color territory set to all the players
   */

  public void assign_all(){
    for (int i = 0; i < colorList.size(); i++){
      assign_one(colorList.get(i));
    }
  }
}
