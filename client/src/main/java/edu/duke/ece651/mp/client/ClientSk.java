package edu.duke.ece651.mp.client;
import edu.duke.ece651.mp.common.*;
import javafx.application.Platform;
import javafx.util.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

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
  public AbstractActionFactory Action;
  public ArrayList<Player> players;

  public HashMap<String, ArrayList<Territory>> old_view_map;
  public HashMap<String, ArrayList<Territory>> new_view_map;

  private String leftBottomBoard;

  public LinkedBlockingQueue<Pair<String, String>> user_password;
  public LoginController loginController;

  public LinkedBlockingQueue<Triplet<String,String,String>> user_pwd1_pwd2;
  public RegisterController registerController;

  public LinkedBlockingQueue<Pair<String, String>> initQueue;
  public LinkedBlockingQueue<String> terriNameQueue;
  public LinkedBlockingQueue<String> actionQueue;
  public LinkedBlockingQueue<Quartet<String, String, String, String>> moveQueue;
  public LinkedBlockingQueue<Quartet<String, String, String, String>> attackQueue;
  public LinkedBlockingQueue<Quartet<String, String, String, String>> changeQueue;
  public Quartet<Map3Controller, Map31Controller, Map32Controller, Map33Controller> map3ControllerList;
  public Map3Controller map3Controller;
  public Map31Controller map31Controller;
  public Map32Controller map32Controller;
  public Map33Controller map33Controller;
  public int signal;
  public int currRoomNum;

  public void setLoginController(LoginController loginController){
    this.loginController = loginController;
  }

  public void setRegisterController(RegisterController registerController) {
    this.registerController = registerController;
  }

  public void setMap3Controller(Map3Controller map3Controller) {
    this.map3Controller = map3Controller;
    this.map3ControllerList = new Quartet<>(map3Controller,null,null,null);
    signal = 1;
    System.out.println("$$$$$" +
            "$$$$$$$in client setMap3");
    System.out.println(map3ControllerList.getValue0());
  }

  public void setMap31Controller(Map31Controller map31Controller) {
    this.map31Controller = map31Controller;
    this.map3ControllerList = new Quartet<>(null,map31Controller,null,null);
    signal = 2;
    System.out.println("$$$$$" +
            "$$$$$$$in client setMap31");
  }

  public void setMap32Controller(Map32Controller map32Controller) {
    this.map32Controller = map32Controller;
    this.map3ControllerList = new Quartet<>(null,null,map32Controller,null);
    signal = 3;
    System.out.println("$$$$$" +
            "$$$$$$$in client setMap32");
    System.out.println(System.identityHashCode(map32Controller));
  }

  public void setMap33Controller(Map33Controller map33Controller) {
    this.map33Controller = map33Controller;
    this.map3ControllerList = new Quartet<>(null,null,null,map33Controller);
     signal = 4;
    System.out.println("$$$$$" +
            "$$$$$$$in client setMap33");
  }


  /**
   * connect to the local host on 9999 port
   * map and toDisplay need to wait for info from server to initialize
   *
   * @throws IOException
   */
  public ClientSk(String Hostname, String port, BufferedReader inputSource, PrintStream outSource)
          throws IOException {
    socket = new Socket(Hostname, Integer.parseInt(port));
    this.map = null;
    this.inputReader = inputSource;
    this.out = outSource;
    this.Action = new V2Action();
    this.players = new ArrayList<Player>();
    this.user_password = new LinkedBlockingQueue<>();
    this.user_pwd1_pwd2 = new LinkedBlockingQueue<>();
    this.initQueue = new LinkedBlockingQueue<>();
    this.actionQueue = new LinkedBlockingQueue<>();
    this.moveQueue = new LinkedBlockingQueue<>();
    this.attackQueue = new LinkedBlockingQueue<>();
    this.changeQueue  = new LinkedBlockingQueue<>();
    this.leftBottomBoard = "";
    this.map3ControllerList = new Quartet<>(null,null,null,null);
    this.currRoomNum = 1;
  }





  public boolean check_username(String toCheck) {
    for (int i = 0; i < toCheck.length(); i++) {
      if (!Character.isLetterOrDigit(toCheck.charAt(i))){
        return false;
      }
    }
    return true;
  }

  public <E> void canBeTaken(LinkedBlockingQueue<E> lbq) {
    while (true) {
      if (lbq.size() != 0) {
        return;
      }
    }
  }

  public void do_register() throws IOException, ClassNotFoundException, InterruptedException {

    String temp = null;
    System.out.println("in do register");
    Triplet<String, String, String> temp_tuple = null;
    while (registerController.register == null) {
      System.out.print("");
    }
    System.out.println("");
    if (registerController.register.equals("Need register")) {
      send_string("Need register");
      while (true) {
        canBeTaken(user_pwd1_pwd2);
        System.out.println("lbq size: " + user_pwd1_pwd2.size());
        temp_tuple = user_pwd1_pwd2.take();
        String username = temp_tuple.getValue0();
        System.out.println("username : " + username);
        if (!check_username(username)) {
          Platform.runLater(() -> {
            registerController.getInvalid_label().setText("Username can only consist of numbers or letters! Please enter again");
            registerController.clearall();
          });
          continue;
        }
        send_string(username);
        temp = accept_string();
        if (!temp.equals("Username valid")) {
          String finalTemp = temp;
          Platform.runLater(() -> {
            registerController.getInvalid_label().setText(finalTemp);
            registerController.clearall();
          });
          continue;
        }
        break;
      }
      while (true) {
        String pwd1 = null;
        String pwd2 = null;
        pwd1 = temp_tuple.getValue1();
        pwd2 = temp_tuple.getValue2();
        System.out.println("flag = true");
        System.out.println("pwd1 : " + pwd1);
        System.out.println("pwd2 : " + pwd2);

        if (!pwd1.equals(pwd2)) {
          Platform.runLater(() -> {
            registerController.getInvalid_label().setText("Two passwords you enter are not the same");
            registerController.clearField();
          });
          canBeTaken(user_pwd1_pwd2);
          temp_tuple = user_pwd1_pwd2.take();
          continue;
        }
        send_string(pwd1);
        Platform.runLater(() -> {
          try {
            registerController.switchToLogin();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
        break;
      }
    } else {
      send_string("No need register");
      System.out.println("&&&&&&&&&In no need register");
      Platform.runLater(() -> {
        try {
          System.out.println("before switch to login");
          registerController.switchToLogin();
          System.out.println("after switch to login");
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }

  }

  public void do_login() throws IOException, ClassNotFoundException, InterruptedException {
    System.out.println("In client : " + System.identityHashCode(this));
    System.out.println("before take");
    System.out.println("In cleint, I am client queue " + user_password + " " + System.identityHashCode(user_password));
    System.out.println("In cleint, I am controller queue " + loginController.linkedBlockingQueue + " " +
            System.identityHashCode(loginController.linkedBlockingQueue));
    System.out.println("In client, I am controller " + System.identityHashCode(loginController));

    System.out.println("after take");

    String res = null;
    Pair<String, String> temp_pair = null;
    while (true) {
      canBeTaken(user_password);
      temp_pair = user_password.take();
      send_string(temp_pair.getKey());
      res = accept_string();
      if (!res.equals("Username valid")) {
        String finalRes = res;
        Platform.runLater(()->{
          loginController.getInvalid_label().setText(finalRes);
        });
        continue;
      }
      break;
    }
    while (true) {
      send_string(temp_pair.getValue());
      res = accept_string();
      if (!res.equals("Password valid")) {
        String finalRes = res;
        Platform.runLater(()->{
          loginController.getInvalid_label().setText(finalRes);
        });
        canBeTaken(user_password);
        temp_pair = user_password.take();
        continue;
      }

      Platform.runLater(() -> {
        try {
          loginController.switchToMap3();
          while (true) {
            if (map3ControllerList.getValue0() != null) {
              break;
            } else if (map3ControllerList.getValue1() != null){
              break;
            } else if (map3ControllerList.getValue2() != null){
              break;
            } else if (map3ControllerList.getValue3() != null){
              break;
            } else {
              //System.out.println("");
            }
          }
          System.out.println("In login@@@@@@@@@@@@@@@@@@@@after switch to map");
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      break;
    }
  }

  //TODO: choose room
  public void choose_room() throws IOException {
    String ans = read_string("Please enter number of players");
    //for test
    send_string("3");
  }

  /**
   * Read a map object from the socket and display it
   *
   * @return A String that shows the info of the whole map
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void game_begin() throws IOException, ClassNotFoundException {
    Thread th = new Thread(() -> {
      try {

        if (Client.ClientSkList.size() == 1) {
          send_string("First");
          if (Client.ClientSkList.size() == 1) {
            while (Client.ClientSkList.get(0).registerController == null) {
              System.out.print("");
            }
          }
          System.out.println("");
          System.out.println("after loop");
          do_register();
          if (Client.ClientSkList.size() == 1) {
            while (Client.ClientSkList.get(0).loginController == null) {
              System.out.print("");
            }
          }
          System.out.println("");
          do_login();
        } else {
          send_string("notFirst");
        }

        if(Client.ClientSkList.size() != 1) {
          System.out.println("clientSklist.size() != 1 : before choose room");
        }
        choose_room();
        //game begin
        if(Client.ClientSkList.size() != 1) {
          System.out.println("clientSklist.size() != 1 : before accept map");
        }

        System.out.println("$$$$$$$$$$$");
        System.out.println(map3ControllerList);
        System.out.println("@@@@@@@@@@@");


        System.out.println("before accept map");
        String map_show1 = new String(accept_map());
        System.out.println("before accept color");
        accept_color();
        if(Client.ClientSkList.size() != 1) {
          System.out.println("clientSklist.size() != 1 : before accept units");
        }
        System.out.println("before accpet units");

        accept_units();
        System.out.println("after accept units");
        set_player();
        System.out.println("after set player");
        init_unit();
        System.out.println(this.player.toString());
        send_player();
        String map_show2 = new String(accept_map());
        set_player();
        initViewMap();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(new_view_map.get("viewed").get(4).getName() + " level 0 units is " +
                new_view_map.get("viewed").get(4).countLevelUnit(0));
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        if (signal == 1) {
          map3ControllerList.getValue0().updateColor();
        } else if (signal == 2) {
          map3ControllerList.getValue1().updateColor();
        } else if(signal == 3) {
          map3ControllerList.getValue2().updateColor();
        } else {
          map3ControllerList.getValue3().updateColor();
        }
        out.print(map_show2);
        do_turns();

      } catch (Exception e) {
        e.printStackTrace();
      }
    });
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
    Platform.runLater(()->{
      if (signal == 1) {
        map3ControllerList.getValue0().getColor().setText(color);
      } else if (signal == 2) {
        map3ControllerList.getValue1().getColor().setText(color);
      } else if(signal == 3) {
        System.out.println("             " +
                "&&&&&&&&&&&&&&&&" + Client.ClientSkList.size() +
                "             " +
                " &&&&&&&&&&&");
        map3ControllerList.getValue2().getColor().setText(color);
      } else {
        map3ControllerList.getValue3().getColor().setText(color);
      }
    });
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
    out.println("You have " + num_units + " level 0 units totally");
    out.println("Please place your units on each territory");
    writeLeftBottomBoard("You have " + num_units + " level 0 units totally. " +
              "Please place your units on each territory by using the right bottom board.\n");
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

  public void writeLeftBottomBoard(String content) {
    leftBottomBoard = content + leftBottomBoard;
    Platform.runLater(()->{
      if (signal == 1) {
        map3ControllerList.getValue0().getLeftBottomBoard().setWrapText(true);
        map3ControllerList.getValue0().getLeftBottomBoard().setText(leftBottomBoard);
      } else if (signal == 2) {
        map3ControllerList.getValue1().getLeftBottomBoard().setWrapText(true);
        map3ControllerList.getValue1().getLeftBottomBoard().setText(leftBottomBoard);
      } else if(signal == 3) {
        map3ControllerList.getValue2().getLeftBottomBoard().setWrapText(true);
        map3ControllerList.getValue2().getLeftBottomBoard().setText(leftBottomBoard);
      } else {
        map3ControllerList.getValue3().getLeftBottomBoard().setWrapText(true);
        map3ControllerList.getValue3().getLeftBottomBoard().setText(leftBottomBoard);
      }
    });
  }
   /**

   *Prompt the player to input the unit and then init the unit for each playe   *r's Territory
   *@return void
   */  
  public void init_unit() throws InterruptedException {
    int limit = num_units;
    int count = 0;
    Pair<String, String> res = null;
    for (Territory t : player.player_terri_set) {
      if (count == player.player_terri_set.size() - 1) {
        t.setBasicUnit(limit);
        if (signal == 1) {
          map3ControllerList.getValue0().third_text.setText(String.valueOf(limit));
          map3ControllerList.getValue0().territory3.setText(t.getName());
          writeLeftBottomBoard("Remaining " + limit + " units are placed in " + t.getName() + "\n");
          map3ControllerList.getValue0().init_units_anchorpane.setVisible(false);
          map3ControllerList.getValue0().init_units_anchorpane.setDisable(true);
        } else if (signal == 2) {
          map3ControllerList.getValue1().third_text.setText(String.valueOf(limit));
          map3ControllerList.getValue1().territory3.setText(t.getName());
          writeLeftBottomBoard("Remaining " + limit + " units are placed in " + t.getName() + "\n");
          map3ControllerList.getValue1().init_units_anchorpane.setVisible(false);
          map3ControllerList.getValue1().init_units_anchorpane.setDisable(true);
        } else if(signal == 3) {
          map3ControllerList.getValue2().third_text.setText(String.valueOf(limit));
          map3ControllerList.getValue2().territory3.setText(t.getName());
          writeLeftBottomBoard("Remaining " + limit + " units are placed in " + t.getName() + "\n");
          map3ControllerList.getValue2().init_units_anchorpane.setVisible(false);
          map3ControllerList.getValue2().init_units_anchorpane.setDisable(true);
        } else {
          map3ControllerList.getValue3().third_text.setText(String.valueOf(limit));
          map3ControllerList.getValue3().territory3.setText(t.getName());
          writeLeftBottomBoard("Remaining " + limit + " units are placed in " + t.getName() + "\n");
          map3ControllerList.getValue3().init_units_anchorpane.setVisible(false);
          map3ControllerList.getValue3().init_units_anchorpane.setDisable(true);
        }
        return;
      }
      writeLeftBottomBoard("How many units do you want to place in " + t.getName() + "?" +
              " Please enter a integer in the blank.\n");
      if (count == 0) {
        Platform.runLater(()->{

          if (signal == 1) {
            map3ControllerList.getValue0().territory1.setText(t.getName());
          } else if (signal == 2) {
            map3ControllerList.getValue1().territory1.setText(t.getName());
          } else if(signal == 3) {
            map3ControllerList.getValue2().territory1.setText(t.getName());
          } else {
            map3ControllerList.getValue3().territory1.setText(t.getName());
          }



        });
        canBeTaken(initQueue);
        res = initQueue.take();
      } else if (count == 1) {

        if (signal == 1) {
          map3ControllerList.getValue0().territory2.setText(t.getName());
        } else if (signal == 2) {
          map3ControllerList.getValue1().territory2.setText(t.getName());
        } else if(signal == 3) {
          map3ControllerList.getValue2().territory2.setText(t.getName());
        } else {
          map3ControllerList.getValue3().territory2.setText(t.getName());
        }

      } else {
        if (signal == 1) {
          map3ControllerList.getValue0().territory3.setText(t.getName());
        } else if (signal == 2) {
          map3ControllerList.getValue1().territory3.setText(t.getName());
        } else if(signal == 3) {
          map3ControllerList.getValue2().territory3.setText(t.getName());
        } else {
          map3ControllerList.getValue3().territory3.setText(t.getName());
        }
      }
      while (true) {

        try {
          int temp = 0;
          if (count == 0) {
            temp = try_readLine(limit, res.getKey());
          } else {
            temp = try_readLine(limit, res.getValue());
          }
          limit -= temp;
          t.setBasicUnit(temp);
          break;
        } catch (IllegalArgumentException | IOException e) {
          out.println(e.getMessage());
          //writeLeftBottomBoard(e.getMessage() + "\n");
          canBeTaken(initQueue);
          res = initQueue.take();
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
  public int try_readLine(int limit, String num) throws IOException, InterruptedException {

    for (int i = 0; i < num.length(); i++) {
      if (!Character.isDigit(num.charAt(i))) {
        throw new IllegalArgumentException("Please enter a valid integer!");
      }
    }
    int in = Integer.parseInt(num);
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
    if (Integer.parseInt(toCheck) < 0 || Integer.parseInt(toCheck) > 9) {
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
  public String parse_check_add(String action, Orders orders, ArrayList<Player> players) throws IOException, InterruptedException {

    writeLeftBottomBoard("Now is " + action + "\n");
    canBeTaken(moveQueue);
    Quartet<String, String, String, String> ans = moveQueue.take();

    System.out.println("In parse_and_check, after moveQueue().take *****************************");
    String s1 = null;
    String s2 = null;
    String s3 = null;
    String s4 = null;

    if (action.equals("C")) {
      int temp_size = 0;
      if (signal == 1) {
        temp_size = map3ControllerList.getValue0().TerriList.size();
      } else if (signal == 2) {
        temp_size = map3ControllerList.getValue1().TerriList.size();
      } else if(signal == 3) {
        temp_size = map3ControllerList.getValue2().TerriList.size();
      } else {
        temp_size = map3ControllerList.getValue3().TerriList.size();
      }
      if(temp_size == 0){
        return "You should choose a territory to upgrade your units!\n";
      }

      if (signal == 1) {
        s1 = map3ControllerList.getValue0().TerriList.get(0);
      } else if (signal == 2) {
        s1 = map3ControllerList.getValue1().TerriList.get(0);
      } else if(signal == 3) {
        s1 = map3ControllerList.getValue2().TerriList.get(0);
      } else {
        s1 = map3ControllerList.getValue3().TerriList.get(0);
      }

      s2 = ans.getValue3();
      s3 = ans.getValue0();
      s4 = ans.getValue1();
      System.out.println("$$$$$$$$$$$$$$$$$In update unit ");
      System.out.println("To level is + " + s4);

      //s1: territoryName, s2: numToUp, s3:currLevel, s4:afterLevel
      if(!check_num(s2)) {
        return "Please enter a valid integer as numToUp!\n";
      }
      if(check_num(s3)) {
        if (!check_num_level(s3)) {
          return "Unit level should within 0 - 6\n";
        }
      } else {
        return "Please enter a valid integer as currLevel!\n";
      }
      if(check_num(s4)) {
        if (!check_num_level(s4)) {
          return "Unit level should within 0 - 6\n";
        }

      } else {
        return "Please enter a valid integer as afterLevel!\n";
      }
    } else {


      int size = 0;
      if (signal == 1) {
        size = map3ControllerList.getValue0().TerriList.size();
      } else if (signal == 2) {
        size = map3ControllerList.getValue1().TerriList.size();
      } else if(signal == 3) {
        size = map3ControllerList.getValue2().TerriList.size();
      } else {
        size = map3ControllerList.getValue3().TerriList.size();
      }


      if (size < 2) {
        return "You should choose src territory and des territory for a move or attack order!\n";
      }

      if (signal == 1) {
        s1 = map3ControllerList.getValue0().TerriList.get(0);
        s2 = map3ControllerList.getValue0().TerriList.get(1);
      } else if (signal == 2) {
        s1 = map3ControllerList.getValue1().TerriList.get(0);
        s2 = map3ControllerList.getValue1().TerriList.get(1);
      } else if(signal == 3) {
        s1 = map3ControllerList.getValue2().TerriList.get(0);
        s2 = map3ControllerList.getValue2().TerriList.get(1);
      } else {
        s1 = map3ControllerList.getValue3().TerriList.get(0);
        s2 = map3ControllerList.getValue3().TerriList.get(1);
      }

      s3 = ans.getValue3();
      s4 = ans.getValue2();
      //s1:sourceTerritoryName destinationTerritoryName numUnitsToDestination unitLevel
      if(!check_num(s3)) {
        return "Please enter a valid integer as numUnitsToDestination!\n";
      }
      if(check_num(s4)) {
        if (!check_num_level(s4)) {
          return "Unit level should within 0 - 6\n";
        }
      }
      else {
        return "Please enter a valid integer as unitLevel!\n";
      }
    }

    if (signal == 1) {
      map3ControllerList.getValue0().TerriList.clear();
    } else if (signal == 2) {
      map3ControllerList.getValue1().TerriList.clear();
    } else if(signal == 3) {
      map3ControllerList.getValue2().TerriList.clear();
    } else {
      map3ControllerList.getValue3().TerriList.clear();
    }

    if (action.equals("MS")) {
      System.out.println("In action.equals(MS) ##########################################");
      String temp = Action.checkForSpyMove(player, players, s1, s2);
      if (temp != null) {

        return temp;
      }
      else {
        Action.spyMove(player, players, s1, s2);

        System.out.println("adter Action.spyMove()$$$$$$$$$$$$$$$$$$$$$$$$$");

        if (signal == 1) {
          map3ControllerList.getValue0().tempUpdateInfo();
        } else if (signal == 2) {
          map3ControllerList.getValue1().tempUpdateInfo();
        } else if(signal == 3) {
          map3ControllerList.getValue2().tempUpdateInfo();
        } else {
          map3ControllerList.getValue3().tempUpdateInfo();
        }

        //new a order class, add it to orders moveList
        Order moveS = new Order(player, s1, s2, Integer.parseInt(s3), Integer.parseInt(s4), Integer.parseInt(s4));
        moveS.moveSpy = true;
        System.out.println("new order move spy %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        orders.MoveUpList.add(moveS);
      }
    }
    else if (action.equals("M")) {
      String temp = Action.checkForMove(player, s1, s2, Integer.parseInt(s3), Integer.parseInt(s4));
      System.out.println("#######Erro message is "+ temp);
      if (temp != null) {
        return temp;
      }
      else {
        Action.Move(player, s1, s2, Integer.parseInt(s3), Integer.parseInt(s4));

        if (signal == 1) {
          map3ControllerList.getValue0().tempUpdateInfo();
        } else if (signal == 2) {
          map3ControllerList.getValue1().tempUpdateInfo();
        } else if(signal == 3) {
          map3ControllerList.getValue2().tempUpdateInfo();
        } else {
          map3ControllerList.getValue3().tempUpdateInfo();
        }


        //new a order class, add it to orders moveList
        orders.MoveUpList.add(new Order(player, s1, s2, Integer.parseInt(s3), Integer.parseInt(s4), Integer.parseInt(s4)));
      }
    }
    else if (action.equals("A")) {
      String temp = Action.checkForAttack(player, s1, s2, Integer.parseInt(s3), players, Integer.parseInt(s4));
      if (temp != null) {
        return temp;
      }
      else {
        Boolean res = player.getTerritory(s1).loseUnits(Integer.parseInt(s3), Integer.parseInt(s4));

        if (res == false) {
          return player.color + " player has invalid attack orders. " +
                  "The numUnits of level " + s4 + "is insufficient.\n";
        }
        if (signal == 1) {
          map3ControllerList.getValue0().tempUpdateInfo();
        } else if (signal == 2) {
          map3ControllerList.getValue1().tempUpdateInfo();
        } else if(signal == 3) {
          map3ControllerList.getValue2().tempUpdateInfo();
        } else {
          map3ControllerList.getValue3().tempUpdateInfo();
        }
        //new a order class, add it to orders attackList

        orders.AttackList.add(new Order(player, s1, s2, Integer.parseInt(s3), Integer.parseInt(s4), Integer.parseInt(s4)));
      }
    }
    else {
      //Change 7=Bomber, 8=Fat Nerd, 9=Spy
      String temp = Action.checkForUpgrade(player, s1, Integer.parseInt(s2), Integer.parseInt(s3), Integer.parseInt(s4));
      if (temp != null) {
        return temp;
      }
      else {
        Action.unitUpgrade(player, s1, Integer.parseInt(s2), Integer.parseInt(s3), Integer.parseInt(s4));
        if (signal == 1) {
          map3ControllerList.getValue0().tempUpdateInfo();
        } else if (signal == 2) {
          map3ControllerList.getValue1().tempUpdateInfo();
        } else if(signal == 3) {
          map3ControllerList.getValue2().tempUpdateInfo();
        } else {
          map3ControllerList.getValue3().tempUpdateInfo();
        }
        //new a order class, add it to orders attackList
        orders.MoveUpList.add(new Order(player, s1, " ", Integer.parseInt(s2), Integer.parseInt(s3), Integer.parseInt(s4)));
      }
    }
    return null;
  }

  /**
   *Check the format of the player's input action  
   *@return void
   *@param s is the player's input of the action
   */
  /*
  public void check_action(String s) {
    if (s.length() != 1) {
      throw new IllegalArgumentException("Please enter one of the the first capital letter of action");
    }
    if (s.charAt(0) != 'M' && s.charAt(0) != 'A' && s.charAt(0) != 'D' && s.charAt(0) != 'U' && s.charAt(0) != 'C') {
      throw new IllegalArgumentException("Please enter one of the the first capital letter of action");
    }
  }


   */
  /**
   *collect one order of the player in one turn
   *@return the action of the player
   *@param orders is the player's orders
   *@throws IOException
   */
  public String collect_one_order(Orders orders, ArrayList<Boolean> if_up) throws IOException, InterruptedException {
    //out.println("You are the " + color + " player, what would you like to do?");
    //out.print("(M)ove\n(A)ttack\n(U)pgrade total tech level\n(C)hange unit level\n(D)one\nPlease enter the first capital letter\n");
    writeLeftBottomBoard("Attention: please do one or none U and some M, A, C, then ends with Done.\n");
    String action;
    while (true) {
      try {
        canBeTaken(actionQueue);
        action = actionQueue.take();
        System.out.println("%%%%%%%%%%%%%%%%%  after actionQueue.take() &&&&&&&&&&&&& action is ");
        break;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
      } catch (InterruptedException e) {
        e.printStackTrace();
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
            System.out.println("^^^^^^^^^^action is " + action + " ^^^^^^^^^^^^^before enter parse_and_check");
            res = parse_check_add(action, orders, players);
          }
          else {
            if (!if_up.get(0)) {
              if (player.getTechLevel() < 6) {
                try {
                  player.upgradeTechLevel();
                  if_up.set(0, true);
                  orders.MoveUpList.add(new Order(player, " ", " ", 0, 0, 0));

                  if (signal == 1) {
                    map3ControllerList.getValue0().tempUpdateInfo1();
                  } else if (signal == 2) {
                    map3ControllerList.getValue1().tempUpdateInfo1();
                  } else if(signal == 3) {
                    map3ControllerList.getValue2().tempUpdateInfo1();
                  } else {
                    map3ControllerList.getValue3().tempUpdateInfo1();
                  }

                } catch(IllegalArgumentException e) {
                  writeLeftBottomBoard(e.getMessage());
                }
              }
              else {
                writeLeftBottomBoard("You have reached top total tech level!\n");
              }
            } else{
              res = "You can only update total level once in one turn!\n";
              writeLeftBottomBoard(res);
              String temp_action = collect_one_order(orders, if_up);
              if (!temp_action.equals("U")) {
                return temp_action;
              } else {
                temp_action = collect_one_order(orders, if_up);
              }
            }
          }
          if (res != null) {
            writeLeftBottomBoard(res);
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
  public void if_end_one_turn() throws IOException, ClassNotFoundException, InterruptedException {
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
  public void collect_orders_and_send() throws IOException, InterruptedException {
    Orders orders = new Orders();
    String temp = new String();
    ArrayList<Boolean> if_up = new ArrayList<>();
    if_up.add(false);
    do {
      temp = collect_one_order(orders, if_up);
      System.out.println("temp action is " + temp);
    } while (!temp.equals("D"));
    send_orders(orders);
  }

  /**
   *handle the situation that the player lose, if player lose, they will ente   *r the input to indicate them to watch the game or disconnect 
   *@return the player's input string  
   *@throws IOException
   */  
  public String handle_lose() throws IOException {
    System.out.println("I am " + player.color + " player!");
    out.println("You lose! Do you want to keep watching game going? Please enter y or n. y means yes, n means no.");
    String if_keep_watch = null;
    System.out.println("Signal is " + signal);
    System.out.println("Map3ControlerList : " + map3ControllerList);
    while(if_keep_watch == null) {
      System.out.println("");
      if (signal == 1) {
        if_keep_watch = map3ControllerList.getValue0().if_keep_watch;
      } else if (signal == 2) {
        if_keep_watch = map3ControllerList.getValue1().if_keep_watch;
      } else if(signal == 3) {
        if_keep_watch = map3ControllerList.getValue2().if_keep_watch;
      } else {
        if_keep_watch = map3ControllerList.getValue3().if_keep_watch;
      }
    }
    while (true) {
      String s = if_keep_watch;
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
      System.out.println("$$$$$$$$$$$$$\n" +
              "$$$$$$$$$$$$$\n" +
              "$$$$$$$$$$$$\n" +
              "$$$$$$$$$$$\n");
      if (signal == 1) {
        map3ControllerList.getValue0().result_text.setDisable(false);
        map3ControllerList.getValue0().result_text.setVisible(true);
        map3ControllerList.getValue0().result_text.setText(res);
      } else if (signal == 2) {
        map3ControllerList.getValue1().result_text.setDisable(false);
        map3ControllerList.getValue1().result_text.setVisible(true);
        map3ControllerList.getValue1().result_text.setText(res);
      } else if(signal == 3) {
        map3ControllerList.getValue2().result_text.setDisable(false);
        map3ControllerList.getValue2().result_text.setVisible(true);
        map3ControllerList.getValue2().result_text.setText(res);
      } else {
        map3ControllerList.getValue3().result_text.setDisable(false);
        map3ControllerList.getValue3().result_text.setVisible(true);
        map3ControllerList.getValue3().result_text.setText(res);
      }

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
  public void do_turns_as_watch() throws IOException, ClassNotFoundException, InterruptedException {
    while (true) {
      Orders new_orders = new Orders();
      send_orders(new_orders);
      if_end_one_turn();
      String map_show = new String(accept_map());
      System.out.println(
              "&&&&&&&&&&&&&In do tunrs as watch"
      );
      out.print(map_show);
      set_player();
      updateNewViewMap();
      if (signal == 1) {
        map3ControllerList.getValue0().updateColorAsWatch();
        map3ControllerList.getValue0().updatePlayerInfo();
      } else if (signal == 2) {
        map3ControllerList.getValue1().updateColorAsWatch();
        map3ControllerList.getValue1().updatePlayerInfo();
      } else if(signal == 3) {
        map3ControllerList.getValue2().updateColorAsWatch();
        map3ControllerList.getValue2().updatePlayerInfo();
      } else {
        map3ControllerList.getValue3().updateColorAsWatch();
        map3ControllerList.getValue3().updatePlayerInfo();
      }

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
  public void do_turns() throws IOException, ClassNotFoundException, InterruptedException {
    String s = null;
    while (true) {
      collect_orders_and_send();
      if_end_one_turn();
      String map_show = new String(accept_map());
      out.print(map_show);
      set_player();
      updateNewViewMap();

      if (signal == 1) {
        map3ControllerList.getValue0().updateColor();
        map3ControllerList.getValue0().updatePlayerInfo();
      } else if (signal == 2) {
        map3ControllerList.getValue1().updateColor();
        map3ControllerList.getValue1().updatePlayerInfo();
      } else if(signal == 3) {
        map3ControllerList.getValue2().updateColor();
        map3ControllerList.getValue2().updatePlayerInfo();
      } else {
        map3ControllerList.getValue3().updateColor();
        map3ControllerList.getValue3().updatePlayerInfo();
      }

      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      String res = (String)ois.readObject();
      if (res.equals("Lose")) {
        if (is_last_loser()) {
          writeLeftBottomBoard("You lose!\n");
          s = "y";
        }
        else {

          if (signal == 1) {
            map3ControllerList.getValue0().end_game1();
          } else if (signal == 2) {
            map3ControllerList.getValue1().end_game1();
          } else if(signal == 3) {
            map3ControllerList.getValue2().end_game1();
          } else {
            map3ControllerList.getValue3().end_game1();
          }

          s = handle_lose();
          System.out.println("after handle lose + +++++++++");
        }
        if (s.equals("y")) {
          //send_string("y");
          if (turn_end_helper()) {
            return;
          }
          System.out.println("before do turn as watch + " + "if_watch = " + s);
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

  /**
   * initViewMap
   */
  public void initViewMap(){
    this.old_view_map = viewMapInitializer();
    this.new_view_map = viewMapInitializer();
  }

  /**
   * viewMapInitializer() is called by the Player's constructor, and it can help the constructor to initialize old_view_map, new_view_map.
   *@param player_terri_set is the latest information of the teritories owned by the player.
   *@return HashMap<String, ArrayList<Territory>> curr_new_map.
   */
  public HashMap<String, ArrayList<Territory>> viewMapInitializer(){
    ArrayList<Territory> cur_viewed_list = viewMapHelper();
    ArrayList<Territory> cur_grey_list = new ArrayList<Territory>();
    // Prepare to return
    HashMap<String, ArrayList<Territory>> cur_new_map = new HashMap<String, ArrayList<Territory>>();
    cur_new_map.put("viewed", cur_viewed_list);
    cur_new_map.put("grey", cur_grey_list);
    return cur_new_map;
  }

  private HashSet<String> getNameList(ArrayList<Territory> list){
    HashSet<String> temp_name = new HashSet<String>();
    for(Territory t : list){
      temp_name.add(t.getName());
    }
    return temp_name;
  }

  /**
   * updateNewViewMap() is used when the new round starts. This method would be called by the Client, and then help the Client to update new_view_map by comparing the old_view_map which is from the last round.
   *@return No return. This method will update HashMap<String, ArrayList<Territory>> new_view_map with cur_view_map.
   */
  public void updateNewViewMap(){
    ArrayList<Territory> old_viewed_list = this.new_view_map.get("viewed");
    ArrayList<Territory> old_grey_list = this.new_view_map.get("grey");

    ArrayList<Territory> cur_viewed_list = viewMapHelper();

    ArrayList<Territory> cur_grey_list = new ArrayList<Territory>();
    HashSet<String> cur_viewed_name = getNameList(cur_viewed_list);
    HashSet<String> old_viewed_name = getNameList(old_viewed_list);

    // Logical part
    for(Territory t : old_grey_list){
      cur_grey_list.add(t.deep_copy());
    }
    //Start Comparing and Updating!
    for(String s : cur_viewed_name){
      if(!old_viewed_name.contains(s)){
        // remove the territory(whose name is s) from cur_grey!
        int index = 0;
        boolean flag = false;
        // If there is no grey territory in the previous greylist.
        if(cur_grey_list.size() == 0){
          break;
        }
        for(;index < cur_grey_list.size(); index++){
          Territory t = cur_grey_list.get(index);
          if(t.getName().equals(s)){
            flag = true;
            break;
          }
        }
        System.out.println(cur_grey_list.size() + "$$$$$$$$$$$$ cur_grey_list.size()");
        System.out.println(index + "$$$$$$$$$$$$$$$index");

        if (flag == true) {
          cur_grey_list.remove(index);
        }
      }
    }

    for(String s : old_viewed_name){
      System.out.println(s);
      if(!cur_viewed_name.contains(s)){
        // Move the territory(whose name is s) from old_viewed to cur_grey!
        int index = 0;
        for(; index < old_viewed_list.size(); index++){
          Territory t = old_viewed_list.get(index);
          if(t.getName().equals(s)){
            break;
          }
        }
        Territory t = old_viewed_list.get(index).deep_copy();
        cur_grey_list.add(t);

      }
    }
    HashMap<String, ArrayList<Territory>> cur_new_map = new HashMap<String, ArrayList<Territory>>();
    System.out.println("cur_viewed_list.size()" + cur_viewed_list.size());
    System.out.println("cur_grey_list.size()" + cur_grey_list.size());
    cur_new_map.put("viewed", cur_viewed_list);
    cur_new_map.put("grey", cur_grey_list);
    this.new_view_map = cur_new_map;

    HashMap<String, ArrayList<Territory>> cur_old_map = new HashMap<String, ArrayList<Territory>>();
    System.out.println("old_viewed_list.size()" + old_viewed_list.size());
    System.out.println("old_grey_list.size()" + old_grey_list.size());
    cur_old_map.put("viewed", old_viewed_list);
    cur_old_map.put("grey", old_grey_list);
    this.old_view_map = cur_old_map;
  }

  /**
   *viewMapHelper() is called by viewMapInitializer() and updateViewMap(). This method would deep copy the information from the last territories, and then traverse whole the neighbors from those territories. Finally, Add those neighbors into the return ArrayList.
   *@param  player_terri_set is the latest information of the teritories owned by the player.
   *@return ArrayList<Territory> cur_viewed_list back to viewMapInitializer() or updateViewMap().
   */
  private ArrayList<Territory> viewMapHelper(){
    ArrayList<Territory> cur_viewed_list = new ArrayList<Territory>();
    HashSet<String> cur_viewed_name = new HashSet<String>();

    // 1. Adding the Territories that the player owns
    for(Territory t : player.player_terri_set){
      cur_viewed_list.add(t);
      cur_viewed_name.add(t.getName());
      System.out.println("---------------------origin--------------");
      System.out.println(t.getName() + " level 0 units is " + t.countLevelUnit(0));
    }

    // Adding the Neighbors of those Territories
    // 2. Deep copy
    ArrayList<Territory> current_viewed_territory_copy = new ArrayList<Territory>();
    for(Territory t : cur_viewed_list){
      Territory copy = t.deep_copy();
      current_viewed_territory_copy.add(copy);

    }

    // 3. Traverse Neighbors now
    for(Territory terri : player.player_terri_set){
      for(Territory neigh : terri.getNeigh()){
        // current_viewed_territory doesn't have the neigh territory
        if(!cur_viewed_name.contains(neigh.getName())){

          Territory copy = findTerritory(neigh.getName()).deep_copy();

          cur_viewed_list.add(copy);
          System.out.println("--------------copy neigh--------------------");
          System.out.println(neigh.getName() + " level 0 units is " + copy.countLevelUnit(0));
          cur_viewed_name.add(neigh.getName());
        }
      }
    }

    //4. Add where spy at
    for (Player p : players){
      for (Territory t : p.player_terri_set) {
        if (t.countSpy(player.color) > 0 && !cur_viewed_name.contains(t.getName())) {
          Territory copy = findTerritory(t.getName()).deep_copy();
          cur_viewed_list.add(copy);
          cur_viewed_name.add(t.getName());
        }
      }
    }


    return cur_viewed_list;
  }


  public Territory findTerritory(String name){
    for (Player p : players) {
      for (Territory t : p.player_terri_set) {
        if (t.getName().equals(name)) {
          System.out.println("before copy");
          System.out.println(t.getName() + " level 0 units is " + t.countLevelUnit(0));
          return t;
        }
      }
    }
    return null;
  }


}

