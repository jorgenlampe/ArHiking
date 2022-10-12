package com.example.arhiking.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arhiking.Models.Hike_Activity;

import java.util.List;

@Dao
public interface HikeActivityDao {
    @Query("SELECT * FROM hike_Activity")
    LiveData<List<Hike_Activity>> getAll();

    @Query("SELECT * FROM hike_activity WHERE hikeActivityId IN (:hikeActivityIds)")
    List<Hike_Activity> loadAllByIds(int[] hikeActivityIds);

    @Delete
    void deleteHikeActivity(Hike_Activity hikeActivity);

    @Update
    void updateHikeActivity(Hike_Activity hikeActivity);

    @Insert
    long[] insertAll(Hike_Activity... hikeActivities);


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