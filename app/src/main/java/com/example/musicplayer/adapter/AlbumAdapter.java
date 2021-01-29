package com.example.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musicplayer.R;
import com.example.musicplayer.model.Album;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.fastscroll.PopupTextProvider;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> implements PopupTextProvider {

    private final ArrayList<Album> albums;
    private final Context context;

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_album_iv);
            textView = itemView.findViewById(R.id.list_album_tv);
        }
    }

    public AlbumAdapter(ArrayList<Album> albums, Context context) {
        this.albums = albums;
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Glide.with(context)
                .load(albums.get(position).getAlbumArtUri())
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);
        holder.textView.setText(albums.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    @NonNull
    @Override
    public String getPopupText(int position) {
        return albums.get(position).getTitle().substring(0, 1).toUpperCase();
    }
}
