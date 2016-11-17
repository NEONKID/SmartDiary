package smartdiary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by neonkid on 11/16/16.
 */
public class LoginController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final String flag_id = "dlug";
    private final String flag_pw = "dlugdlug";

    @FXML protected void ClickAction(ActionEvent event) {
        if(!flag_id.equals(usernameField.getText())) {
            showError(0);
        } else {
            if(!flag_pw.equals(passwordField.getText())) {
                showError(1);
            } else {
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success !");
                    alert.setHeaderText(usernameField.getText() + "님 환영합니다!");
                    alert.setContentText("마지막 로그인 날짜: ");
                    alert.showAndWait();
                    setScene();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void showError(int code) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error !");
        switch(code) {
            case 0:
                alert.setHeaderText("ID가 일치하지 않습니다!");
                alert.setContentText("사용자ID를 확인 후, 다시 입력해주세요.");
                break;
            case 1:
                alert.setHeaderText("Password가 일치하지 않습니다!");
                alert.setContentText("비밀번호를 확인 후, 다시 입력해주세요.");
                break;
            default:
                alert.setHeaderText("알 수 없는 오류");
                alert.setContentText("프로그래에 문제가 발생했습니다. 개발자에게 문의하세요.");
        }
        alert.showAndWait();
    }

    private void setScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("SmartDiary");
        stage.show();
        Main.getStage().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
