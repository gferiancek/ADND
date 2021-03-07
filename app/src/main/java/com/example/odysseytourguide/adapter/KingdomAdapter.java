package com.example.odysseytourguide.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.odysseytourguide.R;
import com.example.odysseytourguide.model.Kingdom;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class KingdomAdapter extends RecyclerView.Adapter<KingdomAdapter.KingdomViewHolder> {

    private final ArrayList<Kingdom> kingdoms;
    private final Context context;
    private final LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    private int lastExpandedPosition = -1;

    public class KingdomViewHolder extends RecyclerView.ViewHolder {

        ImageView kingdomImageView;
        ImageView moonImageView;
        ImageView regionalImageView;
        TextView kingdomTextView;
        TextView moonTextView;
        TextView regionalTextView;
        ConstraintLayout detailsLayout;
        ConstraintLayout headerLayout;
        Guideline withoutRegional;
        Guideline withRegional;
        ListView landmarkListView;

        public KingdomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.kingdomImageView = itemView.findViewById(R.id.kingdom_list_iv);
            this.moonImageView =  itemView.findViewById(R.id.moon_iv);
            this.regionalImageView = itemView.findViewById(R.id.regional_iv);
            this.kingdomTextView = itemView.findViewById(R.id.kingdom_list_tv);
            this.moonTextView = itemView.findViewById(R.id.moon_tv);
            this.regionalTextView = itemView.findViewById(R.id.regional_tv);
            this.detailsLayout = itemView.findViewById(R.id.expandable_cl);
            this.headerLayout = itemView.findViewById(R.id.header_cl);
            this.withoutRegional = itemView.findViewById(R.id.guideline_3);
            this.withRegional = itemView.findViewById(R.id.guideline_2);
            this.landmarkListView = itemView.findViewById(R.id.landmark_lv);

            RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(context) {
                @Override
                protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return super.calculateSpeedPerPixel(displayMetrics) * 1.25f;
                }
            };

            headerLayout.setOnClickListener(v -> {
                Kingdom currentKingdom = kingdoms.get(getAdapterPosition());
                if (lastExpandedPosition != -1 && lastExpandedPosition != kingdoms.indexOf(currentKingdom)) {
                    Kingdom lastExpandedKingdom = kingdoms.get(lastExpandedPosition);
                    lastExpandedKingdom.setExpanded(!lastExpandedKingdom.isExpanded());
                    notifyItemChanged(lastExpandedPosition);
                }
                currentKingdom.setExpanded(!currentKingdom.isExpanded());
                notifyItemChanged(getAdapterPosition());

                // 3 cards fit on the screen, so if we are >= the size - 3, then we will disable smooth
                // scrolling when collapsing the card.  This prevents the app from trying to scroll to
                // a position that is already on screen, and avoids visual artifacts.
                if (currentKingdom.isExpanded()) {
                    smoothScroller.setTargetPosition(getAdapterPosition());
                    layoutManager.startSmoothScroll(smoothScroller);
                }
                if (currentKingdom.isExpanded()) {
                    lastExpandedPosition = kingdoms.indexOf(currentKingdom);
                } else {
                    lastExpandedPosition = -1;
                }
            });
        }
    }

    public KingdomAdapter(ArrayList<Kingdom> kingdoms, Context context, LinearLayoutManager layoutManager) {
        this.kingdoms = kingdoms;
        this.context = context;
        this.layoutManager = layoutManager;
    }

    @NonNull
    @Override
    public KingdomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KingdomViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kingdom_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KingdomViewHolder holder, int position) {
        Kingdom currentKingdom = kingdoms.get(position);
        Glide.with(context)
                .load(currentKingdom.getImageID())
                .into(holder.kingdomImageView);
        holder.kingdomTextView.setText(currentKingdom.getKingdomName());
        if (currentKingdom.isExpanded()) {
            holder.kingdomTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_baseline_expand_less_36, 0);
        } else {
            holder.kingdomTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_baseline_expand_more_36, 0);
        }

        ConstraintSet constraintSet1 = new ConstraintSet();
        ConstraintSet constraintSet2 = new ConstraintSet();
        constraintSet1.clone(holder.headerLayout);
        constraintSet2.clone(holder.headerLayout);
        if (currentKingdom.getRegionalImageID() == -1) {
            constraintSet1.connect(holder.moonTextView.getId(), ConstraintSet.END,
                    holder.headerLayout.getId(), ConstraintSet.END, 0);
            constraintSet2.connect(holder.moonImageView.getId(), ConstraintSet.START,
                    holder.withoutRegional.getId(), ConstraintSet.END, 0);
        } else {
            constraintSet1.connect(holder.moonTextView.getId(), ConstraintSet.END,
                    holder.regionalImageView.getId(), ConstraintSet.START, 0);
            constraintSet2.connect(holder.moonImageView.getId(), ConstraintSet.START,
                    holder.withRegional.getId(), ConstraintSet.END, 0);
        }
        constraintSet1.applyTo(holder.headerLayout);
        constraintSet2.applyTo(holder.headerLayout);

        Glide.with(context)
                .load(currentKingdom.getPowerMoonImageID())
                .into(holder.moonImageView);
        holder.moonTextView.setText(currentKingdom.getPowerMoonCount());
        Glide.with(context)
                .load(currentKingdom.getRegionalImageID())
                .into(holder.regionalImageView);
        holder.regionalTextView.setText(currentKingdom.getRegionalCount());

        boolean isExpanded = currentKingdom.isExpanded();
        holder.detailsLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return kingdoms.size();
    }
}
