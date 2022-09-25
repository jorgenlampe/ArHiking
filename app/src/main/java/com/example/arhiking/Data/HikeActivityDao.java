package com.example.arhiking.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.arhiking.Models.Hike;
import com.example.arhiking.Models.HikeActivity;

import java.util.List;

@Dao
public interface HikeActivityDao {
    @Query("SELECT * FROM hikeActivity")
    List<HikeActivity> getAll();

    @Query("SELECT * FROM hikeActivity WHERE hikeActivityId IN (:hikeActivityIds)")
    List<HikeActivity> loadAllByIds(int[] hikeActivityIds);

    @Query("SELECT * FROM hikeActivity WHERE hike_activity_name LIKE :hikeActivityName")
    HikeActivity findByName(String hikeActivityName);

    @Insert
    void insertAll(HikeActivity... hikeActivities);

    @Delete
    void delete(HikeActivity hikeActivity);
}