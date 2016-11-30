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

import java.io.*;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by neonkid on 11/16/16.
 */
public class LoginController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    private File logfile;

    private String flag_id = System.getProperty("user.name");

    @FXML protected void ClickAction(ActionEvent event) {
        String path = SmartDiary.getFile().getPath() + "/shadow";
        String flag_pw = "";
        try {
            flag_pw = readContentFrom(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
                    alert.setContentText("로그인에 성공하셨습니다.");
                    writeLog(0);
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
                writeLog(2);
                break;
            case 1:
                alert.setHeaderText("Password가 일치하지 않습니다!");
                alert.setContentText("비밀번호를 확인 후, 다시 입력해주세요.");
                writeLog(3);
                break;
            default:
                alert.setHeaderText("알 수 없는 오류");
                alert.setContentText("프로그램에 문제가 발생했습니다. 개발자에게 문의하세요.");
                writeLog(-1);
        }
        alert.showAndWait();
    }

    private void writeLog(int code) {
        logfile = new File(SmartDiary.getFile().getPath() + "/login.log");
        Date date = new Date();
        String out = "";
        switch(code) {
            case 0:
                out = "[Success] Login: " + date.toString() + "\n";
                break;
            case 2:
                out = "[Fail] Invalid ID ! " + date.toString() + "\n";
                break;
            case 3:
                out = "[Fail] Invalid Password ! " + date.toString() + "\n";
                break;
            default:
                out = "[Crit] Unknwon Error" + date.toString() + "\n";
                break;
        }
        try {
            FileWriter fw = new FileWriter(logfile, true);
            fw.write(out);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setTitle("SmartDiary");
        stage.show();
        SmartDiary.getStage().hide();
    }

    public static String readContentFrom(String textFileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(textFileName));
        StringBuilder builder = new StringBuilder();
        char[] buf = new char[8];

        while(bufferedReader.read(buf) > 0) {
            builder.append(buf);
        }
        System.out.println(builder.toString());
        return builder.toString();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameField.setText(System.getProperty("user.name"));
    }
}