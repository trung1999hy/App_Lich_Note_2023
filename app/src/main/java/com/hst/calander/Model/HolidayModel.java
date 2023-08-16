package com.hst.calander.Model;


public class HolidayModel {
    String headingDate;
    String holidayDayName;
    String holidayName;

    public String getHeadingDate() {
        return this.headingDate;
    }

    public void setHeadingDate(String str) {
        this.headingDate = str;
    }

    public String getHolidayName() {
        return this.holidayName;
    }

    public void setHolidayName(String str) {
        this.holidayName = str;
    }

    public String getHolidayDayName() {
        return this.holidayDayName;
    }

    public void setHolidayDayName(String str) {
        this.holidayDayName = str;
    }
}
