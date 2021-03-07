package com.example.odysseytourguide.model;

import java.util.ArrayList;

public class ObjectLibrary {

    private static final ObjectLibrary INSTANCE = new ObjectLibrary();
    public static final ArrayList<Kingdom> kingdomList = new ArrayList<>();

    // Empty Constructor
    private ObjectLibrary() {}

    public static ObjectLibrary getInstance() { return INSTANCE; }

    public void addToLibrary(ArrayList<Kingdom> kingdomList) {

    }
}
