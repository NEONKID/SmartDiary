/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdiary;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
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

/**
 *
 * @author dell
 */
public class MoneyManager implements Initializable {

    @FXML private TextField text1;
    @FXML private TextField text2;
    @FXML private TextField text3;
    @FXML private TextField text4;

    @FXML
    protected void calcMoney(ActionEvent event) {
        int a = Integer.parseInt(text1.getText());
        int b = Integer.parseInt(text2.getText());
        int c = Integer.parseInt(text3.getText());
        double calc = a+b-c;
        DecimalFormat df = new DecimalFormat("#,##0");
        text4.setText(String.valueOf(df.format(calc)));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}