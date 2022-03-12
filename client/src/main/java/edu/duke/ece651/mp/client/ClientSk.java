package edu.duke.ece651.mp.client;
import edu.duke.ece651.mp.common.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Each ClientSk per player, the communication hub between a player and the server
 */
public class ClientSk {
  private BasicMap map;
  private Socket socket;
  private MapDisplay toDisplay;

  /**
   * connect to the local host on 9999 port
   * map and toDisplay need to wait for info from server to initialize
   * @throws IOException
   */
  public ClientSk() throws IOException {
    socket = new Socket("0.0.0.0", 9999);
    this.map = null;
  }


  /**
   * Read a map object from the socket and display it
   * @return A String that shows the info of the whole map
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public String accept_map() throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
    map = (BasicMap)ois.readObject();
    toDisplay = new BasicMapDisplay(map);
    ois.close();
    return toDisplay.display();
  }


  /**
   * close the socket connection
   * @throws IOException
   */

  public void close_client() throws IOException {
    socket.close();
  }
}

