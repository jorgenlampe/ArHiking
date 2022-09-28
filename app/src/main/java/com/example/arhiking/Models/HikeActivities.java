package com.example.arhiking.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
    public class HikeActivities {
        @PrimaryKey(autoGenerate = true)
        public int hikeActivityId;

        @ColumnInfo(name = "hike_activity_name")
        public String hikeActivityName;

        @ColumnInfo(name = "hike_activity_description")
        public String hikeActivityDescription;

        @ColumnInfo(name = "hike_id", defaultValue = "0")
        public long hike_id;

      /*  @ColumnInfo(name = "hike_activity_length")
        public double hikeActivityLength;

        @ColumnInfo(name = "hike_activity_duration")
        public double hikeActivityDuration;

        @ColumnInfo(name = "hike_activity_start_time")
        public Date hikeActivityStartTime;

        @ColumnInfo(name = "hike_activity_stop_time")
        public Date hikeActivityStopTime;
*/
        @ColumnInfo(name = "hike_activity_accelerometer_sensor_data_time_registered")
        public Long hikeAccelerometerSensorDataTimeRegistered;

        @ColumnInfo(name = "hike_activity_accelerometer_sensor_data")
        public Float hikeAccelerometerSensorData;

        @ColumnInfo(name = "hike_activity_geomagnetic_sensor_data_time_registered")
        public Long hikeGeomagneticSensorDataTimeRegistered;

        @ColumnInfo(name = "hike_activity_geomagnetic_sensor_data")
        public Float hikeGeomagneticSensorData;
/*
        @ColumnInfo(name = "hike_activity_accelerometer_sensor_data")
        public String hikeAccelerometerSensorData;

        @ColumnInfo(name = "hike_activity_geomagnetic_sensor_data_time_registered")
        public Date hikeGeomagneticSensorDataTimeRegistered;

        @ColumnInfo(name = "hike_activity_geomagnetic_sensor_data")
        public String hikeGeomagneticSensorData;
        */
    }
