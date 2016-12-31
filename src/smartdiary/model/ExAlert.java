package smartdiary.model;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by neonkid on 12/28/16.
 */
public class ExAlert extends Alert {
    private final String title = "프로그램 오류";

    public ExAlert(Exception ex) {
        super(AlertType.ERROR);
        setTitle(title);
        setHeaderText("치명적인 오류가 발생했습니다.");
        setContentText("이 문제가 계속 발생할 경우, 개발자에게 문의하세요.");
        setResizable(false);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The Exception stacktrace was: ");
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        getDialogPane().setExpandableContent(expContent);
        getDialogPane().setExpanded(true);
    }

    public ExAlert(Exception ex, String str) {
        super(AlertType.ERROR);
        this.setTitle(title);
        this.setHeaderText(ex.getMessage());
        this.setContentText(str);
    }
}
