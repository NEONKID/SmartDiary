package smartdiary.model;

import javafx.scene.control.Alert;

/**
 * Created by neonkid on 12/28/16.
 */
public class ExAlert extends Alert {
    private final String title = "프로그램 오류";

    public ExAlert(Exception ex) {
        super(AlertType.ERROR);
        this.setTitle(title);
        this.setHeaderText(ex.getMessage());
        this.setContentText("프로그램 관리자에게 문의하세요.");
    }

    public ExAlert(Exception ex, String str) {
        super(AlertType.ERROR);
        this.setTitle(title);
        this.setHeaderText(ex.getMessage());
        this.setContentText(str);
    }
}
