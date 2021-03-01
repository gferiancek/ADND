package com.example.musicplayer;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.musicplayer.adapter.MusicPagerAdapter;
import com.example.musicplayer.databinding.ActivityMainBinding;
import com.example.musicplayer.model.MusicLibrary;
import com.example.musicplayer.model.Song;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<Song> songList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isReadStoragePermissionGranted();

        String[] titles = new String[]{
                getString(R.string.now_playing),
                getString(R.string.artists),
                getString(R.string.albums),
                getString(R.string.songs)};

        setSupportActionBar(binding.toolbar);
        binding.viewPager.setAdapter(new MusicPagerAdapter(this, titles));
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> tab.setText(titles[position])).attach();
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

    public void isReadStoragePermissionGranted() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            getUserSongInfo();
            MusicLibrary.getInstance().addToLibrary(songList);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    3);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getUserSongInfo();
            MusicLibrary.getInstance().addToLibrary(songList);
        } else {
            Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Switch isn't necessary since there's only one menu item.  Used so that it's easier to add
        // more menu items when desired.
        switch (item.getItemId()) {
            case R.id.settings_item:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
