package com.example.whowantstobeamirannaire;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.whowantstobeamirannaire.databinding.ActivityQuizBinding;
import com.example.whowantstobeamirannaire.databinding.FragmentQuestionBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.fragment.app.Fragment;

public class QuestionFragment extends Fragment {

    private int questionIndex;
    private int totalScore;
    private int numberOfWrongAnswers;
    private int red_500;
    private boolean quizNotFailed = true;
    private double difficultyMultiplier;

    private Question currentQuestion;
    private ArrayList<Question> questionList = new ArrayList<>();
    private FragmentQuestionBinding binding;
    private CountDownTimer countDownTimer;
    static public PassDataInterface sendTotalScore;
    static ActivityQuizBinding activityBinding;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * @param questionList         Is the list of questions for the quiz
     * @param passDataInterface    is used to communicate with QuizActivity.
     * @param difficultyMultiplier adds a multiplier to the final score, based on selected difficulty.
     * @return
     */
    public static QuestionFragment newInstance(ArrayList<Question> questionList, PassDataInterface passDataInterface, double difficultyMultiplier, ActivityQuizBinding binding) {
        QuestionFragment questionFragment = new QuestionFragment();
        sendTotalScore = passDataInterface;
        activityBinding = binding;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("questionList", questionList);
        bundle.putDouble("difficultyMultiplier", difficultyMultiplier);
        questionFragment.setArguments(bundle);
        return questionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            questionList = bundle.getParcelableArrayList("questionList");
            difficultyMultiplier = bundle.getDouble("difficultyMultiplier");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQuestionBinding.inflate(inflater, container, false);

        // Set onClick Listener for the Submit Button
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAnswer();
            }
        });

        // Set up timer and progressBar
        countDownTimer = new CountDownTimer(7000, 25) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timerTextView.setText(String.format(Locale.getDefault(), "%.3f", (float) millisUntilFinished * .001));
                binding.timerProgressbar.setProgress((int) millisUntilFinished);
            }

            // If the user doesn't answer before the timer is over, a blank submission is sent instead.
            @Override
            public void onFinish() {
                submitAnswer();
            }
        };

        // Get a handle on colors for wrong answers
        red_500 = getResources().getColor(R.color.red_500);

        // Everything is set up, so we can load the first question, and start the quiz!
        loadNextQuestion();
        return binding.getRoot();
    }

    /**
     * Fetches the answer from the user's selection and passes that info to the proper helper functions.
     */
    public void submitAnswer() {
        String submittedAnswer = "";
        // Ensure timer is cancelled so we can restart it for the next question.
        countDownTimer.cancel();
        if (questionIndex < 10) {
            // Fetches user's selection by getting the id of checked RadioButton.
            int selectedRadioId = binding.choicesContainer.getCheckedRadioButtonId();
            binding.choicesContainer.clearCheck();
            // Ensures that a radio button was clicked to avoid calling getText on a null view
            if (selectedRadioId != -1) {
                RadioButton selectedButton = getView().findViewById(selectedRadioId);
                submittedAnswer = selectedButton.getText().toString();
            }
        } else if (questionIndex == 10) {
            if (binding.checkbox1.isChecked()) {
                submittedAnswer += binding.checkbox1.getText().toString();
            }
            if (binding.checkbox2.isChecked()) {
                submittedAnswer += binding.checkbox2.getText().toString();
            }
            if (binding.checkbox3.isChecked()) {
                submittedAnswer += binding.checkbox3.getText().toString();
            }
            if (binding.checkbox4.isChecked()) {
                submittedAnswer += binding.checkbox4.getText().toString();
            }
        } else {
            submittedAnswer += binding.bonusEditText.getText().toString().toLowerCase();
        }

        scoreQuestion(submittedAnswer);
        // Delay to change Campanella's face back to the default.  Let's the user see the correct/incorrect
        // face for a short amount of time before reverting.
        if (quizNotFailed) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    binding.campanellaImageView.setImageResource(R.drawable.campanella_8);
                }
            }, 750);
        }
        questionIndex++;
        activityBinding.quizScrollView.scrollTo(0, 0);
        loadNextQuestion();
    }

    /**
     * @param submittedAnswer is the user input from the submitAnswer() function, and is compared to the
     *                        currentQuestion object's correct answer field.  Takes the appropriate action for a right/wrong answer.
     *                        <p>
     *                        If the questionIndex is < 10, then the user is in the main part of the quiz, where points are based
     *                        on remaining time left on the timer.  If it is 10 or above, they are in the bonus question section
     *                        which has no timer and gives a fixed amount of points for a correct answer, with no penalty for a wrong answer.
     */
    private void scoreQuestion(String submittedAnswer) {
        if (questionIndex < 10) {
            if (currentQuestion.getCorrectAnswer().equals(submittedAnswer)) {
                // Text view is shown in seconds, but we award 1 point per millisecond, so we times the value by 1000.
                float currentScore = Float.parseFloat(binding.timerTextView.getText().toString()) * 1000;
                totalScore += currentScore;
                binding.scoreTextView.setText(String.valueOf(totalScore));
                // Image shown for correct answers
                binding.campanellaImageView.setImageResource(R.drawable.campanella_9);
            } else {
                numberOfWrongAnswers += 1;
                // Image shown for wrong answers
                binding.campanellaImageView.setImageResource(R.drawable.campanella_10);
                switch (numberOfWrongAnswers) {
                    case 1:
                        binding.wrongImageView1.setColorFilter(red_500);
                        break;
                    case 2:
                        binding.wrongImageView2.setColorFilter(red_500);
                        break;
                    case 3:
                        quizNotFailed = false;
                        break;
                }
            }
        } else {
            if (currentQuestion.getCorrectAnswer().equals(submittedAnswer)) {
                int bonusQuestionValue = 1500;
                totalScore += bonusQuestionValue;
                Toast.makeText(getActivity(), getString(R.string.bonus_correct), Toast.LENGTH_SHORT).show();
                binding.scoreTextView.setText(String.valueOf(totalScore));
                // Image shown for correct answers
                binding.campanellaImageView.setImageResource(R.drawable.campanella_9);
            } else {
                Toast.makeText(getActivity(), getString(R.string.bonus_wrong), Toast.LENGTH_SHORT).show();
                binding.campanellaImageView.setImageResource(R.drawable.campanella_10);
            }
        }
    }

    /**
     * If the questionIndex < questionList.size() && quizNotFailed then we check if it's less than 10, which
     * means we are on a normal question. If the user has gotten 1 or less questions wrong, we will load in the
     * bonus questions.  If questionIndex is 10, we're on the first bonus question using Checkboxes.  If it is
     * 11 we are on the second bonus question using an EditText.
     * <p>
     * Equation for total score is the totalScore from answering questions * # of x's remaining * difficultyMultiplier.
     * If the quiz was failed, we just do totalScore * difficultyMultiplier to avoid * by 0.  Otherwise, the
     * normal equation persists.
     */
    private void loadNextQuestion() {
        if (questionIndex < questionList.size() && quizNotFailed) {
            currentQuestion = questionList.get(questionIndex);
            List<String> currentChoices = currentQuestion.getAnswerChoices();
            binding.questionTextView.setText(currentQuestion.getQuestion());
            if (questionIndex < 10) {
                binding.choice1.setText(currentChoices.get(0));
                binding.choice2.setText(currentChoices.get(1));
                binding.choice3.setText(currentChoices.get(2));
                binding.choice4.setText(currentChoices.get(3));
                countDownTimer.start();
            } else if (questionIndex == 10) {
                // Bonus question 1 is checkboxes, so we set them up
                binding.choicesContainer.setVisibility(View.GONE);
                binding.checkboxContainer.setVisibility(View.VISIBLE);
                binding.checkbox1.setText(currentChoices.get(0));
                binding.checkbox2.setText(currentChoices.get(1));
                binding.checkbox3.setText(currentChoices.get(2));
                binding.checkbox4.setText(currentChoices.get(3));
            } else {
                // Bonus question 2 is an EditText, so we set that up
                binding.checkboxContainer.setVisibility(View.GONE);
                binding.bonusEditText.setVisibility(View.VISIBLE);
            }
        } else if (!quizNotFailed) {
            // This is triggered when a user has 3 wrong answers.
            totalScore = (int) (totalScore * difficultyMultiplier);
            sendTotalScore.onDataReceived(totalScore);
        } else {
            // User has answered all questions and finished the quiz!
            totalScore = (int) (totalScore * (3 - numberOfWrongAnswers) * difficultyMultiplier);
            sendTotalScore.onDataReceived(totalScore);
        }
    }

    /**
     * If the user exits in the middle of a quiz, we need to make sure the countDownTimer
     * does not continue to run in the background.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        countDownTimer = null;
    }
}