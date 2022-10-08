package com.example.arhiking.Data;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.arhiking.Models.HikeGeoPoint;

import org.osmdroid.util.GeoPoint;


@Dao
public interface GeoPointsDao {

    @Insert
    void insertAll(GeoPoint... geoPoints);

}