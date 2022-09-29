package com.example.arhiking.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.arhiking.Models.HikeActivities;

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

    @Query("UPDATE hikeActivities SET hike_activity_geomagnetic_sensor_data_time_registered = :date WHERE hikeActivityId LIKE :hike_id")
    void addGeomagneticSensorTimeData(int hike_id, Long date);

    @Query("UPDATE hikeActivities SET hike_activity_geomagnetic_sensor_data = :data WHERE hikeActivityId LIKE :hike_id")
    void addGeomagneticSensorData(int hike_id, Float data);

    @Query("UPDATE hikeActivities SET hike_activity_gyroscope_sensor_data_time_registered = :date WHERE hikeActivityId LIKE :hike_id")
    void addGyroscopeTimeData(int hike_id, Long date);

    @Query("UPDATE hikeActivities SET hike_activity_gyroscope_sensor_data = :data WHERE hikeActivityId LIKE :hike_id")
    void addGyroscopeSensorData(int hike_id, Float data);
/*
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