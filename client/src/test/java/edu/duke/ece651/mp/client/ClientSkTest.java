package edu.duke.ece651.mp.client;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.desktop.SystemEventListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class ClientSkTest {
  @Disabled
  @Test
  public void test_send_map() throws IOException, ClassNotFoundException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    ClientSk clientSk = new ClientSk(input, System.out);
    assertEquals("test\nHogwalts\n", clientSk.accept_map());
    clientSk.close_client();
  }



}