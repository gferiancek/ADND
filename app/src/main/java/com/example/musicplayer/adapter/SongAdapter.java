package com.example.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musicplayer.R;
import com.example.musicplayer.model.Song;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.fastscroll.PopupTextProvider;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> implements PopupTextProvider {

    private final ArrayList<Song> songs;
    private final Context context;

    public static class SongViewHolder extends RecyclerView.ViewHolder {

        ImageView albumArtImageView;
        TextView titleTextView;
        TextView artistTextView;
        TextView runtimeTextView;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            albumArtImageView = itemView.findViewById(R.id.album_art_iv);
            titleTextView = itemView.findViewById(R.id.title_tv);
            artistTextView = itemView.findViewById(R.id.artist_tv);
            runtimeTextView = itemView.findViewById(R.id.runtime_tv);
        }
    }

    public SongAdapter(ArrayList<Song> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.titleTextView.setText(songs.get(position).getTitle());
        holder.artistTextView.setText(songs.get(position).getArtist());
        holder.runtimeTextView.setText(songs.get(position).getRuntime());
        Glide.with(context)
                .load(songs.get(position).getAlbumArtUri())
                .placeholder(R.drawable.placeholder)
                .into(holder.albumArtImageView);

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    @NonNull
    @Override
    public String getPopupText(int position) {
        return songs.get(position).getTitle().substring(0, 1).toUpperCase();
    }
}
