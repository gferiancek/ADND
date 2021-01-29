package com.example.musicplayer.model;

import java.util.ArrayList;

/**
 * High scope class that contains info about a single Artist.  Holds a reference to each Album
 * object that belongs to the Artist, and in turn can also access the information about each
 * Song object belonging to any of the Album objects.
 */
public class Artist {

    private String name;
    private ArrayList<Album> albumList = new ArrayList<Album>();

    public Artist(String name, ArrayList<Album> albumList) {
        this.name = name;
        this.albumList = albumList;
    }

    public ArrayList<Album> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(ArrayList<Album> albumList) {
        this.albumList = albumList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
