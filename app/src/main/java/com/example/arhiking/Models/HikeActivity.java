package com.example.arhiking.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


    @Entity
    public class HikeActivity {
        @PrimaryKey(autoGenerate = true)
        public int hikeActivityId;

        @ColumnInfo(name = "hike_activity_name")
        public String hikeActivityName;

        @ColumnInfo(name = "hike_activity_description")
        public String hikeActivityDescription;

        @ColumnInfo(name = "hike_id", defaultValue = "0")
        public long hike_id;

        @ColumnInfo(name = "hike_activity_length")
        public double hikeActivityLength;

        @ColumnInfo(name = "hike_activity_duration")
        public double hikeActivityDuration;

    }
