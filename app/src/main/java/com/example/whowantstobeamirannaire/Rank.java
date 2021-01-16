package com.example.whowantstobeamirannaire;

public class Rank {

    public String[] rankList;
    public boolean isBold;

    public Rank (String[] rankList, boolean isBold) {
        this.rankList = rankList;
        this.isBold = isBold;
    }

    public String[] getRankList() {
        return rankList;
    }

    public void setRankList(String[] rankList) {
        this.rankList = rankList;
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }
}
