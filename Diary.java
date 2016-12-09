/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdiary.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import smartdiary.SmartDiary;
/**
 *
 * @author wnsud
 */
public class Diary implements Initializable {
    
    @FXML private Button savediary;
    @FXML private Button cleardiary;
    @FXML private ImageView imgweather;
    @FXML private Image img;
    @FXML private JFXDatePicker datePicker;
    @FXML private TextField jaemok;
    @FXML private TextArea naeyong;
    @FXML private TextField plus;
    @FXML private TextField minus;
    @FXML private StackPane stackPane;
    private String weather = "맑음";

    @FXML public void settoday() {
        
    }
    
    @FXML
    public void imgsun(ActionEvent event){
        img = new Image(getClass().getResource("/smartdiary/images/sunny.png").toString());
        imgweather.setImage(img);
        weather = "맑음";
    }
    @FXML
    public void imgcloud(ActionEvent event){
        img = new Image(getClass().getResource("/smartdiary/images/cloudy.png").toString());
        imgweather.setImage(img);
        weather = "구름";
    }
    @FXML
    public void imgrain(ActionEvent event){
        img = new Image(getClass().getResource("/smartdiary/images/rainy.png").toString());
        imgweather.setImage(img);
        weather = "비";
    }
    @FXML
    public void imgsnow(ActionEvent event){
        img = new Image(getClass().getResource("/smartdiary/images/snowy.png").toString());
        imgweather.setImage(img);
        weather = "눈";
    }

    @FXML
    public void saveFile(ActionEvent event){
        try {
            String diaryDir = datePicker.getValue().toString().substring(0, 4);     // 년
            String diaryFile = datePicker.getValue().toString().substring(0, 7);    // 월
            
            File dirOfDiary = new File(SmartDiary.getFile().getPath() + "/Contents/"+"/Diary/"+ diaryDir);
            File fileofDiary = new File(dirOfDiary.getPath() + "/" + diaryFile + ".smd");
            String filePath = fileofDiary.getPath();
            
            // 중복 확인 
            String temp = "";
            DiaryFileReader diaryFileReader = new DiaryFileReader();
            ArrayList<String> lineList = new ArrayList<String>();   // 내용 저장을 위한 ArrayList 정의
            try {
            diaryFileReader.readFile(dirOfDiary + "/" + datePicker.getValue().toString().substring(0, 7)+".smd");
            lineList = diaryFileReader.getList();
            int lineNum = lineList.size();
            int i = 0, j;
            while(i < lineNum) {
                String a = datePicker.getValue().toString();
                String b = null;
                if(lineList.get(i).length() != 0) {
                    b = lineList.get(i).substring(0, lineList.get(i).length());
                }
                if(a.equals(b)) {
                    System.out.println("중복 발견!");
                    while(lineList.get(i).length() < 50)
                    {
                        i++;
                    }
                }
                else
                {
                    if(i == 0){temp = lineList.get(i)+"\n";}
                    else{temp = temp + lineList.get(i)+"\n";}
                }
                i++;
            }
            //System.out.println(lineList.get(0));
            //System.out.println(temp);
            FileWriter fw = new FileWriter(fileofDiary.getPath());
            fw.write(temp);
            fw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
	}
            //중복 검출 끝
            
            AESHelper aesHelper = new AESHelper(UserController.getAESKey());

            if(!dirOfDiary.isDirectory()) {
                dirOfDiary.mkdirs();
            }

            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new FileWriter(filePath, true))); // append = false
                out.println(aesHelper.aesEncode(datePicker.getValue().toString()));
                out.println(aesHelper.aesEncode(weather));
                out.println(aesHelper.aesEncode(jaemok.getText()));
                out.println(aesHelper.aesEncode(naeyong.getText()));
                out.println(aesHelper.aesEncode("------------------------------------------------------------"));
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try {
            String moneyDir = datePicker.getValue().toString().substring(0, 4);
            String moneyFile = datePicker.getValue().toString().substring(0, 7);

            File dirOfMoney = new File(SmartDiary.getFile().getPath() + "/Contents/" + "/Money/" + moneyDir);
            File fileofMoney = new File(dirOfMoney.getPath() + "/" + moneyFile + ".smd");
            String filePath2 = fileofMoney.getPath();
            
            // 중복 확인 
            String temp2 = "";
            DiaryFileReader diaryFileReader = new DiaryFileReader();
            ArrayList<String> lineList = new ArrayList<String>();   // 내용 저장을 위한 ArrayList 정의
            try {
            diaryFileReader.readFile(dirOfMoney + "/" + datePicker.getValue().toString().substring(0, 7)+".smd");
            lineList = diaryFileReader.getList();
            int lineNum = lineList.size();
            int i = 0, j;
            while(i < lineNum) {
                String a = datePicker.getValue().toString();
                String b = null;
                if(lineList.get(i).length() != 0) {
                    b = lineList.get(i).substring(0, lineList.get(i).length());
                }
                if(a.equals(b))
                {
                    System.out.println("중복날짜 검출!");
                    i=i+4;
                }
                else {
                    if(i == 0){temp2 = lineList.get(i)+"\n";}
                    else{temp2 = temp2 + lineList.get(i)+"\n";}
                    i++; }
            }
            FileWriter fw = new FileWriter(fileofMoney.getPath());
            fw.write(temp2);
            fw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
	}
            //중복 검출 끝
            
            AESHelper aesHelper = new AESHelper(UserController.getAESKey());

            if(!dirOfMoney.isDirectory()) {
                dirOfMoney.mkdirs();
            }

            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath2, true))); // append = false
                out.println(aesHelper.aesEncode(datePicker.getValue().toString()));
                out.println(aesHelper.aesEncode(plus.getText()));
                out.println(aesHelper.aesEncode(minus.getText()));
                out.println(aesHelper.aesEncode("------------------------------------------------------------"));
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void clear(ActionEvent event){
        //  clear the textarea after checked by user
        JFXDialogLayout base = new JFXDialogLayout();
                JFXDialog checkClear = new JFXDialog(stackPane, base, JFXDialog.DialogTransition.CENTER);
                JFXButton clearAgree = new JFXButton("Yes");
                JFXButton clearCancel = new JFXButton("No");

                clearAgree.setRipplerFill(Paint.valueOf("#ffffff"));
                clearAgree.setTextFill(Paint.valueOf("#ffffff"));
                clearAgree.setStyle("-fx-background-color: #4059a9");
                clearCancel.setRipplerFill(Paint.valueOf("#ffffff"));
                clearCancel.setTextFill(Paint.valueOf("#ffffff"));
                clearCancel.setStyle("-fx-background-color: #4059a9");
                
                base.setHeading(new Text("null"));
                base.setBody(new Text("저장되지 않은 내용이 지워집니다. 지우겠습니까?"));
                clearAgree.setOnAction((ActionEvent e) -> {
                    checkClear.close();
                    try {
                        //Clear the textfield,area
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                clearCancel.setOnAction((ActionEvent e) -> {
                    checkClear.close();
                    try {
                        //Clear the textfield,area
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                base.setActions(clearAgree, clearCancel);
                checkClear.show();
    }

    @FXML
    public void TextReader() throws IOException {
        String baseDir = SmartDiary.getFile().getPath() + "/Contents" + "/Diary/" + datePicker.getValue().toString().substring(0, 4);
        String save = datePicker.getValue().toString().substring(0, 7)+".smd";       //검색결과가 저장된 파일명
        File dir = new File(baseDir);   // 읽어들일 디렉토리의 객체
        DiaryFileReader diaryFileReader = new DiaryFileReader();
        ArrayList<String> lineList = new ArrayList<String>();   // 내용 저장을 위한 ArrayList 정의

        if(!dir.isDirectory()) {
            // 디렉토리가 아니거나 없으면 종료
            System.out.println(baseDir + " is not directory or exist ");
        }
        diaryFileReader.readFile(baseDir + "/" + save);
        lineList = diaryFileReader.getList();

        jaemok.setText("");
        naeyong.setText("");

        // 라인단위 출력(for loop)
        int lineNum = lineList.size();
        int i = 0, j;
        while(i < lineNum) {
            String a = datePicker.getValue().toString();
            String b = null;
            if(lineList.get(i).length() != 0) {
                b = lineList.get(i).substring(0, lineList.get(i).length());
            }
            if(a.equals(b))
            {
                jaemok.setText(lineList.get(i + 2));
                j = i + 3;
                while(lineList.get(j).length() < 60)
                {
                    if(naeyong.getText()==""){
                        naeyong.setText(lineList.get(j) + "\n");
                        j++;
                        continue;
                    }
                    naeyong.setText(naeyong.getText() + lineList.get(j) + "\n");
                    j++;
                }
                break;
            }
            else { i++; }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
        try {
            TextReader();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate oldDate, LocalDate newDate) {
                try {
                    TextReader();
                    plus.setText("0");
                    minus.setText("0");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        plus.setText("0");
        minus.setText("0");
    }
}