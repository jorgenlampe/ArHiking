package com.example.arhiking.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.arhiking.Models.Hike;

import java.util.List;

@Dao
public interface HikeDao {
    @Query("SELECT * FROM hike")
    List<Hike> getAll();

    @Query("SELECT * FROM hike WHERE hikeId IN (:hikeIds)")
    List<Hike> loadAllByIds(int[] hikeIds);

    @Query("SELECT * FROM hike WHERE hike_name LIKE :name")
    Hike findByName(String name);

    @Insert
    void insertAll(Hike... hikes);

    @Delete
    void delete(Hike hike);
}