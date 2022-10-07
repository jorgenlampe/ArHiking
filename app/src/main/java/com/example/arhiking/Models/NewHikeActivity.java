package com.example.arhiking.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.arhiking.Data.AppDatabase;

import org.osmdroid.util.GeoPoint;


@Entity(tableName = "hikeActivity")
    public class NewHikeActivity {

    @PrimaryKey(autoGenerate = true)
    public int hikeActivityId;

    @ColumnInfo(name = "hike_activity_name")
    public String hikeActivityName;

    @ColumnInfo(name = "hike_activity_description")
    public String hikeActivityDescription;

    @ColumnInfo(name = "hike_id", defaultValue = "0")
    public long hike_id;




        /*@ColumnInfo(name = "gyroscopeSensorData")
        public List<GyroscopeSensorData> gyroscopeSensorData;

        @ColumnInfo(name = "acceleromaterSensorData")
        public List<AccelerometerData> acceleromaterSensorData;

        @ColumnInfo(name = "geomagneticSensorData")
        public List<GeomagneticSensorData> geomagneticSensorData;*/



/*
        @ColumnInfo(name = "hike_activity_accelerometer_sensor_data")
        public String hikeAccelerometerSensorData;

        @ColumnInfo(name = "hike_activity_geomagnetic_sensor_data_time_registered")
        public Date hikeGeomagneticSensorDataTimeRegistered;

        @ColumnInfo(name = "hike_activity_geomagnetic_sensor_data")
        public String hikeGeomagneticSensorData;
        */
    }
