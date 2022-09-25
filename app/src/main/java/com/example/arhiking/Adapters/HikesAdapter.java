package com.example.arhiking.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arhiking.Tour;
import com.example.arhiking.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class HikesAdapter extends RecyclerView.Adapter<HikesAdapter.HikesViewHolder>{

    private final List<Tour> hikes;

    public HikesAdapter(List<Tour> hikes) {
        this.hikes = hikes;
    }

    @NonNull
    @Override
    public HikesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HikesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_hike,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HikesViewHolder holder, int position) {
        holder.setHike(hikes.get(position));
    }

    @Override
    public int getItemCount() {
        return hikes.size();
    }

    static class HikesViewHolder extends RecyclerView.ViewHolder {

        private final RoundedImageView imageHikePoster;
        private final TextView hikeName, hikeLocation, hikeLength, hikeDate, hikeAscent, hikeTime;
        private final RatingBar ratingBar;

        public HikesViewHolder(View view){
            super(view);
            imageHikePoster = view.findViewById(R.id.imageHikePoster);
            hikeName = view.findViewById(R.id.hikeName);
            hikeLocation = view.findViewById(R.id.hikeLocation);
            hikeLength = view.findViewById(R.id.hikeLength);
            hikeAscent = view.findViewById(R.id.hikeAscent);
            hikeDate = view.findViewById(R.id.hikeDate);
            hikeTime = view.findViewById(R.id.hikeTime);
            ratingBar = view.findViewById(R.id.hikeRatingBar);
        }

        void setHike(Tour hike){
            imageHikePoster.setImageResource(hike.poster);
            hikeName.setText(hike.name);
            hikeLocation.setText(hike.hikeLocation);
            hikeLength.setText(hike.hikeLength);
            hikeAscent.setText(hike.hikeAscent);
            hikeDate.setText(hike.hikeDate);
            hikeTime.setText(hike.hikeTime);
            ratingBar.setRating(hike.rating);
        }
    }
}
