package com.example.musicplayer.model;

import java.util.ArrayList;
import java.util.Collections;

public class MusicLibrary {

    private final ArrayList<Artist> artistList = new ArrayList<>();
    private ArrayList<Album> albumList = new ArrayList<>();
    private ArrayList<Song> songList = new ArrayList<>();

    // Empty constructor
    public MusicLibrary() {
    }

    public ArrayList<Artist> getArtistList() {
        return artistList;
    }

    public void addSongToLibrary(ArrayList<Song> songList) {
        for (Song song : songList) {
            if (!artistList.isEmpty()) {
                int doesNotMatchCount = 0;
                for (Artist artist : artistList) {
                    if (!artist.getName().equals(song.getArtist())) {
                        doesNotMatchCount++;
                    }
                }
                // this condition means that the artist we are adding did not match ANY in the list.
                if (doesNotMatchCount == artistList.size()) {
                    artistList.add(new Artist(song.getArtist()));
                }
            } else {
                artistList.add(new Artist(song.getArtist()));
            }
        }
        Collections.sort(artistList, (artist1, artist2) ->
                artist1.getName().compareToIgnoreCase(artist2.getName()));
    }

    public ArrayList<Album> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(ArrayList<Album> albumList) {
        this.albumList = albumList;
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }

    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }
}
