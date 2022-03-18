package edu.duke.ece651.mp.server;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import edu.duke.ece651.mp.common.*;
import edu.duke.ece651.mp.client.*;

import javax.print.attribute.standard.Severity;

import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.HashSet;

class ServerSkTest {

  public ClientSk build_client1() throws IOException, ClassNotFoundException {
    BufferedReader inData= new BufferedReader(new StringReader("5\n28\n16\nM\nGondor Mordor 1\nD\n"));
    ClientSk clientSk = new ClientSk(inData, System.out);
    return clientSk;
  }

  public ClientSk build_client2() throws IOException, ClassNotFoundException {
    BufferedReader inData= new BufferedReader(new StringReader("5\n28\n16\nM\nNarnia Oz 1\nD\n"));
    ClientSk clientSk = new ClientSk(inData, System.out);
    return clientSk;
  }

  public ClientSk build_client3() throws IOException, ClassNotFoundException {
    BufferedReader inData= new BufferedReader(new StringReader("5\n28\n16\nM\nElantris Scadrial 1\nD\n"));
    ClientSk clientSk = new ClientSk(inData, System.out);
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
          GameMap gm = server.create_one_map("Territory3.txt", 3);
          maps.add(gm);
          ServerSk serverSk = new ServerSk(maps);
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
    clientSk1.send_player();
    clientSk2.accept_map();
    clientSk2.accept_color();
    clientSk2.accept_units();
    clientSk2.set_player();
    clientSk2.init_unit();
    clientSk2.send_player();
    clientSk3.accept_map();
    clientSk3.accept_color();
    clientSk3.accept_units();
    clientSk3.set_player();
    clientSk3.init_unit();
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
    assertEquals(30, clientSk1.map.get_num_units());
    clientSk2.accept_map();
    clientSk2.set_player();
    int numOfOz2 = 0;
    for (Territory t : clientSk2.player.player_terri_set) {
      if (t.getName().equals("Oz")) {
        numOfOz2 = t.countUnit();
      }
    }
    assertEquals("Green", clientSk2.player.color);
    assertEquals(30, clientSk2.map.get_num_units());
    assertEquals(numOfOz1 + 2, numOfOz2);
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
    assertEquals(30, clientSk3.map.get_num_units());

    th.interrupt();
    th.join();
  }

}
