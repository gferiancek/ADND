package com.example.whowantstobeamirannaire;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whowantstobeamirannaire.databinding.FragmentQuestionBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuestionFragment extends Fragment {

    private int questionIndex;
    private Question currentQuestion;
    private ArrayList<Question> questionList = new ArrayList<>();
    private FragmentQuestionBinding binding;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            questionList = bundle.getParcelableArrayList("questionList");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        currentQuestion = questionList.get(questionIndex);
        assignDataToViews();
        View view = binding.getRoot();
        return view;
    }

    private void assignDataToViews() {
        binding.questionTextView.setText(currentQuestion.getQuestion());
        binding.choice1.setText(currentQuestion.getAnswerChoices()[0]);
        binding.choice2.setText(currentQuestion.getAnswerChoices()[1]);
        binding.choice3.setText(currentQuestion.getAnswerChoices()[2]);
        binding.choice4.setText(currentQuestion.getAnswerChoices()[3]);
    }
}