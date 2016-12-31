package smartdiary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import smartdiary.SmartDiary;
import smartdiary.model.AESHelper;
import smartdiary.model.ExAlert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by neonkid on 12/28/16.
 */
public class DataController implements Initializable {
    @FXML private DialogPane wipePane;
    @FXML private ButtonType cancelButtonType;
    @FXML private ButtonType finishButtonType;
    @FXML private PasswordField passwordField;
    private FXMLDocumentController mainWindow;

    public void setMainWindow(FXMLDocumentController mainWindow) { this.mainWindow = mainWindow; }

    private void closeWindow() { FXMLDocumentController.getStage().close(); }

    private void passwordCheck() {
        try {
            String old_pw = LoginController.readContentFrom(SmartDiary.getFile().getPath() + File.separator + "shadow");
            AESHelper aesHelper = new AESHelper(UserController.getAESKey());
            if(!aesHelper.getSha512(passwordField.getText()).equals(old_pw)) {
                showAlert(0);
            } else {
                showAlert(1);
            }
        } catch (IOException ex) {
            ExAlert alert = new ExAlert(ex, "shadow 파일을 읽을 수 없습니다!");
            alert.show();
        } catch (Exception ex) {
            ExAlert alert = new ExAlert(ex, "패스워드 확인 중, 오류가 발생했습니다. \n" + "암/복호화 문제일 수 있습니다. 개발자에게 문의하세요.");
            alert.show();
            ex.printStackTrace();
        }
    }

    private void showAlert(int flag) {
        Alert alert;
        switch(flag) {
            case 0:
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("비밀번호 오류");
                alert.setHeaderText("비밀번호가 올바르지 않습니다.");
                alert.setContentText("정확한 비밀번호를 입력하지 않으면, \n초기화하실 수 없습니다.");
                alert.show();
                break;
            default:
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("SmartDiary 사용자 데이터 초기화");
                alert.setHeaderText("정말로 초기화 하시겠습니까?");
                alert.setContentText("여기서 계속 진행하시면, 취소하실 수 없습니다.");
                Optional<ButtonType>result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    wipeData(SmartDiary.getFile());
                    if(!SmartDiary.getFile().isDirectory()) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("초기화 완료");
                        alert.setHeaderText("모든 데이터가 초기화되었습니다.");
                        alert.setContentText("프로그램을 종료합니다.");
                        alert.showAndWait();
                        System.exit(0);
                    }
                } else {
                    closeWindow();
                }
                break;
        }
    }

    private void wipeData(File userFile) {
        try {
            File[] userallFiles = userFile.listFiles();
            assert userallFiles != null;
            for (File file : userallFiles) {
                if(file.isFile()) {
                    file.delete();
                } else {
                    wipeData(file);
                }
                file.delete();
            }
            userFile.delete();
        } catch (AssertionError ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("초기화 오류");
            alert.setHeaderText("Critical Error");
            alert.setContentText("초기화 하는 중 오류가 발생했습니다. \n" + "개발자에게 문의하세요.");
            alert.show();
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Node cancelButton = wipePane.lookupButton(cancelButtonType);
        cancelButton.setOnMouseClicked(mouseEvent -> closeWindow());

        Node finishButton = wipePane.lookupButton(finishButtonType);
        finishButton.setOnMouseClicked(mouseEvent -> passwordCheck());
    }
}
