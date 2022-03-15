package edu.duke.ece651.mp.server;
import edu.duke.ece651.mp.common.*;
import org.checkerframework.checker.units.qual.A;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * The high-level controlling of game, such as
 * ServerSk manipulate the serverSocket to accept clients,
 * control all the maps/roooms ,
 * create a new thread to handle every room game processing,
 * assign player territory set
 */
public class ServerSk {
  private ServerSocket serverSocket;
  private ArrayList<GameMap> rooms;

  /**
   * build a listening socket on port 9999, init all the rooms
   * @param rooms, all the rooms preparing for game starting
   * @throws IOException
   */
  public ServerSk(ArrayList<GameMap> rooms) throws IOException {
    this.serverSocket = new ServerSocket(9999);
    this.rooms = rooms;
  }

  /**
   * build thread to process each room
   * @throws IOException
   */
  public void build_server() throws IOException {
    //choose a map as a room
    for (int i = 0; i < rooms.size(); i++) {
      process(rooms.get(i));
    }
  }
  /**
   * a thread method to process each room game behavior
   * @param map
   */

  /**
   * a thread method to process each room game behavior
   * @param map
   */
  public void process(GameMap map) {

    new Thread(new Runnable() {
      @Override
      public void run() {

        ArrayList<Socket> socket_list = accept_server(map);
        try {
          send_map_to_all(socket_list, map);
          send_color(socket_list, map.get_player_list());
          send_num_units(socket_list, map.get_num_units());
          accept_player(socket_list, map.get_player_list());
          send_map_to_all(socket_list, map);
        } catch (IOException | ClassNotFoundException ex) {
          ex.printStackTrace();
        }
        /*
        finally {
          try {
            close_all_sk(socket_list);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

         */

      }
    }).start();


  }



  /**
   * accept players in the room and store their socket in a list
   * @param map
   * @return
   */
  public ArrayList<Socket> accept_server(GameMap map) {

    ArrayList<Socket> ans_list = new ArrayList<Socket>();
    for (int i = 0; i < map.get_num_players(); i++) {
      try {
        Socket temp_socket = serverSocket.accept();
        //error handle, same player cannot enter one room more than once
        System.out.println("Accept client " + i + " successfully");
        ans_list.add(temp_socket);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return ans_list;
  }

  /**
   * send room map to all of them
   * @param socket_list
   * @param map
   * @throws IOException
   */
  public void send_map_to_all(ArrayList<Socket> socket_list, GameMap map) throws IOException {
    ObjectOutputStream oos = null;
    for (Socket s : socket_list) {
      try {
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(map);
        oos.flush();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      /*finally {
        try {
          //oos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }*/
    }
  }

  public void send_color(ArrayList<Socket> socket_list, ArrayList<Player> player_list) throws IOException {

    ObjectOutputStream oos = null;
    int i = 0;
    for (Socket s : socket_list) {
      try {
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(player_list.get(i).color);
        oos.flush();
        i++;
      } catch (IOException ex) {
        ex.printStackTrace();
      }

    }
  }

  public void send_num_units(ArrayList<Socket> socket_list, int num_units) throws IOException {
    ObjectOutputStream oos = null;
    for (Socket s : socket_list) {
      try {
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(num_units);
        oos.flush();
      } catch (IOException ex) {
        ex.printStackTrace();
      }

    }
  }

  public void accept_player(ArrayList<Socket> socket_list, ArrayList<Player> players) throws IOException, ClassNotFoundException {
    players.clear();
    for (Socket s : socket_list) {
      ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
      Player temp = (Player) ois.readObject();
      players.add(temp);
    }
  }

  /**
   * close all the client socket in the room after game ends
   * @param socket_list
   * @throws IOException
   */
  public void close_all_sk(ArrayList<Socket> socket_list) throws IOException {
    for (Socket s : socket_list) {
      s.close();
    }
  }
}
