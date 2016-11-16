package smartdiary;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by neonkid on 11/16/16.
 */
public class LoginController implements Initializable {

    @FXML
    private Text actiontarget;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final String flag_id = "dlug1";
    private final String flag_pw = "dlugdlug";

    @FXML protected void ClickAction(ActionEvent event) {
        actiontarget.setText("ID: " + usernameField.getText() + "\t" +"PW: " + passwordField.getText());

        if(!flag_id.equals(usernameField.getText())) {
            actiontarget.setText("Invalid ID !");
        } else {
            if(!flag_pw.equals(passwordField.getText())) {
                actiontarget.setText("Invalid password !");
            } else {
                try {
                    setScene();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void setScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("SmartDiary");
        stage.show();
        SmartDiary.getStage().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
