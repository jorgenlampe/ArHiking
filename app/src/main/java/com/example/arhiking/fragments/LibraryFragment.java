package com.example.arhiking.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.arhiking.Adapters.HikesAdapter;
import com.example.arhiking.Adapters.LibraryAdapter;
import com.example.arhiking.R;
import com.example.arhiking.Tour;
import com.example.arhiking.databinding.FragmentLibraryBinding;
import com.example.arhiking.viewmodels.LibraryViewModel;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding binding;
    private RecyclerView mRecyclerView;
    private LibraryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LibraryViewModel notificationsViewModel =
                new ViewModelProvider(this).get(LibraryViewModel.class);

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mRecyclerView = root.findViewById(R.id.hikeLibraryRecyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new LibraryAdapter(getHikes());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.library_menu, menu);

                MenuItem searchItem = menu.findItem(R.id.action_search_hike);
                SearchView searchView = (SearchView) searchItem.getActionView();

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        mAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //dummy data for RecyclerView
    private ArrayList<Tour> getHikes() {
        ArrayList<Tour> hikes = new ArrayList<>();

        Tour bakkanosiHike = new Tour();
        bakkanosiHike.poster = R.drawable.hike_1;
        bakkanosiHike.name = "Bakkanosi";
        bakkanosiHike.hikeLocation = "Jordalen, Voss, Norway";
        bakkanosiHike.hikeLength = "20km";
        bakkanosiHike.hikeAscent = "580 - 1398m";
        bakkanosiHike.hikeTime = "8h";
        bakkanosiHike.hikeDate = "23.07.2022";
        bakkanosiHike.hikeDifficulty = "Hard";
        bakkanosiHike.hikeElevation = "1398m";
        bakkanosiHike.image1 = R.drawable.hike_1;
        bakkanosiHike.image2 = R.drawable.hike_1;
        bakkanosiHike.image3 = R.drawable.hike_1;
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
        prestHike.hikeDifficulty = "Moderate";
        prestHike.hikeElevation = "1478m";
        prestHike.image1 = R.drawable.hike_3;
        prestHike.image2 = R.drawable.hike_3;
        prestHike.image3 = R.drawable.hike_3;
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
        litlefjelletHike.hikeDifficulty = "Moderate";
        litlefjelletHike.hikeElevation = "790m";
        litlefjelletHike.image1 = R.drawable.hike_2;
        litlefjelletHike.image2 = R.drawable.hike_2;
        litlefjelletHike.image3 = R.drawable.hike_2;
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
        stalheimsnipaHike.hikeDifficulty = "Hard";
        stalheimsnipaHike.hikeElevation = "844m";
        stalheimsnipaHike.image1 = R.drawable.hike_4;
        stalheimsnipaHike.image2 = R.drawable.hike_4;
        stalheimsnipaHike.image3 = R.drawable.hike_4;
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