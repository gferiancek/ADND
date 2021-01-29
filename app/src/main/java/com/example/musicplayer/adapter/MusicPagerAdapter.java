package com.example.musicplayer.adapter;

import com.example.musicplayer.fragment.NowPlayingFragment;
import com.example.musicplayer.fragment.SingleListFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MusicPagerAdapter extends FragmentStateAdapter {
    String[] titles;

    public MusicPagerAdapter(@NonNull FragmentActivity fragmentActivity, String[] titles) {
        super(fragmentActivity);
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new NowPlayingFragment();
        }
        return SingleListFragment.newInstance(titles[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
