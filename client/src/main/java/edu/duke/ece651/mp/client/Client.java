/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.mp.client;

import com.sun.javafx.tk.TKSceneListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.desktop.AppForegroundListener;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;



public class Client extends Application {

    public static ArrayList<ClientSk> ClientSkList;
    public static ArrayList<Scene> SceneList;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientSkList = new ArrayList<>();
        SceneList = new ArrayList<>();

        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        game_start();
        stage.setScene(get_scene("Register.fxml"));
        stage.setResizable(true);
        stage.show();
    }

    public void game_start() throws IOException, ClassNotFoundException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        //System.out.println("Please enter the server host name");
        //String hostName = input.readLine();
        //System.out.println("Please enter the server port number");
        //String port = input.readLine();
        ClientSk clientSk = new ClientSk("0.0.0.0", "9999", input, System.out);
        ClientSkList.add(clientSk);
        setAllScene();
        new_game("0.0.0.0", "9999", input, System.out, true);

    }

    public static void new_game(String hostName, String port,
                                BufferedReader input, PrintStream out, boolean if_first) throws IOException, ClassNotFoundException {
        if (if_first) {
            ClientSkList.get(0).game_begin();
        }
        else {
            System.out.println("In Client , new_game fucntion");
            ClientSk clientSk = new ClientSk(hostName, port, input, out);
            ClientSkList.add(clientSk);
            if (ClientSkList.size() == 2) {
                SceneList.add(createScene("Map31.fxml", 1000, 760));
            } else if (ClientSkList.size() == 3) {
                SceneList.add(createScene("Map32.fxml", 1000, 760));
            } else {
                SceneList.add(createScene("Map33.fxml", 1000, 760));
            }
            System.out.println("after new add room");
            ClientSkList.get(ClientSkList.size() - 1).game_begin();
        }
    }

    public static Scene get_scene(String fileName) throws IOException {
        if (fileName.equals("Login.fxml")){
            return SceneList.get(0);
        } else if (fileName.equals("Register.fxml")) {
            return SceneList.get(1);
        } else if (fileName.equals("Map3.fxml")) {
            return SceneList.get(2);
        } else if (fileName.equals("Map31.fxml")) {
            return SceneList.get(3);
        }
        else if (fileName.equals("Map32.fxml")) {
            return SceneList.get(4);
        }
        else if (fileName.equals("Map33.fxml")) {
            return SceneList.get(5);
        } else {
            return  null;
        }
    }

    public static void setAllScene() throws IOException {
       Scene loginScene = createScene("Login.fxml", 640, 400) ;
       Scene registerScene = createScene("Register.fxml", 620, 400);
       Scene Map3Scene = createScene("Map3.fxml", 1000, 760);
       SceneList.add(loginScene);
       SceneList.add(registerScene);
       SceneList.add(Map3Scene);
    }

    public static Scene createScene(String fileName, int width, int height) throws IOException {
        URL fxmlResource = Client.class.getResource("/ui/" + fileName);
        FXMLLoader loader = new FXMLLoader(fxmlResource);
        VBox vbox = loader.load();
        Scene scene = new Scene(vbox, width, height);
        return scene;
    }
}
