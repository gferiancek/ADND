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

    /**
     * Function used to add a list of songs into the main app library.  Since the songs have all of the
     * relevant artist/album/art/etc info, use use them to reverse engineer the Artist -> Album -> Song
     * structure. The goal is to check if an artist/album already exist in or main library and either add
     * to the existing objects or create new ones if they are not present.  (Songs don't follow this.  A user
     * can have the exact same song on their device, but there shouldn't ever be two objects for the exact
     * same Artist or Album.)
     *
     * @param newSongList MainActivity.java will query the MediaStore and pass the list of songs as newSongList
     *                    to this function.
     */
    public void addToLibrary(ArrayList<Song> newSongList) {
        for (Song song : newSongList) {
            // This pattern repeats for future checks:  Uses the details from the current song to see
            // if a matching object already exists in the global library list. Either returns the object
            // from the global list or null if it doesn't exist.
            Artist currentArtist = artistList.stream().filter(artist -> artist.getName().equals(song.getArtist())).findFirst().orElse(null);
            // Local variables used for comparing values of the current song to those in the global lists.
            Album currentAlbum;
            Song currentSong;
            ArrayList<Song> albumSongList = new ArrayList<>();
            if (currentArtist != null) {
                // Artist exists, so we proceed to check if the current album exists.
                currentAlbum = currentArtist.getAlbumList().stream().filter(album -> album.getTitle().equals(song.getAlbum())).findFirst().orElse(null);
                if (currentAlbum != null) {
                    currentSong = currentAlbum.getSongList().stream().filter(s -> s.getTitle().equals(song.getTitle())).findFirst().orElse(null);
                    // Artist and Album both exist, so we add the song to the album.
                    currentAlbum.getSongList().add(song);
                } else {
                    // Artist exists but the Album is null.  Adds the album
                    // and current song to the lists
                    albumSongList.add(song);
                    currentAlbum = new Album(song.getAlbum(), albumSongList, song.getAlbumArtUri());
                    currentArtist.getAlbumList().add(currentAlbum);
                    albumList.add(currentAlbum);
                }
                // These are the album/song lists contained inside of the Artist object, which is separate
                // from the global list of all albums/songs.  Need to sort on every pass while we still
                // have reference to the currentObjects.
                currentAlbum.getSongList().sort((song1, song2) ->
                        Integer.compare(song1.getTrackNumber(), song2.getTrackNumber()));
                currentArtist.getAlbumList().sort((album1, album2) ->
                        album1.getTitle().compareToIgnoreCase(album2.getTitle()));

            } else {
                // Artist is null, so we need to make a new Artist, and the
                // contained Album and Song objects. No sorting is required, since there will only be
                // one album and one song in each list.
                albumSongList.add(song);
                currentAlbum = new Album(song.getAlbum(), albumSongList, song.getAlbumArtUri());
                ArrayList<Album> artistAlbumList = new ArrayList<>();
                artistAlbumList.add(currentAlbum);
                artistList.add(new Artist(song.getArtist(), artistAlbumList));
                albumList.add(currentAlbum);
            }
            // Since we allow duplicate songs, this can be done at the end of the loop, independent
            // of all of the above checks.
            songList.add(song);
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
