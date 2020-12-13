package com.example.baseballtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView headerRecyclerView;
    RecyclerView topRecyclerView;
    RecyclerView bottomRecyclerView;
    ArrayList<String> headerSource = new ArrayList<>();
    ArrayList<String> topSource = new ArrayList<>();
    ArrayList<String> bottomSource = new ArrayList<>();
    InningAdapter headerAdapter;
    InningAdapter topInningAdapter;
    InningAdapter bottomInningAdapter;
    LinearLayoutManager headerHorizontalLayout;
    LinearLayoutManager topHorizontalLayout;
    LinearLayoutManager bottomHorizontalLayout;
    boolean firstRun = true;
    boolean thirdRun = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headerRecyclerView = findViewById(R.id.header_recyclerview);
        addItemsToRecyclerViewArrayList(headerSource);
        firstRun = false;
        headerAdapter = new InningAdapter(headerSource, 0);
        headerHorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        headerRecyclerView.setLayoutManager(headerHorizontalLayout);
        headerRecyclerView.setAdapter(headerAdapter);

        topRecyclerView = findViewById(R.id.inning_top_recyclerview);
        addItemsToRecyclerViewArrayList(topSource);
        thirdRun = true;
        topInningAdapter = new InningAdapter(topSource, 1);
        topHorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        topRecyclerView.setLayoutManager(topHorizontalLayout);
        topRecyclerView.setAdapter(topInningAdapter);

        bottomRecyclerView = findViewById(R.id.inning_bottom_recyclerview);
        addItemsToRecyclerViewArrayList(bottomSource);
        bottomInningAdapter = new InningAdapter(bottomSource, 1);
        bottomHorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        bottomRecyclerView.setLayoutManager(bottomHorizontalLayout);
        bottomRecyclerView.setAdapter(bottomInningAdapter);

    }

    public void addItemsToRecyclerViewArrayList(ArrayList<String> arrayList) {
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");
        if (thirdRun) {
            arrayList.add("_");
            arrayList.add("7");
            arrayList.add("_");
        } else {
            arrayList.add("6");
            arrayList.add("7");
            arrayList.add("8");
        }
        arrayList.add("9");
        arrayList.add("");
        if (firstRun) {
            arrayList.add("R");
            arrayList.add("H");
            arrayList.add("E");
        } else {
            arrayList.add("8");
            arrayList.add("12");
            arrayList.add("13");
        }
    }

    public void setFocus(View view) {
        TextView inningFocus = (TextView) topRecyclerView.findViewHolderForAdapterPosition(1).itemView.findViewById(R.id.inning_textview);
        inningFocus.setBackgroundResource(R.drawable.inning_focus);
    }
}