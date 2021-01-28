package com.example.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.model.Artist;
import com.qtalk.recyclerviewfastscroller.RecyclerViewFastScroller;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> implements RecyclerViewFastScroller.OnPopupTextUpdate {

    private ArrayList<Artist> artists;
    private Context context;

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView artistTextView;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            artistTextView = itemView.findViewById(R.id.artist_list_tv);
        }
    }

    public ArtistAdapter(ArrayList<Artist> artists, Context context) {
        this.artists = artists;
        this.context = context;
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

    @NotNull
    @Override
    public CharSequence onChange(int i) {
        return artists.get(i).getName().substring(0, 1).toUpperCase();
    }
}
