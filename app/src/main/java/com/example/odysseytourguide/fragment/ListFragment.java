package com.example.odysseytourguide.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.odysseytourguide.R;
import com.example.odysseytourguide.adapter.KingdomAdapter;
import com.example.odysseytourguide.databinding.FragmentListBinding;
import com.example.odysseytourguide.model.Kingdom;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    private String title;
    private static final String KINGDOM_CASE = "Kingdoms";
    private static final String NPCS_CASE = "NPCs";
    private static final String ENEMIES_CASE = "Enemies";
    private static final String COLLECTABLE_CASE = "Collectibles";
    private static final int NO_IMAGE = -1;
    private static final String NO_COIN_COUNT = "";
    private FragmentListBinding binding;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(String title) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.title = getArguments().getString("title");
        }
        binding = FragmentListBinding.inflate(getLayoutInflater());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.fragmentRv.setLayoutManager(linearLayoutManager);
        switch (title) {
            case KINGDOM_CASE:
                binding.fragmentRv.setAdapter(new KingdomAdapter(
                        initializeKingdomList(), getContext(), linearLayoutManager));
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private ArrayList<Kingdom> initializeKingdomList() {
        ArrayList<Kingdom> kingdomList = new ArrayList<>();
        kingdomList.add(new Kingdom(R.drawable.cap_kingdom, R.drawable.generic_moon, R.drawable.cap_regional,
                getString(R.string.cap_name), getString(R.string.cap_moon_count),
                getString(R.string.cap_regional_count), false));
        kingdomList.add(new Kingdom(R.drawable.cascade_kingdom, R.drawable.generic_moon, R.drawable.cascade_regional,
                getString(R.string.cascade_name), getString(R.string.cascade_moon_count),
                getString(R.string.cascade_regional_count), false));
        kingdomList.add(new Kingdom(R.drawable.sand_kingdom, R.drawable.sand_moon, R.drawable.sand_regional,
                getString(R.string.sand_name), getString(R.string.sand_moon_count),
                getString(R.string.sand_regional_count), false));
        kingdomList.add(new Kingdom(R.drawable.lake_kingdom, R.drawable.lake_moon, R.drawable.lake_regional,
                getString(R.string.lake_name), getString(R.string.lake_moon_count),
                getString(R.string.lake_regional_count), false));
        kingdomList.add(new Kingdom(R.drawable.wooded_kingdom, R.drawable.wooded_moon, R.drawable.wooded_regional,
                getString(R.string.wooded_name), getString(R.string.wooded_moon_count),
                getString(R.string.wooded_regional_count), false));
        kingdomList.add(new Kingdom(R.drawable.cloud_kingdom, R.drawable.generic_moon, NO_IMAGE,
                getString(R.string.cloud_name), getString(R.string.cloud_moon_count),
                NO_COIN_COUNT, false));
        kingdomList.add(new Kingdom(R.drawable.lost_kingdom, R.drawable.generic_moon, R.drawable.lost_regional,
                getString(R.string.lost_name), getString(R.string.lost_moon_count),
                getString(R.string.lost_regional_count), false));
        kingdomList.add(new Kingdom(R.drawable.metro_kingdom, R.drawable.metro_moon, R.drawable.metro_regional,
                getString(R.string.metro_name), getString(R.string.metro_moon_count),
                getString(R.string.metro_regional_count), false));
        kingdomList.add(new Kingdom(R.drawable.snow_kingdom, R.drawable.snow_moon, R.drawable.snow_regional,
                getString(R.string.snow_name), getString(R.string.snow_moon_count),
                getString(R.string.snow_regional_count), false));
        kingdomList.add(new Kingdom(R.drawable.seaside_kingdom, R.drawable.seaside_moon, R.drawable.seaside_regional,
                getString(R.string.seaside_name), getString(R.string.seaside_moon_count),
                getString(R.string.seaside_regional_count), false));
        kingdomList.add(new Kingdom(R.drawable.luncheon_kingdom, R.drawable.luncheon_moon, R.drawable.luncheon_regional,
                getString(R.string.luncheon_name), getString(R.string.luncheon_moon_count),
                getString(R.string.luncheon_regional_count), false));
        kingdomList.add(new Kingdom(R.drawable.ruined_kingdom, R.drawable.generic_moon, NO_IMAGE,
                getString(R.string.ruined_name), getString(R.string.ruined_moon_count),
                NO_COIN_COUNT, false));
        kingdomList.add(new Kingdom(R.drawable.bowsers_kingdom, R.drawable.bowsers_moon, R.drawable.bowsers_regional,
                getString(R.string.bowsers_name), getString(R.string.bowsers_moon_count),
                getString(R.string.bowsers_regional_count), false));
        kingdomList.add(new Kingdom(R.drawable.moon_kingdom, R.drawable.moon_moon, R.drawable.moon_regional,
                getString(R.string.moon_name), getString(R.string.moon_moon_count),
                getString(R.string.moon_regional_count), false));
        kingdomList.add(new Kingdom(R.drawable.mushroom_kingdom, R.drawable.mushroom_moon, R.drawable.mushroom_regional,
                getString(R.string.mushroom_name), getString(R.string.mushroom_moon_count),
                getString(R.string.mushroom_regional_count), false));
        return kingdomList;
    }
}