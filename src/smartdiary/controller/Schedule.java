package smartdiary.controller;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by neonkid on 12/3/16.
 */
public class Schedule {
    private final SimpleStringProperty date = new SimpleStringProperty("");
    private final SimpleStringProperty memo = new SimpleStringProperty("");

    public Schedule() {
        this("", "");
    }

    public Schedule(String date, String memo) {
        setDate(date);
        setMemo(memo);
    }

    public String getDate() {
        return date.get();
    }

    public String getMemo() {
        return memo.get();
    }

    private void setDate(String _date) { date.set(_date);}

    private void setMemo(String _memo) {
        memo.set(_memo);
    }

    public String toString() {
        return "Date: " + getDate() + " | Memo: " + getMemo();
    }
}
