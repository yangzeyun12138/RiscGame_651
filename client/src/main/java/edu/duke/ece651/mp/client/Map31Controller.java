package edu.duke.ece651.mp.client;

import edu.duke.ece651.mp.common.Player;
import edu.duke.ece651.mp.common.Territory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.javatuples.Quartet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;

public class Map31Controller implements Initializable {

    @FXML
    private Button add_room_button;

    @FXML
    private Button attack_button;

    @FXML
    private MenuButton change_room_menubutton;

    @FXML
    private TextField choose_level_menubutton;

    @FXML
    private Button done_button;

    @FXML
    private Button elantris_button;

    @FXML
    public TextField first_textfield;

    @FXML
    private TextField from_level_menubutton;

    @FXML
    private Button gondor_button;

    @FXML
    private Button hogwarts_button;

    @FXML
    public AnchorPane init_units_anchorpane;

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
    public Text player_text;

    @FXML
    public Text player_text1;

    @FXML
    public Text player_text2;

    @FXML
    private Button roshar_button;

    @FXML
    private Button scadrial_button;

    @FXML
    public TextField second_textfield;

    @FXML
    private Button set_unit_button;

    @FXML
    private Button submit_button;

    @FXML
    private TextArea system_textarea;

    @FXML
    private Rectangle tech_text;

    @FXML
    public Text territory1;

    @FXML
    public Text territory2;

    @FXML
    public Text territory3;

    @FXML
    private MenuButton territory_name_menubutton;

    @FXML
    public Text third_text;

    @FXML
    private TextField to_level_menubutton;

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

    @FXML
    private Text Elantris_color;

    @FXML
    private Text Gondor_color;

    @FXML
    private Text Hogwarts_color;

    @FXML
    private Text Midkemia_color;

    @FXML
    private Text Mordor_color;

    @FXML
    private Text Narnia_color;

    @FXML
    private Text Oz_color;

    @FXML
    private Text Roshar_color;

    @FXML
    private Text Scadrial_color;



    @FXML
    void attackClick(MouseEvent event) throws InterruptedException {
        actionQueue.clear();
        TerriList.clear();
        this.actionQueue.put("A");
    }

    @FXML
    void changeRoom(MouseEvent event) {
        me = event;
        for (int i = 0; i < Client.ClientSkList.size(); i++) {
            System.out.println("In for loop : " + i);
            MenuItem menuItem = change_room_menubutton.getItems().get(i);
            menuItem.setDisable(false);
            menuItem.setVisible(true);
        }
    }


    @FXML
    void chooseFromLevel(MouseEvent event) {

    }

    @FXML
    void chooseLevel(MouseEvent event) {

    }

    @FXML
    void chooseToLevel(MouseEvent event) {

    }

    @FXML
    void doneClick(MouseEvent event) throws InterruptedException {
        actionQueue.clear();
        this.actionQueue.put("D");
    }

    @FXML
    void elantrisClick(MouseEvent event) {
        elantris_button.setOpacity(0.1);
        TerriList.add("Elantris");
    }

    @FXML
    void elantrisRelease(MouseEvent event) {
        elantris_button.setOpacity(0.0);
    }

    @FXML
    void getTerritory(MouseEvent event) {
      ;
    }

    @FXML
    void gondorClick(MouseEvent event) {
        gondor_button.setOpacity(0.1);
        TerriList.add("Gondor");
    }

    @FXML
    void gondorRelease(MouseEvent event) {
        gondor_button.setOpacity(0.0);
    }

    @FXML
    void hogwartsClick(MouseEvent event) {
        hogwarts_button.setOpacity(0.1);
        TerriList.add("Hogwarts");
    }

    @FXML
    void hogwartsRelease(MouseEvent event) {
        hogwarts_button.setOpacity(0.0);
    }


    @FXML
    void midkemiaClick(MouseEvent event) {
        midkemia_button.setOpacity(0.1);
        TerriList.add("Midkemia");
    }

    @FXML
    void midkemiaRelease(MouseEvent event) {
        midkemia_button.setOpacity(0.0);
    }

    @FXML
    void mordorClick(MouseEvent event) {
        mordor_button.setOpacity(0.1);
        TerriList.add("Mordor");
    }

    @FXML
    void mordorRelease(MouseEvent event) {
        mordor_button.setOpacity(0.0);
    }

    @FXML
    void moveClick(MouseEvent event) throws InterruptedException {
        actionQueue.clear();
        TerriList.clear();
        this.actionQueue.put("M");
    }

    @FXML
    void narniaClick(MouseEvent event) {
        narnia_button.setOpacity(0.1);
        TerriList.add("Narnia");
    }

    @FXML
    void narniaRelease(MouseEvent event) {
        narnia_button.setOpacity(0.0);
    }

    @FXML
    void ozClick(MouseEvent event) {
        oz_button.setOpacity(0.1);
        TerriList.add("Oz");
    }

    @FXML
    void ozRelease(MouseEvent mouseEvent) {
        oz_button.setOpacity(0.0);
    }

    @FXML
    void rosharClick(MouseEvent event) {
        roshar_button.setOpacity(0.1);
        TerriList.add("Roshar");
    }

    @FXML
    void rosharRelease(MouseEvent event){
        roshar_button.setOpacity(0.0);
    }

    @FXML
    void scadrialClick(MouseEvent event) {
        scadrial_button.setOpacity(0.1);
        TerriList.add("Scadrial");

    }

    @FXML
    void scadrialRelease(MouseEvent event) {
        scadrial_button.setOpacity(0.0);
    }

    @FXML
    public Text result_text;

    @FXML
    public Button continue_button;

    @FXML
    public Button quit_button;

    public void end_game1() {
        result_text.setDisable(false);
        continue_button.setDisable(false);
        quit_button.setDisable(false);
        result_text.setVisible(true);
        continue_button.setVisible(true);
        quit_button.setVisible(true);

    }

    public void end_game2() {
        result_text.setVisible(false);
        continue_button.setVisible(false);
        quit_button.setVisible(false);
        result_text.setDisable(true);
        continue_button.setDisable(true);
        quit_button.setDisable(true);
    }

    @FXML
    public void continueClick(MouseEvent event) {
        end_game2();
        if_keep_watch = "y";
    }

    @FXML
    public void quitClick(MouseEvent event) {
        end_game2();
        if_keep_watch = "n";
    }

    @FXML
    void submitClick(MouseEvent event) throws InterruptedException {
        moveQueue.clear();
        String s1 = from_level_menubutton.getText();
        String s2 = to_level_menubutton.getText();
        String s3 = choose_level_menubutton.getText();
        String s4 = number_of_units_textfield.getText();
        Quartet<String, String , String, String> ans = new Quartet<>(s1,s2,s3,s4);
        moveQueue.put(ans);
        from_level_menubutton.clear();
        to_level_menubutton.clear();
        choose_level_menubutton.clear();
        number_of_units_textfield.clear();
        from_level_menubutton.setPromptText("# of From level");
        to_level_menubutton.setPromptText("# of To level");
        choose_level_menubutton.setPromptText("# of Level");
        number_of_units_textfield.setPromptText("# of Units");
    }

    @FXML
    void upgradeClick(MouseEvent event) throws InterruptedException {
        actionQueue.clear();
        TerriList.clear();
        this.actionQueue.put("C");
    }

    @FXML
    void upgradeTechClick(MouseEvent event) throws InterruptedException {
        this.actionQueue.put("U");
    }

    @FXML
    void NarniaInfo(ActionEvent ae) throws InterruptedException {
        territory_name_menubutton.setText("Narnia");
        showTerriInfo("Narnia");
    }

    @FXML
    void MidkemiaInfo(ActionEvent ae) throws InterruptedException {
        territory_name_menubutton.setText("Midkemia");
        showTerriInfo("Midkemia");
    }
    @FXML
    void OzInfo(ActionEvent ae) throws InterruptedException {
        territory_name_menubutton.setText("Oz");
        showTerriInfo("Oz");
    }
    @FXML
    void GondorInfo(ActionEvent ae) throws InterruptedException {
        territory_name_menubutton.setText("Gondor");
        showTerriInfo("Gondor");
    }
    @FXML
    void ElantrisInfo(ActionEvent ae) throws InterruptedException {
        territory_name_menubutton.setText("Elantris");
        showTerriInfo("Elantris");
    }
    @FXML
    void ScadrialInfo(ActionEvent ae) throws InterruptedException {
        territory_name_menubutton.setText("Scadrial");
        showTerriInfo("Scadrial");
    }
    @FXML
    void MordorInfo(ActionEvent ae) throws InterruptedException {
        territory_name_menubutton.setText("Mordor");
        showTerriInfo("Mordor");
    }
    @FXML
    void RosharInfo(ActionEvent ae) throws InterruptedException {
        territory_name_menubutton.setText("Roshar");
        showTerriInfo("Roshar");
    }
    @FXML
    void HogwartsInfo(ActionEvent ae) throws InterruptedException {
        territory_name_menubutton.setText("Hogwarts");
        showTerriInfo("Hogwarts");
    }


    @FXML
    private Text roomNum;

    private void showTerriInfo(String terriName) {
        ArrayList<Player> players = clientSk.players;
        Player temp = clientSk.Action.getPlayer(terriName, players);
        Territory t = null;
        if (temp == clientSk.player) {
            t = clientSk.Action.findTerritory(clientSk.temp_player, terriName);
        } else {
            t = clientSk.Action.findTerritory(temp, terriName);
        }
        units_level0.setText(String.valueOf(t.countLevelUnit(0)));
        units_level1.setText(String.valueOf(t.countLevelUnit(1)));
        units_level2.setText(String.valueOf(t.countLevelUnit(2)));
        units_level3.setText(String.valueOf(t.countLevelUnit(3)));
        units_level4.setText(String.valueOf(t.countLevelUnit(4)));
        units_level5.setText(String.valueOf(t.countLevelUnit(5)));
        units_level6.setText(String.valueOf(t.countLevelUnit(6)));
        owner_text.setText(temp.color);
    }

    private LinkedBlockingQueue<Pair<String, String>> initQueue;
    private LinkedBlockingQueue<String> terriNameQueue;
    private LinkedBlockingQueue<String> actionQueue;
    private LinkedBlockingQueue<Quartet<String, String, String, String>> moveQueue;
    private LinkedBlockingQueue<Quartet<String, String, String, String>> attackQueue;
    private LinkedBlockingQueue<Quartet<String, String, String, String>> changeQueue;
    private ClientSk clientSk;
    public ArrayList<String> TerriList;
    public String if_keep_watch;
    public int addRoomCount;



    public Map31Controller() {
        this.initQueue = new LinkedBlockingQueue<>();
        this.terriNameQueue = new LinkedBlockingQueue<>();
        this.actionQueue = new LinkedBlockingQueue<>();
        this.moveQueue = new LinkedBlockingQueue<>();
        this.attackQueue = new LinkedBlockingQueue<>();
        this.changeQueue  = new LinkedBlockingQueue<>();
        bind_client();
        this.TerriList = new ArrayList<>();
        this.addRoomCount = 0;
        System.out.println("$$$$$$$$$" +
                "$$$$$$$in map31 controller constructor");
    }


    void bind_client() {
        this.clientSk = Client.ClientSkList.get(Client.ClientSkList.size() - 1);
        this.initQueue = clientSk.initQueue;
        this.terriNameQueue = clientSk.terriNameQueue;
        this.actionQueue = clientSk.actionQueue;
        this.moveQueue = clientSk.moveQueue;
        this.attackQueue = clientSk.attackQueue;
        this.changeQueue  = clientSk.changeQueue;
        clientSk.setMap31Controller(this);
        System.out.println("$$$$$$$$$" +
                "$$$$$$$in map31 bind client end");
    }

    @FXML
    void initialize_units(MouseEvent event) {
        initQueue.clear();
        String num1 = first_textfield.getText();
        String num2 = second_textfield.getText();
        Pair<String, String> num1_num2 = new Pair<>(num1, num2);
        try {
            initQueue.put(num1_num2);
        } catch (Exception e){
            e.printStackTrace();
        }
        player_text.setText(clientSk.player.color);
        updatePlayerInfo();
    }


    public Text getColor() {
        return player_text;
    }

    public TextArea getLeftBottomBoard(){
        return system_textarea;
    }

    public void updatePlayerInfo() {
        player_text1.setText(String.valueOf(clientSk.player.getTechLevel()));
        player_text2.setText(String.valueOf(clientSk.player.getFood()));
    }

    public void tempUpdateInfo(Player temp_player) {
        player_text1.setText(String.valueOf(temp_player.getTechLevel()));
        player_text2.setText(String.valueOf(temp_player.getFood()));
    }

    public void tempUpdateInfo1(Player temp_player) {
        player_text1.setText(String.valueOf(temp_player.getTechLevel()));
        player_text2.setText(String.valueOf(temp_player.getFood()));
    }

    public void updateColor() {
        ArrayList<Player> players = clientSk.players;
        for (Player p : players) {
            for (Territory t : p.player_terri_set) {
                System.out.println("3####################");
                System.out.println(t.getColor());
                if (t.getName().equals("Narnia")) {
                    colorHelper(Narnia_color, t);
                } else if (t.getName().equals("Elantris")){
                    colorHelper(Elantris_color, t);
                } else if (t.getName().equals("Gondor")) {
                    colorHelper(Gondor_color, t);
                } else if (t.getName().equals("Hogwarts")) {
                    colorHelper(Hogwarts_color, t);
                } else if(t.getName().equals("Midkemia")) {
                    colorHelper(Midkemia_color, t);
                } else  if (t.getName().equals("Mordor")) {
                    colorHelper(Mordor_color, t);
                } else if(t.getName().equals("Oz")) {
                    colorHelper(Oz_color, t);
                } else if(t.getName().equals("Roshar")) {
                    colorHelper(Roshar_color, t);
                } else {
                    colorHelper(Scadrial_color, t);
                }
            }
        }
    }

    public void colorHelper(Text text, Territory t) {
        text.setVisible(true);
        text.setText(t.getColor());
        if (t.getColor().equals("Red")) {
            text.setFill(Color.RED);
        } else if (t.getColor().equals("Blue")) {
            text.setFill(Color.BLUE);
        } else {
            text.setFill(Color.GREEN);
        }
    }

    private MouseEvent me;

    @FXML
    void addRoom(MouseEvent event) throws IOException, ClassNotFoundException {
        me = event;
        if(clientSk.currRoomNum < 4) {
            clientSk.currRoomNum++;
        }
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("In map3controller before new game");
        Client.new_game("0.0.0.0", "9999", input, System.out, false);
        System.out.println("In map3controller after new game");
    }

    @FXML
    void changeToRoom1(ActionEvent ae) {

        Scene scene = null;
        try {
            scene = Client.get_scene("Map3.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)me.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void changeToRoom2(ActionEvent ae) {
        Scene scene = null;
        try {
            scene = Client.get_scene("Map31.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)me.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void changeToRoom3(ActionEvent ae) {
        Scene scene = null;
        try {
            scene = Client.get_scene("Map32.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)me.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void changeToRoom4(ActionEvent ae) {
        Scene scene = null;
        try {
            scene = Client.get_scene("Map33.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)me.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.roomNum.setText("Room2");
    }
}

