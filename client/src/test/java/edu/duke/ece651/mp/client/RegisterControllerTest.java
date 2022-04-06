package edu.duke.ece651.mp.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.util.WaitForAsyncUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import javafx.scene.text.Text;


@ExtendWith(ApplicationExtension.class)
public class RegisterControllerTest {
  /*private TextField account_textfield;
    private TextField password_textfield;
    private TextField password_textfield1;
    private Button register_button;
    private Text Invalid_label;

    private TextField testText;
    private RegisterController cont;
    @Start
    private void start(Stage stage) {
      account_textfield = new TextField();
      password_textfield = new TextField();
      password_textfield1 = new TextField();
      register_button = new Button();
      Invalid_label = new Text();

      //model = mock(RPNStack.class); //this is what is new here!
        cont = new RegisterController();
        cont.currentNumber = testText;
    }
    private void addNums(String ... strs) {
        Platform.runLater(()->{
            for (String s: strs){
                Button b = new Button(s);
                cont.onNumberButton(new ActionEvent(b,null));
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
    }
    @Test
    public void test_onNumberButton_7(FxRobot robot) {
        addNums("7");
        FxAssert.verifyThat(testText, TextInputControlMatchers.hasText("7"));
    }
    @Test
    public void test_onNumberButton_pi(FxRobot robot) {
        addNums("3", ".", "1", "4");
        FxAssert.verifyThat(testText, TextInputControlMatchers.hasText("3.14"));
    }

    @Test
    void test_enterButton(FxRobot robot) {
        Platform.runLater(()->{
            testText.setText("1234.5");
            Button b = new Button("Enter");
            cont.onEnter(new ActionEvent(b,null));
        });
        WaitForAsyncUtils.waitForFxEvents();
        verify(model).pushNum(1234.5);
        verifyNoMoreInteractions(model);
        FxAssert.verifyThat(testText, TextInputControlMatchers.hasText(""));
    }

  */
}




