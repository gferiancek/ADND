package com.example.musicplayer;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.databinding.ActivityMainBinding;
import com.example.musicplayer.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SongAdapter songAdapter;
    ArrayList<Song> songList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserSongInfo();
        Collections.sort(songList,
                (song1, song2) -> song1.getTitle().compareToIgnoreCase(song2.getTitle()));
        songAdapter = new SongAdapter(songList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        binding.songListRv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        binding.songListRv.setAdapter(songAdapter);


    }

    public void showSize(View view) {
        Toast.makeText(this, String.valueOf(songList.size()), Toast.LENGTH_SHORT).show();
    }

    public void getUserSongInfo() {
        Uri collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
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
            // Cache column indices.
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            int artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
            int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
            int albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
            int trackNumberColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.CD_TRACK_NUMBER);
            int runtimeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int yearColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR);


            while (cursor.moveToNext()) {
                // Get values of columns for a given Audio.
                String name = cursor.getString(nameColumn);
                String artist = cursor.getString(artistColumn);
                String album = cursor.getString(albumColumn);
                String albumId = cursor.getString(albumIdColumn);
                int trackNumber = cursor.getInt(trackNumberColumn);
                int runtime = cursor.getInt(runtimeColumn);
                int year = cursor.getInt(yearColumn);

                String runtimeString = String.format("%2d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(runtime) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(runtime)),
                        TimeUnit.MILLISECONDS.toSeconds(runtime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(runtime)));
                // Stores column values and the contentUri in a local object
                // that represents the media file.
                Uri tempUri = Uri.parse("content://media/external/audio/albumart");
                Uri albumArtUri = ContentUris.withAppendedId(tempUri, Long.parseLong(albumId));
                songList.add(new Song(name, artist, album, trackNumber, runtimeString, year, albumArtUri));
            }
        }
    }
}
