package com.example.whowantstobeamirannaire;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whowantstobeamirannaire.databinding.FragmentResultsBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ResultsFragment extends Fragment {

    private FragmentResultsBinding binding;
    private String titleMessage;
    private int playerRank;
    private int totalScore;

    public ResultsFragment() {
        // Required empty public constructor
    }

    public static ResultsFragment newInstance(int playerRank, String titleMessage, int totalScore) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("playerRank", playerRank);
        bundle.putString("titleMessage", titleMessage);
        bundle.putInt("totalScore", totalScore);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.playerRank = bundle.getInt("playerRank");
            this.titleMessage = bundle.getString("titleMessage");
            this.totalScore = bundle.getInt("totalScore");
            Toast.makeText(getContext(), getString(R.string.resultMessage, totalScore, playerRank), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentResultsBinding.inflate(inflater, container, false);
        // Setup RecyclerView
        ResultsAdapter resultsAdapter = new ResultsAdapter(initializeRanks());
        binding.resultsRecyclerView.setAdapter(resultsAdapter);
        binding.resultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.resultsTitleTextView.setText(titleMessage);

        return binding.getRoot();
    }

    private List<Rank> initializeRanks() {
        List<Rank> rankList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            boolean isBold = false;
            if (i == 0 || i == playerRank) {
                isBold = true;
            }
            String arrayIDString = "rank_" + i;
            int arrayID = getResources().getIdentifier(arrayIDString, "array", getContext().getPackageName());
            rankList.add(new Rank(getResources().getStringArray(arrayID), isBold));
        }
        return rankList;
    }
}