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
  private Socket socket;
  private MapDisplay toDisplay;
  private String color;
  private int num_units;
  public Player player;
  final BufferedReader inputReader;
  final PrintStream out;
  private AbstractActionFactory Action;

  /**
   * connect to the local host on 9999 port
   * map and toDisplay need to wait for info from server to initialize
   *
   * @throws IOException
   */
  public ClientSk(BufferedReader inputSource, PrintStream outSource) throws IOException {
    socket = new Socket("0.0.0.0", 9999);
    this.map = null;
    this.inputReader = inputSource;
    this.out = outSource;
    this.Action = new V1Action();
  }


  /**
   * Read a map object from the socket and display it
   *
   * @return A String that shows the info of the whole map
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public String accept_map() throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    map = (BasicMap) ois.readObject();
    toDisplay = new BasicMapDisplay(map);
    return toDisplay.display();
  }

  public void accept_color() throws IOException, ClassNotFoundException {
    /*
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.color = in.readLine();

     */
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    this.color = (String) ois.readObject();
    out.print("You are " + color + " player, ");
  }

  public void accept_units() throws IOException, ClassNotFoundException {
    /*
    DataInputStream dis = new DataInputStream(socket.getInputStream());
    this.num_units = dis.readInt();
     */
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    this.num_units = (int) ois.readObject();
    out.println("you have " + num_units + " units totally");
    out.println("Please place your units on each territory");
  }

  public void set_player() {
    for (Player p : map.get_player_list()) {
      if (this.color.equals(p.color)) {
        this.player = p;
      }
    }
  }

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

  public String read_string(String promt) throws IOException {
    out.print(promt);
    String s = inputReader.readLine();
    return s;
  }


  public String parse_check_add(String action, Orders orders) throws IOException {
    String order = read_string("Please enter your order as following format\nsourceTerritoryName destinationTerritoryName " +
            "numUnitsToDestination\n");
    String src = new String();
    String des = new String();
    int index1 = order.indexOf(" ");
    src = order.substring(0, index1);
    int index2 = order.indexOf(" ", index1 + 1);
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
      String temp = Action.checkForAttack(player, src, des, num_move);
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

  public void check_action(String s) {

    if (s.length() != 1) {
      throw new IllegalArgumentException("Please enter one of the the first capital letter of action");
    }
    if (s.charAt(0) != 'M' && s.charAt(0) != 'A' && s.charAt(0) != 'D') {
      throw new IllegalArgumentException("Please enter one of the the first capital letter of action");
    }
  }

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

            res = parse_check_add(action, orders);
          } else {
            res = parse_check_add(action, orders);
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

  public void collect_orders_and_send() throws IOException {
    Orders orders = new Orders();
    String temp = new String();
    do {
      temp = collect_one_order(orders);
    } while (!temp.equals("D"));
    send_orders(orders);
  }

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

  public void close_client() throws IOException {
    socket.close();
  }
}

