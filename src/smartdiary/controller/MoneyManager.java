/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdiary.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import smartdiary.Diary.DiaryFileReader;
import smartdiary.SmartDiary;

/**
 *
 * @author dell
 */
public class MoneyManager implements Initializable {
    @FXML private TextField text1;
    @FXML private TextField text2;
    @FXML private TextField text3;
    @FXML private TextField text4;
    @FXML private TableView<Money> tableView;
    @FXML private TableColumn<Money, String> Date;
    @FXML private TableColumn<Money, String> plus;
    @FXML private TableColumn<Money, String> minus;
    @FXML private DatePicker datepicker;

    
    private final ObservableList<Money>data = FXCollections.observableArrayList();
    
    @FXML
    public void TextReader() throws IOException {
        data.clear();
        String month = datepicker.getValue().toString();
    
        String baseDir = SmartDiary.getFile().getPath() + "/Contents" + "/Money/" + month.substring(0,4);
        String save = month.substring(0, 7)+".smd";       //검색결과가 저장된 파일명
        File dir = new File(baseDir);   // 읽어들일 디렉토리의 객체
        
        DiaryFileReader diaryFileReader = new DiaryFileReader();
        ArrayList<String> lineList; // 내용 저장을 위한 ArrayList 정의
        int plus = 0, minus = 0, i = 0;
        
        if(!dir.isDirectory()) {    // 디렉토리가 아니거나 없으면...
            System.out.println(baseDir + " is not directory or exist ");
            dir.mkdirs();
        }
        diaryFileReader.readFile(baseDir + "/" + save);
        lineList = diaryFileReader.getList();

        int lineNum = lineList.size();  // 라인 단위 저장 및 카운트를 위한 변수 정의
        while(i < lineNum) {
            data.add(new Money(lineList.get(i), lineList.get(i + 1), lineList.get(i + 2)));
            plus = plus + Integer.parseInt(lineList.get(i + 1));
            minus = minus + Integer.parseInt(lineList.get(i + 2));
            i = i + 4;
        }
        tableView.setItems(data);

          
        text2.setText(String.valueOf(plus));
        text3.setText(String.valueOf(minus));
        int x = Integer.parseInt(text1.getText());
        int y = Integer.parseInt(text2.getText());
        int z = Integer.parseInt(text3.getText());
        
        double calc = x + y - z;
        DecimalFormat df = new DecimalFormat("#,##0");
        text4.setText(String.valueOf(df.format(calc)));
    }
    
    
    
    @FXML
    public void Date_OnEditCommit(TableColumn.CellEditEvent<Money, String> event) {
        Money money = event.getRowValue();
        money.setDate(event.getNewValue());
    }
    @FXML
    public void plus_OnEditCommit(TableColumn.CellEditEvent<Money, String> event) {
        Money money = event.getRowValue();
        money.setPlus(event.getNewValue());
    }
    @FXML
    public void minus_OnEditCommit(TableColumn.CellEditEvent<Money, String> event) {
        Money money = event.getRowValue();
        money.setMinus(event.getNewValue());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datepicker.setValue(LocalDate.now());
    }
}