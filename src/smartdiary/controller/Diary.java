/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdiary.controller;

import com.jfoenix.controls.JFXDatePicker;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.DatePicker;
import java.util.ResourceBundle;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import smartdiary.SmartDiary;
/**
 *
 * @author wnsud
 */
public class Diary implements Initializable {
    
    @FXML private Button sunny;
    @FXML private Button snowy;
    @FXML private Button rainy;
    @FXML private Button cloudy;
    @FXML private Button savediary;
    @FXML private Button cleardiary;
    @FXML private ImageView imgweather;
    @FXML private Image img;
    @FXML private JFXDatePicker datePicker;
    @FXML private TextField jaemok;
    @FXML private TextArea naeyong;
    @FXML private TextField plus;
    @FXML private TextField minus;
    private File file;
    private File file2;
    private Date today;
    String weather = "맑음";

    
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
    public void save(ActionEvent event){
        
        //String fileName =datePicker.getValue().getYear() + "_" + datePicker.getValue().getMonth().toString().substring(0, 3);
        //file = new File(SmartDiary.getFile().getPath() + "/" + fileName + "_diary.smd");
        //try {
        //    String filePath = file.getPath();
            
        String diaryDir = datePicker.getValue().toString().substring(0, 4);
        String diaryDir2 = datePicker.getValue().toString().substring(5, 7);
        String diaryFile = datePicker.getValue().toString();
        
        File dirOfDiary = new File(SmartDiary.getFile().getPath() + "/Contents/"+"/Diary/"+ diaryDir + "/" + diaryDir2);
        File fileofDiary = new File(dirOfDiary.getPath() + "/" + diaryDir2 + ".smd");
        try {
            if(!dirOfDiary.isDirectory()) {
                dirOfDiary.mkdirs();
            }
            String filePath = fileofDiary.getPath();    
            
            PrintWriter out = new PrintWriter(new BufferedWriter(
            new FileWriter(filePath, true))); // append = false
            out.println(datePicker.getValue());
            out.println(weather);
            out.println(jaemok.getText());
            out.println(naeyong.getText());
            out.println("------------------------------------------------------------");
            out.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //String fileName2 = datePicker.getValue().getYear() + "_" + datePicker.getValue().getMonth().toString().substring(0, 3);
        //file2 = new File(SmartDiary.getFile().getPath() + "/" + fileName2 + "_money.smd");
        //try {
        //    String filePath2 = file2.getPath();
            
        
        String moneyDir = datePicker.getValue().toString().substring(0, 4);
        String moneyDir2 = datePicker.getValue().toString().substring(5, 7);
        String moneyFile = datePicker.getValue().toString();
        
        File dirOfMoney = new File(SmartDiary.getFile().getPath() + "/Contents/"+"/Money/"+ moneyDir + "/" + moneyDir2);
        File fileofMoney = new File(dirOfMoney.getPath() + "/" + diaryDir2 + ".smd");
        try {
            if(!dirOfMoney.isDirectory()) {
                dirOfMoney.mkdirs();
            }
            String filePath2 = fileofMoney.getPath();
        
            PrintWriter out = new PrintWriter(new BufferedWriter(
            new FileWriter(filePath2, true))); // append = false
            out.println(datePicker.getValue());
            out.println(plus.getText());
            out.println(minus.getText());
            out.println("------------------------------------------------------------");
            out.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void clear(ActionEvent event){
        //clear the textarea after checked by user
    }
    
    //String baseDir = "C:\\Temp";    //검색할 디렉토리
    
    
    @FXML
    public void TextReader() throws IOException {
    jaemok.setText("");
    naeyong.setText("");
    String baseDir = SmartDiary.getFile().getPath() + "/Contents"+"/Diary/"+ datePicker.getValue().toString().substring(0, 4) + "/" + datePicker.getValue().toString().substring(5, 7);
    String word = datePicker.getValue().toString();         //검색할 단어
    String save = datePicker.getValue().toString().substring(5, 7)+".smd";       //검색결과가 저장된 파일명
        //읽어들일 디렉토리의 객체
        File dir = new File(baseDir);
        
        if(!dir.isDirectory()) {
            //디렉토리가 아니거나 없으면 종료
            System.out.println(baseDir + " is not directory or exist ");
            //System.exit(0);
        }
        //System.out.println(baseDir);
        //System.out.println(word);
        //System.out.println(save);
    
        FileReader frd = null;
        BufferedReader brd = null;

        // 내용 저장을 위한 ArrayList 정의
        ArrayList<String> lineList = new ArrayList<String>();

        // 라인 단위 저장 및 카운트를 위한 변수 정의
        String rLine = null;
        int lineNum = 0;
        boolean hasMore = true;

        try {
             frd = new FileReader(baseDir+"/"+save);
             brd = new BufferedReader(frd);  

             while (hasMore) {
                    if((rLine = brd.readLine())!= null){
                          // ArrayList에 읽은 라인 추가
                          lineList.add(rLine);
                          lineNum++;
                          hasMore = true;
                    } else
                          hasMore = false;                       
             }
             frd.close();
             brd.close();
        } catch (IOException e) {
             e.printStackTrace();
        }
        // 라인단위 출력(for loop)
        lineNum = lineList.size();
        int i = 0;
        int j = 0;
        String a = "";
        String b = "";
        while(i<lineNum) {
            a = datePicker.getValue().toString();
            //String b = lineList.get(i).toString().substring(1, lineList.get(i).length());
            if(lineList.get(i).length() != 0) {
                b = lineList.get(i).toString().substring(0, lineList.get(i).length());
            }
            if(a.equals(b))
            {
                jaemok.setText(lineList.get(i+2));
                j=i+3;
                while(lineList.get(j).toString().length() < 60)
                {
                    naeyong.setText(naeyong.getText()+"\n"+lineList.get(j));
                    j++;
                }
                break;
            }
            else {i++; continue;}
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
        plus.setText("0");
        minus.setText("0");
    }
}
