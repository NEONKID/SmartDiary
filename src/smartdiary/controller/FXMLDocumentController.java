package smartdiary.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXRadioButton;
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author wnsud
 */
public class FXMLDocumentController implements Initializable {
    @FXML private ToggleGroup radioGroups;
    @FXML private JFXRadioButton diaryButton;
    @FXML private JFXRadioButton scheduleButton;
    @FXML private JFXRadioButton moneyButton;
    @FXML private AnchorPane Diary;
    @FXML private AnchorPane MoneyManager;
    @FXML private AnchorPane Schedule;
    private static Stage stage;
    
    public static Stage getStage() {
        return stage;
    }

    @FXML
    public void wipeData(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/smartdiary/view/DataManager.fxml"));
        StackPane newWindow = (StackPane)loader.load();
        DataController controller = loader.getController();
        controller.setMainWindow(this);

        stage = new Stage();
        stage.setTitle("SmartDiary 초기화");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(newWindow);
        scene.getStylesheets().add(getClass().getResource("/smartdiary/css/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loadUserController(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/smartdiary/view/UserManager.fxml"));
        StackPane newWindow = (StackPane)loader.load();
        UserController controller = loader.getController();
        controller.setMainWindow(this);
        
        stage = new Stage();
        stage.setTitle("비밀번호 변경");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        Scene scene = new Scene(newWindow);
        scene.getStylesheets().add(getClass().getResource("/smartdiary/css/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loadLoginHistoryView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/smartdiary/view/loginHistory.fxml"));
        StackPane newWindow = (StackPane) loader.load();
        HistoryController controller = loader.getController();
        controller.setMainWindow(this);

        stage = new Stage();
        stage.setTitle("로그인 기록");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(newWindow);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loadAboutProgram(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/smartdiary/view/aboutSmartDiary.fxml"));
        StackPane newWindow = (StackPane) loader.load();
        AboutController controller = loader.getController();
        controller.setMainWindow(this);

        stage = new Stage();
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(newWindow);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> stage.close());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        radioGroups.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observableValue, Toggle old_toggle, Toggle new_toggle) -> {
            if(radioGroups.getSelectedToggle() == diaryButton) {
                System.out.println("Diary Clicked");
                diaryLoad();
            } else if(radioGroups.getSelectedToggle() == scheduleButton) {
                System.out.println("Scheduler Clicked");
                schedulerLoad();
            } else if(radioGroups.getSelectedToggle() == moneyButton) {
                System.out.println("Money Clicked");
                moneyLoad();
            }
        });
    }

    private void diaryLoad() {
        Schedule.setVisible(false);
        MoneyManager.setVisible(false);
        Diary.setVisible(true);
    }

    private void schedulerLoad() {
        Schedule.setVisible(true);
        Diary.setVisible(false);
        MoneyManager.setVisible(false);
    }

    private void moneyLoad() {
        MoneyManager.setVisible(true);
        Diary.setVisible(false);
        Schedule.setVisible(false);
    }
}