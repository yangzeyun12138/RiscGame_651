package edu.duke.ece651.mp.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.javatuples.Quartet;

import java.util.concurrent.LinkedBlockingQueue;

public class Map3Controller {
    //set init units
    @FXML
    private Text territory1;
    @FXML
    private Text territory2;
    @FXML
    private Text territory3;
    @FXML
    private TextField first_textfield;
    @FXML
    private TextField second_textfield;
    @FXML
    private Text third_text;
    @FXML
    private Button set_unit_button;

    //player color, total tech level, food
    @FXML
    private Text player_text;
    @FXML
    private Text player_text1;
    @FXML
    private Text player_text2;



    @FXML
    private Button add_room_button;

    @FXML
    private Button attack_button;

    @FXML
    private ChoiceBox<?> change_room_choicebox;

    @FXML
    private SplitMenuButton choose_level_menu_button;

    @FXML
    private SplitMenuButton choose_level_menu_button1;

    @FXML
    private SplitMenuButton choose_level_menu_button11;

    @FXML
    private Button done_button;

    @FXML
    private Button elantris_button;


    @FXML
    private Button gondor_button;

    @FXML
    private Button hogwarts_button;

    @FXML
    private Button midkemia_button;

    @FXML
    private Button mordor_button;

    @FXML
    private Button move_button;

    @FXML
    private Button narnia_button;

    @FXML
    private TextField number_of_units_textfield;

    @FXML
    private Text owner_text;

    @FXML
    private Button oz_button;




    @FXML
    private Button roshar_button;

    @FXML
    private Button scadrial_button;


    @FXML
    private Button submit_button;

    @FXML
    private TextArea system_textarea;

    @FXML
    private Rectangle tech_text;


    @FXML
    private SplitMenuButton territory_name_menu_button;

    @FXML
    private Text units_level0;

    @FXML
    private Text units_level1;

    @FXML
    private Text units_level2;

    @FXML
    private Text units_level3;

    @FXML
    private Text units_level4;

    @FXML
    private Text units_level5;

    @FXML
    private Text units_level6;

    @FXML
    private Button upgrade_button;

    @FXML
    private Button upgrade_tech_button;


    private LinkedBlockingQueue<Pair<String, String>> initQueue;
    private LinkedBlockingQueue<String> actionQueue;
    private LinkedBlockingQueue<Quartet<String, String, String, String>> moveQueue;
    private LinkedBlockingQueue<Quartet<String, String, String, String>> attackQueue;
    private LinkedBlockingQueue<Quartet<String, String, String, String>> changeQueue;
    private ClientSk clientSk;

    public Map3Controller() {
        this.initQueue = new LinkedBlockingQueue<>();
        this.actionQueue = new LinkedBlockingQueue<>();
        this.moveQueue = new LinkedBlockingQueue<>();
        this.attackQueue = new LinkedBlockingQueue<>();
        this.changeQueue  = new LinkedBlockingQueue<>();
        bind_client();
    }


    void bind_client() {
        this.clientSk = Client.ClientSkList.get(Client.ClientSkList.size() - 1);
        this.initQueue = clientSk.initQueue;
        this.actionQueue = clientSk.actionQueue;
        this.moveQueue = clientSk.moveQueue;
        this.attackQueue = clientSk.attackQueue;
        this.changeQueue  = clientSk.changeQueue;
        clientSk.setMap3Controller(this);
    }

    @FXML
    void initialize_units(MouseEvent event) {

    }

    @FXML
    void ChooseTerritory(MouseEvent event) {

    }


    @FXML
    void showRoom(MouseEvent event) {

    }

    public Text getColor() {
        return player_text;
    }

    public TextArea getLeftBottomBoard(){
        return system_textarea;
    }
}

