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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
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

    @FXML
    public void TextReader() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
    
        String baseDir = SmartDiary.getFile().getPath() + "/Contents" + "/Money/" + today.substring(0,4);
        String save = today.substring(0, 7)+".smd";       //검색결과가 저장된 파일명
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
            String a = today.substring(0, 7);
            String b = null;
            plus = plus + Integer.parseInt(lineList.get(i + 1));
            minus = minus + Integer.parseInt(lineList.get(i + 2));
            i = i + 4;
        }
          
        text2.setText(String.valueOf(plus));
        text3.setText(String.valueOf(minus));
        int x = Integer.parseInt(text1.getText());
        int y = Integer.parseInt(text2.getText());
        int z = Integer.parseInt(text3.getText());
        
        double calc = x + y - z;
        DecimalFormat df = new DecimalFormat("#,##0");
        text4.setText(String.valueOf(df.format(calc)));
    }  
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}