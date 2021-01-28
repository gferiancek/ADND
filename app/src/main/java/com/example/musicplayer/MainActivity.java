package com.example.musicplayer;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.musicplayer.adapter.ArtistAdapter;
import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.databinding.ActivityMainBinding;
import com.example.musicplayer.model.MusicLibrary;
import com.example.musicplayer.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SongAdapter songAdapter;
    ArrayList<Song> songList = new ArrayList<>();
    ArtistAdapter artistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserSongInfo();
        Collections.sort(songList, (song1, song2) ->
                song1.getTitle().compareToIgnoreCase(song2.getTitle()));
        songAdapter = new SongAdapter(songList, this);
        binding.songListRv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        binding.songListRv.setAdapter(songAdapter);
        MusicLibrary musicLibrary = new MusicLibrary();
        musicLibrary.addSongToLibrary(songList);

        artistAdapter = new ArtistAdapter(musicLibrary.getArtistList(), this);
        binding.artistListRv.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));
        binding.artistListRv.setAdapter(artistAdapter);

    }

    public void getUserSongInfo() {
        Uri collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ALBUM_ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.CD_TRACK_NUMBER,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.ALBUM_ID,
        };
        try (Cursor cursor = getApplicationContext().getContentResolver().query(
                collection,
                projection,
                null,
                null,
                null
        )) {
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            int fileNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ARTIST);
            int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
            int albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
            int trackNumberColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.CD_TRACK_NUMBER);
            int runtimeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int yearColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR);

            while (cursor.moveToNext()) {
                // Null check each value and either save value or provide default value
                String name = cursor.getString(nameColumn) != null ?
                        cursor.getString(nameColumn) : cursor.getString(fileNameColumn);
                String artist = cursor.getString(artistColumn) != null ?
                        cursor.getString(artistColumn) : getString(R.string.unknown_text);
                String album = cursor.getString(albumColumn) != null ?
                        cursor.getString(albumColumn) : getString(R.string.unknown_text);
                long albumId = cursor.getLong(albumIdColumn);
                int trackNumber = cursor.getInt(trackNumberColumn);
                int runtime = cursor.getInt(runtimeColumn);
                int year = cursor.getInt(yearColumn);

                String runtimeString = String.format(Locale.getDefault(), "%2d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(runtime),
                        TimeUnit.MILLISECONDS.toSeconds(runtime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(runtime)));

                Uri tempUri = Uri.parse("content://media/external/audio/albumart");
                Uri albumArtUri = ContentUris.withAppendedId(tempUri, albumId);
                songList.add(new Song(name, artist, album, trackNumber, runtimeString, year, albumArtUri));
            }
        }
    }
}
