package smartdiary.controller;

import javafx.event.ActionEvent;
import smartdiary.model.Money;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXDatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import smartdiary.model.Diary.DiaryFileReader;
import smartdiary.SmartDiary;

/**
 *
 * @author
 *
 * last modified by neonkid 12/28/16
 */
public class MoneyController implements Initializable {
    @FXML private TextField text1;
    @FXML private TextField text2;
    @FXML private TextField text3;
    @FXML private TextField text4;
    @FXML private TableView<Money> tableView;
    @FXML private JFXDatePicker datepicker;
    
    private final ObservableList<Money>data = FXCollections.observableArrayList();
    
    @FXML
    public void MoneyReader(ActionEvent event) {
        String month = datepicker.getValue().toString();
    
        String baseDir = SmartDiary.getFile().getPath() + "/Contents" + "/Money/" + month.substring(0,4);
        String save = month.substring(0, 7)+".smd";       // 검색결과가 저장된 파일명
        File dir = new File(baseDir);   // 읽어들일 디렉토리의 객체
        
        DiaryFileReader diaryFileReader = new DiaryFileReader();
        ArrayList<String> lineList; // 내용 저장을 위한 ArrayList 정의
        DecimalFormat df = new DecimalFormat("#,##0");

        int plus = 0, minus = 0, i = 0;

        if(!dir.isDirectory()) {    // 디렉토리가 아니거나 없으면...
            System.out.println(baseDir + " is not directory or exist ");
            dir.mkdirs();
        }
        diaryFileReader.readFile(baseDir + "/" + save);
        lineList = diaryFileReader.getList();
        data.clear();

        if(!isStringNum(text1.getText().trim().replaceAll(",", ""))) {
            text1.setText("0");
        }
        while(i < lineList.size()) {
            data.add(new Money(lineList.get(i), String.valueOf(df.format(Integer.parseInt(lineList.get(i + 1)))),
                    String.valueOf(df.format(Integer.parseInt(lineList.get(i + 2))))));
            plus += Integer.parseInt(lineList.get(i + 1));
            minus += Integer.parseInt(lineList.get(i + 2));
            i += 4;
        }
        tableView.setItems(data);
        text2.setText(String.valueOf(df.format(plus)));
        text3.setText(String.valueOf(df.format(minus)));

        int x = Integer.parseInt(text1.getText().trim().replaceAll(",", ""));
        int y = Integer.parseInt(text2.getText().trim().replaceAll(",", ""));
        int z = Integer.parseInt(text3.getText().trim().replaceAll(",", ""));
        double cal = x + y - z;

        text1.setText(String.valueOf(df.format(x)));
        text4.setText(String.valueOf(df.format(cal)));
    }

    private boolean isStringNum(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
       
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datepicker.setValue(LocalDate.now());
    }
}