package edu.duke.ece651.mp.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Triple;
import org.javatuples.Triplet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class RegisterController implements Initializable {

    @FXML
    private TextField account_textfield;

    @FXML
    private TextField password_textfield;

    @FXML
    private TextField password_textfield1;

    @FXML
    private Button register_button;

    @FXML
    private Text Invalid_label;

    private LinkedBlockingQueue<Triplet<String,String,String>> linkedBlockingQueue;
    private ClientSk clientSk;
    public MouseEvent me;

    @FXML
    void sendRegistration(MouseEvent event) {

        String username = account_textfield.getText();
        String password1 = password_textfield.getText();
        String password2 = password_textfield1.getText();
        try {
            linkedBlockingQueue.put(new Triplet<>(username, password1, password2));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public RegisterController() {
        this.linkedBlockingQueue = new LinkedBlockingQueue<>();
        bind_client();
        //Invalid_label.setText("");
    }


    void bind_client() {
        this.clientSk = Client.ClientSkList.get(0);
        this.linkedBlockingQueue = clientSk.user_pwd1_pwd2;
        clientSk.setRegisterController(this);
    }

    public Text getInvalid_label() {
        return Invalid_label;
    }

    public TextField getAccount_textfield() {
        return account_textfield;
    }

    public TextField getPassword_textfield() {
        return password_textfield;
    }

    public TextField getPassword_textfield1() {
        return password_textfield1;
    }
    public void clearall() {
        account_textfield.setText("");
        password_textfield.setText("");
        password_textfield1.setText("");
    }

    public void clearField() {
        password_textfield.setText("");
        password_textfield1.setText("");
    }

    public void switchToLogin() throws IOException {

        System.out.println("In switch to Login");
        Scene scene = Client.get_scene("Login.fxml");
        Stage stage = (Stage)((Node)me.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Text if_register;

    @FXML
    private Button yesClick;

    @FXML
    private Button noClick;

    public String register;

    @FXML
    private FlowPane flow_pane;


    public void  initHelper() {
        if_register.setVisible(false);
        if_register.setDisable(true);
        yesClick.setDisable(true);
        yesClick.setVisible(false);
        noClick.setVisible(false);
        noClick.setDisable(true);
        account_textfield.setVisible(true);
        account_textfield.setDisable(false);
        password_textfield.setDisable(false);
        password_textfield.setVisible(true);
        Invalid_label.setDisable(false);
        Invalid_label.setVisible(true);
        register_button.setDisable(false);
        register_button.setVisible(true);
        password_textfield1.setVisible(true);
        password_textfield1.setDisable(false);
    }
    @FXML
    public void yesClick(MouseEvent event) {
        me = event;
        register = "Need register";
        initHelper();

    }

    @FXML
    public void noClick(MouseEvent event) {
        me = event;
        register = "No need register";
        initHelper();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        account_textfield.setVisible(false);
        account_textfield.setDisable(true);
        password_textfield.setDisable(true);
        password_textfield.setVisible(false);
        Invalid_label.setDisable(true);
        Invalid_label.setVisible(false);
        register_button.setDisable(true);
        register_button.setVisible(false);
        password_textfield1.setVisible(false);
        password_textfield1.setDisable(true);
    }
}

