package smartdiary.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import smartdiary.Diary.DiaryFileReader;
import smartdiary.SmartDiary;
import smartdiary.Diary.DiaryFileWriter;
/**
 *
 * @author wnsud
 */
public class DiaryController implements Initializable {   
    @FXML private Button savediary;
    @FXML private Button cleardiary;
    @FXML private ImageView imgweather;
    @FXML private Image img;
    @FXML private JFXDatePicker datePicker;
    @FXML private TextField title;
    @FXML private TextArea content;
    @FXML private TextField income;
    @FXML private TextField expense;
    @FXML private StackPane stackPane;
    private String weather = "맑음";
    
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
    public void saveFile(ActionEvent event) { 
        String saveDir = datePicker.getValue().toString().substring(0, 4);
        String saveFile = datePicker.getValue().toString().substring(0, 7);
        String[] contents_diary = { datePicker.getValue().toString(), weather, title.getText(), content.getText() }; 
        String[] contents_money = { datePicker.getValue().toString(), income.getText(), expense.getText() };
            
        File dirOfDiary = new File(SmartDiary.getFile().getPath() + "/Contents/" + "/Diary/"+ saveDir);
        File fileofDiary = new File(dirOfDiary.getPath() + "/" + saveFile + ".smd");
        String diaryPath = fileofDiary.getPath();
            
        File dirOfMoney = new File(SmartDiary.getFile().getPath() + "/Contents/" + "/Money/" + saveDir);
        File fileofMoney = new File(dirOfMoney.getPath() + "/" + saveFile + ".smd");
        String moneyPath = fileofMoney.getPath();

        DiaryFileWriter diaryFileWriter = new DiaryFileWriter();
        if(!dirOfDiary.isDirectory()) {
            dirOfDiary.mkdirs();
        } 
        if(!dirOfMoney.isDirectory()) {
            dirOfMoney.mkdirs();
        }            
        diaryFileWriter.checkDiary(diaryPath, datePicker.getValue().toString());
        diaryFileWriter.writeDiary(diaryPath, contents_diary);
        
        diaryFileWriter.checkMoney(moneyPath, datePicker.getValue().toString());
        diaryFileWriter.writeMoney(moneyPath, contents_money);
    }
        
    @FXML
    public void clear(ActionEvent event){
        //  clear the textarea after checked by user
        JFXDialogLayout base = new JFXDialogLayout();
        JFXDialog checkClear = new JFXDialog(stackPane, base, JFXDialog.DialogTransition.CENTER);
        JFXButton clearAgree = new JFXButton("Yes");
        JFXButton clearCancel = new JFXButton("No");

        clearAgree.setId("left-button");
        clearCancel.setId("right-button");
        
        base.setHeading(new Text(null));
        base.setBody(new Text("저장되지 않은 내용이 지워집니다. 지우겠습니까?"));
        
        clearAgree.setOnAction((ActionEvent e) -> {
            checkClear.close();
            try {
                 // Clear the Diary
                title.clear();
                content.clear();
                income.clear();
                expense.clear();
                img = new Image(getClass().getResource("/smartdiary/images/sunny.png").toString());
                imgweather.setImage(img);   // Default <<sunny
            } catch (Exception ex) {
            }
        });
        clearCancel.setOnAction((ActionEvent e) -> {
            checkClear.close(); // Cancel. no action
        });
        base.setActions(clearAgree, clearCancel);
        checkClear.show();
    }

    @FXML
    public void DiaryReader() throws IOException {
        String baseDir = SmartDiary.getFile().getPath() + "/Contents" + "/Diary/" + datePicker.getValue().toString().substring(0, 4);
        String save = datePicker.getValue().toString().substring(0, 7)+".smd";       //검색결과가 저장된 파일명
        File dir = new File(baseDir);   // 읽어들일 디렉토리의 객체
        DiaryFileReader diaryFileReader = new DiaryFileReader();
        ArrayList<String> lineList;   // 내용 저장을 위한 ArrayList 정의
        
        title.setText("");
        content.setText("");

        if(!dir.isDirectory()) {
            // 디렉토리가 아니거나 없으면 종료
            System.out.println(baseDir + " is not directory or exist ");
        }
        diaryFileReader.readFile(baseDir + "/" + save);
        lineList = diaryFileReader.getList();

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
                title.setText(lineList.get(i + 2));
                j = i + 3;
                while(lineList.get(j).length() < 60)
                {
                    if(content.getText().equals("")) {
                        content.setText(lineList.get(j) + "\n");
                        j++;
                        continue;
                    }
                    content.setText(content.getText() + lineList.get(j) + "\n");
                    j++;
                }
                break;
            }
            else { i++; }
        }
        income.setText("0");
        expense.setText("0");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
        try {
            DiaryReader();
        } catch (IOException ex) {
        }
        datePicker.valueProperty().addListener((ObservableValue<? extends LocalDate> observableValue, LocalDate oldDate, LocalDate newDate) -> {
            try {
                DiaryReader();
            } catch (IOException ex) {
            }
        });
    }
}