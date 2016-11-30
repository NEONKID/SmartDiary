package smartdiary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by neonkid on 11/28/16.
 */
public class UserController implements Initializable {
    @FXML private TextField userid;
    @FXML private PasswordField field1;
    @FXML private PasswordField field2;
    private File shadow;

    @FXML
    public void clickOK(ActionEvent event) {
        if(!field1.getText().equals(field2.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("비밀번호 불일치!");
            alert.setHeaderText("비밀번호가 일치하지 않습니다.");
            alert.setContentText("다시 확인하고 입력해주시기 바랍니다.");
            alert.showAndWait();
        } else if(field1.getLength() != 8 && field2.getLength() != 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("비밀번호 입력 수 제한");
            alert.setHeaderText("비밀번호는 반드시 8자리여야 합니다.");
            alert.setContentText("다시 확인하고 입력해주시기 바랍니다.");
            alert.showAndWait();
        }
        else {
            try {
                shadow = new File(SmartDiary.getFile().getPath() + "/shadow");
                FileWriter fw = new FileWriter(shadow, true);
                fw.write(field1.getText().toString());
                fw.flush();
                fw.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("설정 완료");
                alert.setHeaderText("비밀번호 설정이 완료되었습니다.");
                alert.setContentText("프로그램을 다시 실행해 주세요.");
                alert.showAndWait();
                System.exit(0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void clickCancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("프로그램 종료");
        alert.setHeaderText("취소하겠습니까?");
        alert.setContentText("취소할 경우, 푸로그램이 종료됩니다.");
        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> System.exit(0));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userid.setText(System.getProperty("user.name"));
    }
}
