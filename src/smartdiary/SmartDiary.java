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

/**
 *
 * @author wnsud
 */
public class SmartDiary extends Application {
    private static Stage stage;
    private String username = System.getProperty("user.name");
    private Alert alert;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage LoginStage) throws Exception {
        Parent loginView = FXMLLoader.load(getClass().getResource("Login.fxml"));

        stage = LoginStage;
        stage.setTitle("Welcome to SmartDiary !");
        stage.setScene(new Scene(loginView));
        stage.show();

        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("user.name"));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void checkOS(int code) {
        switch(code) {
            case 0:
                break;
            case 1:
                break;
            default:
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("프로그램 실행 오류");
                alert.setHeaderText("");
                alert.setContentText("");
                break;
        }
    }

}
