package com.example.arhiking.Data;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DeleteColumn;
import androidx.room.RenameColumn;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;

import com.example.arhiking.Models.Hike;
import com.example.arhiking.Models.HikeActivity;
import com.example.arhiking.Models.User;

import java.lang.annotation.Annotation;

@Database(entities = {User.class, Hike.class, HikeActivity.class}, version = 7, autoMigrations = {
        @AutoMigration (from = 6, to = 7, spec = AppDatabase.MyAutoMigration.class)
}, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract HikeDao hikeDao();
    public abstract HikeActivityDao hikeActivityDao();

    @DeleteColumn(tableName = "User", columnName = "email")
    @DeleteColumn(tableName = "Hike", columnName = "userCreatorId")
    static class MyAutoMigration implements AutoMigrationSpec {

    }


}