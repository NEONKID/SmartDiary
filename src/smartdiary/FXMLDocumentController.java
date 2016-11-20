/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdiary;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author wnsud
 */
public class FXMLDocumentController implements Initializable {
    @FXML private ToggleGroup radioGroups;
    @FXML private RadioButton diaryButton;
    @FXML private RadioButton scheduleButton;
    @FXML private RadioButton moneyButton;
    @FXML private AnchorPane MoneyManager;

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
        MoneyManager.setVisible(false);
    }

    private void schedulerLoad() {
        MoneyManager.setVisible(false);
    }

    private void moneyLoad() {
        MoneyManager.setVisible(true);
    }
}