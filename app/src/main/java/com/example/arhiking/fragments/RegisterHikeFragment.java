package com.example.arhiking.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.room.Room;

import com.example.arhiking.Data.AppDatabase;
import com.example.arhiking.Data.HikeActivityDao;
import com.example.arhiking.Models.Hike_Activity;
import com.example.arhiking.databinding.FragmentRegisterHikeBinding;
import com.example.arhiking.viewmodels.RegisterHikeViewModel;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;

public class RegisterHikeFragment extends Fragment {

    private FragmentRegisterHikeBinding binding;

    private MapView map;
    private IMapController mapController;

    private ImageView imgPlay;
    private ImageView imgStop;
    private ImageView imgPause;

    HikeActivityDao hikeActivityDao;
    RegisterHikeViewModel registerHikeViewModel;
    AppDatabase db;

    private static final String TAG = "RegisterHikeFragment";
    private int trackingStatus;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registerHikeViewModel =
                new ViewModelProvider(this).get(RegisterHikeViewModel.class);

        binding = FragmentRegisterHikeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        hikeActivityDao = db.hikeActivityDao();

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

        imgPlay = binding.imageViewPlay;

        imgPause = binding.imageViewPause;

        imgPause.setOnClickListener(((View.OnClickListener) v -> {
            trackingStatus = 2;
            registerHikeViewModel.getTrackingStatus().setValue(trackingStatus);
            registerHikeViewModel.pauseSensorService();

        }));


        imgPlay.setOnClickListener(v -> {
            //todo get database instance....
            if (trackingStatus != 2) {//hvis pause, ikke opprett ny tur i database
                Hike_Activity newActivity = new Hike_Activity();
                long[] id = hikeActivityDao.insertAll(newActivity);
                registerHikeViewModel.getHikeActivityId().
                        setValue(id[0]);

            }

            trackingStatus = 1;

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

                    }
            );
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
}