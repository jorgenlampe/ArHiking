package com.example.arhiking;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.arhiking.Data.AppDatabase;
import com.example.arhiking.Data.HikeDao;
import com.example.arhiking.Data.UserDao;
import com.example.arhiking.Models.Hike;
import com.example.arhiking.Models.HikesWithHikesActivities;
import com.example.arhiking.Models.User;
import com.example.arhiking.Models.UserWithHikes;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.example.arhiking.databinding.ActivityMainBinding;
import com.google.firebase.firestore.GeoPoint;
import com.google.type.LatLng;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int REQUEST_CODE = 999;
    LocationManager locationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        askForPermission();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_map, R.id.navigation_library,
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

            List<UserWithHikes> userWithHikes = userDao.getUserWithHikes();
            Log.i("userWithHikes", userWithHikes.toString());

            List<HikesWithHikesActivities> activities = hikeDao.getHikesWithActivities();

            Log.i("activities", activities.toString());


        }
        catch (Exception e) {
            new Exception(e.getMessage(), e);
        }

    }

    private void askForPermission() {
        if (!permissionsGranted())
            requestPermissionsForLocation();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode) {
            case REQUEST_CODE:{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    Log.i("permission info", "permission granted");
                    Toast.makeText(getApplicationContext(),
                            "Starting tracking...", Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("permission info", "permission not granted");
                    Toast.makeText(getApplicationContext(),
                            "Premission not granted", Toast.LENGTH_SHORT).show();
                }
            } break;
            default:
                super.onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        }
    }



    private void requestPermissionsForLocation() {
        requestPermissions(
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE

        );
    }

    private boolean permissionsGranted() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        return true;
    }


}