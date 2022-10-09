package com.example.arhiking.Data;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.arhiking.Models.HikeGeoPoint;


@Dao
public interface GeoPointsDao {

    @Insert
    void insertAll(HikeGeoPoint... geoPoints);

}