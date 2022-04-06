package edu.duke.ece651.mp.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.management.monitor.MonitorSettingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class LoginController  {

    @FXML
    private TextField account_textfield;

    @FXML
    private Button login_button;

    @FXML
    private TextField password_textfield;

    @FXML
    private Button register_button;

    @FXML
    private Text Invalid_label;

    public LinkedBlockingQueue<Pair<String, String>> linkedBlockingQueue;
    private ClientSk clientSk;
    private MouseEvent me;

    @FXML
    void sendInformation(MouseEvent event) {
        String username = account_textfield.getText();
        String password = password_textfield.getText();
        Pair<String, String> user_password = new Pair<>(username, password);
        System.out.println(username + " " + password);
        try {
            linkedBlockingQueue.put(user_password);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("In controller, I am controller queue " + linkedBlockingQueue + " " +
                System.identityHashCode(linkedBlockingQueue));
        System.out.println("In controller, I am controller "+ System.identityHashCode(this));
        System.out.println("deque size: " + linkedBlockingQueue.size());
        System.out.println("In controller empty? " + linkedBlockingQueue.isEmpty());
        me = event;
    }

    public LoginController() {
        this.linkedBlockingQueue = new LinkedBlockingQueue<>();
        //this.Invalid_label.setText("");
        bind_client();
        System.out.println("finish bind");
    }


    void bind_client() {
        this.clientSk = Client.ClientSkList.get(0);
        System.out.println("ClientSk in controller : " + System.identityHashCode(clientSk));
        this.linkedBlockingQueue = clientSk.user_password;
        clientSk.setLoginController(this);
        System.out.println("In controller constructor client.controller: " + clientSk.loginController);
    }

    public Text getInvalid_label() {
        return Invalid_label;
    }



    public void switchToMap3() throws IOException {
        Scene scene = Client.get_scene("Map3.fxml");
        Stage stage = (Stage)((Node)me.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



}