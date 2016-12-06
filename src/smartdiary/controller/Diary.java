/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdiary.controller;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.DatePicker;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
    @FXML private DatePicker date;
    
    @FXML public void settoday(){
        
    }
    
    @FXML
    public void imgsun(ActionEvent event){
        img = new Image(getClass().getResource("/smartdiary/images/sunny.png").toString());
        imgweather.setImage(img);
    }
    @FXML
    public void imgcloud(ActionEvent event){
        img = new Image(getClass().getResource("/smartdiary/images/cloudy.png").toString());
        imgweather.setImage(img);
    }
    @FXML
    public void imgrain(ActionEvent event){
        img = new Image(getClass().getResource("/smartdiary/images/rainy.png").toString());
        imgweather.setImage(img);
    }
    @FXML
    public void imgsnow(ActionEvent event){
        img = new Image(getClass().getResource("/smartdiary/images/snowy.png").toString());
        imgweather.setImage(img);
    }
    @FXML
    public void save(ActionEvent event){
        //for save code
    }
    @FXML
    public void clear(ActionEvent event){
        //clear the textarea after checked by user
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {      

    }
}
