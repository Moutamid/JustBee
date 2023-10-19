package com.moutamid.justbee.models;

public class HistoryModel {
    String colonyID, date, event;

    public HistoryModel() {
    }

    public HistoryModel(String colonyID, String date, String event) {
        this.colonyID = colonyID;
        this.date = date;
        this.event = event;
    }

    public String getColonyID() {
        return colonyID;
    }

    public void setColonyID(String colonyID) {
        this.colonyID = colonyID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
