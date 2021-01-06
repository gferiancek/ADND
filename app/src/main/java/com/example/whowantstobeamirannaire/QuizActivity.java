package com.example.whowantstobeamirannaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.whowantstobeamirannaire.databinding.ActivityQuizBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    ActivityQuizBinding binding;
    ArrayList<Question> questionList = new ArrayList<>();
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // prepare all of the questions for the selected difficulty
        Intent intent = getIntent();
        String currentDifficulty = intent.getStringExtra("difficulty");
        ArrayList<Question> questionList = initializeQuestions(currentDifficulty);

        // Load the initial fragment and start the quiz!
        Bundle questionBundle = new Bundle();
        questionBundle.putParcelableArrayList("questionList", questionList);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, QuestionFragment.class, questionBundle)
                    .commit();
        }

        // Media Player
        mediaPlayer = MediaPlayer.create(this, R.raw.arrival_existence);
        mediaPlayer.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    /**
     * Used to enable Sticky Immersive Mode.
     */
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    /**
     * Function that makes the question objects for each difficutly and adds them into a List.
     *
     * @param difficulty  Pulled from the MainActivity to load the questions for the selected difficultly.
     */
    private ArrayList<Question> initializeQuestions(String difficulty) {
        Resources appResources = getResources();
        switch (difficulty) {
            case "normal":
                for (int i = 1; i <= 20; i++) {
                    int normalQuestionID = appResources.getIdentifier("normal_question_" + i, "string", getPackageName());
                    int normalChoicesID = appResources.getIdentifier("normal_answers_" + i, "array", getPackageName());
                    String[] normalChoices = appResources.getStringArray(normalChoicesID);
                    questionList.add(new Question(getString(normalQuestionID), normalChoices, normalChoices[0]));
                }
                break;
            case "hard":
                for (int i = 1; i <= 20; i++) {
                    int hardQuestionID = appResources.getIdentifier("hard_question_" + i, "string", getPackageName());
                    int hardChoicesID = appResources.getIdentifier("hard_answers_" + i, "array", getPackageName());
                    String[] hardChoices = appResources.getStringArray(hardChoicesID);
                    questionList.add(new Question(getString(hardQuestionID), hardChoices, hardChoices[0]));
                }
                break;
            case "brutal":
                for (int i = 1; i <= 20; i++) {
                    int brutalQuestionID = appResources.getIdentifier("brutal_question_" + i, "string", getPackageName());
                    int brutalChoicesID = appResources.getIdentifier("brutal_answers_" + i, "array", getPackageName());
                    String[] brutalChoices = appResources.getStringArray(brutalChoicesID);
                    questionList.add(new Question(getString(brutalQuestionID), brutalChoices, brutalChoices[0]));
                }
                break;
            case "maniac":
                for (int i = 1; i <= 20; i++) {
                    int maniacQuestionID = appResources.getIdentifier("maniac_question_" + i, "string", getPackageName());
                    int maniacChoicesID = appResources.getIdentifier("maniac_answers_" + i, "array", getPackageName());
                    String[] maniacChoices = appResources.getStringArray(maniacChoicesID);
                    questionList.add(new Question(getString(maniacQuestionID), maniacChoices, maniacChoices[0]));
                }
                break;
        }
        // Question order is randomized, so we shuffle.  Also only 10 questions per quiz, so we remove
        // the extra with sublist().clear()
        Collections.shuffle(questionList);
        questionList.subList(10, 19).clear();
        return questionList;
    }


    /**
     * Next three functions are Overridden to properly handle the mediaPlaer.
     */
    @Override
    protected void onPause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}