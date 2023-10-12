package com.moutamid.justbee.models;

public class ColonyModel {
    String id, name, location, colonyOrigin, queenOrigin, feed, pests, Treatment, brood;
    long date;
    double honeyProduction;

    public ColonyModel() {
    }

    public ColonyModel(String id, String name, String location, String colonyOrigin, String queenOrigin, String feed, String pests, String treatment, String brood, long date, double honeyProduction) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.colonyOrigin = colonyOrigin;
        this.queenOrigin = queenOrigin;
        this.feed = feed;
        this.pests = pests;
        Treatment = treatment;
        this.brood = brood;
        this.date = date;
        this.honeyProduction = honeyProduction;
    }

    public ColonyModel(String id, String name, String location, String colonyOrigin, long date) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.colonyOrigin = colonyOrigin;
        this.date = date;
    }

    public String getBrood() {
        return brood;
    }

    public void setBrood(String brood) {
        this.brood = brood;
    }

    public String getQueenOrigin() {
        return queenOrigin;
    }

    public void setQueenOrigin(String queenOrigin) {
        this.queenOrigin = queenOrigin;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getPests() {
        return pests;
    }

    public void setPests(String pests) {
        this.pests = pests;
    }

    public String getTreatment() {
        return Treatment;
    }

    public void setTreatment(String treatment) {
        Treatment = treatment;
    }

    public double getHoneyProduction() {
        return honeyProduction;
    }

    public void setHoneyProduction(double honeyProduction) {
        this.honeyProduction = honeyProduction;
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
