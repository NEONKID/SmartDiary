package smartdiary.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by neonkid on 12/3/16.
 */
public class Schedule {
    private final StringProperty date = new SimpleStringProperty(this, "Date", "");
    private final StringProperty memo = new SimpleStringProperty(this, "Memo", "");

    public Schedule() {
        this("", "");
    }

    public Schedule(String date, String memo) {
        setDate(date);
        setMemo(memo);
    }

    public StringProperty dateProperty() { return date; }
    public String getDate() {
        return date.get();
    }

    public StringProperty memoProperty() { return memo; }
    public String getMemo() {
        return memo.get();
    }

    public void setDate(String _date) { date.set(_date);}

    public void setMemo(String _memo) {
        memo.set(_memo);
    }

    public String toString() {
        return getDate() + "\t" + getMemo();
    }
}
