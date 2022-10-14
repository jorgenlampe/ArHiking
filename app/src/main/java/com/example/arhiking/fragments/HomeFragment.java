package com.example.arhiking.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.arhiking.Adapters.HikesAdapter;
import com.example.arhiking.R;
import com.example.arhiking.Tour;
import com.example.arhiking.databinding.FragmentHomeBinding;
import com.example.arhiking.viewmodels.HomeViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    MaterialButton startHikeButton;
    //CardView cardBrowseLibrary;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        startHikeButton = (MaterialButton) root.findViewById(R.id.clickToStartButton);
        // todo connect button to start hike function

        // Navigate to library when clicking on cardview in home fragment
        // todo: Fix bottom navigation after library opens through cardview onClick
        CardView cardBrowseLibrary = (CardView) root.findViewById(R.id.cardBrowseLibrary);
        cardBrowseLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_navigation_library);
            }
        });

        // Navigate to map when clicking on cardview in home fragment
        // todo: Fix bottom navigation after map opens through cardview onClick
        CardView cardViewMap = (CardView) root.findViewById(R.id.cardMapView);
        cardViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_navigation_map);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupHikesViewPager();
    }

    // Dummy data for hikes view in HomeFragment
    private void setupHikesViewPager() {
        ViewPager2 hikesViewPager = getView().findViewById(R.id.hikesViewPager);
        hikesViewPager.setClipToPadding(false);
        hikesViewPager.setClipChildren(false);
        hikesViewPager.setOffscreenPageLimit(3);
        hikesViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        hikesViewPager.setPageTransformer(compositePageTransformer);
        hikesViewPager.setAdapter(new HikesAdapter(getHikes()));
    }

    private List<Tour> getHikes() {
        List<Tour> hikes = new ArrayList<>();

        Tour bakkanosiHike = new Tour();
        bakkanosiHike.poster = R.drawable.hike_1;
        bakkanosiHike.name = "Bakkanosi";
        bakkanosiHike.hikeLocation = "Jordalen, Voss, Norway";
        bakkanosiHike.hikeLength = "20km";
        bakkanosiHike.hikeAscent = "580 - 1398m";
        bakkanosiHike.hikeTime = "8h";
        bakkanosiHike.hikeDate = "23.07.2022";
        bakkanosiHike.rating = 4.5f;
        hikes.add(bakkanosiHike);

        Tour prestHike = new Tour();
        prestHike.poster = R.drawable.hike_3;
        prestHike.name = "Prest";
        prestHike.hikeLocation = "Aurland, Norway";
        prestHike.hikeLength = "6.5km";
        prestHike.hikeAscent = "793 - 1478m";
        prestHike.hikeTime = "3h";
        prestHike.hikeDate = "04.07.2022";
        prestHike.rating = 4f;
        hikes.add(prestHike);

        Tour litlefjelletHike = new Tour();
        litlefjelletHike.poster = R.drawable.hike_2;
        litlefjelletHike.name = "Litlefjellet";
        litlefjelletHike.hikeLocation = "Romsdalen og Eikesdalen, Norway";
        litlefjelletHike.hikeLength = "1.7km";
        litlefjelletHike.hikeAscent = "540 - 790m";
        litlefjelletHike.hikeTime = "30min";
        litlefjelletHike.hikeDate = "21.06.2022";
        litlefjelletHike.rating = 5f;
        hikes.add(litlefjelletHike);

        Tour stalheimsnipaHike = new Tour();
        stalheimsnipaHike.poster = R.drawable.hike_4;
        stalheimsnipaHike.name = "Stalheimsnipa";
        stalheimsnipaHike.hikeLocation = "Stalheim, Voss, Norway";
        stalheimsnipaHike.hikeLength = "3.5km";
        stalheimsnipaHike.hikeAscent = "115 - 844m";
        stalheimsnipaHike.hikeTime = "2.5h";
        stalheimsnipaHike.hikeDate = "28.05.2022";
        stalheimsnipaHike.rating = 4f;
        hikes.add(stalheimsnipaHike);

        return hikes;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}