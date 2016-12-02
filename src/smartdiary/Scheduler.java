package smartdiary;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by neonkid on 11/23/16.
 */
public class Scheduler implements Initializable {
    @FXML private StackPane stackPane;
    @FXML private JFXTreeTableView treeview;
    @FXML private JFXTextField newmemo;
    private ObservableList<Schedule> schedules;

    @FXML
    private void showDialog() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("기록 취소"));
        content.setBody(new Text("현재 적은 내용은 저장되지 않습니다."));

        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("OKAY");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newmemo.setText("");
                dialog.close();
            }
        });

        content.setActions(button);
        dialog.show();
    }

    @FXML
    private void writeMemo(ActionEvent event) {
        schedules.add(new Schedule("2016-12-03", "과제 제출 전"));
        schedules.add(new Schedule("2016-12-10", "과제 제출 일"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JFXTreeTableColumn<Schedule, String> nameCol = new JFXTreeTableColumn<>("날짜");
        nameCol.setPrefWidth(150);
        nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Schedule, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Schedule, String> param) {
                return param.getValue().getValue().date;
            }
        });

        JFXTreeTableColumn<Schedule,String> ageCol = new JFXTreeTableColumn<>("일정");
        ageCol.setPrefWidth(150);
        ageCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Schedule, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Schedule, String> param) {
                return param.getValue().getValue().memo;
            }
        });

        schedules = FXCollections.observableArrayList();

        final TreeItem<Schedule> root = new RecursiveTreeItem<Schedule>(schedules, RecursiveTreeObject::getChildren);
        treeview.getColumns().setAll(ageCol, nameCol);
        treeview.setRoot(root);
        treeview.setShowRoot(false);
    }

    class Schedule extends RecursiveTreeObject<Schedule> {
        StringProperty date;
        StringProperty memo;

        public Schedule(String date, String memo) {
            this.date = new SimpleStringProperty(date);
            this.memo = new SimpleStringProperty(memo);
        }
    }
}
