package com.example.arhiking.Data;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.arhiking.Models.User;

@Database(entities = {User.class}, version = 2, autoMigrations = {
        @AutoMigration (from = 1, to = 2)
}, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}