package com.moutamid.justbee.models;

import java.util.ArrayList;

public class QueenSourceModel {
    String queenSource, title1, title2;
    ArrayList<QueenPerformance> list;

    public QueenSourceModel() {
    }

    public QueenSourceModel(String queenSource, String title1, String title2, ArrayList<QueenPerformance> list) {
        this.queenSource = queenSource;
        this.title1 = title1;
        this.title2 = title2;
        this.list = list;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getQueenSource() {
        return queenSource;
    }

    public void setQueenSource(String queenSource) {
        this.queenSource = queenSource;
    }

    public ArrayList<QueenPerformance> getList() {
        return list;
    }

    public void setList(ArrayList<QueenPerformance> list) {
        this.list = list;
    }
}
