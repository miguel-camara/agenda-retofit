package com.app.agendaandroid.model;

public class Event {
    private String id;
    private String activity;
    private String date;
    private String time;
    private String uid;
    private boolean status;

    public Event(String activity, String date, String time, String uid, boolean status) {
        this.activity = activity;
        this.date = date;
        this.time = time;
        this.uid = uid;
        this.status = status;
     }

    public Event(String id, String activity, String date, String time, String uid, boolean status) {
        this.id = id;
        this.activity = activity;
        this.date = date;
        this.time = time;
        this.uid = uid;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
