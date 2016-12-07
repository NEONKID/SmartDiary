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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ResourceBundle;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
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
    @FXML private TextField dtitle;
    @FXML private TextArea context;
    @FXML private TextField plus;
    @FXML private TextField minus;
    @FXML private StackPane stackPane;
    private File file;
    private File file2;
    private Date today;
    String weather = "맑음";
    
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
            out.println(dtitle.getText());
            out.println(context.getText());
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
        JFXDialogLayout checkClear = new JFXDialogLayout();
                JFXDialog checkMessage = new JFXDialog(stackPane, checkClear, JFXDialog.DialogTransition.CENTER);
                JFXButton clearAgree = new JFXButton("Yes");
                JFXButton clearCancel = new JFXButton("No");

                clearAgree.setRipplerFill(Paint.valueOf("#ffffff"));
                clearAgree.setTextFill(Paint.valueOf("#ffffff"));
                clearAgree.setStyle("-fx-background-color: #4059a9");
                clearCancel.setRipplerFill(Paint.valueOf("#ffffff"));
                clearCancel.setTextFill(Paint.valueOf("#ffffff"));
                clearCancel.setStyle("-fx-background-color: #4059a9");

                checkClear.setHeading(new Text(""));
                checkClear.setBody(new Text("저장되지 않은 모든 내용이 사라집니다. \n 지우시겠습니까?"));
                clearAgree.setOnAction((ActionEvent e) -> {
                    checkMessage.close();
                    try {
                        //Delete what user writed context
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                clearCancel.setOnAction((ActionEvent e) -> {
                    checkMessage.close();
                });
                checkClear.setActions(clearAgree,clearCancel);
                checkMessage.show();
    }
    
    private void saveFile(File file) {
        try {
            String filePath = file.getPath();
            PrintWriter out = new PrintWriter(new BufferedWriter(
            new FileWriter(filePath, true))); // append = false
            out.print(datePicker.getValue());
            out.print("   ");   // 빈칸 3개
            out.println(weather);
            out.println(dtitle.getText());
            out.println(context.getText());
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
