package com.example.whowantstobeamirannaire;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.whowantstobeamirannaire.databinding.ActivityQuizBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Explode;

public class QuizActivity extends AppCompatActivity implements PassDataInterface {

    ActivityQuizBinding binding;
    ArrayList<Question> questionList = new ArrayList<>();
    MediaPlayer mediaPlayer;
    String currentDifficulty;
    double difficultyMultiplier;
    boolean isDevModeEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // prepare all of the questions for the selected difficulty; loaded from intent sent from Main Activity
        Intent intent = getIntent();
        currentDifficulty = intent.getStringExtra("difficulty");
        isDevModeEnabled = intent.getBooleanExtra("devMode", false);
        ArrayList<Question> questionList = initializeQuestions(currentDifficulty);

        // Load the initial fragment and start the quiz!
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, QuestionFragment.newInstance(questionList, QuizActivity.this, difficultyMultiplier, binding))
                    .commit();
        }
        playMusic(currentDifficulty);
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
     * @param difficulty Pulled from the MainActivity to load the questions for the selected difficultly.
     *                   <p>
     *                   NOTE: the correctAnswer field is always choices.get(0).  In the stringArray, it is sorted so that the
     *                   correct answer is always choice number 1.  That way, we can just bundle all choices in a single array
     *                   and not have to worry about making a separate string in strings.xml or manually coding in the position of the correct
     *                   answer.  Instead we just make a temporary string, correctAnswer, and then pass it to the Question.
     */
    private ArrayList<Question> initializeQuestions(String difficulty) {
        String questionIDString = "";
        String choiceIDString = "";
        // Strings are named diffuculty_question_# and difficulty_answers_#, so we can easily progmatically
        // cycle through them.  This will set the proper prefixes, and we add the # below in the for loop.
        switch (difficulty) {
            case "normal":
                questionIDString = "normal_question_";
                choiceIDString = "normal_answers_";
                difficultyMultiplier = 1.00;
                break;
            case "hard":
                questionIDString = "hard_question_";
                choiceIDString = "hard_answers_";
                difficultyMultiplier = 1.50;
                break;
            case "brutal":
                questionIDString = "brutal_question_";
                choiceIDString = "brutal_answers_";
                difficultyMultiplier = 2.00;
                break;
            case "maniac":
                questionIDString = "maniac_question_";
                choiceIDString = "maniac_answers_";
                difficultyMultiplier = 3.00;
                break;

        }

        Resources appResources = getResources();
        // Uses the values defined above to navigate to the proper strings and add 20 questions to the questionList
        for (int i = 1; i <= 20; i++) {
            int questionID = appResources.getIdentifier(questionIDString + i, "string", getPackageName());
            int choicesID = appResources.getIdentifier(choiceIDString + i, "array", getPackageName());
            List<String> choices = Arrays.asList(appResources.getStringArray(choicesID));
            String correctAnswer = choices.get(0);
            // If Dev mode is off, we will shuffle all the choices so the correct answer isn't at the top
            if (!isDevModeEnabled) {
                Collections.shuffle(choices);
            }
            questionList.add(new Question(getString(questionID), choices, correctAnswer));
        }
        // Question order is randomized, so we shuffle.  Also only 10 questions per quiz, so we remove
        // the extra with sublist().clear()
        Collections.shuffle(questionList);
        questionList.subList(9, 19).clear();

        // The quiz features two bonus questions if you get 1 or less questions wrong.  we add that here
        // AFTER we shuffle so that they are on the end of the list. These are used to fulfill the requirement
        // of having multiple input types for the questions.
        List<String> bonusChoices = Arrays.asList(appResources.getStringArray(R.array.bonus_answers_1));
        // This will be a checkbox answer, so it requires two choices to be selected.
        String correctAnswer = bonusChoices.get(0) + bonusChoices.get(2);
        questionList.add(new Question(getString(R.string.bonus_question_1), bonusChoices, correctAnswer));
        // The second bonus question uses an EditText for input, so choices are a blank array.
        questionList.add(new Question(getString(R.string.bonus_question_2), new ArrayList<>(), getString(R.string.bonus_answer_2).toLowerCase()));
        return questionList;
    }


    /**
     * Next three functions are Overridden to properly handle the mediaPlayer.
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

    private void scoreQuiz() {

    }

    /**
     * @param totalScore is sent from the QuestionFragment once it determines that the user has failed
     *                   or finished the quiz.  It is used in this interface to pass that data back to the MainActicity,
     *                   and is then used to call the ResultsFragment and displays the user's score and other relevant information.
     */
    @Override
    public void onDataReceived(int totalScore) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        int playerRank = calculatePlayerRank(totalScore);
        String titleMessage = "";

        if (playerRank > 7) {
            playMusic("fail");
            titleMessage = getString(R.string.fail);
        } else if (playerRank > 4) {
            playMusic("semipass");
            titleMessage = getString(R.string.semipass);
        } else if (playerRank > 1) {
            playMusic("pass");
            titleMessage = getString(R.string.pass);
        } else {
            playMusic("perfect");
            titleMessage = getString(R.string.perfect);
        }
        // creates results fragment and sets the transition.
        ResultsFragment resultsFragment = ResultsFragment.newInstance(playerRank, titleMessage, totalScore);
        resultsFragment.setEnterTransition(new Explode().setDuration(200));
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, resultsFragment, null)
                .commit();
    }

    public void playMusic(String songTitle) {
        int songID = getResources().getIdentifier(songTitle, "raw", getPackageName());

        mediaPlayer = MediaPlayer.create(this, songID);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    /**
     * Simply checks the player's score and assigngs a numerical rank based on that score.  1 is the
     * best rank, 10 is the worst.
     */
    public int calculatePlayerRank(int totalScore) {
        int playerRank;
        if (totalScore >= 400000) {
            playerRank = 1;
        } else if (totalScore >= 350000) {
            playerRank = 2;
        } else if (totalScore >= 300000) {
            playerRank = 3;
        } else if (totalScore >= 250000) {
            playerRank = 4;
        } else if (totalScore >= 200000) {
            playerRank = 5;
        } else if (totalScore >= 150000) {
            playerRank = 6;
        } else if (totalScore >= 100000) {
            playerRank = 7;
        } else if (totalScore >= 50000) {
            playerRank = 8;
        } else if (totalScore >= 10000) {
            playerRank = 9;
        } else {
            playerRank = 10;
        }
        return playerRank;
    }
}
