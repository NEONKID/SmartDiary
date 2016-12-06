package smartdiary.controller;

import com.jfoenix.controls.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import jfxtras.scene.control.CalendarPicker;
import jfxtras.scene.control.CalendarTextField;
import smartdiary.SmartDiary;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Paint;

/**
 * Created by neonkid on 11/23/16.
 */
public class Scheduler implements Initializable {
    @FXML private StackPane stackPane;
    @FXML private TableView<Schedule> tableView;
    @FXML private JFXTextField newmemo;
    @FXML private TableColumn<Schedule, String> dateCol;
    @FXML private TableColumn<Schedule, String> memoCol;
    @FXML private CalendarPicker calendarPicker;
    @FXML private CalendarTextField lCalendarTextField;
    @FXML private JFXTextField filterbox;
    private final ObservableList<Schedule>data = FXCollections.observableArrayList();
    private File file;
    
    @FXML
    private void getInformation(MouseEvent event) {
        lCalendarTextField.setText(tableView.getSelectionModel().getSelectedItem().getDate());
        newmemo.setText(tableView.getSelectionModel().getSelectedItem().getMemo());
    }

    @FXML
    private void writeMemo(ActionEvent event) {
        if(lCalendarTextField.getText().trim().isEmpty() || newmemo.getText().trim().isEmpty() ) {
            showDialog(2);
        } else {
            Schedule currentSchedule = new Schedule(lCalendarTextField.getText(), newmemo.getText());
            data.add(currentSchedule);

            lCalendarTextField.setText("");
            newmemo.clear();
        }
    }

    @FXML
    private void delMemo(ActionEvent event) {
        data.removeAll(tableView.getSelectionModel().getSelectedItems());
        tableView.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String fileName = LocalDate.now().getYear() + "_" + LocalDate.now().getMonth().toString().substring(0, 3);
        file = new File(SmartDiary.getFile().getPath() + "/" + fileName + "_schedules.smd");
        
        if(file != null) {
            readFile(file);
        }
        
        filterbox.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                filterMemoList((String)oldValue, (String)newValue);
            }
        });
        
        tableView.setItems(data);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setPlaceholder(new Label("새로운 일정을 추가해주세요."));
        
        lCalendarTextField.calendarProperty().bindBidirectional(calendarPicker.calendarProperty());
        calendarPicker.showTimeProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) -> {
            lCalendarTextField.setDateFormat(calendarPicker.getShowTime() ? SimpleDateFormat.getDateInstance() : SimpleDateFormat.getDateInstance());
        });
        lCalendarTextField.setDateFormat(calendarPicker.getShowTime() ? SimpleDateFormat.getDateTimeInstance() : SimpleDateFormat.getDateInstance());
        lCalendarTextField.setPromptText("날짜를 선택해주세요. ");
        
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        memoCol.setCellFactory(TextFieldTableCell.forTableColumn());
    }
    
    public void filterMemoList(String oldValue, String newValue) {
        ObservableList<Schedule> filteredList = FXCollections.observableArrayList();
        if(filterbox == null || (newValue.length() < oldValue.length()) ) {
            tableView.setItems(data);
        } else {
            newValue = newValue.toUpperCase();
            for(Schedule schedules : tableView.getItems()) {
                String filDate = schedules.getDate();
                String filMemo = schedules.getMemo();
                if(filDate.toUpperCase().contains(newValue) || filMemo.toUpperCase().contains(newValue)) {
                    filteredList.add(schedules);
                }
            }
            tableView.setItems(filteredList);
        }
    }
    
    @FXML
    public void dateCol_OnEditCommit(TableColumn.CellEditEvent<Schedule, String> event) {
        Schedule schedule = event.getRowValue();
        schedule.setDate(event.getNewValue());
    }
    
    @FXML
    public void memoCol_OnEditCommit(TableColumn.CellEditEvent<Schedule, String> event) {
        Schedule schedule = event.getRowValue();
        schedule.setMemo(event.getNewValue());
    }

    @FXML
    private void handleSave(ActionEvent event) {
        if(data.isEmpty()) {
            showDialog(3);
        } else {
            if(file != null) {
                saveFile(tableView.getItems(), file);
            }
        }
    }

    private void readFile(File file) {
        try {
            BufferedReader inReader = new BufferedReader(new FileReader(file));
            String line = null;
            String[] splited = null;
            while((line = inReader.readLine()) != null) {
                splited = line.split("\t");
                for(int i = 0; i < splited.length; i++) {
                    splited[i] = splited[i].trim();
                }
                data.add(new Schedule(splited[0], splited[1]));
            }
            inReader.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveFile(ObservableList<Schedule> scheduleObservableList, File file) {
        JFXSnackbar jFXSnackbar = new JFXSnackbar(stackPane);
        try {
            BufferedWriter outwriter = new BufferedWriter(new FileWriter(file));
            for(Schedule schedules : scheduleObservableList) {
                outwriter.write(schedules.toString());
                outwriter.newLine();
            }
            System.out.println(" :: " + scheduleObservableList.toString());
            outwriter.close();
            jFXSnackbar.show("성공적으로 저장되었습니다.", 5000);
        } catch (IOException ex) {
            jFXSnackbar.show("죄송합니다. 오류가 발생했습니다.", 5000);
            ex.printStackTrace();
        }
    }

    private void showDialog(int flag) {
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("확인");
        switch(flag) {
            case 1:
                content.setHeading(new Text("일정 삭제"));
                content.setBody(new Text("삭제되었습니다."));
                
                button.setRipplerFill(Paint.valueOf("#ffffff"));
                button.setTextFill(Paint.valueOf("#ffffff"));
                button.setStyle("-fx-background-color: #2196F3");
                button.setOnAction((ActionEvent event) -> {
                    newmemo.setText("");
                    dialog.close();
                });
                content.setActions(button);
                dialog.show();
                break;
            case 2:
                content.setHeading(new Text("값 없음!"));
                content.setBody(new Text("값이 입력되지 않았습니다. 다시 입력해주세요."));
                
                button.setRipplerFill(Paint.valueOf("#ffffff"));
                button.setTextFill(Paint.valueOf("#ffffff"));
                button.setStyle("-fx-background-color: #2196F3");
                button.setOnAction((ActionEvent event) -> {
                    dialog.close();
                });
                content.setActions(button);
                dialog.show();
                break;
            case 3:
                content.setHeading(new Text("EMPTY TABLE"));
                content.setBody(new Text("테이블이 비었습니다. 항목을 추가해주세요."));
                
                button.setRipplerFill(Paint.valueOf("#ffffff"));
                button.setTextFill(Paint.valueOf("#ffffff"));
                button.setStyle("-fx-background-color: #2196F3");
                button.setOnAction((ActionEvent event) -> {
                    dialog.close();
                });
                content.setActions(button);
                dialog.show();
            default:
                break;
        }
    }
}
