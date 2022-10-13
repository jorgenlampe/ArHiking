package com.example.arhiking.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arhiking.R;
import com.example.arhiking.Tour;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.HikeLibraryViewHolder> {
    private final List<Tour> hikeList;

    public LibraryAdapter(List<Tour> hikes) {
        this.hikeList = hikes;
    }

    @NonNull
    @Override
    public HikeLibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HikeLibraryViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_hike_library, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HikeLibraryViewHolder holder, int position) {
        holder.setHike(hikeList.get(position));
    }

    @Override
    public int getItemCount() {
        return hikeList.size();
    }

    public static class HikeLibraryViewHolder extends RecyclerView.ViewHolder{
        public RoundedImageView libraryHikePoster;
        public TextView libraryHikeNameTextView, libraryHikeLocationTextView, libraryDistanceDataTextView,
                libraryDurationDataTextView, libraryAscentDataTextView, libraryElevationDataTextView,
                libraryDifficultyDataTextView, libraryDateDataTextView;
        public RoundedImageView librarySmallImage1, librarySmallImage2, librarySmallImage3;

        public HikeLibraryViewHolder(@NonNull View itemView) {
            super(itemView);

            libraryHikePoster = itemView.findViewById(R.id.imageLibraryHikePoster);
            libraryHikeNameTextView = itemView.findViewById(R.id.libraryHikeNameTextView);
            libraryHikeLocationTextView = itemView.findViewById(R.id.libraryHikeLocationTextView);
            libraryDistanceDataTextView = itemView.findViewById(R.id.libraryDistanceDataTextView);
            libraryDurationDataTextView = itemView.findViewById(R.id.libraryDurationDataTextView);
            libraryAscentDataTextView = itemView.findViewById(R.id.libraryAscentDataTextView);
            libraryElevationDataTextView = itemView.findViewById(R.id.libraryElevationDataTextView);
            libraryDifficultyDataTextView = itemView.findViewById(R.id.libraryDifficultyDataTextView);
            libraryDateDataTextView = itemView.findViewById(R.id.libraryDateDataTextView);
            librarySmallImage1 = itemView.findViewById(R.id.librarySmallImage1);
            librarySmallImage2 = itemView.findViewById(R.id.librarySmallImage2);
            librarySmallImage3 = itemView.findViewById(R.id.librarySmallImage3);
        }

        void setHike(Tour hike){
            libraryHikePoster.setImageResource(hike.poster);
            librarySmallImage1.setImageResource(hike.image1);
            librarySmallImage2.setImageResource(hike.image2);
            librarySmallImage3.setImageResource(hike.image3);
            libraryHikeNameTextView.setText(hike.name);
            libraryDateDataTextView.setText(hike.hikeDate);
            libraryHikeLocationTextView.setText(hike.hikeLocation);
            libraryDistanceDataTextView.setText(hike.hikeLength);
            libraryDurationDataTextView.setText(hike.hikeTime);
            libraryAscentDataTextView.setText(hike.hikeAscent);
            libraryElevationDataTextView.setText(hike.hikeElevation);
            libraryDifficultyDataTextView.setText(hike.hikeDifficulty);
        }
    }
}
