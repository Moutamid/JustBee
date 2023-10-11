package com.moutamid.justbee.models;

public class ColonyModel {
    String id, name, location, colonyOrigin;
    long date;


    public ColonyModel(String id, String name, String location, String colonyOrigin, long date) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.colonyOrigin = colonyOrigin;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getColonyOrigin() {
        return colonyOrigin;
    }

    public void setColonyOrigin(String colonyOrigin) {
        this.colonyOrigin = colonyOrigin;
    }
}
