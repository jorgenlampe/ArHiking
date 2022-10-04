package com.example.arhiking.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.arhiking.Data.AppDatabase;

import org.osmdroid.util.GeoPoint;

@Entity
public class HikeGeoPoint {

    @PrimaryKey(autoGenerate = true)
    public int geoPointId;

    @TypeConverters(AppDatabase.Converters.class)
    @ColumnInfo(name = "geoPoint")
    public GeoPoint geoPoint;

    @ColumnInfo(name = "hike_activity_id", defaultValue = "0")
    public long hike_activity_id;
}
