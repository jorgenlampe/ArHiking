package com.example.arhiking.fragments;

import android.content.Context;

import android.content.Intent;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;
import androidx.room.Room;


import com.example.arhiking.Data.AppDatabase;
import com.example.arhiking.Data.HikeActivityDao;
import com.example.arhiking.Models.Hike_Activity;

import com.example.arhiking.KalmanFilter.KalmanLatLong;
import com.example.arhiking.R;
import com.example.arhiking.databinding.FragmentMapBinding;

import com.example.arhiking.databinding.FragmentRegisterHikeBinding;
import com.example.arhiking.viewmodels.RegisterHikeViewModel;
import com.google.android.gms.maps.model.LatLng;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RegisterHikeFragment extends Fragment {

    private FragmentRegisterHikeBinding binding;

    private MapView map;
    private IMapController mapController;

    private ImageView imgPlay;
    private ImageView imgStop;
    private ImageView imgPause;
    public com.google.android.gms.maps.model.LatLng locationLatLong;
    ArrayList<Location> locationList;

    ArrayList<Location> oldLocationList;
    ArrayList<Location> noAccuracyLocationList;
    ArrayList<Location> inaccurateLocationList;
    ArrayList<Location> kalmanNGLocationList;
    long runStartTimeInMillis;


    boolean isLogging;

    float currentSpeed = 0.0f; // meters/second

    KalmanLatLong kalmanFilter;



    HikeActivityDao hikeActivityDao;
    RegisterHikeViewModel registerHikeViewModel;
    AppDatabase db;

    private static final String TAG = "RegisterHikeFragment";
    private int trackingStatus;

    private GeoPoint geoPoint;

    private Location locationFromGeoPoint;

    private List<GeoPoint> trackedPath;

    Polyline path = new Polyline();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registerHikeViewModel =
                new ViewModelProvider(this).get(RegisterHikeViewModel.class);

        binding = FragmentRegisterHikeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        hikeActivityDao = db.hikeActivityDao();

        locationList = new ArrayList<>();
        noAccuracyLocationList = new ArrayList<>();
        oldLocationList = new ArrayList<>();
        inaccurateLocationList = new ArrayList<>();
        kalmanNGLocationList = new ArrayList<>();
        kalmanFilter = new KalmanLatLong(3);



        Context ctx = getActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        // Inflate the layout for this fragment
        map = binding.registerHikeMapView;
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        map.setMultiTouchControls(true);
        mapController = map.getController();
        mapController.setZoom(13.0);
        GeoPoint startPoint = new GeoPoint(63.45, 10.42);
        //todo sette aktuelt startpoint
        mapController.setCenter(startPoint);

        trackedPath = new ArrayList<>();

        imgPlay = binding.imageViewPlay;

        imgPause = binding.imageViewPause;

        imgPause.setOnClickListener(((View.OnClickListener) v -> {
            trackingStatus = 2;
            registerHikeViewModel.getTrackingStatus().setValue(trackingStatus);
            registerHikeViewModel.pauseSensorService();

        }));


        imgPlay.setOnClickListener(v -> {

            if (trackingStatus != 2) {//hvis pause, ikke opprett ny tur i database
                Hike_Activity newActivity = new Hike_Activity();
                long[] id = hikeActivityDao.insertAll(newActivity);
                registerHikeViewModel.getHikeActivityId().
                        setValue(id[0]);

            }

            trackingStatus = 1;

            GeoPoint startingPoint = registerHikeViewModel.getCurrentLocation().getValue();
            mapController.setCenter(startingPoint);
            Marker startPosMarker = new Marker(map);
            startPosMarker.setPosition(startingPoint);
            startPosMarker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
            startPosMarker.setTitle("Startposisjon");
            startPosMarker.setSubDescription("Turen starter her");
            map.getOverlays().add(startPosMarker);


            registerHikeViewModel.startSensorService();

            registerHikeViewModel.getSensorData().observe(
                    getViewLifecycleOwner(), aFloat -> {
                        //todo... analysere/filtrere data fra sensorer
                        Log.i("Sensor change observed. OrientationX: ",
                                String.valueOf(aFloat[0]));
                        Log.i("Sensor change observed. OrientationY: ",
                                String.valueOf(aFloat[1]));
                        Log.i("Sensor change observed. OrientationZ: ",
                                String.valueOf(aFloat[2]));
                    });

            registerHikeViewModel.getAccelerationData().observe(
                    getViewLifecycleOwner(), accel -> {

                        Log.i("Akselerasjon er: ", String.valueOf(accel));

                    });
            registerHikeViewModel.getCurrentLocation().observe(getViewLifecycleOwner(), curLoc -> {
                locationFromGeoPoint = new Location(LocationManager.GPS_PROVIDER);
                locationFromGeoPoint.setLatitude(curLoc.getLatitude());
                locationFromGeoPoint.setLongitude(curLoc.getLongitude());
                filterAndAddLocation(locationFromGeoPoint);
                /*trackedPath.add(curLoc);*/
                path.setPoints(trackedPath);
                map.getOverlayManager().add(path);
                map.invalidate();

            });
        });

        imgStop = binding.imageViewStop;
        imgStop.setOnClickListener(v -> {
            registerHikeViewModel.stopSensorService();
            trackingStatus = 2;
            registerHikeViewModel.getTrackingStatus().setValue(trackingStatus);
        });

        return root;
    }

    public void onResume() {
        super.onResume();

        if (map != null)
            map.onResume();
    }

    public void onPause() {
        super.onPause();

        if (map != null)
            map.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean filterAndAddLocation(Location location){

        long age = getLocationAge(location);

        if(age > 10 * 1000){ //more than 10 seconds
            Log.d(TAG, "Location is old");
            oldLocationList.add(location);
            return false;
        }

        if(location.getAccuracy() <= 0){
            Log.d(TAG, "Latitidue and longitude values are invalid.");
            noAccuracyLocationList.add(location);
            return false;
        }

        //setAccuracy(newLocation.getAccuracy());
        float horizontalAccuracy = location.getAccuracy();
        if(horizontalAccuracy > 100){
            Log.d(TAG, "Accuracy is too low.");
            inaccurateLocationList.add(location);
            return false;
        }


        /* Kalman Filter */
        float Qvalue;

        long locationTimeInMillis = (long)(location.getElapsedRealtimeNanos() / 1000000);
        long elapsedTimeInMillis = locationTimeInMillis - runStartTimeInMillis;

        if(currentSpeed == 0.0f){
            Qvalue = 3.0f; //3 meters per second
        }else{
            Qvalue = currentSpeed; // meters per second
        }

        kalmanFilter.Process(location.getLatitude(), location.getLongitude(), location.getAccuracy(), elapsedTimeInMillis, Qvalue);
        double predictedLat = kalmanFilter.get_lat();
        double predictedLng = kalmanFilter.get_lng();

        Location predictedLocation = new Location("");//provider name is unecessary
        predictedLocation.setLatitude(predictedLat);//your coords of course
        predictedLocation.setLongitude(predictedLng);
        float predictedDeltaInMeters =  predictedLocation.distanceTo(location);

        if(predictedDeltaInMeters > 60){
            Log.d(TAG, "Kalman Filter detects mal GPS, we should probably remove this from track");
            kalmanFilter.consecutiveRejectCount += 1;

            if(kalmanFilter.consecutiveRejectCount > 3){
                kalmanFilter = new KalmanLatLong(3); //reset Kalman Filter if it rejects more than 3 times in raw.
            }

            kalmanNGLocationList.add(location);
            return false;
        }else{
            kalmanFilter.consecutiveRejectCount = 0;
        }

        /* Notifiy predicted location to UI */
        Intent intent = new Intent("PredictLocation");
        intent.putExtra("location", predictedLocation);
        LocalBroadcastManager.getInstance(getActivity().getApplication()).sendBroadcast(intent);

        Log.d(TAG, "Location quality is good enough.");
        currentSpeed = location.getSpeed();
        geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
        locationList.add(location);
        trackedPath.add(geoPoint);


        return true;
    }

    private long getLocationAge(Location newLocation){
        long locationAge;
        if(android.os.Build.VERSION.SDK_INT >= 17) {
            long currentTimeInMilli = (long)(SystemClock.elapsedRealtimeNanos() / 1000000);
            long locationTimeInMilli = (long)(newLocation.getElapsedRealtimeNanos() / 1000000);
            locationAge = currentTimeInMilli - locationTimeInMilli;
        }else{
            locationAge = System.currentTimeMillis() - newLocation.getTime();
        }
        return locationAge;
    }

    /*@Override
    public void onLocationChanged(@NonNull Location location) {
        locationLatLong = new LatLng(location.getLatitude(), location.getLongitude());

        if (!startPos){
            GeoPoint startPoint = new GeoPoint(locationLatLong.latitude, locationLatLong.longitude);

            mapController.setCenter(startPoint);
            startPos = true;
            tracking = true;
            Marker startPosMarker = new Marker(map);
            startPosMarker.setPosition(startPoint);
            startPosMarker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
            startPosMarker.setTitle("Startposisjon");
            startPosMarker.setSubDescription("Turen starter her");
            map.getOverlays().add(startPosMarker);
            trackedPath.add(startPoint);
        }

        if (tracking) {
            GeoPoint currentPosition = new GeoPoint(locationLatLong.latitude, locationLatLong.longitude);
            trackedPath.add(currentPosition);

            path.setPoints(trackedPath);
            map.getOverlayManager().add(path);
            map.invalidate();
        }
    }*/
}