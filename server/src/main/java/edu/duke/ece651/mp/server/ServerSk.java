package edu.duke.ece651.mp.server;
import edu.duke.ece651.mp.common.*;
import org.checkerframework.checker.units.qual.A;

import javax.crypto.AEADBadTagException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
  private AbstractActionFactory Action;
  //might be a HashMap List if more than one room
  private ArrayList<HashMap<String, ArrayList<Order>>> AttackMap_list;
  //might be a ArrayList if more than one room
  private ArrayList<Integer> socket_len_list;
  private HashMap<String, String> userInfo;
  private Lock lock;
  private ArrayList<ArrayList<Socket>> socket2D;
  //private BufferedWriter bufferedWriter;

  /**
   * build a listening socket on port 9999, init all the rooms
   * @param rooms, all the rooms preparing for game starting
   * @throws IOException
   */
  public ServerSk(ArrayList<GameMap> rooms, int port, ArrayList<String> lines) throws IOException {
    this.serverSocket = new ServerSocket(port);
    this.rooms = rooms;
    this.Action = new V2Action();
    this.AttackMap_list = new ArrayList<>();
    this.socket_len_list = new ArrayList<>();
    for (int i = 0; i < rooms.size(); i++) {
      HashMap<String, ArrayList<Order>> temp = new HashMap<>();
      AttackMap_list.add(temp);
    }
    for (GameMap gm : rooms) {
      this.socket_len_list.add(gm.get_num_players());
    }
    this.userInfo = getUserInfo(lines);
    this.lock = new ReentrantLock();
    initSocket2D();
  }

  public void initSocket2D() {
    this.socket2D = new ArrayList<>();
    for (int i = 0; i < rooms.size(); i++) {
      ArrayList<Socket> temp = new ArrayList<>();
      socket2D.add(temp);
    }
  }

  public HashMap<String, String> getUserInfo(ArrayList<String> lines) {
    HashMap<String, String> userInfo = new HashMap<>();
    for (int i = 0; i< lines.size(); i++) {
      String[] separateLine = lines.get(i).split(":", 2);
      userInfo.put(separateLine[0], separateLine[1]);
    }
    return  userInfo;
  }

  public void build_server() throws InterruptedException {
    ArrayList<Boolean> flag = new ArrayList<Boolean>(Arrays.asList(false, false, false, false));
    int count = 0;

    for (GameMap room : rooms) {
      for (int j = 0; j < room.get_num_players(); j++) {
        accept_client();
        accept_client();
        count++;
      }
    }

    while (true) {
      for (int i = 0; i < rooms.size(); i++) {
        if (socket2D.get(i).size() == 3) {
          if (flag.get(i).equals(false)) {
            process(rooms.get(i), socket2D.get(i));
            System.out.println("start a game!");
            flag.set(i, true);
          }
        }
      }

      /*
      if(checkEnd() && (flag.get(0).equals(true) || flag.get(1).equals(true) ||
      flag.get(2).equals(true) || flag.get(3).equals(true))){
        break;
      }

       */
    }

  }

  public boolean checkEnd() {
    for (int i = 0; i < socket2D.size(); i++) {
      for (int j = 0; j < socket2D.get(i).size(); j++) {
        if (!socket2D.get(i).get(j).isClosed()){
          return false;
        }
      }
    }
    return true;
  }




  public void accept_client(){
    Thread th = new Thread(() -> {
      try {
        Socket temp_socket = serverSocket.accept();

        String res = accept_string(temp_socket);

        System.out.println("IP : " + temp_socket.getInetAddress());
        System.out.println("Post : " + temp_socket.getPort());
        //error handle, same player cannot enter one room more than once
        if (res.equals("First")) {
          handle_register(temp_socket);
          handle_login(temp_socket);
        }

        choose_room(temp_socket);

      } catch (Exception ex) {
        //ex.printStackTrace();
      }
    });
    th.start();
  }

  public void choose_room(Socket sk) throws IOException, ClassNotFoundException {
    String num_players = accept_string(sk);
    //socket2D.get(Integer.parseInt(num_players) - 2).add(sk);
    for (int i = 0; i < 4; i++) {
      if (socket2D.get(i).size() < 3) {
        socket2D.get(i).add(sk);
        break;
      }
    }
  }

  public void send_string(String toSend, Socket socket) {
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(toSend);
      oos.flush();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String accept_string(Socket socket) throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    return  (String) ois.readObject();
  }

  public void writeUserInfo(String userName, String passWord) throws IOException {
    String directory = System.getProperty("user.dir") + "/src/main/java/edu/duke/ece651/mp/";
    directory = directory + "userInfo.txt";
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(directory, true));
    String toWrite = userName + ":" + passWord + "\n";
    bufferedWriter.write(toWrite);
    bufferedWriter.close();
  }

  public void handle_register(Socket socket) throws IOException, ClassNotFoundException {
    String need_register = null;
    String userName = null;
    String passWord = null;
    need_register = accept_string(socket);
    if (need_register.equals("No need register")) {
      return;
    }
    while (true) {
      userName = accept_string(socket);
      if (userInfo.containsKey(userName)) {
        send_string("Username has already existed!", socket);
        continue;
      }
      else {
        send_string("Username valid", socket);
        break;
      }
    }
    passWord = accept_string(socket);
    userInfo.put(userName, passWord);
    if (lock.tryLock()) {
      try {
        writeUserInfo(userName, passWord);
      } catch (Exception ex){
      }
      finally {
        lock.unlock();
      }
    }
  }

  public void handle_login(Socket socket) throws IOException, ClassNotFoundException {
    String userName = null;
    String passWord = null;
    while (true) {
      userName = accept_string(socket);
      if (!userInfo.containsKey(userName)) {
        send_string("Username doesn't exist!", socket);
        continue;
      }
      else {
        send_string("Username valid", socket);
        break;
      }
    }
    while (true) {
      passWord = accept_string(socket);
      if (!passWord.equals(userInfo.get(userName))) {
        send_string("Password incorrect!", socket);
        continue;
      }
      else{
        send_string("Password valid", socket);
        break;
      }
    }
  }

  /*
  public void handle_all_register_login(ArrayList<Socket> socket_list) throws IOException, ClassNotFoundException, InterruptedException {
    ArrayList<Thread> thList = new ArrayList<>();
    for (Socket s: socket_list) {
      Thread th = new Thread() {
        @Override()
        public void run() {
          try {
            handle_register(s);
            handle_login(s);
          } catch (Exception e) {
          }
        }
      };
      thList.add(th);
      th.start();
    }
    for (Thread th : thList) {
      th.join();
    }
  }

   */

  /**
   * a thread method to process each room game behavior
   * @param map
   */

  /**
   * a thread method to process each room game behavior
   * @param map
   */
  public void process(GameMap map, ArrayList<Socket> socket_list)
          throws InterruptedException {

    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          send_map_to_all(socket_list, map);
          send_color(socket_list, map.get_player_list());
          send_num_units(socket_list, map.get_num_units(), map.get_player_list());
          accept_player(socket_list, map.get_player_list());
          send_map_to_all(socket_list, map);
          do_turns(socket_list, map);
        } catch (IOException | ClassNotFoundException ex) {
          ex.printStackTrace();
        }
      }
    };
    th.start();
  }





  /**
   * Whenever find a IOexception from the socket we enter this function
   * remove the client sercer from server list and then remove player from player list
   * @param players all the players
   * @param socket_list all the cleint socket
   * @param toDelete index need to remove from these two lists
   */
  public void handleDisconnection(ArrayList<Player> players, ArrayList<Socket> socket_list, ArrayList<Integer> toDelete) {
    ArrayList<Socket> temp_sk_list = new ArrayList<Socket>();
    ArrayList<Player> temp_players = new ArrayList<Player>();
    for (int i = 0; i < toDelete.size(); i++) {
      int index = toDelete.get(i);
      temp_players.add(players.get(index));
      temp_sk_list.add(socket_list.get(index));
      int tempIndex = players.size() - 2;
      socket_len_list.set(tempIndex, socket_len_list.get(tempIndex) - 1);
    }
    for (int i = 0; i< temp_sk_list.size(); i++) {
      socket_list.remove(temp_sk_list.get(i));
      players.add(temp_players.get(i));
      players.remove(temp_players.get(i));
    }
  }

  /**
   * send room map to all the cleints
   * @param socket_list
   * @param map
   * @throws IOException
   */
  public void send_map_to_all(ArrayList<Socket> socket_list, GameMap map) throws IOException {
    ObjectOutputStream oos = null;
    ArrayList<Integer> toDelete = new ArrayList<Integer>();
    for (int i = 0; i < socket_list.size(); i++) {
      Socket s = socket_list.get(i);
      try {
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(map);
        oos.flush();
      } catch (IOException ex) {
        toDelete.add(i);
      }
    }

    if (toDelete.size() != 0) {
      handleDisconnection(map.get_player_list(), socket_list, toDelete);
    }

  }

  /**
   * send color to according client/player
   * @param socket_list
   * @param player_list
   * @throws IOException
   */
  public void send_color(ArrayList<Socket> socket_list, ArrayList<Player> player_list) throws IOException {
    ObjectOutputStream oos = null;
    ArrayList<Integer> toDelete = new ArrayList<Integer>();
    for (int i = 0; i < socket_list.size(); i++) {
      Socket s = socket_list.get(i);
      Player p = player_list.get(i);
      try {
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(p.color);
        oos.flush();
      } catch (IOException ex) {
        toDelete.add(i);
      }
    }

    if (toDelete.size() != 0) {
      handleDisconnection(player_list, socket_list, toDelete);
    }
  }

  /**
   * send init total num_units to all the clients
   * @param socket_list
   * @param num_units
   * @param players
   * @throws IOException
   */
  public void send_num_units(ArrayList<Socket> socket_list, int num_units, ArrayList<Player> players) throws IOException {
    ObjectOutputStream oos = null;
    ArrayList<Integer> toDelete = new ArrayList<Integer>();
    for (int i = 0; i < socket_list.size(); i++) {
      Socket s = socket_list.get(i);
      try {
        oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(num_units);
        oos.flush();
      } catch (IOException ex) {
        toDelete.add(i);
      }
    }

    if (toDelete.size() != 0) {
      handleDisconnection(players, socket_list, toDelete);
    }
  }

  /**
   * accecpt the player object from all the cleints to do the initialization
   * @param socket_list
   * @param players
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public void accept_player(ArrayList<Socket> socket_list, ArrayList<Player> players) throws ClassNotFoundException, IOException {
    players.clear();
    for (Socket s : socket_list) {
      ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
      Player temp = (Player) ois.readObject();
      players.add(temp);
    }
  }


  /**
   * implement all the move orders
   * @param ordersList the objects including orders from all the clients
   * @param players
   */
  public void do_move_up(ArrayList<Orders> ordersList, ArrayList<Player> players)  {
    for (int i = 0; i < ordersList.size(); i++) {
      for (Order o : ordersList.get(i).MoveUpList) {
        if (o.getSrc().equals(" ")) {
          //upgrade total tech
          players.get(i).upgradeTechLevel();
        }
        else if (o.getDest().equals(" ")){
          //change unit type
          Action.checkForUpgrade(players.get(i), o.getSrc(), o.getNumUnit(), o.currLevel, o.afterLevel);
          Action.unitUpgrade(players.get(i), o.getSrc(), o.getNumUnit(), o.currLevel, o.afterLevel);
        }
        else {
          //move
          Action.checkForMove(players.get(i), o.getSrc(), o.getDest(), o.getNumUnit(), o.currLevel);

          Action.Move(players.get(i), o.getSrc(), o.getDest(), o.getNumUnit(), o.currLevel);
        }
      }
    }
  }

  /**
   * implement all the attack orders
   * @param players
   */
  public void do_attack(ArrayList<Player> players){
    int tempIndex = players.size() - 2;
    for (Map.Entry<String, ArrayList<Order>> entry : AttackMap_list.get(tempIndex).entrySet()) {
      ArrayList<Order> attackList = entry.getValue();
      String des = entry.getKey();
      Player defender = Action.getPlayer(des, players);
      ArrayList<Order> refineList = Action.refineAttack(attackList, players);
      ArrayList<Integer> randoms = Action.getRandomIdx(refineList.size());
      for (int i : randoms) {
        defender = Action.Attack(refineList.get(i).player, defender, refineList.get(i).getSrc(), des,
                refineList.get(i).unitList, players);
      }
    }
  }

  /**
   * accept all the orders from all the clients, implement them and check if someone has invalid orders, ask this player
   * to resend all orders for this turn
   * @param socket_list
   * @param players
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void do_one_turn(ArrayList<Socket> socket_list, ArrayList<Player> players) throws IOException, ClassNotFoundException{
    ArrayList<Integer> toDelete = new ArrayList<Integer>();
    ArrayList<Orders> ordersList = new ArrayList<Orders>();
    for (int i = 0; i < socket_list.size(); i++) {
      Socket s = socket_list.get(i);
      try {
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        Orders temp = (Orders) ois.readObject();
        ordersList.add(temp);
      } catch (IOException ex) {
        toDelete.add(i);
      }
    }
    if (toDelete.size() != 0) {
      handleDisconnection(players, socket_list, toDelete);
    }

    while (true) {
      try {
        ArrayList<Player> playersCopy = new ArrayList<Player>();
        for (int i = 0; i < players.size(); i++) {
          Player temp = players.get(i).deep_copy();
          playersCopy.add(temp);
        }
        do_move_up(ordersList, playersCopy);
        Action.loseAttackUnit(ordersList, playersCopy);
        int tempIndex = players.size() - 2;
        AttackMap_list.set(tempIndex, Action.arrangeAttackOrder(ordersList, playersCopy));
        do_attack(playersCopy);

        ObjectOutputStream oos = null;
        toDelete.clear();

        for (int i = 0; i < socket_list.size(); i++) {
          try {
            oos = new ObjectOutputStream(socket_list.get(i).getOutputStream());
            oos.writeObject("Total success");
            oos.flush();
          } catch (IOException ex) {
            toDelete.add(i);
          }
        }

        if (toDelete.size() != 0) {
          handleDisconnection(playersCopy, socket_list, toDelete);
        }

        players.clear();
        for (int i = 0; i < playersCopy.size(); i++) {
          players.add(playersCopy.get(i));
        }
        break;
      } catch (IllegalArgumentException e) {
        int space = e.getMessage().indexOf(" ");
        String temp_color = e.getMessage().substring(0, space);
        int skIndex = Action.getIndexFromPlayers(players, temp_color);
        Orders new_orders = getNewOrders(socket_list, skIndex, e.getMessage(), players);
        if (new_orders != null) {
          ordersList.set(skIndex, new_orders);
        }
        else {
          ordersList.remove(skIndex);
        }
      }
    }

    Action.Done(players);

  }

  /**
   * called by do_one_turn when find invalid orders, use this function new orders from this player
   * @param socket_list
   * @param skIndex the index where invalid orders happen
   * @param msg error message
   * @param players
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public Orders getNewOrders(ArrayList<Socket> socket_list, int skIndex, String msg, ArrayList<Player> players) throws IOException, ClassNotFoundException {
    ObjectOutputStream oos = null;
    ArrayList<Integer> toDelete = new ArrayList<Integer>();
    for (int i = 0; i < socket_list.size(); i++) {
      try {
        oos = new ObjectOutputStream(socket_list.get(i).getOutputStream());
        if (i == skIndex) {
          oos.writeObject(msg);
        } else {
          oos.writeObject("Success");
        }
        oos.flush();
      } catch (IOException ex) {
        toDelete.add(i);
      }
    }

    if (toDelete.size() != 0) {
      handleDisconnection(players, socket_list, toDelete);
    }

    Orders temp = new Orders();
    try {
      ObjectInputStream ois = new ObjectInputStream(socket_list.get(skIndex).getInputStream());
      temp = (Orders) ois.readObject();
    } catch (IOException ex) {
      Player temp_p = players.remove(skIndex);
      players.add(temp_p);
      socket_list.remove(skIndex);
      temp = null;

    }
    return temp;
  }

  /**
   * check if someone lose at the end of every turn, if lose ask them if they will keep watching or disconnect
   * @param players
   * @param socket_list
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void handlePotentialLose(ArrayList<Player> players, ArrayList<Socket> socket_list) throws IOException, ClassNotFoundException {
    ArrayList<Integer> toDelete = new ArrayList<Integer>();
    int tempIndex = players.size() - 2;
    for (int i = 0; i < socket_len_list.get(tempIndex); i++) {
      Player p = players.get(i);
      ObjectOutputStream oos = null;

      Socket s = socket_list.get(i);
      //send lose
      try {
        oos = new ObjectOutputStream(s.getOutputStream());
        if (p.checkLose()) {
          oos.writeObject("Lose");
        }
        else {
          oos.writeObject("notLose");
        }
        oos.flush();
      } catch (IOException ex) {
        toDelete.add(i);
      }
    }

    if (toDelete.size() != 0) {
      handleDisconnection(players, socket_list, toDelete);
    }

  }

  /**
   * check if someone win the game at the end of each turn and send the winning info to all the players
   * @param players
   * @param res
   * @param socket_list
   */
  public void handleWin(ArrayList<Player> players, String res, ArrayList<Socket> socket_list) {
    ArrayList<Integer> toDelete = new ArrayList<Integer>();
    for (int i = 0; i < socket_list.size(); i++) {
      ObjectOutputStream oos = null;
      try {
        oos = new ObjectOutputStream(socket_list.get(i).getOutputStream());
        oos.writeObject(res + " player win!");
        oos.flush();
      } catch (IOException ex) {
        toDelete.add(i);
      }
    }

    if (toDelete.size() != 0) {
      handleDisconnection(players, socket_list, toDelete);
    }

  }

  /**
   * game controller, do one turn first and then send result of this turn to all players.
   * check win or lose and handle them.
   * @param socket_list
   * @param map
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void do_turns(ArrayList<Socket> socket_list, GameMap map) throws IOException, ClassNotFoundException {
    ArrayList<Player> players = map.get_player_list();
    while (true) {
      do_one_turn(socket_list, map.get_player_list());
      send_map_to_all(socket_list, map);
      //checkLose
      handlePotentialLose(players, socket_list);
      //checkWin
      String res = Action.checkWin(players);
      if (res != null) {
        handleWin(map.get_player_list(), res, socket_list);
        int tempIndex = players.size() - 2;
        for (int i = 0; i < socket_len_list.get(tempIndex); i++) {
        }
        close_all_sk(socket_list);
        break;
      }
      else {
        ArrayList<Integer> toDelete = new ArrayList<Integer>();
        ObjectOutputStream oos = null;

        for (int i = 0; i < socket_list.size(); i++) {
          try {
            oos = new ObjectOutputStream(socket_list.get(i).getOutputStream());
            oos.writeObject("noBodyWin");
            oos.flush();
          } catch (IOException ex) {
            toDelete.add(i);
          }
        }

        if (toDelete.size() != 0) {
          handleDisconnection(players, socket_list, toDelete);
        }

      }
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
