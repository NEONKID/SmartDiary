package smartdiary;

import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Created by neonkid on 11/23/16.
 */
public class Scheduler implements Initializable {
    public CalendarView calendarView;

    Thread updateThread = new Thread("Calendar: Update Time Thread") {
        @Override
        public void run() {
            while(true) {
                Platform.runLater(()-> {
                    calendarView.setToday(LocalDate.now());
                    calendarView.setTime(LocalTime.now());
                });

                try {
                    sleep(10000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateThread.setPriority(Thread.MIN_PRIORITY);
        updateThread.setDaemon(true);
        updateThread.start();
    }
}
