package com.example.arhiking.Data;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DeleteTable;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.room.migration.AutoMigrationSpec;

import com.example.arhiking.Models.AccelerometerData;
import com.example.arhiking.Models.GeomagneticSensorData;
import com.example.arhiking.Models.GyroscopeSensorData;
import com.example.arhiking.Models.Hike;
import com.example.arhiking.Models.HikeGeoPoint;
import com.example.arhiking.Models.Hike_Activity;
import com.example.arhiking.Models.User;
import com.google.gson.Gson;


import org.osmdroid.util.GeoPoint;

import java.util.Date;

@Database(entities = {User.class, Hike.class, Hike_Activity.class, HikeGeoPoint.class,
GyroscopeSensorData.class, AccelerometerData.class, GeomagneticSensorData.class}
        , version = 19, autoMigrations = {
        @AutoMigration (from = 18, to = 19, spec = AppDatabase.MyAutoMigration.class)
}, exportSchema = true)
@TypeConverters({AppDatabase.Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract HikeDao hikeDao();
    public abstract HikeActivityDao hikeActivityDao();
    public abstract AccelerometerDao accelerometerDao();
    public abstract GeomagneticDao geomagneticDao();
    public abstract GyroscopeDao gyroscopeDao();
    public abstract GeoPointsDao geoPointsDao();

    //@DeleteTable.Entries(value = @DeleteTable(tableName = "hikeActivity"))
    static class MyAutoMigration implements AutoMigrationSpec {

    }

    public static class Converters {
        @TypeConverter
        public Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }

        @TypeConverter
        public static GeoPoint stringToGeoPoint(String data) {
            return new Gson().fromJson(data, GeoPoint.class);
        }

        @TypeConverter
        public static String geoPointToString(GeoPoint geoPoint) {
            return new Gson().toJson(geoPoint);
        }

    }


}