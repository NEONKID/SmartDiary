
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdiary.controller;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author wnsud
 */
public class Day_Diary {
    private final SimpleStringProperty date = new SimpleStringProperty("");
    private final SimpleStringProperty title = new SimpleStringProperty("");
    private final SimpleStringProperty context = new SimpleStringProperty("");
    private final SimpleStringProperty weather = new SimpleStringProperty("");
    public Day_Diary(){
        this("","","","");
    }
    
    public Day_Diary(String date, String title, String context, String weather) {
        setDate(date);
        setTitle(title);
        setContext(context);
        setWeather(weather);
    }//Data for diary setting
    
    public String getDate() {
        return date.get();
    }
    public String getTitle() {
        return title.get();
    }
    public String getContext() {
        return context.get();
    }
    public String getWeather() {
        return weather.get();
    }
    public void setDate(String day) {
        date.set(day);
    }
    public void setTitle(String name) {
       title.set(name);
    }
    public void setContext(String value) {
        context.set(value);
    }
    public void setWeather(String imgpath) {
        weather.set(imgpath);
    }
}
    
