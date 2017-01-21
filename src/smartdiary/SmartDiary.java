package smartdiary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import smartdiary.model.ExAlert;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/**
 *
 * @author wnsud
 */
public class SmartDiary extends Application {
    private static Stage stage;
    private String username = System.getProperty("user.name");
    private String osname = System.getProperty("os.name");
    private String homedir = System.getProperty("user.home");
    private static File file;

    public static Stage getStage() {
        return stage;
    }
    public static File getFile() {
        return file;
    }

    @Override
    public void start(Stage LoginStage) throws Exception {
        LoginStage.resizableProperty().setValue(Boolean.FALSE);
        Parent loginView = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        boolean flag = false;

        System.out.println("Username: " + username);
        System.out.println("Used OS: " + osname);
        System.out.println("Directory: " + homedir);
        flag = checkOS(osname);

        try {
            FileLock lock = new RandomAccessFile(new File(".smartDiary.lock"), "rw").getChannel().lock();
            if(flag) {
                stage = LoginStage;
                stage.getIcons().add(new Image(getClass().getResourceAsStream("images/pIcon.png")));
                stage.setTitle("Welcome to SmartDiary !");
                stage.setScene(new Scene(loginView));
                stage.sizeToScene();
                stage.setResizable(false);
                stage.show();
            } else {
                registerUser();
            }
        } catch (Throwable th) {
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private boolean checkOS(String name) {
        String path = "";
        path = homedir + File.separator + ".smartdiary";
        file = new File(path);
        if(!file.isDirectory()) {
            showError(1);
            return false;
        } else {
            File shadow = new File(path + File.separator + "shadow");
            if(!shadow.isFile()) {
                showError(1);
                return false;
            }
            return true;
        }
    }

    private void showError(int code) {
        Alert alert;
        switch(code) {
            case 1:
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("프로그램 알림");
                alert.setHeaderText("SmartDiary 초기 설정");
                alert.setContentText("프로그램의 기존 정보가 존재하지 않습니다.\n초기 사용자 설정을 진행합니다.");
                alert.showAndWait();
                break;
            default:
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("알 수 없는 오류");
                alert.setHeaderText("오류");
                alert.setContentText("알 수 없는 오류가 발생했습니다. \n프로그램을 종료합니다.");
                alert.showAndWait();
                System.exit(0);
                break;
        }
    }

    private void registerUser() {
        try {
            Parent registerView = FXMLLoader.load(getClass().getResource("view/registerUser.fxml"));

            Scene scene = new Scene(registerView);
            Stage registerStage = new Stage();

            registerStage.resizableProperty().setValue(Boolean.FALSE);
            registerStage.setScene(scene);
            registerStage.setTitle("사용자 등록");
            registerStage.show();
        } catch (IOException ex) {
            ExAlert alert = new ExAlert(ex, "파일 저장 중 오류가 발생했습니다. \n다시 시도해주세요.\n문제를 확인할 수 없는 경우, 개발자에게 문의하세요.");
            alert.show();
            ex.printStackTrace();
        } catch (Exception ex) {
            ExAlert alert = new ExAlert(ex);
            alert.show();
            ex.printStackTrace();
        }
    }
}
