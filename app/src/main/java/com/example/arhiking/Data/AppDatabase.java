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
import com.example.arhiking.Models.NewHikeActivity;
import com.example.arhiking.Models.User;


import java.util.Date;

@Database(entities = {User.class, Hike.class, NewHikeActivity.class,
GyroscopeSensorData.class, AccelerometerData.class, GeomagneticSensorData.class}
        , version = 16, autoMigrations = {
        @AutoMigration (from = 15, to = 16, spec = AppDatabase.MyAutoMigration.class)
}, exportSchema = true)
@TypeConverters({AppDatabase.Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract HikeDao hikeDao();
    public abstract HikeActivityDao hikeActivityDao();


    @DeleteTable.Entries(value = @DeleteTable(tableName = "HikeActivities"))
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

    }


}