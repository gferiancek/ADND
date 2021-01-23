package com.example.musicplayer.model;

import java.util.ArrayList;

/**
 * High scope class that contains info about a single Artist.  Holds a reference to each Album
 * object that belongs to the Artist, and in turn can also access the information about each
 * Song object belonging to any of the Album objects.
 */
public class Artist {

    private String artist;
    private ArrayList<Album> albumList = new ArrayList<Album>();

    public Artist(String artist) {
        this.artist = artist;
        this.albumList = albumList;
    }
}
