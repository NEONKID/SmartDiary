package smartdiary.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import smartdiary.Diary.DiaryFileReader;
import smartdiary.SmartDiary;
import smartdiary.Diary.DiaryFileWriter;
import smartdiary.aesEnDecrypt.AESHelper;

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
    @FXML private TextField income;
    @FXML private TextField expense;
    @FXML private StackPane stackPane;
    @FXML private HTMLEditor content;
    private String weather = "맑음";

    @FXML
    public void imgsun(){
        img = new Image(getClass().getResource("/smartdiary/images/sunny.png").toString());
        imgweather.setImage(img);
        weather = "맑음";
    }
    @FXML
    public void imgcloud(){
        img = new Image(getClass().getResource("/smartdiary/images/cloudy.png").toString());
        imgweather.setImage(img);
        weather = "구름";
    }
    @FXML
    public void imgrain(){
        img = new Image(getClass().getResource("/smartdiary/images/rainy.png").toString());
        imgweather.setImage(img);
        weather = "비";
    }
    @FXML
    public void imgsnow(){
        img = new Image(getClass().getResource("/smartdiary/images/snowy.png").toString());
        imgweather.setImage(img);
        weather = "눈";
    }

    @FXML
    public void saveFile(ActionEvent event) {
        JFXSnackbar jFXSnackbar = new JFXSnackbar(stackPane);
        
        String saveDir = datePicker.getValue().toString().substring(0, 4);
        String saveFile = datePicker.getValue().toString().substring(0, 7);

        String[] contents_diary = { datePicker.getValue().toString(), weather, title.getText(), content.getHtmlText() };
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
        
        if(income.getText().equals("")){ income.setText("0"); }
        if(expense.getText().equals("")){ expense.setText("0"); }
        
        try {
            diaryFileWriter.checkDiary(diaryPath, datePicker.getValue().toString());
            diaryFileWriter.writeDiaryMoney(diaryPath, contents_diary);
        
            diaryFileWriter.checkMoney(moneyPath, datePicker.getValue().toString());
            diaryFileWriter.writeDiaryMoney(moneyPath, contents_money);
            jFXSnackbar.show("성공적으로 저장되었습니다.", 3000);
        } catch (IOException ex) {
            jFXSnackbar.show("죄송합니다. 오류가 발생했습니다.", 3000);
        }       
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
                content.setHtmlText("");
                income.clear();
                expense.clear();
            } catch (Exception ex) {
            }
        });
        clearCancel.setOnAction((ActionEvent e) -> {
            checkClear.close(); // Cancel. no action
        });
        base.setActions(clearAgree, clearCancel);
        checkClear.show();
    }

    private void DiaryReader() throws IOException {
        String baseDir = SmartDiary.getFile().getPath() + "/Contents" + "/Diary/" + datePicker.getValue().toString().substring(0, 4);
        String moneyDir = SmartDiary.getFile().getPath() + "/Contents" + "/Money/" + datePicker.getValue().toString().substring(0, 4);
        String save = datePicker.getValue().toString().substring(0, 7)+".smd";       //검색결과가 저장된 파일명
        String Line = "------------------------------------------------------------";
        File dir = new File(baseDir);   // 읽어들일 디렉토리의 객체
        DiaryFileReader diaryFileReader = new DiaryFileReader();
        DiaryFileReader moneyFileReader = new DiaryFileReader();
        ArrayList<String> diaryLine;   // 내용을 불러오기 위한 ArrayList 정의
        ArrayList<String> moneyLine;
        
        title.setText("");
        content.setHtmlText("");

        if(!dir.isDirectory()) {
            // 디렉토리가 아니거나 없으면 종료
            System.out.println(baseDir + " is not directory or exist ");
            return;
        }
        
        diaryFileReader.readFile(baseDir + "/" + save);
        diaryLine = diaryFileReader.getList();
        
        // 라인단위 출력(for loop)
        int lineNum = diaryLine.size();
        int i = 0, j;
        while(i < lineNum) {
            String date = datePicker.getValue().toString();
            String cp = null;
            if(diaryLine.get(i).length() != 0) {
                cp = diaryLine.get(i).substring(0, diaryLine.get(i).length());
            }
            if(date.equals(cp)) {
                switch(diaryLine.get(i + 1)) {
                    case "맑음":
                        imgsun();
                        break;
                    case "구름":
                        imgcloud();
                        break;
                    case "비":
                        imgrain();
                        break;
                    case "눈":
                        imgsnow();
                        break;
                }               
                title.setText(diaryLine.get(i + 2));
                j = i + 3;
                
                while(!diaryLine.get(j).equals(Line)) {
                    content.setHtmlText(diaryLine.get(j));
                    j++;
                }
                break;
            }
            else {
                i++;
            }
        }
        
        moneyFileReader.readFile(moneyDir + "/" + save);
        moneyLine = moneyFileReader.getList();
        lineNum = moneyLine.size();
        i = 0;
        while(i < lineNum) {
            String date = datePicker.getValue().toString();
            String cp = null;
            if(moneyLine.get(i).length() != 0) {
                cp = moneyLine.get(i).substring(0, moneyLine.get(i).length());
            }
            if(date.equals(cp)) {
                income.setText(moneyLine.get(i + 1));
                expense.setText(moneyLine.get(i + 2));               
                break;
            }
            else {
                i++;
                income.setText("0");
                expense.setText("0");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
        content.setId("html-editor");
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