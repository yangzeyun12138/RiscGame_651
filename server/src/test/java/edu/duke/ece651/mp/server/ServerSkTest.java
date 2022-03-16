package edu.duke.ece651.mp.server;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import edu.duke.ece651.mp.common.*;
import edu.duke.ece651.mp.client.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.HashSet;

class ServerSkTest {

  public ClientSk build_client() throws IOException, ClassNotFoundException {
    BufferedReader inData= new BufferedReader(new StringReader("5\n28\n16\n"));
    ClientSk clientSk = new ClientSk(inData);
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
          GameMap gm = server.create_one_map("Territory.txt", 3);
          maps.add(gm);
          ServerSk serverSk = new ServerSk(maps);
          serverSk.build_server();
        } catch (Exception e) {
        }
      }
    };
    th.start();
    Thread.sleep(100);
    ClientSk clientSk1 = build_client();
    ClientSk clientSk2 = build_client();
    ClientSk clientSk3 = build_client();
    String map_show1 = new String(clientSk1.accept_map());
    String map_show2 = new String(clientSk2.accept_map());
    String map_show3 = new String(clientSk3.accept_map());
    clientSk1.accept_color();
    clientSk1.accept_units();
    clientSk1.set_player();
    clientSk1.init_unit();
    clientSk1.send_player();
    assertEquals("Red", clientSk1.player.color);
    assertEquals(30, clientSk1.map.get_num_units());

    clientSk2.accept_color();
    clientSk2.accept_units();
    clientSk2.set_player();
    clientSk2.init_unit();
    clientSk2.send_player();
    assertEquals("Green", clientSk2.player.color);
    assertEquals(30, clientSk2.map.get_num_units());

    clientSk3.accept_color();
    clientSk3.accept_units();
    clientSk3.set_player();
    clientSk3.init_unit();
    clientSk3.send_player();

    assertEquals("Blue", clientSk3.player.color);
    assertEquals(30, clientSk3.map.get_num_units());

    th.interrupt();
    th.join();
  }

}
