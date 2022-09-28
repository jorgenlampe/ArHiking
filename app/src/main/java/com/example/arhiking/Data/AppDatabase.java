package com.example.arhiking.Data;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DeleteColumn;
import androidx.room.DeleteTable;
import androidx.room.RenameColumn;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.room.migration.AutoMigrationSpec;

import com.example.arhiking.Models.Hike;
import com.example.arhiking.Models.HikeActivities;
import com.example.arhiking.Models.HikeActivity;
import com.example.arhiking.Models.User;

import java.lang.annotation.Annotation;
import java.util.Date;

@Database(entities = {User.class, Hike.class, HikeActivities.class}, version = 14, autoMigrations = {
        @AutoMigration (from = 13, to = 14, spec = AppDatabase.MyAutoMigration.class)
}, exportSchema = true)
@TypeConverters({AppDatabase.Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract HikeDao hikeDao();
    public abstract HikeActivityDao hikeActivityDao();

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