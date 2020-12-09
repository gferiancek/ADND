package com.example.baseballtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InningAdapter extends RecyclerView.Adapter<InningAdapter.InningView> {

    private List<String> inningList;
    private boolean isHeader;

    public class InningView extends RecyclerView.ViewHolder {

        TextView inningTextView;

        public InningView(View view) {
            super(view);
            inningTextView = view.findViewById(R.id.inning_textview);
        }
    }

    public InningAdapter(List<String> horizontalList, int headerDetector) {
        this.inningList = horizontalList;
        if (headerDetector == 0) { isHeader = true; }
    }

    @NonNull
    @Override
    public InningView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;

        if (isHeader) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_header, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_inning, parent, false);
        }

        // Since recyclerView is fixed at 9 items, we divide width by 9 to evenly space each view
        itemView.getLayoutParams().width = parent.getMeasuredWidth() / 13;
        return new InningView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InningView holder, int position) {

        holder.inningTextView.setText(inningList.get(position));
    }

    @Override
    public int getItemCount() {
        return inningList.size();
    }
}
