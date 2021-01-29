package com.example.musicplayer.model;

import java.util.ArrayList;

public class MusicLibrary {

    private static final MusicLibrary INSTANCE = new MusicLibrary();
    public static final ArrayList<Artist> artistList = new ArrayList<>();
    public static final ArrayList<Album> albumList = new ArrayList<>();
    public static final ArrayList<Song> songList = new ArrayList<>();

    // Empty constructor
    private MusicLibrary() {
    }

    // Allows other classes to access singleton instance
    public static MusicLibrary getInstance() {
        return INSTANCE;
    }

    public void addToLibrary(ArrayList<Song> newSongList) {
        for (Song song : newSongList) {
            Artist currentArtist = artistList.stream().filter(artist -> artist.getName().equals(song.getArtist())).findFirst().orElse(null);
            Album currentAlbum;
            Song currentSong;
            ArrayList<Song> albumSongList = new ArrayList<>();
            // Uses streams to determine if Artist -> Album -> Song with current song details already exist.
            // If at any point one of these are null, we add in whatever details are missing.
            if (currentArtist != null) {
                currentAlbum = currentArtist.getAlbumList().stream().filter(album -> album.getTitle().equals(song.getAlbum())).findFirst().orElse(null);
                if (currentAlbum != null) {
                    currentSong = currentAlbum.getSongList().stream().filter(s -> s.getTitle().equals(song.getTitle())).findFirst().orElse(null);
                    // Artist and Album both exist, so if the song is null, we add it to the album.  If it isn't
                    // null then we do not have to add any data because the song is a duplicate.
                    if (currentSong == null) {
                        currentAlbum.getSongList().add(song);
                        songList.add(song);
                    }
                    // This is triggered if the Artist exists but the Album is null.  Adds the album
                    // and current song to the lists
                } else {
                    albumSongList.add(song);
                    currentAlbum = new Album(song.getAlbum(), albumSongList, song.getAlbumArtUri());
                    currentArtist.getAlbumList().add(currentAlbum);
                    albumList.add(currentAlbum);
                    songList.add(song);
                }
                // These are the album/song lists contained inside of the Artist object, which is separate
                // from the global list of all albums/songs.  Need to sort on every pass while we still
                // have reference to the currentObjects.
                currentAlbum.getSongList().sort((song1, song2) ->
                        Integer.compare(song1.getTrackNumber(), song2.getTrackNumber()));
                currentArtist.getAlbumList().sort((album1, album2) ->
                        album1.getTitle().compareToIgnoreCase(album2.getTitle()));

                // This is triggered when the Artist is null, so we need to make a new Artist, and the
                // contained Album and Song objects. No sorting is required, since there will only be
                // one album and one song in each local list.
            } else {
                albumSongList.add(song);
                currentAlbum = new Album(song.getAlbum(), albumSongList, song.getAlbumArtUri());
                ArrayList<Album> artistAlbumList = new ArrayList<>();
                artistAlbumList.add(currentAlbum);
                artistList.add(new Artist(song.getArtist(), artistAlbumList));
                albumList.add(currentAlbum);
                songList.add(song);
            }
        }
        // After every song has been parsed, we can sort the global lists.
        artistList.sort((artist1, artist2) ->
                artist1.getName().compareToIgnoreCase(artist2.getName()));
        albumList.sort((album1, album2) ->
                album1.getTitle().compareToIgnoreCase(album2.getTitle()));
        songList.sort((song1, song2) ->
                song1.getTitle().compareToIgnoreCase(song2.getTitle()));
    }

    public ArrayList<Artist> getArtistList() {
        return artistList;
    }

    public ArrayList<Album> getAlbumList() {
        return albumList;
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }
}
