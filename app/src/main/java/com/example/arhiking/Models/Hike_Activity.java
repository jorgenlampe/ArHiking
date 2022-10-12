package com.example.arhiking.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Hike_Activity {

    @PrimaryKey(autoGenerate = true)
    public long hikeActivityId;

    @ColumnInfo(name = "hike_activity_name")
    public String hikeActivityName;

    @ColumnInfo(name = "hike_activity_description")
    public String hikeActivityDescription;

    @ColumnInfo(name = "hike_id", defaultValue = "0")
    public long hike_id;

    @ColumnInfo(name = "hike_distance", defaultValue = "0")
    public double hikeDistance;

    @ColumnInfo(name = "hike_time_registered")
    public long timeRegistered;


}
