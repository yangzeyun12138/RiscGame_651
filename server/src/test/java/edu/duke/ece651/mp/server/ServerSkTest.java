package edu.duke.ece651.mp.server;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import edu.duke.ece651.mp.common.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

class ServerSkTest {
  @Disabled
  @Test
  public void test_send_map() throws IOException {
    String testname = "test";
    String color = "Red";
    ArrayList<String> test_color = new ArrayList<String>();
    test_color.add("Red");
    HashSet<Territory> test_territory = new HashSet<>();
    test_territory.add(new LandTerritory("Hogwalts", color));
    BasicMap bm = new BasicMap(testname, test_color, test_territory);
    ArrayList<GameMap> al = new ArrayList<GameMap>();
    al.add(bm);
    ServerSk serverSk = new ServerSk(al);
    serverSk.build_server();
  }

}
