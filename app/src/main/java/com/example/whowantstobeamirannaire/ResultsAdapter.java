package com.example.whowantstobeamirannaire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Rank> rankList;
    private final static int TYPE_BOLD = 1;
    private final static int TYPE_NORMAL = 2;

    /**
     * Two instances of the ViewHolder are made so that we can bold certain items within the recyclerviewer,
     * mainly the title and the rank attained by the user.
     */
    public ResultsAdapter(List<Rank> list) {
        this.rankList = list;
    }

    public class BoldViewHolder extends RecyclerView.ViewHolder {
        private TextView rankTextView;
        private TextView rangeTextView;
        private TextView prizeTextView;

        public BoldViewHolder(View view) {
            super(view);
            rankTextView = view.findViewById(R.id.rank_header);
            rangeTextView = view.findViewById(R.id.range_header);
            prizeTextView = view.findViewById(R.id.prize_header);
        }

        public void setBoldDetails(Rank rank) {
            rankTextView.setText(rank.getRankList()[0]);
            rangeTextView.setText(rank.getRankList()[1]);
            prizeTextView.setText(rank.getRankList()[2]);
        }
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {

        private TextView rankTextView;
        private TextView rangeTextView;
        private TextView prizeTextView;

        public NormalViewHolder(View view) {
            super(view);
            rankTextView = view.findViewById(R.id.rank_text_view);
            rangeTextView = view.findViewById(R.id.range_text_view);
            prizeTextView = view.findViewById(R.id.prize_text_view);
        }

        public void setNormalDetails(Rank rank) {
            rankTextView.setText(rank.getRankList()[0]);
            rangeTextView.setText(rank.getRankList()[1]);
            prizeTextView.setText(rank.getRankList()[2]);
        }
    }

    /**
     * Used to determine if the current list item should have bold or normal weighted text.
     */
    @Override
    public int getItemViewType(int position) {
        if (rankList.get(position).isBold) {
            return TYPE_BOLD;
        } else {
            return TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;

        if (viewType == TYPE_NORMAL) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rank_list_item, parent, false);
            return new NormalViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rank_list_header, parent, false);
            return new BoldViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            ((NormalViewHolder) holder).setNormalDetails(rankList.get(position));
        } else {
            ((BoldViewHolder) holder).setBoldDetails(rankList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }
}