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
        String fileName = LocalDate.now().getYear() + "_" + LocalDate.now().getMonth().toString().substring(0, 3);
        file = new File(SmartDiary.getFile().getPath() + "/" + fileName + "_diary.smd");
        //saveFile(file);
        try {
            String filePath = file.getPath();
            PrintWriter out = new PrintWriter(new BufferedWriter(
            new FileWriter(filePath, true))); // append = false
            out.print(datePicker.getValue());
            out.print("   ");   // 빈칸 3개
            out.println(weather);
            out.println(jaemok.getText());
            out.println(naeyong.getText());
            out.println("------------------------------");
            out.close();
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
        
        String fileName2 = LocalDate.now().getYear() + "_" + LocalDate.now().getMonth().toString().substring(0, 3);
        file2 = new File(SmartDiary.getFile().getPath() + "/" + fileName2 + "_money.smd");
        //saveFile(file);
        try {
            String filePath2 = file2.getPath();
            PrintWriter out = new PrintWriter(new BufferedWriter(
            new FileWriter(filePath2, true))); // append = false
            out.println(datePicker.getValue());
            out.println(plus.getText());
            out.println(minus.getText());
            out.println("------------------------------");
            out.close();
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }
    @FXML
    public void clear(ActionEvent event){
        //clear the textarea after checked by user
    }
    
    private void saveFile(File file) {
        try {
            String filePath = file.getPath();
            PrintWriter out = new PrintWriter(new BufferedWriter(
            new FileWriter(filePath, true))); // append = false
            out.print(datePicker.getValue());
            out.print("   ");   // 빈칸 3개
            out.println(weather);
            out.println(jaemok.getText());
            out.println(naeyong.getText());
            out.println("------------------------------");
            out.close();
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
    }
}
