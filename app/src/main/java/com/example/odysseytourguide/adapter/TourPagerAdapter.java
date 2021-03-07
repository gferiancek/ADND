package com.example.odysseytourguide.adapter;

import com.example.odysseytourguide.fragment.ListFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TourPagerAdapter extends FragmentStateAdapter {

    String[] titles;

    public TourPagerAdapter(@NonNull FragmentActivity fragmentActivity, String[] titles) {
        super(fragmentActivity);
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ListFragment.newInstance(titles[position]);
    }


    @Override
    public int getItemCount() {
        return titles.length;
    }
}
