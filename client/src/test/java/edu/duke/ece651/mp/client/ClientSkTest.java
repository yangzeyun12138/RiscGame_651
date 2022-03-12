package edu.duke.ece651.mp.client;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ClientSkTest {
  @Disabled
  @Test
  public void test_send_map() throws IOException, ClassNotFoundException {
    ClientSk clientSk = new ClientSk();
    assertEquals("test\nHogwalts\n", clientSk.accept_map());
    clientSk.close_client();
  }

}