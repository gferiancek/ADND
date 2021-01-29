package com.example.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.model.Artist;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.fastscroll.PopupTextProvider;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> implements PopupTextProvider {

    private final ArrayList<Artist> artists;

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView artistTextView;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            artistTextView = itemView.findViewById(R.id.artist_list_tv);
        }
    }

    public ArtistAdapter(ArrayList<Artist> artists, Context context) {
        this.artists = artists;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArtistViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        holder.artistTextView.setText(artists.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    @NonNull
    @Override
    public String getPopupText(int position) {
        return artists.get(position).getName().substring(0, 1).toUpperCase();
    }

}
