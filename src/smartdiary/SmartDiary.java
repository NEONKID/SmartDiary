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
import javafx.stage.Stage;

/**
 *
 * @author wnsud
 */
public class SmartDiary extends Application {
    private static Stage stage;

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
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
