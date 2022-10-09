package com.example.arhiking.Models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class HikeActivitiesWithAccelerometerData {

    @Embedded
    public Hike_Activity hikeActivity;
    @Relation(
            parentColumn = "hikeAtivityId",
            entityColumn = "hike__activity_id"
    )
    public List<AccelerometerData> accelerometerData;
}
