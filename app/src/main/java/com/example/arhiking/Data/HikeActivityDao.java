package com.example.arhiking.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arhiking.Models.Hike;
import com.example.arhiking.Models.HikeActivities;
import com.example.arhiking.Models.HikeActivity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

@Dao
public interface HikeActivityDao {
    @Query("SELECT * FROM hikeActivities")
    LiveData<List<HikeActivities>> getAll();

    @Query("SELECT * FROM HikeActivities WHERE hikeActivityId IN (:hikeActivityIds)")
    List<HikeActivities> loadAllByIds(int[] hikeActivityIds);
/*
    @Query("SELECT * FROM hikeActivity WHERE hike_activity_name LIKE :hikeActivityName")
    HikeActivity findByName(String hikeActivityName);

    @Insert
    void insertAll(HikeActivity... hikeActivities);
*/
    @Query("UPDATE hikeActivities SET hike_activity_accelerometer_sensor_data_time_registered = :date WHERE hikeActivityId LIKE :hike_id")
    void addAccelerometerSensorTimeData(int hike_id, Long date);

     @Query("UPDATE hikeActivities SET hike_activity_accelerometer_sensor_data = :data WHERE hikeActivityId LIKE :hike_id")
    void addAccelerometerSensorData(int hike_id, Float data);
/*
    @Delete
    void delete(HikeActivity hikeActivity);

    @Query("SELECT hike_activity_accelerometer_sensor_data_time_registered FROM hikeActivity WHERE " +
            "hikeActivityId LIKE (:hikeActivityId)")
    Date getAccelerometerSensorDataTimeByHikeActivityId(
            int hikeActivityId);

    @Query("SELECT hike_activity_accelerometer_sensor_data FROM hikeActivity WHERE " +
            "hikeActivityId LIKE (:hikeActivityId)")
    String getAccelerometerSensorDataByHikeActivityName(
            int hikeActivityId);

    @Query("SELECT hike_activity_geomagnetic_sensor_data_time_registered FROM hikeActivity WHERE " +
            "hikeActivityId LIKE (:hikeActivityId)")
    Date getGeomagneticSensorDataTimeByHikeActivityName(
            int hikeActivityId);

    @Query("SELECT hike_activity_geomagnetic_sensor_data FROM hikeActivity WHERE " +
            "hikeActivityId LIKE (:hikeActivityId)")
    String getGeomagneticSensorDataByHikeActivityName(
            int hikeActivityId);
*/
}