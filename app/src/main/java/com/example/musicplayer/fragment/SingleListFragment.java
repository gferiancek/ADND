package com.example.musicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer.adapter.AlbumAdapter;
import com.example.musicplayer.adapter.ArtistAdapter;
import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.databinding.FragmentSingleListBinding;
import com.example.musicplayer.model.MusicLibrary;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;

/**
 * Fragment used when only a single RecyclerView is used for the display of content.
 */
public class SingleListFragment extends Fragment {

    private String title;
    private static final String ARTIST_CASE = "Artists";
    private static final String ALBUM_CASE = "Albums";
    private static final String SONG_CASE = "Songs";
    private FragmentSingleListBinding binding;

    public SingleListFragment() {
        // Required empty public constructor
    }

    public static SingleListFragment newInstance(String title) {
        SingleListFragment fragment = new SingleListFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * String title, pulled from the TabLayout, is passed in from the MusicPagerAdapter and is compared
     * against the *_CASE constants to display different sets of information based on the tab that
     * the user navigates to.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.title = getArguments().getString("title");
        }
        binding = FragmentSingleListBinding.inflate(getLayoutInflater());

        binding.verticalRv.setLayoutManager(new LinearLayoutManager(getContext()));
        switch (title) {
            case ARTIST_CASE:
                binding.verticalRv.setAdapter(new ArtistAdapter(
                        MusicLibrary.getInstance().getArtistList(), getContext()));
                break;
            case ALBUM_CASE:
                binding.verticalRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
                binding.verticalRv.setAdapter(new AlbumAdapter(
                        MusicLibrary.getInstance().getAlbumList(), getContext()));
                break;
            case SONG_CASE:
                binding.verticalRv.setAdapter(new SongAdapter(
                        MusicLibrary.getInstance().getSongList(), getContext()));
                break;
        }
        new FastScrollerBuilder(binding.verticalRv).build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}