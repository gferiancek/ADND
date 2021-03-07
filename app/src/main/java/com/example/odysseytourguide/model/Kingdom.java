package com.example.odysseytourguide.model;

import java.util.List;

public class Kingdom {

    private final int imageID;
    private final int powerMoonImageID;
    private final int regionalImageID;
    private final String kingdomName;
    private final String powerMoonCount;
    private final String regionalCount;
    private boolean isExpanded;
    private final List<Landmark> landmarkList;

    public Kingdom(int imageID, int powerMoonImageID, int regionalImageID, String kingdomName,
                   String powerMoonCount, String regionalCount, boolean isExpanded, List<Landmark> landmarkList) {
        this.imageID = imageID;
        this.powerMoonImageID = powerMoonImageID;
        this.regionalImageID = regionalImageID;
        this.kingdomName = kingdomName;
        this.powerMoonCount = powerMoonCount;
        this.regionalCount = regionalCount;
        this.isExpanded = isExpanded;
        this.landmarkList = landmarkList;
    }

    public int getImageID() {
        return imageID;
    }

    public int getPowerMoonImageID() {
        return powerMoonImageID;
    }

    public int getRegionalImageID() {
        return regionalImageID;
    }

    public String getKingdomName() {
        return kingdomName;
    }

    public String getPowerMoonCount() {
        return powerMoonCount;
    }

    public String getRegionalCount() {
        return regionalCount;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public List<Landmark> getLandmarkList() { return landmarkList; }
}
