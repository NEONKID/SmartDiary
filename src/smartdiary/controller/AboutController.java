package smartdiary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by neonkid on 12/31/16.
 */
public class AboutController implements Initializable {
    @FXML private Label jreLabel, jdkLabel;
    private FXMLDocumentController mainWindow;

    public void setMainWindow(FXMLDocumentController mainWindow) { this.mainWindow = mainWindow; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jreLabel.setText("JRE: " + System.getProperty("java.runtime.version"));
        jdkLabel.setText(null);
    }
}
