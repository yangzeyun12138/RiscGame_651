package edu.duke.ece651.mp.client;

import com.google.errorprone.annotations.MustBeClosed;
import edu.duke.ece651.mp.common.Player;
import edu.duke.ece651.mp.common.Territory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.checkerframework.checker.units.qual.A;
import org.javatuples.Quartet;


import javafx.event.ActionEvent;

import javafx.scene.image.ImageView;
import java.awt.*;
import java.awt.event.TextEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;

public class Map3Controller implements Initializable {

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
    private Text roomNum;

    @FXML
    void addRoom(MouseEvent event) throws IOException, ClassNotFoundException {
        //me = event;
        if(clientSk.currRoomNum < 4) {
            clientSk.currRoomNum++;
            System.out.println("******************" +
                    "************In add Room current room num is " + clientSk.currRoomNum);
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
        System.out.println("In changeToRoom2");
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

    @FXML
    void attackClick(MouseEvent event) throws InterruptedException {
        actionQueue.clear();
        TerriList.clear();
        this.actionQueue.put("A");
    }



    @FXML
    void changeRoom(MouseEvent event) {
        me = event;
        System.out.println("%%%%%%%%%%%%%%\n" +
                "In change room!!!!!!!!!!");
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
        System.out.println("after set if_keep_watch");
        System.out.println("if_keep_watch = " + if_keep_watch);
    }

    @FXML
    public void quitClick(MouseEvent event) {
        end_game2();
        if_keep_watch = "n";
    }

    @FXML
    void submitClick(MouseEvent event) throws InterruptedException {
        moveQueue.clear();
        String s1 = null;
        String s2 = null;
        String s3 = choose_level_menubutton.getText();
        if (s3.equals("")) {
            s3 = "0";
        }
        if (isBomber == true) {
            s1 = "0";
            s2 = "7";
            s3 = "7";
            isBomber = false;
        } else if (isFatNerd == true) {
            s1 = "0";
            s2 = "8";
            s3 = "8";
            isFatNerd = false;
        } else if (isSpy == true) {
            System.out.println("In map3controller isSpy = " + isSpy);
            s1 = "0";
            s2 = "9";
            s3 = "9";
            isSpy = false;
        } else {
            s1 = from_level_menubutton.getText();
            if (s1.equals("")) {
                s1 = "0";
            }
            s2 = to_level_menubutton.getText();
            if (s2.equals("")) {
                s2 = "0";
            }
        }

        String s4 = number_of_units_textfield.getText();
        if (s4.equals("")) {
            s4 = "0";
        }
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
        actionQueue.clear();
        TerriList.clear();
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

    private Pair<Territory, String> getTerri(String terriName, HashMap<String, ArrayList<Territory>> toShow){
        ArrayList<Territory> viewed = toShow.get("viewed");
        ArrayList<Territory> grey = toShow.get("grey");
        for (Territory t : viewed) {
            if (t.getName().equals(terriName)) {
                Pair<Territory, String> ans = new Pair<>(t, "viewed");
                return ans;
            }
        }
        for (Territory t : grey) {
            if (t.getName().equals(terriName)) {
                Pair<Territory, String> ans = new Pair<>(t, "grey");
                return ans;
            }
        }
        Pair<Territory, String> ans = new Pair<>(null, null);
        return ans;
    }

    private void showTerriInfo(String terriName) {
        // viewed grey invisible
        HashMap<String, ArrayList<Territory>> toShow = this.clientSk.new_view_map;
        System.out.println("In show Terri Info + new view map is " + toShow);
        Pair<Territory, String> res = getTerri(terriName, toShow);
        Territory t = res.getKey();
        if (res.getValue() != null) {
            if (res.getValue().equals("viewed")) {
                for (Player p : clientSk.players) {
                    for (Territory temp : p.player_terri_set) {
                        if (t.getName().equals(temp.getName())) {
                            t = temp;
                        }
                    }
                }

                units_level0.setText(String.valueOf(t.countLevelUnit(0)));
                units_level0.setFill(Color.BLACK);
                units_level1.setText(String.valueOf(t.countLevelUnit(1)));
                units_level1.setFill(Color.BLACK);
                units_level2.setText(String.valueOf(t.countLevelUnit(2)));
                units_level2.setFill(Color.BLACK);
                units_level3.setText(String.valueOf(t.countLevelUnit(3)));
                units_level3.setFill(Color.BLACK);
                units_level4.setText(String.valueOf(t.countLevelUnit(4)));
                units_level4.setFill(Color.BLACK);
                units_level5.setText(String.valueOf(t.countLevelUnit(5)));
                units_level5.setFill((Color.BLACK));
                units_level6.setText(String.valueOf(t.countLevelUnit(6)));
                units_level6.setFill(Color.BLACK);
                bomber_text.setText(String.valueOf(t.countLevel(7)));
                bomber_text.setFill(Color.BLACK);
                fatnerd_text.setText(String.valueOf(t.countLevel(8)));
                fatnerd_text.setFill(Color.BLACK);
                spy_text.setText(String.valueOf(t.countSpy(this.clientSk.player.color)));
            }
            else {

                units_level0.setText(String.valueOf(t.countLevelUnit(0)));
                units_level0.setFill(Color.WHITE);
                units_level1.setText(String.valueOf(t.countLevelUnit(1)));
                units_level1.setFill(Color.WHITE);
                units_level2.setText(String.valueOf(t.countLevelUnit(2)));
                units_level2.setFill(Color.WHITE);
                units_level3.setText(String.valueOf(t.countLevelUnit(3)));
                units_level3.setFill(Color.WHITE);
                units_level4.setText(String.valueOf(t.countLevelUnit(4)));
                units_level4.setFill(Color.WHITE);
                units_level5.setText(String.valueOf(t.countLevelUnit(5)));
                units_level5.setFill(Color.WHITE);
                units_level6.setText(String.valueOf(t.countLevelUnit(6)));
                units_level6.setFill(Color.WHITE);
                bomber_text.setText(String.valueOf(t.countLevel(7)));
                bomber_text.setFill(Color.WHITE);
                fatnerd_text.setText(String.valueOf(t.countLevel(8)));
                fatnerd_text.setFill(Color.WHITE);
                spy_text.setText("0");
            }
        }
        else {
            units_level0.setText("x");
            units_level1.setText("x");
            units_level2.setText("x");
            units_level3.setText("x");
            units_level4.setText("x");
            units_level5.setText("x");
            units_level6.setText("x");
            bomber_text.setText("x");
            fatnerd_text.setText("x");
            spy_text.setText("0");
        }
    }

    public void setFog(Territory t, String type) {
        if (type.equals("viewed")) {
            if (t.getName().equals("Narnia")) {
                Narnia_fog.setOpacity(0);
            } else if (t.getName().equals("Elantris")){
                Elantris_fog.setOpacity(0);
            } else if (t.getName().equals("Gondor")) {
                Gondor_fog.setOpacity(0);
            } else if (t.getName().equals("Hogwarts")) {
                Hogwarts_fog.setOpacity(0);
            } else if(t.getName().equals("Midkemia")) {
                Midkemia_fog.setOpacity(0);
            } else  if (t.getName().equals("Mordor")) {
                Mordor_fog.setOpacity(0);
            } else if(t.getName().equals("Oz")) {
                Oz_fog.setOpacity(0);
            } else if(t.getName().equals("Roshar")) {
                Roshar_fog.setOpacity(0);
            } else {
                Scadrial_fog.setOpacity(0);
            }
        } else {
            if (t.getName().equals("Narnia")) {
                Narnia_fog.setOpacity(0.6);
            } else if (t.getName().equals("Elantris")){
                Elantris_fog.setOpacity(0.6);
            } else if (t.getName().equals("Gondor")) {
                Gondor_fog.setOpacity(0.6);
            } else if (t.getName().equals("Hogwarts")) {
                Hogwarts_fog.setOpacity(0.6);
            } else if(t.getName().equals("Midkemia")) {
                Midkemia_fog.setOpacity(0.6);
            } else  if (t.getName().equals("Mordor")) {
                Mordor_fog.setOpacity(0.6);
            } else if(t.getName().equals("Oz")) {
                Oz_fog.setOpacity(0.6);
            } else if(t.getName().equals("Roshar")) {
                Roshar_fog.setOpacity(0.6);
            } else {
                Scadrial_fog.setOpacity(0.6);
            }
        }
    }

    private LinkedBlockingQueue<Pair<String, String>> initQueue;
    private LinkedBlockingQueue<String> terriNameQueue;
    private LinkedBlockingQueue<String> actionQueue;
    private LinkedBlockingQueue<Quartet<String, String, String, String>> moveQueue;
    private LinkedBlockingQueue<Quartet<String, String, String, String>> attackQueue;
    private LinkedBlockingQueue<Quartet<String, String, String, String>> changeQueue;
    public ClientSk clientSk;
    public ArrayList<String> TerriList;
    public String if_keep_watch;
    public int addRoomCount;
    private MouseEvent me;


    public Map3Controller() {
        this.initQueue = new LinkedBlockingQueue<>();
        this.terriNameQueue = new LinkedBlockingQueue<>();
        this.actionQueue = new LinkedBlockingQueue<>();
        this.moveQueue = new LinkedBlockingQueue<>();
        this.attackQueue = new LinkedBlockingQueue<>();
        this.changeQueue  = new LinkedBlockingQueue<>();
        bind_client();
        this.TerriList = new ArrayList<>();
        this.addRoomCount = 0;
        isSpy = false;
        isFatNerd = false;
        isBomber = false;
    }


    void bind_client() {
        this.clientSk = Client.ClientSkList.get(Client.ClientSkList.size() - 1);
        this.initQueue = clientSk.initQueue;
        this.terriNameQueue = clientSk.terriNameQueue;
        this.actionQueue = clientSk.actionQueue;
        this.moveQueue = clientSk.moveQueue;
        this.attackQueue = clientSk.attackQueue;
        this.changeQueue  = clientSk.changeQueue;
        clientSk.setMap3Controller(this);
        System.out.println("@@@@@@@@@@@after set map3Controller");
        System.out.println("@@@@@@@@@@@clientSk map3Controller" + System.identityHashCode(clientSk.map3Controller));
        System.out.println("@@@@@@@@@@@bind_client map3Controller" + System.identityHashCode(this));
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

    public void tempUpdateInfo() {
        player_text1.setText(String.valueOf(this.clientSk.player.getTechLevel()));
        player_text2.setText(String.valueOf(this.clientSk.player.getFood()));
    }

    public void tempUpdateInfo1() {
        player_text1.setText(String.valueOf(this.clientSk.player.getTechLevel()));
        player_text2.setText(String.valueOf(this.clientSk.player.getFood()));
    }

    public void updateColor() {
        HashMap<String, ArrayList<Territory>> toShow = this.clientSk.new_view_map;
        // viewed grey invisible
        for (Territory t : toShow.get("viewed")) {
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

        for (Territory t : toShow.get("grey")) {
            if (t.getName().equals("Narnia")) {
                colorHelper2(Narnia_color, t);
            } else if (t.getName().equals("Elantris")){
                colorHelper2(Elantris_color, t);
            } else if (t.getName().equals("Gondor")) {
                colorHelper2(Gondor_color, t);
            } else if (t.getName().equals("Hogwarts")) {
                colorHelper2(Hogwarts_color, t);
            } else if(t.getName().equals("Midkemia")) {
                colorHelper2(Midkemia_color, t);
            } else  if (t.getName().equals("Mordor")) {
                colorHelper2(Mordor_color, t);
            } else if(t.getName().equals("Oz")) {
                colorHelper2(Oz_color, t);
            } else if(t.getName().equals("Roshar")) {
                colorHelper2(Roshar_color, t);
            } else {
                colorHelper2(Scadrial_color, t);
            }
        }
    }

    public void updateColorAsWatch() {
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

    public void colorHelper2(Text text, Territory t) {
        text.setVisible(true);
        text.setText(t.getColor());
        text.setFill(Color.WHITE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.roomNum.setText("Room1");
    }

    @FXML
    void moveSpy(MouseEvent event) throws InterruptedException {
        actionQueue.clear();
        TerriList.clear();
        this.actionQueue.put("MS");
    }



    @FXML
    void clickSpy(ActionEvent event) {
        isSpy = true;
    }

    @FXML
    void clickBomber(ActionEvent event) {
        isBomber = true;
    }

    @FXML
    void clickFatNerd(ActionEvent event) {
        isFatNerd = true;
    }

    @FXML
    void Cloak(ActionEvent event) throws InterruptedException {
        actionQueue.clear();
        TerriList.clear();
        this.actionQueue.put("Cloak");
    }

    @FXML
    void DivineGuard(ActionEvent event) throws InterruptedException {
        actionQueue.clear();
        TerriList.clear();
        this.actionQueue.put("G");
    }

    private boolean isSpy;
    private boolean isFatNerd;
    private boolean isBomber;

    @FXML
    private Text bomber_text;

    @FXML
    private Text fatnerd_text;

    @FXML
    private  Text spy_text;

    @FXML
    private ImageView Narnia_fog;

    @FXML
    private  ImageView Elantris_fog;

    @FXML
    private  ImageView Gondor_fog;

    @FXML
    private  ImageView Hogwarts_fog;

    @FXML
    private ImageView Midkemia_fog;

    @FXML
    private  ImageView Mordor_fog;

    @FXML
    private ImageView Oz_fog;

    @FXML
    private ImageView Roshar_fog;

    @FXML
    private ImageView Scadrial_fog;

    @FXML
    private ImageView Narnia_guard;

    @FXML
    private  ImageView Elantris_guard;

    @FXML
    private  ImageView Gondor_guard;

    @FXML
    private  ImageView Hogwarts_guard;

    @FXML
    private ImageView Midkemia_guard;

    @FXML
    private  ImageView Mordor_guard;

    @FXML
    private ImageView Oz_guard;

    @FXML
    private ImageView Roshar_guard;

    @FXML
    private ImageView Scadrial_guard;

    public void setGuard(ArrayList<Player> players) {

        for (Player p : players) {
            for (Territory t : p.player_terri_set) {
                if (t.getName().equals("Narnia")) {
                    Pair<Territory, String> res = getTerri(t.getName(), this.clientSk.new_view_map);
                    if (res.getValue() !=null && res.getValue().equals("viewed")) {
                        if (t.getBeGuarded() > 0) {
                            Narnia_guard.setVisible(true);
                        } else {
                            Narnia_guard.setVisible(false);
                        }
                    }
                } else if (t.getName().equals("Elantris")){
                    Pair<Territory, String> res = getTerri(t.getName(), this.clientSk.new_view_map);
                    if (res.getValue() !=null && res.getValue().equals("viewed")) {
                        if (t.getBeGuarded() > 0) {
                            Elantris_guard.setVisible(true);
                        } else {
                            Elantris_guard.setVisible(false);
                        }
                    }
                } else if (t.getName().equals("Gondor")) {
                    Pair<Territory, String> res = getTerri(t.getName(), this.clientSk.new_view_map);
                    if (res.getValue() !=null && res.getValue().equals("viewed")) {
                        if (t.getBeGuarded() > 0) {
                            Gondor_guard.setVisible(true);
                        } else {
                            Gondor_guard.setVisible(false);
                        }
                    }
                } else if (t.getName().equals("Hogwarts")) {
                    Pair<Territory, String> res = getTerri(t.getName(), this.clientSk.new_view_map);
                    if (res.getValue() !=null && res.getValue().equals("viewed")) {
                        if (t.getBeGuarded() > 0) {
                            Hogwarts_guard.setVisible(true);
                        } else {
                            Hogwarts_guard.setVisible(false);
                        }
                    }
                } else if(t.getName().equals("Midkemia")) {
                    Pair<Territory, String> res = getTerri(t.getName(), this.clientSk.new_view_map);
                    if (res.getValue() !=null && res.getValue().equals("viewed")) {
                        if (t.getBeGuarded() > 0) {
                            Midkemia_guard.setVisible(true);
                        } else {
                            Midkemia_guard.setVisible(false);
                        }
                    }
                } else  if (t.getName().equals("Mordor")) {
                    Pair<Territory, String> res = getTerri(t.getName(), this.clientSk.new_view_map);
                    if (res.getValue() !=null && res.getValue().equals("viewed")) {
                        if (t.getBeGuarded() > 0) {
                            Mordor_guard.setVisible(true);
                        } else {
                            Mordor_guard.setVisible(false);
                        }
                    }
                } else if(t.getName().equals("Oz")) {
                    Pair<Territory, String> res = getTerri(t.getName(), this.clientSk.new_view_map);
                    if (res.getValue() !=null && res.getValue().equals("viewed")) {
                        if (t.getBeGuarded() > 0) {
                            Oz_guard.setVisible(true);
                        } else {
                            Oz_guard.setVisible(false);
                        }
                    }
                } else if(t.getName().equals("Roshar")) {
                    Pair<Territory, String> res = getTerri(t.getName(), this.clientSk.new_view_map);
                    if (res.getValue() !=null && res.getValue().equals("viewed")) {
                        if (t.getBeGuarded() > 0) {
                            Roshar_guard.setVisible(true);
                        } else {
                            Roshar_guard.setVisible(false);
                        }
                    }
                } else {
                    Pair<Territory, String> res = getTerri(t.getName(), this.clientSk.new_view_map);
                    if (res.getValue() !=null && res.getValue().equals("viewed")) {
                        if (t.getBeGuarded() > 0) {
                            Scadrial_guard.setVisible(true);
                        } else {
                            Scadrial_guard.setVisible(false);
                        }
                    }
                }
            }
        }
    }

}

