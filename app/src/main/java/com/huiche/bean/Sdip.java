package com.huiche.bean;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class Sdip {
    public Sdip() {

    }
    public String date;
    public String page;
    public String rows;
    public String style;
    public String time;
    public String toDate;
    public String toTime;
    public String days;
    public String seconds;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return "Sdip{" +
                "date='" + date + '\'' +
                ", page='" + page + '\'' +
                ", rows='" + rows + '\'' +
                ", style='" + style + '\'' +
                ", time='" + time + '\'' +
                ", toDate='" + toDate + '\'' +
                ", toTime='" + toTime + '\'' +
                ", days='" + days + '\'' +
                ", seconds='" + seconds + '\'' +
                '}';
    }
}
