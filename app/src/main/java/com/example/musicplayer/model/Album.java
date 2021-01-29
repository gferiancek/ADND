package com.example.musicplayer.model;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Mid-tier scope.  This class contains info about a single album, and hold a reference to each
 * Song object included in the album.
 */
public class Album {

    private String title;
    private ArrayList<Song> songList;
    private Uri albumArtUri;

    public Album(String title, ArrayList<Song> songList, Uri albumArtUri) {
        this.title = title;
        this.songList = songList;
        this.albumArtUri = albumArtUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }

    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }

    public Uri getAlbumArtUri() {
        return albumArtUri;
    }

    public void setAlbumArtUri(Uri albumArtUri) {
        this.albumArtUri = albumArtUri;
    }
}
