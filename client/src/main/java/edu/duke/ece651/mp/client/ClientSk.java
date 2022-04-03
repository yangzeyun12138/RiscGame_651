package edu.duke.ece651.mp.client;
import com.google.common.annotations.VisibleForTesting;
import edu.duke.ece651.mp.common.*;

import javax.swing.*;
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
    this.Action = new V2Action();
    this.players = new ArrayList<Player>();
  }


  public boolean check_username(String toCheck) {
    for (int i = 0; i < toCheck.length(); i++) {
      if (!Character.isLetterOrDigit(toCheck.charAt(i))){
        return false;
      }
    }
    return true;
  }

  public void do_register() throws IOException, ClassNotFoundException {
    String ans1 = null;
    String ans2 = null;
    ans1 = read_string("register? Please enter y or n");
    if (ans1.equals("y")) {
      send_string("Need register");
      while (true) {
        ans1 = read_string("Please enter username: (username can only consist of numbers or letters)");
        if (!check_username(ans1)) {
          out.println("Username can only consist of numbers or letters! Please enter again");
          continue;
        }
        send_string(ans1);
        ans1 = accept_string();
        if (!ans1.equals("Username valid")) {
          out.println(ans1);
          continue;
        }
        break;
      }
      while (true) {
        ans1 = read_string("Please enter password");
        ans2 = read_string("Please enter password again");
        if (!ans1.equals(ans2)) {
          continue;
        }
        send_string(ans1);
        out.println("register success");
        break;
      }
    }
    else{
      send_string("No need register");
    }
  }

  public void do_login() throws IOException, ClassNotFoundException {
    String ans1 = null;
    while (true) {
      ans1 = read_string("Please enter username: ");
      send_string(ans1);
      ans1 = accept_string();
      if (!ans1.equals("Username valid")) {
        out.println(ans1);
        continue;
      }
      break;
    }
    while (true) {
      ans1 = read_string("Please enter password:");
      send_string(ans1);
      ans1 = accept_string();
      if (!ans1.equals("Password valid")) {
        out.println(ans1);
        continue;
      }
      out.println("login success");
      break;
    }
  }

  public void choose_room() throws IOException {
    String ans = read_string("Please enter number of players");
    send_string(ans);
  }

  /**
   * Read a map object from the socket and display it
   *
   * @return A String that shows the info of the whole map
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void game_begin() throws IOException, ClassNotFoundException {
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          String ans1 = null;
          String ans2 = null;
          //register
          do_register();
          //login
          do_login();
          //choose room
          choose_room();
          //game begin
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
        } catch (Exception e) {
        }
      }
    };
    th.start();
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
    out.println("you have " + num_units + " level 0 units totally");
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

  public void send_string(String toSend) {
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(toSend);
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
  public String read_string(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return s;
  }

  public boolean check_num(String toCheck) {
    for (int i = 0; i < toCheck.length(); i++) {
      if (!Character.isDigit(toCheck.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public boolean check_num_level(String toCheck) {
    if (Integer.parseInt(toCheck) < 0 || Integer.parseInt(toCheck) > 6) {
      return false;
    }
    return true;
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
  public String parse_check_add(String action, Orders orders, ArrayList<Player> players, Player temp_player) throws IOException {
    String hint = null;
    if (action.equals("C")) {
      hint = "Please enter your order as following format\nterritoryName numToUp currLevel afterLevel";
    } else {
      hint = "Please enter your order as following format\nsourceTerritoryName destinationTerritoryName "  +
            "numUnitsToDestination unitLevel";
    }
    String order = read_string(hint);
    int index1 = order.indexOf(" ");
    if (index1 == -1) {
      return new String(hint);
    }
    int index2 = order.indexOf(" ", index1 + 1);
    if (index2 == -1) {
      return new String(hint);
    }
    int index3 = order.indexOf(" ", index2 + 1);
    if (index3 == -1) {
      return new String(hint);
    }
    int index4 = order.indexOf(" ", index3 + 1);
    if (index4 != -1) {
      return new String(hint);
    }
    String s1 = order.substring(0, index1);
    String s2 = order.substring(index1 + 1, index2);
    String s3 = order.substring(index2 + 1, index3);
    String s4 = order.substring(index3 + 1);

    if (action.equals("C")) {
      //s1: territoryName, s2: numToUp, s3:currLevel, s4:afterLevel
      if(!check_num(s2)) {
        return "Please enter a valid integer as numToUp!";
      }
      if(check_num(s3)) {
        if (!check_num_level(s3)) {
          return "Unit level should within 0 - 6";
        }
      } else {
        return "Please enter a valid integer as currLevel!";
      }
      if(check_num(s4)) {
        if (!check_num_level(s4)) {
          return "Unit level should within 0 - 6";
        }

      } else {
        return "Please enter a valid integer as afterLevel!";
      }
    } else {
      //s1:sourceTerritoryName destinationTerritoryName numUnitsToDestination unitLevel
      if(!check_num(s3)) {
        return "Please enter a valid integer as numUnitsToDestination!";
      }
      if(check_num(s4)) {
        if (!check_num_level(s4)) {
          return "Unit level should within 0 - 6";
        }
      }
      else {
        return "Please enter a valid integer as unitLevel!";
      }
    }

    if (action.equals("M")) {
      String temp = Action.checkForMove(temp_player, s1, s2, Integer.parseInt(s3), Integer.parseInt(s4));
      if (temp != null) {
        return temp;
      }
      else {
        Action.Move(temp_player, s1, s2, Integer.parseInt(s3), Integer.parseInt(s4));
        //new a order class, add it to orders moveList
        orders.MoveUpList.add(new Order(temp_player, s1, s2, Integer.parseInt(s3), Integer.parseInt(s4), Integer.parseInt(s4)));
      }
    }
    else if (action.equals("A")) {
      String temp = Action.checkForAttack(temp_player, s1, s2, Integer.parseInt(s3), players, Integer.parseInt(s4));
      if (temp != null) {
        return temp;
      }
      else {
        Boolean res = temp_player.getTerritory(s1).loseUnits(Integer.parseInt(s3), Integer.parseInt(s4));
        if (res == false) {
          return temp_player.color + " player has invalid attack orders. " +
                  "The numUnits of level " + s4 + "is insufficient.\n";
        }
        //new a order class, add it to orders attackList
        orders.AttackList.add(new Order(temp_player, s1, s2, Integer.parseInt(s3), Integer.parseInt(s4), Integer.parseInt(s4)));
      }
    }
    else {
      //Change
      String temp = Action.checkForUpgrade(temp_player, s1, Integer.parseInt(s2), Integer.parseInt(s3), Integer.parseInt(s4));
      if (temp != null) {
        return temp;
      }
      else {
        Action.unitUpgrade(temp_player, s1, Integer.parseInt(s2), Integer.parseInt(s3), Integer.parseInt(s4));
        //new a order class, add it to orders attackList
        orders.MoveUpList.add(new Order(temp_player, s1, " ", Integer.parseInt(s2), Integer.parseInt(s3), Integer.parseInt(s4)));
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
    if (s.charAt(0) != 'M' && s.charAt(0) != 'A' && s.charAt(0) != 'D' && s.charAt(0) != 'U' && s.charAt(0) != 'C') {
      throw new IllegalArgumentException("Please enter one of the the first capital letter of action");
    }
  }

  /**
   *collect one order of the player in one turn
   *@return the action of the player
   *@param orders is the player's orders
   *@throws IOException
   */
  public String collect_one_order(Orders orders, ArrayList<Boolean> if_up, Player temp_player) throws IOException {
    out.println("You are the " + color + " player, what would you like to do?");
    out.print("(M)ove\n(A)ttack\n(U)pgrade total tech level\n(C)hange unit level\n(D)one\nPlease enter the first capital letter\n");
    out.println("Attention: please do one or none U and some M, A, C, then ends with Done.");
    String action;
    while (true) {
      try {
        action = inputReader.readLine();
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
          if (!action.equals("U")) {
            res = parse_check_add(action, orders, players, temp_player);
          }
          else {
            if (!if_up.get(0)) {
              if (temp_player.getTechLevel() < 6) {
                try {
                  temp_player.upgradeTechLevel();
                  if_up.set(0, true);
                  orders.MoveUpList.add(new Order(temp_player, " ", " ", 0, 0, 0));
                  out.println("Upgrade total tech level success! Now is " + temp_player.getTechLevel());
                } catch(IllegalArgumentException e) {
                  out.print(e.getMessage());
                }
              }
              else {
                out.println("You have reached top total tech level!");
              }
            } else{
              res = "You can only update total level once in one turn!";
              out.println(res);
              String temp_action = collect_one_order(orders, if_up, temp_player);
              if (!temp_action.equals("U")) {
                return temp_action;
              } else {
                temp_action = collect_one_order(orders, if_up, temp_player);
              }
            }
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
    ArrayList<Boolean> if_up = new ArrayList<>();
    if_up.add(false);
    Player temp_player = this.player.deep_copy();
    do {
      temp = collect_one_order(orders, if_up, temp_player);
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

