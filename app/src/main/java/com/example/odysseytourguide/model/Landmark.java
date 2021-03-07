package com.example.odysseytourguide.model;

public class Landmark {

    private final String landmarkName;
    private final String landmarkDescription;
    private final int landmarkImage;

    public Landmark(String landmarkName, String landmarkDescription, int landmarkImage) {
        this.landmarkName = landmarkName;
        this.landmarkDescription = landmarkDescription;
        this.landmarkImage =landmarkImage;
    }

    public String getLandmarkName() {
        return landmarkName;
    }

    public String getLandmarkDescription() {
        return landmarkDescription;
    }

    public int getLandmarkImage() {
        return landmarkImage;
    }
}
