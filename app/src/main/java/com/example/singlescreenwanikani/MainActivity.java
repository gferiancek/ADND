package com.example.singlescreenwanikani;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    LinearLayout aboutLayout;
    LinearLayout howItWorksLayout;
    RelativeLayout theTeamLayout;
    LinearLayout testimonialsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        // Get a handle on each layout view in order to toggle visibility on/off based on navigation input
        aboutLayout = findViewById(R.id.about_layout);
        howItWorksLayout = findViewById(R.id.how_it_works_layout);
        theTeamLayout = findViewById(R.id.the_team_layout);
        testimonialsLayout = findViewById(R.id.testimonials_layout);
        // Hides all layout views except for aboutLayout, since that is the default view that should show upon startup
        toggleAllVisibilityGone();
        aboutLayout.setVisibility(View.VISIBLE);
        // Get a handle on scrollView to scroll to the top whenever a navigation item is clicked
        ScrollView contentScrollView = findViewById(R.id.content_scrollView);

        // Get a handle on the BottomNavigationBar and load the proper data when each item is clicked.
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                toggleAllVisibilityGone();
                contentScrollView.scrollTo(0, 0);
                switch (item.getItemId()) {
                    case R.id.about:
                        aboutLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.how_it_works:
                        howItWorksLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.the_team:
                        theTeamLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.testimonials:
                        testimonialsLayout.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
                }
        });

    }

    public void toggleAllVisibilityGone() {
        aboutLayout.setVisibility(View.GONE);
        howItWorksLayout.setVisibility(View.GONE);
        theTeamLayout.setVisibility(View.GONE);
        testimonialsLayout.setVisibility(View.GONE);
    }
}