package com.example.arhiking.Data;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DeleteColumn;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;

import com.example.arhiking.Models.User;

import java.lang.annotation.Annotation;

@Database(entities = {User.class}, version = 4, autoMigrations = {
        @AutoMigration (from = 3, to = 4, spec = AppDatabase.MyAutoMigration.class)
}, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    @DeleteColumn(tableName = "User", columnName = "email")
    static class MyAutoMigration implements AutoMigrationSpec {

    }


}