package com.example.musicplayer.model;

import java.util.ArrayList;

/**
 * Mid-tier scope.  This class contains info about a single album, and hold a reference to each
 * Song object included in the album.
 */
public class Album {

    private String title;
    private String artist;
    private ArrayList<Song> songList = new ArrayList<Song>();

    public Album(String title, String artist, ArrayList<Song> songList) {
        this.title = title;
        this.artist = artist;
        this.songList = songList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }

    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }
}
