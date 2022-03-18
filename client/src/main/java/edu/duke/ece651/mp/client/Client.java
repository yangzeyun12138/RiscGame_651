/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.mp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the server host name");
        String hostName = input.readLine();
        ClientSk clientSk = new ClientSk(hostName ,input, System.out);
        String map_show1 = new String(clientSk.accept_map());
        System.out.print(map_show1);
        clientSk.accept_color();
        clientSk.accept_units();
        clientSk.set_player();
        clientSk.init_unit();
        clientSk.send_player();
        String map_show2 = new String(clientSk.accept_map());
        System.out.print(map_show2);
        clientSk.do_turns();
    }
}
