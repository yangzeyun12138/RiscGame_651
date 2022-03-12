package edu.duke.ece651.mp.common;
import java.util.ArrayList;

public interface GameMap {
    public int get_num_players();
    public ArrayList<Player> get_player_list();
    public void assign_one(String color);
    public void assign_all();
}
