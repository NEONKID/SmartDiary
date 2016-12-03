/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdiary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author wnsud
 */
public class SmartDiary extends Application {
    private static Stage stage;
    private String username = System.getProperty("user.name");
    private String osname = System.getProperty("os.name");
    private String homedir = System.getProperty("user.home");
    private Alert alert;
    private static File file;

    public static Stage getStage() {
        return stage;
    }

    public static File getFile() {
        return file;
    }

    @Override
    public void start(Stage LoginStage) throws Exception {
        boolean flag = false;
        Parent loginView = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));

        System.out.println("Username: " + username);
        System.out.println("Used OS: " + osname);
        System.out.println("Directory: " + homedir);
        flag = checkOS(osname);

        if(flag != false) {
            stage = LoginStage;
            stage.setTitle("Welcome to SmartDiary !");
            stage.setScene(new Scene(loginView));
            stage.sizeToScene();
            stage.show();
        } else {
            registerUser();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private boolean checkOS(String name) {
        String path = "";
        int win_flag = name.indexOf("win");
        int nux_flag = name.indexOf("nux");

        if(nux_flag > 0) {
            path = homedir + "/.smartDiary";
            file = new File(path);
            if(!file.isDirectory()) {
                showError(1);
                file.mkdirs();
                return false;
            } else {
                file = new File(path);
                return true;
            }
        } else if(win_flag > 0) {
            path = homedir + "\\.smartDiary";
            file = new File(path);
            if(!file.exists()) {
                showError(1);
                file.mkdirs();
                return false;
            } else {
                file = new File(path);
                return true;
            }
        } else {
            showError(2);
        }
        return false;
    }

    private void showError(int code) {
        switch(code) {
            case 0:
                break;
            case 1:
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("프로그램 알림");
                alert.setHeaderText("SmartDiary 초기 설정");
                alert.setContentText("프로그램의 기존 정보가 존재하지 않습니다.\n초기 사용자 설정을 진행합니다.");
                alert.showAndWait();
                break;
            case 2:
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("프로그램 실행 오류");
                alert.setHeaderText("지원하지 않는 운영체제입니다.");
                alert.setContentText("이 프로그램은 Windows, Linux 운영체제에서만 사용 가능합니다.");
                alert.showAndWait();
                System.exit(0);
                break;
        }
    }

    private void registerUser() {
        try {
            Parent registerView = FXMLLoader.load(getClass().getResource("fxml/registerUser.fxml"));

            Scene scene = new Scene(registerView);
            Stage registerStage = new Stage();

            registerStage.setScene(scene);
            registerStage.setTitle("사용자 등록");
            registerStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
