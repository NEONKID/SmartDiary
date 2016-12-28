package smartdiary.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import smartdiary.SmartDiary;
import smartdiary.model.ExAlert;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by neonkid on 12/28/16.
 */
public class HistoryController implements Initializable {
    private FXMLDocumentController mainWindow;
    private final String path = SmartDiary.getFile().getPath() + "/login.log";
    private final ArrayList<String>arrayList = new ArrayList<>();

    @FXML private TextArea logArea;

    public void setMainWindow(FXMLDocumentController mainWindow) { this.mainWindow = mainWindow; }

    @FXML
    public void closeWindow(ActionEvent event) {
        FXMLDocumentController.getStage().close();
    }

    private void readFile() {
        String sLine;
        try {
            BufferedReader bufferedReader;
            try(FileReader fileReader = new FileReader(path)) {
                bufferedReader = new BufferedReader(fileReader);
                while((sLine = bufferedReader.readLine()) != null) {
                    arrayList.add(sLine);
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            ExAlert alert = new ExAlert(ex, "login.log 파일을 찾을 수 없습니다!");
            alert.show();
        } catch (Exception ex) {
            ExAlert alert = new ExAlert(ex);
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readFile();

        for (String logStr : arrayList) {
            logArea.setWrapText(true);
            logArea.appendText(logStr + "\n");
        }
    }
}
