package com.example.arhiking.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.arhiking.Models.HikeActivitiesWithGeoPoints;
import com.example.arhiking.Models.Hike_Activity;
import com.example.arhiking.Models.HikesWithHikesActivities;

import java.util.List;

@Dao
public interface HikeActivityDao {
    @Query("SELECT * FROM hike_Activity")
    LiveData<List<Hike_Activity>> getAll();

    @Query("SELECT * FROM hike_activity WHERE hikeActivityId IN (:hikeActivityIds)")
    List<Hike_Activity> loadAllByIds(int[] hikeActivityIds);

    @Delete
    void deleteHikeActivity(Hike_Activity hikeActivity);

    @Update
    void updateHikeActivity(Hike_Activity hikeActivity);

    @Insert
    long[] insertAll(Hike_Activity... hikeActivities);

    @Transaction
    @Query("SELECT * FROM Hike_Activity")
    public List<HikeActivitiesWithGeoPoints> getHikeActivitiesWithGeoPoints();
}