package com.hst.calander.Model;


public class CalenderViewModel {
    String date;
    String description;
    int id;
    boolean isNote;
    String milli;
    String time;
    String title;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public String getMilli() {
        return this.milli;
    }

    public void setMilli(String str) {
        this.milli = str;
    }

    public boolean isNote() {
        return this.isNote;
    }

    public void setNote(boolean z) {
        this.isNote = z;
    }
}
