package com.example.baseballtracker;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    // Constants
    public static final int TOTAL_RUNS_POSITION = 10;
    public static final int TOTAL_HITS_POSITION = 11;
    public static final int TOTAL_ERRORS_POSITION = 12;
    public static final ArrayList<String> STARTING_HEADER_DATA = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "", "R", "H", "E"));
    public static final ArrayList<String> STARTING_INNING_DATA = new ArrayList<>(Arrays.asList("_", "_", "_", "_", "_", "_", "_", "_", "_", "", "0", "0", "0"));
    public static final String AWAY_HIT = "away_hit";
    public static final String AWAY_RUN = "away_run";
    public static final String AWAY_ERROR = "away_error";
    public static final String AWAY_STRIKE = "away_strike";
    public static final String AWAY_OUT = "away_out";
    public static final String AWAY_BALL = "away_ball";
    public static final String HOME_HIT = "home_hit";
    public static final String HOME_RUN = "home_run";
    public static final String HOME_ERROR = "home_error";
    public static final String HOME_STRIKE = "home_strike";
    public static final String HOME_OUT = "home_out";
    public static final String HOME_BALL = "home_ball";
    // RecyclerView Components
    RecyclerView headerRecyclerView;
    RecyclerView topRecyclerView;
    RecyclerView bottomRecyclerView;
    InningAdapter headerAdapter;
    InningAdapter topInningAdapter;
    InningAdapter bottomInningAdapter;
    LinearLayoutManager headerHorizontalLayout;
    LinearLayoutManager topHorizontalLayout;
    LinearLayoutManager bottomHorizontalLayout;

    // Game State Components
    int currentInning = 1;
    boolean isTopInning = true;
    boolean isGameOver;


    // Current Inning Components
    int awayCurrentHits;
    int awayCurrentRuns;
    int awayCurrentErrors;
    int awayStrikeCount;
    int awayOutCount;
    int awayBallCount;
    int homeCurrentHits;
    int homeCurrentRuns;
    int homeCurrentErrors;
    int homeStrikeCount;
    int homeOutCount;
    int homeBallCount;
    int red_500;
    int gray_500_light;
    TextView awayHitTextView;
    TextView awayRunTextView;
    TextView awayErrorTextView;
    ImageView awayStrikeOne;
    ImageView awayStrikeTwo;
    ImageView awayOutOne;
    ImageView awayOutTwo;
    ImageView awayBallOne;
    ImageView awayBallTwo;
    ImageView awayBallThree;
    TextView homeHitTextView;
    TextView homeRunTextView;
    TextView homeErrorTextView;
    ImageView homeStrikeOne;
    ImageView homeStrikeTwo;
    ImageView homeOutOne;
    ImageView homeOutTwo;
    ImageView homeBallOne;
    ImageView homeBallTwo;
    ImageView homeBallThree;

    // Scoreboard Components
    int awayTotalRuns;
    int awayTotalHits;
    int awayTotalErrors;
    int homeTotalRuns;
    int homeTotalHits;
    int homeTotalErrors;
    ArrayList<String> headerData = new ArrayList<>();
    ArrayList<String> awayData = new ArrayList<>();
    ArrayList<String> homeData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets up necessary components for the header RecyclerView. Header detector is used to tell
        // the InningAdapter how to style the TextViews.
        headerData.addAll(STARTING_HEADER_DATA);
        headerRecyclerView = findViewById(R.id.header_recyclerview);
        headerAdapter = new InningAdapter(headerData, 0);
        headerHorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        headerRecyclerView.setLayoutManager(headerHorizontalLayout);
        headerRecyclerView.setAdapter(headerAdapter);

        // Sets up necessary components for the Top Inning RecyclerView
        awayData.addAll(STARTING_INNING_DATA);
        topRecyclerView = findViewById(R.id.inning_top_recyclerview);
        topInningAdapter = new InningAdapter(awayData, 1);
        topHorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        topRecyclerView.setLayoutManager(topHorizontalLayout);
        topRecyclerView.setAdapter(topInningAdapter);
        // Used to only call the setFocus() function once the layout of the RecyclerView has been loaded
        topRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setFocus();
                // We only need this listener for the initial startup, so we remove it on the first call
                topRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        // Sets up necessary components for the Bottom Inning RecyclerView
        homeData.addAll(STARTING_INNING_DATA);
        bottomRecyclerView = findViewById(R.id.inning_bottom_recyclerview);
        bottomInningAdapter = new InningAdapter(homeData, 1);
        bottomHorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        bottomRecyclerView.setLayoutManager(bottomHorizontalLayout);
        bottomRecyclerView.setAdapter(bottomInningAdapter);

        // Gets a handle on Away views
        awayHitTextView = findViewById(R.id.away_hit_textview);
        awayRunTextView = findViewById(R.id.away_run_textview);
        awayErrorTextView = findViewById(R.id.away_error_textview);
        awayStrikeOne = findViewById(R.id.away_strike_1);
        awayStrikeTwo = findViewById(R.id.away_strike_2);
        awayOutOne = findViewById(R.id.away_out_1);
        awayOutTwo = findViewById(R.id.away_out_2);
        awayBallOne = findViewById(R.id.away_ball_1);
        awayBallTwo = findViewById(R.id.away_ball_2);
        awayBallThree = findViewById(R.id.away_ball_3);


        // Gets a handle on Home views
        homeHitTextView = findViewById(R.id.home_hit_textview);
        homeRunTextView = findViewById(R.id.home_run_textview);
        homeErrorTextView = findViewById(R.id.home_error_textview);
        homeStrikeOne = findViewById(R.id.home_strike_1);
        homeStrikeTwo = findViewById(R.id.home_strike_2);
        homeOutOne = findViewById(R.id.home_out_1);
        homeOutTwo = findViewById(R.id.home_out_2);
        homeBallOne = findViewById(R.id.home_ball_1);
        homeBallTwo = findViewById(R.id.home_ball_2);
        homeBallThree = findViewById(R.id.home_ball_3);
        // I was reading about Kotlin and found out you can directly access views by their id! Imagine
        // only typing awayHitTextView.text = "" and never using findViewById again! Looking forward to
        // starting the Kotlin course after this one :) (There is ButterKnife.. but Kotlin is even better!)

        // Get a handle on colors commonly used in the program
        red_500 = getResources().getColor(R.color.red_500);
        gray_500_light = getResources().getColor(R.color.grey_500_light);

    }

    /**
     * Function that determines which button was pressed and then increments the corresponding statistic
     * by one. Hits, Runs and Errors simply increase by 1 and are set to display on their respective
     * TextView.  Strikes, Outs and Balls are more dependant on each other and also have multiple ImageViews
     * representing each one, so the helper function displayImageStatistic is used to handle the extra logic.
     */
    public void updateStatistic(View view) {
        String buttonIdentifier = view.getTag().toString();
        if (isGameOver) {
            Toast.makeText(this, getString(R.string.game_is_over), Toast.LENGTH_SHORT).show();
        } else if (isTopInning) {
            switch (buttonIdentifier) {
                case AWAY_HIT:
                    awayCurrentHits++;
                    awayHitTextView.setText(String.valueOf(awayCurrentHits));
                    break;
                case AWAY_RUN:
                    awayCurrentRuns++;
                    awayRunTextView.setText(String.valueOf(awayCurrentRuns));
                    break;
                case AWAY_ERROR:
                    awayCurrentErrors++;
                    awayErrorTextView.setText(String.valueOf(awayCurrentErrors));
                    break;
                case AWAY_STRIKE:
                    awayStrikeCount++;
                    displayImageStatistic(buttonIdentifier, awayStrikeCount);
                    break;
                case AWAY_OUT:
                    awayOutCount++;
                    displayImageStatistic(buttonIdentifier, awayOutCount);
                    break;
                case AWAY_BALL:
                    awayBallCount++;
                    displayImageStatistic(buttonIdentifier, awayBallCount);
                    break;
                // Home team isn't at bat, so pressing their buttons will not add to their statistics or advance the game.
                default:
                    Toast.makeText(this, getString(R.string.home_not_at_bat), Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            switch (buttonIdentifier) {
                case HOME_HIT:
                    homeCurrentHits++;
                    homeHitTextView.setText(String.valueOf(homeCurrentHits));
                    break;
                case HOME_RUN:
                    homeCurrentRuns++;
                    homeRunTextView.setText(String.valueOf(homeCurrentRuns));
                    break;
                case HOME_ERROR:
                    homeCurrentErrors++;
                    homeErrorTextView.setText(String.valueOf(homeCurrentErrors));
                    break;
                case HOME_STRIKE:
                    homeStrikeCount++;
                    displayImageStatistic(buttonIdentifier, homeStrikeCount);
                    break;
                case HOME_OUT:
                    homeOutCount++;
                    displayImageStatistic(buttonIdentifier, homeOutCount);
                    break;
                case HOME_BALL:
                    homeBallCount++;
                    displayImageStatistic(buttonIdentifier, homeBallCount);
                    break;
                // Away team isn't at bat, so pressing their buttons will not add to their statistics or advance the game.
                default:
                    Toast.makeText(this, getString(R.string.away_not_at_bat), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Function used to display Current Inning Statistics to their respective ImageViews. Red_500 color
     * shows an active state, while gray_500_light is used to show an inactive state. NOTE: This helper
     * function is slightly redundant as it runs through all of the cases again, however, it allows me
     * to use reflection when one value causes another to increase. (i.e. when getting a third strike,
     * I can increment outs and rerun the function.  If the switches were added to updateStatistics, that
     * reflection wouldn't be possible.
     *
     * @param tag   is passed in from the addStatistics function, and is used to determine which group of
     *              ImageViews we are currently working with.
     * @param value is passed in from the addStatistics function, and is used to determine which ImageView
     *              needs to have the colorFilter changed to display the correct # for that statistic.
     *              Cases for 3 and higher is a representation of Baseball rules.  I.E. 3 strikes will turn into 1 out,
     *              4 balls turns into 1 strike, etc. so in those cases, we will reset necessary values back to 0
     *              and increment the proper value by 1 and recall the equation, manually setting the proper tag.
     */
    public void displayImageStatistic(String tag, int value) {
        switch (tag) {
            case AWAY_STRIKE:
                switch (value) {
                    case 1:
                        awayStrikeOne.setColorFilter(red_500);
                        break;
                    case 2:
                        awayStrikeTwo.setColorFilter(red_500);
                        break;
                    case 3:
                        resetAwayStrikes();
                        resetAwayBalls();
                        awayOutCount++;
                        displayImageStatistic(AWAY_OUT, awayOutCount);
                        break;
                }
                break;
            case AWAY_OUT:
                switch (value) {
                    case 1:
                        awayOutOne.setColorFilter(red_500);
                        resetAwayStrikes();
                        resetAwayBalls();
                        break;
                    case 2:
                        awayOutTwo.setColorFilter(red_500);
                        resetAwayStrikes();
                        resetAwayBalls();
                        break;
                    case 3:
                        resetAwayStrikes();
                        resetAwayOuts();
                        resetAwayBalls();
                        transitionToNextInning();
                        break;
                }
                break;
            case AWAY_BALL:
                switch (value) {
                    case 1:
                        awayBallOne.setColorFilter(red_500);
                        break;
                    case 2:
                        awayBallTwo.setColorFilter(red_500);
                        break;
                    case 3:
                        awayBallThree.setColorFilter(red_500);
                        break;
                    case 4:
                        resetAwayBalls();
                        awayStrikeCount++;
                        displayImageStatistic(AWAY_STRIKE, awayStrikeCount);
                        break;
                }
                break;
            case HOME_STRIKE:
                switch (value) {
                    case 1:
                        homeStrikeOne.setColorFilter(red_500);
                        break;
                    case 2:
                        homeStrikeTwo.setColorFilter(red_500);
                        break;
                    case 3:
                        resetHomeStrikes();
                        resetHomeBalls();
                        homeOutCount++;
                        displayImageStatistic(HOME_OUT, homeOutCount);
                        break;
                }
                break;
            case HOME_OUT:
                switch (value) {
                    case 1:
                        homeOutOne.setColorFilter(red_500);
                        resetHomeStrikes();
                        resetHomeBalls();
                        break;
                    case 2:
                        homeOutTwo.setColorFilter(red_500);
                        resetHomeStrikes();
                        resetHomeBalls();
                        break;
                    case 3:
                        resetHomeStrikes();
                        resetHomeOuts();
                        resetHomeBalls();
                        transitionToNextInning();
                        break;
                }
                break;
            case HOME_BALL:
                switch (value) {
                    case 1:
                        homeBallOne.setColorFilter(red_500);
                        break;
                    case 2:
                        homeBallTwo.setColorFilter(red_500);
                        break;
                    case 3:
                        homeBallThree.setColorFilter(red_500);
                        break;
                    case 4:
                        resetHomeBalls();
                        homeStrikeCount++;
                        displayImageStatistic("home_strike", homeStrikeCount);
                        break;
                }
                break;
        }
    }

    // displayImageStatistic helper function
    public void resetAwayStrikes() {
        awayStrikeCount = 0;
        awayStrikeOne.setColorFilter(gray_500_light);
        awayStrikeTwo.setColorFilter(gray_500_light);
    }

    // displayImageStatistic helper function
    public void resetAwayOuts() {
        awayOutCount = 0;
        awayOutOne.setColorFilter(gray_500_light);
        awayOutTwo.setColorFilter(gray_500_light);
    }

    // displayImageStatistic helper function
    public void resetAwayBalls() {
        awayBallCount = 0;
        awayBallOne.setColorFilter(gray_500_light);
        awayBallTwo.setColorFilter(gray_500_light);
        awayBallThree.setColorFilter(gray_500_light);
    }

    // displayImageStatistic helper function
    public void resetHomeStrikes() {
        homeStrikeCount = 0;
        homeStrikeOne.setColorFilter(gray_500_light);
        homeStrikeTwo.setColorFilter(gray_500_light);
    }

    // displayImageStatistic helper function
    public void resetHomeOuts() {
        homeOutCount = 0;
        homeOutOne.setColorFilter(gray_500_light);
        homeOutTwo.setColorFilter(gray_500_light);
    }

    // displayImageStatistic helper function
    public void resetHomeBalls() {
        homeBallCount = 0;
        homeBallOne.setColorFilter(gray_500_light);
        homeBallTwo.setColorFilter(gray_500_light);
        homeBallThree.setColorFilter(gray_500_light);
    }

    /**
     * Function that is used to post the stats from the current inning to the scoreboard on the top
     * of the screen.  isTopInning is used to post to the correct RecyclerView, and the three POSITION
     * constants are used for updating the total score, as those columns never change.
     */
    public void postToScoreboard() {
        int currentPosition = currentInning - 1;
        if (isTopInning) {
            awayTotalHits += awayCurrentHits;
            awayTotalRuns += awayCurrentRuns;
            awayTotalErrors += awayCurrentErrors;
            awayData.set(currentPosition, String.valueOf(awayCurrentRuns));
            awayData.set(TOTAL_RUNS_POSITION, String.valueOf(awayTotalRuns));
            awayData.set(TOTAL_HITS_POSITION, String.valueOf(awayTotalHits));
            awayData.set(TOTAL_ERRORS_POSITION, String.valueOf(awayTotalErrors));
            topInningAdapter.notifyDataSetChanged();
        } else {
            homeTotalHits += homeCurrentHits;
            homeTotalRuns += homeCurrentRuns;
            homeTotalErrors += homeCurrentErrors;
            homeData.set(currentPosition, String.valueOf(homeCurrentRuns));
            homeData.set(TOTAL_RUNS_POSITION, String.valueOf(homeTotalRuns));
            homeData.set(TOTAL_HITS_POSITION, String.valueOf(homeTotalHits));
            homeData.set(TOTAL_ERRORS_POSITION, String.valueOf(homeTotalErrors));
            bottomInningAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Function used to highlight the current inning on the scoreboard. isTopInning is a boolean used
     * to determine if we're at the bottom or top of the inning, and then we simply - 1 from the current inning
     * to get the position of the correct TextView and change it's background.
     */
    public void setFocus() {
        TextView inningFocus;
        int currentPosition = currentInning - 1;
        if (isTopInning) {
            inningFocus = topRecyclerView.findViewHolderForAdapterPosition(currentPosition)
                    .itemView.findViewById(R.id.inning_textview);
        } else {
            inningFocus = bottomRecyclerView.findViewHolderForAdapterPosition(currentPosition)
                    .itemView.findViewById(R.id.inning_textview);
        }
        inningFocus.setBackgroundResource(R.drawable.inning_focus);
    }

    /**
     * See setFocus above.  Same thing, but sets background of TextView back to white so that we can set
     * the focus on a new TextView when the inning advances.
     */
    public void removeFocus() {
        TextView inningFocus;
        int currentPosition = currentInning - 1;
        if (isTopInning) {
            inningFocus = topRecyclerView.findViewHolderForLayoutPosition(currentPosition)
                    .itemView.findViewById(R.id.inning_textview);
        } else {
            inningFocus = bottomRecyclerView.findViewHolderForLayoutPosition(currentPosition)
                    .itemView.findViewById(R.id.inning_textview);
        }
        inningFocus.setBackgroundColor(getResources().getColor(R.color.white));
    }

    /**
     * Function that saves all data from the current inning on the scoreboard and then resets the current
     * inning statistics and moves to the next inning.  We only need to play the bottom of the 9th if
     * awayTotalRuns is >= homeTotalRuns.  If it is the 9th inning and that isn't true, then the game
     * has ended in one way or another which is handled by the by the else branch of the main if statement.
     * <p>
     * In the case of a game over, isGameOver is set to true so that the user cannot edit anymore statistics
     * and a reset button is made visible to let the user start a new game.
     */
    public void transitionToNextInning() {
        postToScoreboard();
        removeFocus();
        if (currentInning < 9) {
            if (isTopInning) {
                isTopInning = false;
            } else {
                isTopInning = true;
                currentInning++;
                resetCurrentInning();
            }
            setFocus();
        } else {
            if (isTopInning && awayTotalRuns >= homeTotalRuns) {
                isTopInning = false;
                setFocus();
            } else {
                if (awayTotalRuns < homeTotalRuns) {
                    Toast.makeText(this, getString(R.string.home_wins), Toast.LENGTH_SHORT).show();
                } else if (awayTotalRuns > homeTotalRuns) {
                    Toast.makeText(this, getString(R.string.away_wins), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.tie_game), Toast.LENGTH_SHORT).show();
                }
                isGameOver = true;
                Button restartButton = findViewById(R.id.restart_button);
                restartButton.setVisibility(VISIBLE);
            }
        }

    }

    /**
     * Helper function for transitionToNextInning.  It is only called after the Bottom of the inning
     * so that you can still see the statistics for the away team while the home team is at bat.  Also,
     * updateImageStatistic already resets strikes, outs, and balls upon reaching 3 outs, so we only
     * need to reset the TextViews and their respective variables.
     */
    public void resetCurrentInning() {
        awayCurrentHits = 0;
        awayCurrentRuns = 0;
        awayCurrentErrors = 0;
        awayHitTextView.setText(String.valueOf(awayCurrentHits));
        awayRunTextView.setText(String.valueOf(awayCurrentRuns));
        awayErrorTextView.setText(String.valueOf(awayCurrentErrors));

        homeCurrentHits = 0;
        homeCurrentRuns = 0;
        homeCurrentErrors = 0;
        homeHitTextView.setText(String.valueOf(homeCurrentHits));
        homeRunTextView.setText(String.valueOf(homeCurrentRuns));
        homeErrorTextView.setText(String.valueOf(homeCurrentErrors));
    }

    /**
     * Reinitialize all of the game data back to it's original starting values.
     *
     * @param view
     */
    public void restartGame(View view) {
        awayData.clear();
        homeData.clear();
        awayData.addAll(STARTING_INNING_DATA);
        homeData.addAll(STARTING_INNING_DATA);
        topInningAdapter.notifyDataSetChanged();
        bottomInningAdapter.notifyDataSetChanged();

        removeFocus();
        resetCurrentInning();
        view.setVisibility(View.INVISIBLE);
        isGameOver = false;
        isTopInning = true;
        currentInning = 1;
    }

    /**
     * Dev function to jump to the ninth inning.  Implemented so that you don't need to hit the add out
     * button 54 times every time you want to test a different part of the isGameOver logic. I left it in
     * since I figured it would be useful for the reviewer! :)
     */
    public void jumpToNinthInning(View view) {
        removeFocus();
        currentInning = 9;
        isTopInning = true;
        isGameOver = false;
        setFocus();
    }
}