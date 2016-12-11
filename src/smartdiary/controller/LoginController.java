package smartdiary.controller;

import smartdiary.aesEnDecrypt.AESHelper;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import smartdiary.SmartDiary;

import java.io.*;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by neonkid on 11/16/16.
 */
public class LoginController implements Initializable {

    @FXML private JFXTextField usernameField;
    @FXML private JFXPasswordField passwordField;
    @FXML private StackPane stackPane;

    private final String flag_id = System.getProperty("user.name");

    @FXML 
    protected void ClickAction(ActionEvent event) {
        String path;
        String flag_pw = "";
        path = SmartDiary.getFile().getPath() + "/shadow";

        try {
            AESHelper aesHelper = new AESHelper(UserController.getAESKey());
            flag_pw = readContentFrom(path);

            if(!flag_id.equals(usernameField.getText())) {
                showError(0);
            } else {
                if(!flag_pw.equals(aesHelper.aesEncode(passwordField.getText()))) {
                    showError(1);
                    passwordField.clear();
                } else {
                    JFXDialogLayout content = new JFXDialogLayout();
                    JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
                    JFXButton button = new JFXButton("OKAY");

                    button.setRipplerFill(Paint.valueOf("#ffffff"));
                    button.setTextFill(Paint.valueOf("#ffffff"));
                    button.setStyle("-fx-background-color: #4059a9");

                    content.setHeading(new Text("로그인 성공"));
                    content.setBody(new Text(usernameField.getText() + "님 환영합니다!"));
                    writeLog(0);
                    button.setOnAction((ActionEvent e) -> {
                        dialog.close();
                        try {
                            setScene();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    content.setActions(button);
                    dialog.show();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showError(int code) {
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("OKAY");

        button.setRipplerFill(Paint.valueOf("#ffffff"));
        button.setTextFill(Paint.valueOf("#ffffff"));
        button.setStyle("-fx-background-color: #4059a9");

        switch(code) {
            case 0:
                content.setHeading(new Text("로그인 오류"));
                content.setBody(new Text("사용자ID를 확인 후, 다시 입력해주세요."));
                writeLog(2);
                break;
            case 1:
                content.setHeading(new Text("로그인 오류"));
                content.setBody(new Text("비밀번호를 확인 후, 다시 입력해주세요."));
                writeLog(3);
                break;
            default:
                content.setHeading(new Text("알 수 없는 오류"));
                content.setBody(new Text("프로그램에 문제가 발생했습니다. 개발자에게 문의하세요."));
                writeLog(-1);
        }
        button.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(button);
        dialog.show();
    }

    private void writeLog(int code) {
        File logfile = new File(SmartDiary.getFile().getPath() + "/login.log");
        Date date = new Date();
        String out = "";
        
        switch(code) {
            case 0:
                out = "[Success] Login: \t" + date.toString() + "\n";
                break;
            case 2:
                out = "[Fail] Invalid ID ! \t" + date.toString() + "\n";
                break;
            case 3:
                out = "[Fail] Invalid Password ! \t" + date.toString() + "\n";
                break;
            default:
                out = "[Crit] Unknwon Error \t" + date.toString() + "\n";
                break;
        }
        try {
            FileWriter fw = new FileWriter(logfile, true);
            fw.write(out);
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/smartdiary/fxml/FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/smartdiary/css/main.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setTitle("SmartDiary");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/smartdiary/images/pIcon.png")));
        stage.setResizable(false);
        stage.show();
        SmartDiary.getStage().close();
    }

    public static String readContentFrom(String textFileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(textFileName));
        StringBuilder builder = new StringBuilder();
        String shadow;

        while((shadow = bufferedReader.readLine()) != null) {
            builder.append(shadow);
        }
        return builder.toString();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameField.setText(System.getProperty("user.name"));
    }
}