package edu.duke.ece651.mp.client;
import com.google.common.annotations.VisibleForTesting;
import edu.duke.ece651.mp.common.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Each ClientSk per player, the communication hub between a player and the server
 */
public class ClientSk {
  public BasicMap map;
  public Socket socket;
  private MapDisplay toDisplay;
  private String color;
  private int num_units;
  public Player player;
  final BufferedReader inputReader;
  final PrintStream out;
  private AbstractActionFactory Action;
  private ArrayList<Player> players;

  /**
   * connect to the local host on 9999 port
   * map and toDisplay need to wait for info from server to initialize
   *
   * @throws IOException
   */
  public ClientSk(String Hostname, String port, BufferedReader inputSource, PrintStream outSource) throws IOException {
    socket = new Socket(Hostname, Integer.parseInt(port));
    this.map = null;
    this.inputReader = inputSource;
    this.out = outSource;
    this.Action = new V1Action();
    this.players = new ArrayList<Player>();
  }


  /**
   * Read a map object from the socket and display it
   *
   * @return A String that shows the info of the whole map
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void game_begin() throws IOException, ClassNotFoundException {
    String map_show1 = new String(accept_map());
    out.print(map_show1);
    accept_color();
    accept_units();
    set_player();
    init_unit();
    send_player();
    String map_show2 = new String(accept_map());
    out.print(map_show2);
    do_turns();
  }
  
  /**
   * Made the client to accept the string
  
   *@return A String that the client receive  
   *@throws IOException
   *@throws ClassNotFoundException 
   */
  public String accept_string() throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    return  (String) ois.readObject();
  }
  
  /**
   * Made the client to accept the map and return the map

   *@return A String that the client receive the map  
   *@throws IOException
   *@throws ClassNotFoundException 
   */
  public String accept_map() throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    map = (BasicMap) ois.readObject();
    toDisplay = new BasicMapDisplay(map);
    return toDisplay.display();
  }
  
  /**

   * Made the client to accept the its player color
   *@return void
   *@throws IOException
   *@throws ClassNotFoundException 
   */
  public void accept_color() throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    this.color = (String) ois.readObject();
    out.print("You are " + color + " player, ");
  }


  /**

   *Made the client to accept the its player color
   *@return void
   *@throws IOException
   *@throws ClassNotFoundException    
   */
  public void accept_units() throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    this.num_units = (int) ois.readObject();
    out.println("you have " + num_units + " units totally");
    out.println("Please place your units on each territory");
  }

  
   /**

   *Update the player's infomation when client receive the map
   *@return void
   */  
  public void set_player() {
    for (Player p : map.get_player_list()) {
      if (this.color.equals(p.color)) {
        this.player = p;
      }
    }
    this.players = map.get_player_list();
  }
   /**

   *Prompt the player to input the unit and then init the unit for each playe   *r's Territory
   *@return void
   */  
  public void init_unit() {
    int limit = num_units;
    int count = 0;
    for (Territory t : player.player_terri_set) {
      if (count == player.player_terri_set.size() - 1) {
        t.setBasicUnit(limit);
        out.println("Remaining " + limit + " units are placed in " + t.getName());
        return;
      }
      out.println("How many units do you want to place in " + t.getName() + "?");
      out.print("Please enter a integer: ");
      while (true) {
        try {
          int temp = try_readLine(limit);
          limit -= temp;
          t.setBasicUnit(temp);
          break;
        } catch (IllegalArgumentException | IOException e) {
          out.println(e.getMessage());
        }
      }
      count++;
    }
  }

  /**
   *Prompt the player to enter the unit they want to place in his territory 
   *@return the number that the player enter
   *@param limit is the number of player that can put maximum unit 
   */
  public int try_readLine(int limit) throws IOException {
    String s = inputReader.readLine();
    for (int i = 0; i < s.length(); i++) {
      if (!Character.isDigit(s.charAt(i))) {
        throw new IllegalArgumentException("Please enter a valid integer!");
      }
    }
    int in = Integer.parseInt(s);
    if (in >= 0 && in <= limit) {
      return in;
    } else {
      throw new IllegalArgumentException("Please enter an integer within the " + limit + "!");
    }
  }

  /**
   * close the socket connection
   *
   * @throws IOException
   */

  public void send_player() {
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(player);
      oos.flush();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   *Read the player's input 
   *@return  player's input string
   *@param prompt is the input that the player enter
   *@throws IOException
   */  
  public String read_string(String promt) throws IOException {
    out.print(promt);
    String s = inputReader.readLine();
    return s;
  }
  
  /**
   *Parse the players input(orders) and check the player's input format, if 
   *the format is correct, the action will add to orders list  
   *@return if input format is correct, return null, otherwise return error i   *nfo
   *@param action is one of String M, A, D that player choose to enter
   *@param orders is the players orders
   *@param players is the list of the all player
   *@throws IOException
   */  
  public String parse_check_add(String action, Orders orders, ArrayList<Player> players) throws IOException {
    String order = read_string("Please enter your order as following format\nsourceTerritoryName destinationTerritoryName " +
            "numUnitsToDestination\n");
    String src = new String();
    String des = new String();
    int index1 = order.indexOf(" ");
    if (index1 == -1) {
      return new String("Please enter your order as following format\nsourceTerritoryName destinationTerritoryName " +
              "numUnitsToDestination\n");
    }
    src = order.substring(0, index1);
    int index2 = order.indexOf(" ", index1 + 1);
    if (index2 == -1) {
      return new String("Please enter your order as following format\nsourceTerritoryName destinationTerritoryName " +
              "numUnitsToDestination\n");
    }
    int index3 = order.indexOf(" ", index2 + 1);
    if (index3 != -1) {
      return new String("Please enter your order as following format\nsourceTerritoryName destinationTerritoryName " +
              "numUnitsToDestination\n");
    }
    des = order.substring(index1 + 1, index2);
    String numMove = order.substring(index2 + 1);
    for (int i = 0; i < numMove.length(); i++) {
      if (!Character.isDigit(numMove.charAt(i))) {
        return new String("Please enter a valid integer!");
      }
    }
    int num_move = Integer.parseInt(numMove);
    if (action.equals("M")) {
      String temp = Action.checkForMove(player, src, des, num_move);
      if (temp != null) {
        return temp;
      }
      else {
        //new a order class, add it to orders moveList
        orders.MoveList.add(new Order(player, src, des, num_move));
      }
    }
    else {
      String temp = Action.checkForAttack(player, src, des, num_move, players);
      if (temp != null) {
        return temp;
      }
      else {
        //new a order class, add it to orders attackList
        orders.AttackList.add(new Order(player, src, des, num_move));
      }
    }
    return null;
  }

  /**
   *Check the format of the player's input action  
   *@return void
   *@param s is the player's input of the action
   */
  public void check_action(String s) {
    if (s.length() != 1) {
      throw new IllegalArgumentException("Please enter one of the the first capital letter of action");
    }
    if (s.charAt(0) != 'M' && s.charAt(0) != 'A' && s.charAt(0) != 'D') {
      throw new IllegalArgumentException("Please enter one of the the first capital letter of action");
    }
  }

  /**
   *collect one order of the player in one turn
   *@return the action of the player
   *@param orders is the player's orders
   *@throws IOException
   */
  public String collect_one_order(Orders orders) throws IOException {
    out.println("You are the " + color + " player, what would you like to do?");
    out.print("(M)ove\n(A)ttack\n(D)one\nPlease enter the first capital letter\n");
    out.println("Attention: please do some Move or Attack, then ends with Done.");
    String action = null;
    while (true) {
      try {
        action = new String(inputReader.readLine());
        check_action(action);
        break;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
      }
    }
    while (true) {
      String res = null;
      if (action.equals("D")) {
        break;
      }
      else {

        do {
          if (action.equals("M")) {

            res = parse_check_add(action, orders, players);
          } else {
            res = parse_check_add(action, orders, players);
          }
          if (res != null) {
            out.println(res);
          }
        } while (res != null);
        break;
      }
    }
    return action;
  }

  /**
   *judge if one turn is end, if end, client will receive "Total Sucess"  
   *if not end, client input is invalid, client will notify player enter
   *order again
   *@return void
   *@thorws IOException, ClassNotFoundException
   */  
  public void if_end_one_turn() throws IOException, ClassNotFoundException {
    String res = null;
    while(true) {
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      res = (String) ois.readObject();
      if (res.equals("Total success")) {
        break;
      }
      if (!res.equals("Success")) {
        out.print(res);
        out.print("Please enter all your orders again for this turn\n");
        collect_orders_and_send();
      }
    }
  }

  /**
   *collect orders of the player in one turn, until the player input D 
   *@return void  
   *@throws IOException
   */
  public void collect_orders_and_send() throws IOException {
    Orders orders = new Orders();
    String temp = new String();
    do {
      temp = collect_one_order(orders);
    } while (!temp.equals("D"));
    send_orders(orders);
  }

  /**
   *handle the situation that the player lose, if player lose, they will ente   *r the input to indicate them to watch the game or disconnect 
   *@return the player's input string  
   *@throws IOException
   */  
  public String handle_lose() throws IOException {
    out.println("You lose! Do you want to keep watching game going? Please enter y or n. y means yes, n means no.");
    while (true) {
      String s = inputReader.readLine();
      try {
        if (s.length() != 1) {
          throw new IllegalArgumentException("You should enter y or n");
        } else if (!s.equals("y") && !s.equals("n")) {
          throw new IllegalArgumentException("You should enter y or n");
        } else {
          return s;
        }
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
      }

    }
  }

  /**
   * Help to judge if one turn is end  
   *@return if somebody is not win, return true, if somebody win, return fals   *e  
   *@throws IOException, ClassNotFoundException
   */  
  public boolean turn_end_helper() throws IOException, ClassNotFoundException {
    ObjectInputStream ois_new = new ObjectInputStream(socket.getInputStream());
    String res = (String) ois_new.readObject();
    if (!res.equals("noBodyWin")) {
      out.println(res);
      close_client();
      return true;
    }
    else {
      return false;
    }
  }
  /**
   *simulate the lose player  
   *@return void
   *@throws IOException, ClassNotFoundException
*/
  public void do_turns_as_watch() throws IOException, ClassNotFoundException {
    while (true) {
      Orders new_orders = new Orders();
      send_orders(new_orders);
      if_end_one_turn();
      String map_show = new String(accept_map());
      out.print(map_show);
      set_player();
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      String res = (String) ois.readObject();
      if (turn_end_helper()) {
        return;
      }
    }
  }
  /**
   *help to judge if the player is last loser  
   *@return if the player is last loser, return true, otherwise return false 
   */
  public boolean is_last_loser() {
    ArrayList<Player> players = map.get_player_list();
    int count = 0;
    for(int i = 0; i < players.size(); i++){
      if (!players.get(i).checkLose()) {
        count++;
      }
    }
    if (count == 1) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * do all turn in one game
   *@return void
   *@throws IOException, ClassNotFoundException
   */
  public void do_turns() throws IOException, ClassNotFoundException {
    String s = null;
    while (true) {
      collect_orders_and_send();
      if_end_one_turn();
      String map_show = new String(accept_map());
      out.print(map_show);
      set_player();
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      String res = (String)ois.readObject();
      if (res.equals("Lose")) {
        if (is_last_loser()) {
          out.println("You lose!");
          s = "y";
        }
        else {
          s = handle_lose();
        }
        if (s.equals("y")) {
          //send_string("y");
          if (turn_end_helper()) {
            return;
          }
          do_turns_as_watch();
          return;
        }
        else {
          //send_string("n");
          close_client();
          return;
        }
      }
      //send_string("end");
      if (turn_end_helper()) {
        return;
      }
    }
  }
  /**
   *Send the orders to client
   *@return void
   *@param orders is the player's order 
   */  
  public void send_orders(Orders orders) {
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(orders);
      oos.flush();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }


  /**
   *close the client 
   *@return void
   *@throws IOException
   */
  public void close_client() throws IOException {
    socket.close();
  }
}

