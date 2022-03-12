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

  /**
   * connect to the local host on 9999 port
   * map and toDisplay need to wait for info from server to initialize
   * @throws IOException
   */
  public ClientSk(BufferedReader inputSource) throws IOException {
    socket = new Socket("0.0.0.0", 9999);
    this.map = null;
    this.inputReader = inputSource;
  }


  /**
   * Read a map object from the socket and display it
   * @return A String that shows the info of the whole map
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public String accept_map() throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    map = (BasicMap)ois.readObject();
    toDisplay = new BasicMapDisplay(map);
    return toDisplay.display();
  }

  public void accept_color() throws IOException, ClassNotFoundException {
    /*
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.color = in.readLine();

     */
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    this.color = (String)ois.readObject();
    System.out.print("You are " + color + " player, ");
  }

  public void accept_units() throws IOException, ClassNotFoundException {
    /*
    DataInputStream dis = new DataInputStream(socket.getInputStream());
    this.num_units = dis.readInt();
     */
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    this.num_units = (int)ois.readObject();
    System.out.println("you have " + num_units + " units totally");
    System.out.println("Please place your units on each territory");
  }

  public void set_player(){
    for (Player p : map.get_player_list()){
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
        //for test
        //System.out.println(toDisplay.display());
        return;
      }
      System.out.println("How many units do you want to place in " + t.getName() + "?");
      System.out.print("Please enter a integer: ");
      while (true) {
        try {
          int temp = try_readLine(limit);
          limit -= temp;
          t.setBasicUnit(temp);
          break;
        } catch (IllegalArgumentException | IOException e) {
          System.out.println(e.getMessage());
        }
      }
      count++;
    }
  }


  public int try_readLine(int limit) throws IOException {

    String s = inputReader.readLine();
    //BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    //String s = input.readLine();
    for (int i = 0; i < s.length(); i++){
      if (!Character.isDigit(s.charAt(i))){
        throw new IllegalArgumentException("Please enter a valid integer!");
      }
    }
    int in = Integer.parseInt(s);
    if (in >= 0 && in <= limit) {
      return in;
    }
    else {
      throw new IllegalArgumentException("Please enter an integer within the " + limit + "!");
    }
  }

  /**
   * close the socket connection
   * @throws IOException
   */

  public void send_player(){
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(player);
      oos.flush();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void close_client() throws IOException {
    socket.close();
  }
}

