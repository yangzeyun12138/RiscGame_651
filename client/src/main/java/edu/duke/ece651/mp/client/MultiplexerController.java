package edu.duke.ece651.mp.client;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MultiplexerController {

    @FXML
    private Button addRoom;

    @FXML
    private Button room1button;

    @FXML
    private Button room2button;

    @FXML
    private ImageView room2lock;

    @FXML
    private Button room3button;

    @FXML
    private Button room4button;

    @FXML
    private ImageView room3lock;

    @FXML
    private ImageView room4lock;





    // create a new room for player
    @FXML
    void addRoomClick(MouseEvent event) throws IOException, ClassNotFoundException {
        if(clientSk.currRoomNum < 4) {
            clientSk.currRoomNum++;
        }
        if(clientSk.currRoomNum == 4) {
            addRoom.setVisible(false);
            addRoom.setDisable(true);
        }
        if(clientSk.currRoomNum == 2)  {
            //if the currRoomNum is 2, we can set room 2 lock image invisible
            room2lock.setVisible(false);
            room2lock.setDisable(true);
        } else if(clientSk.currRoomNum == 3) {
            //if the currRoomNum is 3, we can set room 2 lock image invisible
            room3lock.setVisible(false);
            room3lock.setDisable(true);
        } else {
            //if the currRoomNum is 3, we can set room 2 lock image invisible
            room4lock.setVisible(false);
            room4lock.setDisable(true);
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Client.new_game("0.0.0.0", "9999", input, System.out, false);
    }

    @FXML
    void room1Click(MouseEvent event) {
        System.out.println("in change room1");
        Scene scene = null;
        try {
            scene = Client.get_scene("Map3.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void room2Click(MouseEvent event) {
        System.out.println("in change room2");
        Scene scene = null;
        try {
            scene = Client.get_scene("Map31.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void room3Click(MouseEvent event) {
        System.out.println("in change room3");
        Scene scene = null;
        try {
            scene = Client.get_scene("Map32.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void room4Click(MouseEvent event) {
        System.out.println("in change room4");
        Scene scene = null;
        try {
            scene = Client.get_scene("Map33.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



    public ClientSk clientSk;

    public MultiplexerController(){
        this.clientSk = Client.ClientSkList.get(Client.ClientSkList.size() - 1);
    }

}
