package edu.duke.ece651.mp.server;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import edu.duke.ece651.mp.common.*;
import edu.duke.ece651.mp.client.*;

import javax.print.attribute.standard.Severity;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

class ServerSkTest {

  public ClientSk build_client1() throws IOException, ClassNotFoundException {
    BufferedReader inData= new BufferedReader(new StringReader("10\n10\nM\nGondor Mordor 1\nD\n" +
            "D\n" +
            "D\n" +
            "D\n"));
    ClientSk clientSk = new ClientSk("0.0.0.0", "9999", inData, System.out);
    return clientSk;
  }

  public ClientSk build_client2() throws IOException, ClassNotFoundException {
    BufferedReader inData= new BufferedReader(new StringReader("10\n10\nM\nNarnia Oz 1\nD\n" +
            "D\ny\n"));
    ClientSk clientSk = new ClientSk("0.0.0.0", "9999", inData, System.out);
    return clientSk;
  }

  public ClientSk build_client3() throws IOException, ClassNotFoundException {
    BufferedReader inData= new BufferedReader(new StringReader("10\n10\nM\nElantris Scadrial 1\nD\n" +
            "A\nScadrial Oz 500\nA\nScadrial Midkemia 100\nA\nElantris Narnia 100\nA\nScadrial Mordor 100\n" +
            "A\nScadrial Hogwarts 100\nD\n" +
            "M\nRoshar Elantris 1000\nM\nRoshar Scadrial 1000\nD\nD\n" +
            "A\nOz Gondor 100\nD\n"));
    ClientSk clientSk = new ClientSk("0.0.0.0", "9999", inData, System.out);
    return clientSk;
  }



  @Test
  public void server_test() throws IOException, ClassNotFoundException, InterruptedException {
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          ArrayList<GameMap> maps = new ArrayList<>();
          Server server = new Server();
          Parse parse =new Parse();
          GameMap gm = server.create_one_map("Territory3.txt", 3, parse);
          maps.add(gm);
          ArrayList<String> lines = parse.readfile("userInfo.txt");
          ServerSk serverSk = new ServerSk(maps, 9999, lines);
          serverSk.build_server();
        } catch (Exception e) {
        }
      }
    };
    th.start();
    Thread.sleep(100);
    ClientSk clientSk1 = build_client1();
    ClientSk clientSk2 = build_client2();
    ClientSk clientSk3 = build_client3();

    clientSk1.accept_map();
    clientSk1.accept_color();
    clientSk1.accept_units();
    clientSk1.set_player();
    clientSk1.init_unit();
    for (Territory t : clientSk1.player.player_terri_set) {
      t.setBasicUnit(1);
    }
    clientSk1.send_player();

    clientSk2.accept_map();
    clientSk2.accept_color();
    clientSk2.accept_units();
    clientSk2.set_player();
    clientSk2.init_unit();
    for (Territory t : clientSk2.player.player_terri_set) {
      t.setBasicUnit(1);
    }
    clientSk2.send_player();

    clientSk3.accept_map();
    clientSk3.accept_color();
    clientSk3.accept_units();
    clientSk3.set_player();
    clientSk3.init_unit();
    for (Territory t : clientSk3.player.player_terri_set) {
      t.setBasicUnit(1000);
    }
    clientSk3.send_player();

    clientSk1.accept_map();
    int numOfMordor1 = 0;
    for (Territory t : clientSk1.player.player_terri_set) {
      if (t.getName().equals("Mordor")) {
        numOfMordor1 = t.countUnit();
      }
    }
    clientSk1.collect_orders_and_send();


    clientSk2.accept_map();
    int numOfOz1 = 0;
    for (Territory t : clientSk2.player.player_terri_set) {
      if (t.getName().equals("Oz")) {
        numOfOz1 = t.countUnit();
      }
    }
    clientSk2.collect_orders_and_send();

    clientSk3.accept_map();
    int numOfScadrial1 = 0;
    for (Territory t : clientSk3.player.player_terri_set) {
      if (t.getName().equals("Scadrial")) {
        numOfScadrial1 = t.countUnit();
      }
    }
    clientSk3.collect_orders_and_send();

    assertEquals("Total success", clientSk1.accept_string());
    clientSk1.accept_map();
    clientSk1.set_player();
    int numOfMordor2 = 0;
    for (Territory t : clientSk1.player.player_terri_set) {
      if (t.getName().equals("Mordor")) {
        numOfMordor2 = t.countUnit();
      }
    }
    assertEquals(numOfMordor1 + 2, numOfMordor2 );
    assertEquals("Red", clientSk1.player.color);
    assertEquals("notLose", clientSk1.accept_string());
    assertEquals("noBodyWin", clientSk1.accept_string());

    assertEquals("Total success", clientSk2.accept_string());
    clientSk2.accept_map();
    clientSk2.set_player();
    int numOfOz2 = 0;
    for (Territory t : clientSk2.player.player_terri_set) {
      if (t.getName().equals("Oz")) {
        numOfOz2 = t.countUnit();
      }
    }
    assertEquals("Green", clientSk2.player.color);
    assertEquals(numOfOz1 + 2, numOfOz2);
    assertEquals("notLose", clientSk2.accept_string());
    assertEquals("noBodyWin", clientSk2.accept_string());

    assertEquals("Total success", clientSk3.accept_string());
    clientSk3.accept_map();
    clientSk3.set_player();
    int numOfScadrial2 = 0;
    for (Territory t : clientSk3.player.player_terri_set) {
      if (t.getName().equals("Scadrial")) {
        numOfScadrial2 = t.countUnit();
      }
    }
    assertEquals(numOfScadrial1 + 2, numOfScadrial2);
    assertEquals("Blue", clientSk3.player.color);
    assertEquals("notLose", clientSk3.accept_string());
    assertEquals("noBodyWin", clientSk3.accept_string());

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //second turn
    clientSk1.collect_orders_and_send();
    clientSk2.collect_orders_and_send();
    clientSk3.collect_orders_and_send();

    clientSk1.if_end_one_turn();
    clientSk1.accept_map();
    clientSk1.set_player();
    assertEquals("notLose", clientSk1.accept_string());
    assertEquals("noBodyWin", clientSk1.accept_string());

    clientSk2.if_end_one_turn();
    clientSk2.accept_map();
    clientSk2.set_player();
    assertEquals("Lose", clientSk2.accept_string());
    assertEquals("y", clientSk2.handle_lose());
    assertEquals("noBodyWin", clientSk2.accept_string());

    clientSk3.if_end_one_turn();
    clientSk3.accept_map();
    clientSk3.set_player();
    assertEquals("notLose", clientSk3.accept_string());
    assertEquals("noBodyWin", clientSk3.accept_string());
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //third turn
    clientSk1.collect_orders_and_send();
    Orders new_orders = new Orders();
    clientSk2.send_orders(new_orders);
    clientSk3.collect_orders_and_send();

    assertEquals("Success", clientSk1.accept_string());
    assertEquals("Success", clientSk2.accept_string());
    clientSk3.accept_string();
    clientSk3.collect_orders_and_send();


    assertEquals("Total success", clientSk1.accept_string());
    clientSk1.accept_map();
    clientSk1.set_player();
    assertEquals("notLose", clientSk1.accept_string());
    assertEquals("noBodyWin", clientSk1.accept_string());


    assertEquals("Total success", clientSk2.accept_string());
    clientSk2.accept_map();
    clientSk2.set_player();
    assertEquals("Lose", clientSk2.accept_string());
    assertEquals("noBodyWin", clientSk2.accept_string());


    assertEquals("Total success", clientSk3.accept_string());
    clientSk3.accept_map();
    clientSk3.set_player();
    assertEquals("notLose", clientSk3.accept_string());
    assertEquals("noBodyWin", clientSk3.accept_string());
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //fourth turn
    clientSk1.collect_orders_and_send();
    clientSk2.send_orders(new_orders);
    clientSk3.collect_orders_and_send();

    assertEquals("Total success", clientSk1.accept_string());
    clientSk1.accept_map();
    clientSk1.set_player();
    assertEquals("Lose", clientSk1.accept_string());
    assertEquals("Blue player win!", clientSk1.accept_string());


    assertEquals("Total success", clientSk2.accept_string());
    clientSk2.accept_map();
    clientSk2.set_player();
    assertEquals("Lose", clientSk2.accept_string());
    assertEquals("Blue player win!", clientSk2.accept_string());


    assertEquals("Total success", clientSk3.accept_string());
    clientSk3.accept_map();
    clientSk3.set_player();
    assertEquals("notLose", clientSk3.accept_string());
    assertEquals("Blue player win!", clientSk3.accept_string());

    clientSk1.close_client();
    clientSk2.close_client();
    clientSk3.close_client();
    th.interrupt();
    th.join();
  }

  public ClientSk build_client11() throws IOException, ClassNotFoundException {
    BufferedReader inData= new BufferedReader(new StringReader("10\n10\nM\nGondor Mordor 1\nD\n" +
            "D\n" +
            "D\n" +
            "D\n"));
    ClientSk clientSk = new ClientSk("0.0.0.0", "9998", inData, System.out);
    return clientSk;
  }

  public ClientSk build_client22() throws IOException, ClassNotFoundException {
    BufferedReader inData= new BufferedReader(new StringReader("10\n10\nM\nNarnia Oz 1\nD\n" +
            "D\nn\n"));
    ClientSk clientSk = new ClientSk("0.0.0.0", "9998", inData, System.out);
    return clientSk;
  }

  public ClientSk build_client33() throws IOException, ClassNotFoundException {
    BufferedReader inData= new BufferedReader(new StringReader("10\n10\nM\nElantris Scadrial 1\nD\n" +
            "A\nScadrial Oz 500\nA\nScadrial Midkemia 100\nA\nElantris Narnia 100\nA\nScadrial Mordor 100\n" +
            "A\nScadrial Hogwarts 100\nD\n" +
            "M\nRoshar Elantris 1000\nM\nRoshar Scadrial 1000\nD\nD\n" +
            "A\nOz Gondor 100\nD\n"));
    ClientSk clientSk = new ClientSk("0.0.0.0", "9998", inData, System.out);
    return clientSk;
  }

  @Test
  public void server_test2() throws IOException, ClassNotFoundException, InterruptedException {
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          ArrayList<GameMap> maps = new ArrayList<>();
          Server server = new Server();
          Parse parse = new Parse();
          GameMap gm = server.create_one_map("Territory3.txt", 3, parse);
          maps.add(gm);
          ArrayList<String> lines = parse.readfile("userInfo.txt");
          ServerSk serverSk = new ServerSk(maps, 9998, lines);
          serverSk.build_server();
        } catch (Exception e) {
        }
      }
    };
    th.start();
    Thread.sleep(100);
    ClientSk clientSk1 = build_client11();
    ClientSk clientSk2 = build_client22();
    ClientSk clientSk3 = build_client33();

    clientSk1.accept_map();
    clientSk1.accept_color();
    clientSk1.accept_units();
    clientSk1.set_player();
    clientSk1.init_unit();
    for (Territory t : clientSk1.player.player_terri_set) {
      t.setBasicUnit(1);
    }
    clientSk1.send_player();

    clientSk2.accept_map();
    clientSk2.accept_color();
    clientSk2.accept_units();
    clientSk2.set_player();
    clientSk2.init_unit();
    for (Territory t : clientSk2.player.player_terri_set) {
      t.setBasicUnit(1);
    }
    clientSk2.send_player();

    clientSk3.accept_map();
    clientSk3.accept_color();
    clientSk3.accept_units();
    clientSk3.set_player();
    clientSk3.init_unit();
    for (Territory t : clientSk3.player.player_terri_set) {
      t.setBasicUnit(1000);
    }
    clientSk3.send_player();

    clientSk1.accept_map();
    int numOfMordor1 = 0;
    for (Territory t : clientSk1.player.player_terri_set) {
      if (t.getName().equals("Mordor")) {
        numOfMordor1 = t.countUnit();
      }
    }
    clientSk1.collect_orders_and_send();


    clientSk2.accept_map();
    int numOfOz1 = 0;
    for (Territory t : clientSk2.player.player_terri_set) {
      if (t.getName().equals("Oz")) {
        numOfOz1 = t.countUnit();
      }
    }
    clientSk2.collect_orders_and_send();

    clientSk3.accept_map();
    int numOfScadrial1 = 0;
    for (Territory t : clientSk3.player.player_terri_set) {
      if (t.getName().equals("Scadrial")) {
        numOfScadrial1 = t.countUnit();
      }
    }
    clientSk3.collect_orders_and_send();

    assertEquals("Total success", clientSk1.accept_string());
    clientSk1.accept_map();
    clientSk1.set_player();
    int numOfMordor2 = 0;
    for (Territory t : clientSk1.player.player_terri_set) {
      if (t.getName().equals("Mordor")) {
        numOfMordor2 = t.countUnit();
      }
    }
    assertEquals(numOfMordor1 + 2, numOfMordor2 );
    assertEquals("Red", clientSk1.player.color);
    assertEquals("notLose", clientSk1.accept_string());
    assertEquals("noBodyWin", clientSk1.accept_string());

    assertEquals("Total success", clientSk2.accept_string());
    clientSk2.accept_map();
    clientSk2.set_player();
    int numOfOz2 = 0;
    for (Territory t : clientSk2.player.player_terri_set) {
      if (t.getName().equals("Oz")) {
        numOfOz2 = t.countUnit();
      }
    }
    assertEquals("Green", clientSk2.player.color);
    assertEquals(numOfOz1 + 2, numOfOz2);
    assertEquals("notLose", clientSk2.accept_string());
    assertEquals("noBodyWin", clientSk2.accept_string());

    assertEquals("Total success", clientSk3.accept_string());
    clientSk3.accept_map();
    clientSk3.set_player();
    int numOfScadrial2 = 0;
    for (Territory t : clientSk3.player.player_terri_set) {
      if (t.getName().equals("Scadrial")) {
        numOfScadrial2 = t.countUnit();
      }
    }
    assertEquals(numOfScadrial1 + 2, numOfScadrial2);
    assertEquals("Blue", clientSk3.player.color);
    assertEquals("notLose", clientSk3.accept_string());
    assertEquals("noBodyWin", clientSk3.accept_string());

    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //second turn
    clientSk1.collect_orders_and_send();
    clientSk2.collect_orders_and_send();
    clientSk3.collect_orders_and_send();

    clientSk1.if_end_one_turn();
    clientSk1.accept_map();
    clientSk1.set_player();
    assertEquals("notLose", clientSk1.accept_string());
    assertFalse(clientSk1.turn_end_helper());

    clientSk2.if_end_one_turn();
    clientSk2.accept_map();
    clientSk2.set_player();
    assertEquals("Lose", clientSk2.accept_string());
    //assertEquals("noBodyWin", clientSk2.accept_string());
    assertEquals("n", clientSk2.handle_lose());
    clientSk2.close_client();

    clientSk3.if_end_one_turn();
    clientSk3.accept_map();
    clientSk3.set_player();
    assertEquals("notLose", clientSk3.accept_string());
    assertEquals("noBodyWin", clientSk3.accept_string());
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //third turn
    clientSk1.collect_orders_and_send();
    clientSk3.collect_orders_and_send();

    assertEquals("Success", clientSk1.accept_string());
    clientSk3.accept_string();
    clientSk3.collect_orders_and_send();


    assertEquals("Total success", clientSk1.accept_string());
    clientSk1.accept_map();
    clientSk1.set_player();
    assertEquals("notLose", clientSk1.accept_string());
    assertEquals("noBodyWin", clientSk1.accept_string());



    assertEquals("Total success", clientSk3.accept_string());
    clientSk3.accept_map();
    clientSk3.set_player();
    assertEquals("notLose", clientSk3.accept_string());
    assertEquals("noBodyWin", clientSk3.accept_string());
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //fourth turn
    clientSk1.collect_orders_and_send();
    clientSk3.collect_orders_and_send();

    assertEquals("Total success", clientSk1.accept_string());
    clientSk1.accept_map();
    clientSk1.set_player();
    assertEquals("Lose", clientSk1.accept_string());
    assertTrue(clientSk1.is_last_loser());
    assertEquals("Blue player win!", clientSk1.accept_string());


    assertEquals("Total success", clientSk3.accept_string());
    clientSk3.accept_map();
    clientSk3.set_player();
    assertEquals("notLose", clientSk3.accept_string());
    assertEquals("Blue player win!", clientSk3.accept_string());

    clientSk1.close_client();
    clientSk3.close_client();
    th.interrupt();
    th.join();
  }


}
