/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.mp.server;

import edu.duke.ece651.mp.common.*;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashSet;

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Server server = new Server();
        Parse parse = new Parse();
        ArrayList<String> lines = parse.readfile("userInfo.txt");
        ArrayList<GameMap> maps = server.create_all_map(parse, server);
        ServerSk serverSk = new ServerSk(maps, 9999, lines);
        serverSk.build_server();
    }

    /**
     *
     * @param filename used to parse to generate a map
     * @param numPlayer
     * @return the generated game map
     * @throws IOException
     */
    public GameMap create_one_map(String filename, int numPlayer, Parse parse) throws IOException {
        HashSet<Territory> result = parse.parseTerritoryNeighbor(filename, numPlayer);
        GameMap gm = new BasicMap("Map 1", parse.parseColor("Color.txt", numPlayer), result);
        gm.assign_all();
        gm.init_num_units();
        return gm;
    }

    public ArrayList<GameMap> create_all_map(Parse parse, Server server) throws IOException {
        ArrayList<GameMap> maps = new ArrayList<>();
        GameMap gm1 = server.create_one_map("Territory3.txt", 3, parse);
        maps.add(gm1);
        GameMap gm2 = server.create_one_map("Territory3.txt", 3, parse);
        maps.add(gm2);
        GameMap gm3 = server.create_one_map("Territory3.txt", 3, parse);
        maps.add(gm3);
        GameMap gm4 = server.create_one_map("Territory3.txt", 3, parse);
        maps.add(gm4);
        return maps;
    }
}
