package com.example.arhiking.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.room.Room;
import com.example.arhiking.Data.AppDatabase_v2;
import com.example.arhiking.Data.UserDao;
import com.example.arhiking.Models.HikeActivitiesWithGeoPoints;
import com.example.arhiking.Models.HikeActivityGeoPoint;
import com.example.arhiking.Models.HikesWithHikesActivities;

import com.example.arhiking.Models.User;
import com.example.arhiking.Models.UserWithHikes;
import com.example.arhiking.R;
import com.example.arhiking.databinding.FragmentMapBinding;
import com.example.arhiking.viewmodels.MapViewModel;

import org.checkerframework.checker.units.qual.A;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class MapFragment extends Fragment {


    private FragmentMapBinding binding;

    private MapView map;
    private IMapController mapController;

    private static final String TAG = "MapFragment";

    private static final int PERMISSION_REQUEST_CODE = 1;

    private LocationManager lm;

    private Context ctx;

    AppDatabase_v2 db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapViewModel mapViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);

        ctx = getActivity().getApplicationContext();

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        db = Room.databaseBuilder(ctx,
                AppDatabase_v2.class, "database-v2").allowMainThreadQueries().build();

        if (!checkPermissions())
        {
            requestAllPermissions();
        }
        else {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListenerGPS);
        }


        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
/*
        final TextView textView = binding.textMap;
        mapViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        View mapView = inflater.inflate(R.layout.fragment_map, container, false);

        Context ctx = getActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        // Inflate the layout for this fragment
        map = binding.mapview;
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        map.setMultiTouchControls(true);
        mapController = map.getController();
        mapController.setZoom(13.0);
        GeoPoint startPoint = new GeoPoint(63.45, 10.42);
        mapController.setCenter(startPoint);

  
        List<UserWithHikes> users = db.userDao().getUserWithHikes();

        for (UserWithHikes user : users){
            for (int i = 0; i < user.hikes.size(); i++) {
                Marker startPosMarker = new Marker(map);
                startPosMarker.setPosition(user.hikes.get(i).startingPoint);
                startPosMarker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
                startPosMarker.setTitle("Startposisjon");
                startPosMarker.setSubDescription("Turen starter her");
        }


        List<HikeActivitiesWithGeoPoints> hikes = db.hikeActivityDao().getHikeActivitiesWithGeoPoints();

            for (HikeActivitiesWithGeoPoints hikeActivity : hikes) {
                List<HikeActivityGeoPoint> geoPoints = hikeActivity.hikeActivityGeoPoints;
                for (HikeActivityGeoPoint geoPoint : geoPoints) {
                    List<GeoPoint> trackedPath = new ArrayList<>();
                    trackedPath.add(geoPoint.geoPoint);
                    Polyline path = new Polyline();
                    path.setPoints(trackedPath);

                    map.getOverlayManager().add(path);

                    map.invalidate();
                }
            }



        return root;
    }

    private void requestAllPermissions() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermissions() {
        Context ctx = getActivity().getApplicationContext();
        if (ActivityCompat.checkSelfPermission(ctx , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return false;
        }
        return true;
    }

    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        if (map != null)
            map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        if (map != null)
            map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override public void onRequestPermissionsResult(int requestCode, @Nonnull String[] permissions, @Nonnull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListenerGPS);
                }
                else {
                    Toast.makeText(ctx, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    LocationListener locationListenerGPS=new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            Context ctx = getActivity().getApplicationContext();
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();
            String msg="New Latitude: "+latitude + "New Longitude: "+longitude;
            Toast.makeText(ctx,msg,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}