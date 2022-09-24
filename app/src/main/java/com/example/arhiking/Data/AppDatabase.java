package com.example.arhiking.Data;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DeleteColumn;
import androidx.room.RenameColumn;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;

import com.example.arhiking.Models.Hike;
import com.example.arhiking.Models.User;

import java.lang.annotation.Annotation;

@Database(entities = {User.class, Hike.class}, version = 6, autoMigrations = {
        @AutoMigration (from = 5, to = 6, spec = AppDatabase.MyAutoMigration.class)
}, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract HikeDao hikeDao();

    @DeleteColumn(tableName = "User", columnName = "email")
    @DeleteColumn(tableName = "Hike", columnName = "userCreatorId")
    static class MyAutoMigration implements AutoMigrationSpec {

    }


}