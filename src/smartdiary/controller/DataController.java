package smartdiary.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import smartdiary.SmartDiary;
import smartdiary.model.AESHelper;
import smartdiary.model.ExAlert;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by neonkid on 12/28/16.
 */
public class DataController implements Initializable {
    @FXML private StackPane dataPane;
    @FXML private PasswordField passwordField;
    @FXML private JFXButton runButton;
    private FXMLDocumentController mainWindow;

    public void setMainWindow(FXMLDocumentController mainWindow) { this.mainWindow = mainWindow; }

    @FXML
    private void closeWindow() { FXMLDocumentController.getStage().close(); }

    @FXML
    private void passwordCheck() {
        try {
            String old_pw = LoginController.readContentFrom(SmartDiary.getFile().getPath() + File.separator + "shadow");
            AESHelper aesHelper = new AESHelper(UserController.getAESKey());
            if(!aesHelper.getSha512(passwordField.getText()).equals(old_pw)) {
                showAlert(0);
            } else {
                showAlert(1);
            }
        } catch (FileNotFoundException ex) {
            ExAlert alert = new ExAlert(ex, "shadow 파일을 찾을 수 없습니다!");
            alert.show();
        } catch (Exception ex) {
            ExAlert alert = new ExAlert(ex);
            alert.show();
        }
    }

    private void showAlert(int flag) {
        JFXDialogLayout dialog = new JFXDialogLayout();
        switch(flag) {
            case 0:
                JFXDialog wrongPwd = new JFXDialog(dataPane, dialog, JFXDialog.DialogTransition.CENTER);
                JFXButton okBtn = new JFXButton("확인");
                dialog.setHeading(new Text("비밀번호 오류"));
                dialog.setBody(new Text("비밀번호가 올바르지 않습니다. \n" + "정확한 비밀번호를 입력해주세요."));
                okBtn.setId("blue-button");
                okBtn.setOnAction((ActionEvent event) -> {
                    passwordField.clear();
                    wrongPwd.close();
                });
                dialog.setActions(okBtn);
                wrongPwd.show();
                break;
            default:
                JFXDialog checkWipe = new JFXDialog(dataPane, dialog, JFXDialog.DialogTransition.CENTER);
                JFXButton wipeAgree = new JFXButton("예");
                JFXButton wipeCancel = new JFXButton("아니요");

                dialog.setHeading(new Text("정말로 초기화하시겠습니까?"));
                dialog.setBody(new Text("여기서 계속 진행하시면, 취소하실 수 없습니다."));
                wipeCancel.setId("left-button");
                wipeAgree.setId("wipe-button");

                wipeCancel.setOnAction((ActionEvent event) -> {
                    checkWipe.close();
                    passwordField.clear();
                });
                wipeAgree.setOnAction((ActionEvent event) -> {
                    checkWipe.close();
                    wipeData(SmartDiary.getFile());
                    if(!SmartDiary.getFile().isDirectory()) {
                        JFXDialogLayout findialog = new JFXDialogLayout();
                        JFXDialog finWipe = new JFXDialog(dataPane, findialog, JFXDialog.DialogTransition.CENTER);
                        JFXButton exitButton = new JFXButton("확인");
                        exitButton.setId("blue-button");
                        exitButton.setOnAction((ActionEvent e) -> System.exit(0));
                        findialog.setHeading(new Text("초기화 완료"));
                        findialog.setBody(new Text("모든 데이터가 초기화되었습니다. \n프로그램을 종료합니다."));
                        findialog.setActions(exitButton);
                        finWipe.show();
                    } else {
                        JFXDialogLayout failDialog = new JFXDialogLayout();
                        JFXDialog failWipe = new JFXDialog(dataPane, failDialog, JFXDialog.DialogTransition.CENTER);
                        JFXButton exitButton = new JFXButton("확인");
                        exitButton.setId("blue-button");
                        exitButton.setOnAction((ActionEvent e) -> System.exit(0));
                        failDialog.setHeading(new Text("초기화 실패"));
                        failDialog.setBody(new Text("데이터 초기화 작업에 일부 오류가 있었습니다. \n몇몇 파일이 삭제되지 않았을 수 있습니다."));
                        failDialog.setActions(exitButton);
                        failWipe.show();
                    }
                });
                dialog.setActions(wipeAgree, wipeCancel);
                checkWipe.show();
                break;
        }
    }

    private void wipeData(File userFile) {
        try {
            File[] userallFiles = userFile.listFiles();
            assert userallFiles != null;
            for (File file : userallFiles) {
                if(file.isFile()) {
                    if(file.delete()) {
                        System.out.println("Success !");
                    } else {
                        System.err.println("Fail !");
                        return;
                    }
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
        } catch (Exception ex) {
            ExAlert alert = new ExAlert(ex);
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(passwordField.getText().isEmpty()) {
                runButton.setDisable(true);
            } else {
                runButton.setDisable(false);
            }
        });
    }
}
