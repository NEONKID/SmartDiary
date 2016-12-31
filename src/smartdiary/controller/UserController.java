package smartdiary.controller;

import smartdiary.model.AESHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXDialog;
import java.io.BufferedWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import smartdiary.SmartDiary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.text.Text;

/**
 * Created by neonkid on 11/28/16.
 */
public class UserController implements Initializable {
    @FXML private JFXTextField userid;
    @FXML private JFXPasswordField field1;
    @FXML private JFXPasswordField field2;
    @FXML private JFXPasswordField old_field;
    @FXML private JFXPasswordField new_field;
    @FXML private JFXPasswordField check_field;
    @FXML private StackPane userPane;
    private FXMLDocumentController mainWindow;
    private static final String AESKey = "SDiary of" + System.getProperty("user.name") + " user";

    public static String getAESKey() {
        try {
            AESHelper aesHelper = new AESHelper(AESKey);
            return aesHelper.getSha512(AESKey);
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    @FXML
    public void clickSubmit(ActionEvent event) {
        try {
            String old_pw;
            old_pw = LoginController.readContentFrom(SmartDiary.getFile().getPath() + File.separator + "shadow");
            
            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(userPane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton button = new JFXButton("OKAY");

            AESHelper aesHelper = new AESHelper(getAESKey());

            button.setId("right-button");

            if(!aesHelper.getSha512(old_field.getText()).equals(old_pw)) {
                content.setHeading(new Text("변경 실패"));
                content.setBody(new Text("기존 패스워드가 올바르지 않습니다. \n다시 입력해주세요."));
                button.setOnAction((ActionEvent e) -> {
                    allClear();
                    dialog.close();
                });
                content.setActions(button);
                dialog.show();
            } else if(!new_field.getText().equals(check_field.getText()) || new_field.getText().equals("")) {
                content.setHeading(new Text("변경 실패"));
                content.setBody(new Text("새로이 설정한 패스워드가 올바르지 않습니다. \n다시 입력해주세요."));
                button.setOnAction((ActionEvent e) -> {
                    allClear();
                    dialog.close();
                });
                content.setActions(button);
                dialog.show();
            } else {
                content.setHeading(new Text("변경 성공"));
                content.setBody(new Text("패스워드가 변경되었습니다."));
                button.setOnAction((ActionEvent e) -> {
                    try {
                        setPassword(new_field.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    dialog.close();
                    FXMLDocumentController.getStage().close();
                });
                content.setActions(button);
                dialog.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void changeCancel(ActionEvent event) {
        FXMLDocumentController.getStage().close();
    }

    @FXML
    public void clickOK(ActionEvent event) {
        if(!field1.getText().equals(field2.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("비밀번호 불일치!");
            alert.setHeaderText("비밀번호가 일치하지 않습니다.");
            alert.setContentText("다시 확인하고 입력해주시기 바랍니다.");
            alert.showAndWait();
        } else {
            try {
                setPassword(field1.getText());
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
        alert.setContentText("취소할 경우, 프로그램이 종료됩니다.");
        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> System.exit(0));
    }
    
    private void allClear() {
        old_field.clear();
        new_field.clear();
        check_field.clear();
    }
    
    private void setPassword(String password) throws IOException {
        File shadow = new File(SmartDiary.getFile().getPath() + File.separator + "shadow");
        
        if(!SmartDiary.getFile().isDirectory()) {
            SmartDiary.getFile().mkdirs();
        }

        try (BufferedWriter out = new BufferedWriter(new FileWriter(shadow))) {
            AESHelper aesHelper = new AESHelper(getAESKey());
            out.write(aesHelper.getSha512(password));
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setMainWindow(FXMLDocumentController mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(userid != null) {
            userid.setText(System.getProperty("user.name"));
        }
    }
}
