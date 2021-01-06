package com.example.whowantstobeamirannaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.whowantstobeamirannaire.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rulesButton.setOnClickListener(v -> showRulesDialog());

        mediaPlayer = MediaPlayer.create(this, R.raw.welcome_to_lelocle);
        mediaPlayer.start();

        getWindow().setEnterTransition(new Explode().setDuration(600).setStartDelay(600));
        getWindow().setExitTransition(new Explode().setDuration(600));
        getWindow().setReenterTransition(new Explode().setDuration(600));

        Shader titleOneShader = new LinearGradient(0,0,0,binding.title1Textview.getLineHeight(),
                getResources().getColor(R.color.dark_red), getResources().getColor(R.color.dark_pink), Shader.TileMode.REPEAT);
        binding.title1Textview.getPaint().setShader(titleOneShader);

        Shader titleTwoShader = new LinearGradient(0,0,0,binding.title2Textview.getLineHeight(),
                getResources().getColor(R.color.dark_green), getResources().getColor(R.color.light_green), Shader.TileMode.REPEAT);
        binding.title2Textview.getPaint().setShader(titleTwoShader);

        Shader titleThreeShader = new LinearGradient(0,0,0,binding.title3Textview.getLineHeight(),
                getResources().getColor(R.color.orange), getResources().getColor(R.color.dark_pink), Shader.TileMode.REPEAT);
        binding.title3Textview.getPaint().setShader(titleThreeShader);

        Shader titleFourShader = new LinearGradient(0,0,0,binding.title4Textview.getLineHeight(),
                getResources().getColor(R.color.dark_blue), getResources().getColor(R.color.light_blue), Shader.TileMode.REPEAT);
        binding.title4Textview.getPaint().setShader(titleFourShader);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    @Override
    protected void onPause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.welcome_to_lelocle);
        }
        mediaPlayer.start();
        super.onResume();
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
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

    private void showRulesDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RulesDialogFragment rulesDialog = RulesDialogFragment.newInstance(getString(R.string.rules_title), getString(R.string.rules_message));
        rulesDialog.show(fragmentManager, "fragment_rules");
    }

    public void launchQuizActivity(View view) {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("difficulty", view.getTag().toString());
        mediaPlayer.release();
        mediaPlayer = null;
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}