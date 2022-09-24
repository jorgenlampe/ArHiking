package com.example.arhiking.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hike {
    @PrimaryKey(autoGenerate = true)
    public int hikeId;

    @ColumnInfo(name = "hike_name")
    public String hikeName;

    @ColumnInfo(name = "hike_description")
    public String hikeDescription;

}