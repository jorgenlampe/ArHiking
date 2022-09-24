package com.example.arhiking;

import android.os.Bundle;
import android.util.Log;

import com.example.arhiking.Data.AppDatabase;
import com.example.arhiking.Data.HikeDao;
import com.example.arhiking.Data.UserDao;
import com.example.arhiking.Models.Hike;
import com.example.arhiking.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.arhiking.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_map, R.id.navigation_user,
                R.id.navigation_register_hike)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //create instance of database
        try {

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database-name").allowMainThreadQueries().build();
          //          addMigrations(MIGRATION_3_4).allowMainThreadQueries().build();

            //todo fjerne mulighet for å bruke database i main thread



            //example use of db
            UserDao userDao = db.userDao();

            User user = new User();
            user.firstName = "John";
            user.lastName = "Doe";

            userDao.insertAll(user);

            List<User> users = userDao.getAll();
            String firstName = users.get(0).firstName;
            Log.i("fornavn", firstName);

            HikeDao hikeDao = db.hikeDao();
            Hike hike = new Hike();
            hike.hikeName = "En fin tur";
            hike.hikeDescription = "Turen gikk fra A til B";

            List<Hike> hikes = hikeDao.getAll();
            hikeDao.insertAll(hike);
            String hikeName = hikes.get(0).hikeName;
            Log.i("hikeName", hikeName);

        }
        catch (Exception e) {
            new Exception(e.getMessage(), e);
        }
    }

}