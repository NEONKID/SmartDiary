package smartdiary.Money;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by neonkid on 12/3/16.
 */
public class Money {
    private final StringProperty date = new SimpleStringProperty(this, "Date", "");
    private final StringProperty plus = new SimpleStringProperty(this, "Plus", "");
    private final StringProperty minus = new SimpleStringProperty(this, "Minus", "");

    public Money() {
        this("", "", "");
    }

    public Money(String date, String plus, String minus) {
        setDate(date);
        setPlus(plus);
        setMinus(minus);
    }

    public StringProperty dateProperty() { return date; }
    public String getDate() {
        return date.get();
    }

    public StringProperty plusProperty() { return plus; }
    public String getPlus() {
        return plus.get();
    }
    
    public StringProperty minusProperty() { return minus; }
    public String getMinus() {
        return minus.get();
    }
    
    public void setDate(String _date) { date.set(_date);}

    public void setPlus(String _plus) { plus.set(_plus);}

    public void setMinus(String _minus) { minus.set(_minus);}

    public String toString() {
        return getDate() + "\t" + getPlus() + "\t" + getMinus();
    }
}
