package com.example.arhiking.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.arhiking.Data.AppDatabase_v2;
import com.example.arhiking.Data.HikeActivityDao;
import com.example.arhiking.MainActivity;
import com.example.arhiking.Models.Hike_Activity;
import com.example.arhiking.R;
import com.example.arhiking.databinding.FragmentCompletedHikeBinding;
import com.example.arhiking.databinding.FragmentHomeBinding;
import com.example.arhiking.viewmodels.RegisterHikeViewModel;

import org.osmdroid.util.GeoPoint;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CompletedHikeFragment extends Fragment {

    private FragmentCompletedHikeBinding binding;
    DatePickerDialog picker;
    EditText calendarEditText;
    RegisterHikeViewModel viewModel;
    Button btnSave;
    AppDatabase_v2 db;
    Context ctx;
    HikeActivityDao dao;
    EditText etHikeName;
    TextView tvDuration;
    TextView tvDistance;
    TextView tvHighestElevation;
    TextView tvSpeed;

    //Hike_Activity hikeActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompletedHikeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ctx = getActivity().getApplicationContext();
        db = Room.databaseBuilder(ctx,
                AppDatabase_v2.class, "database-v2").allowMainThreadQueries().build();

        dao = db.hikeActivityDao();
        viewModel =
                new ViewModelProvider(getActivity()).get(RegisterHikeViewModel.class);

        Hike_Activity hikeActivity = dao.getHikeActivityById(
                viewModel.getHikeActivityId().getValue());


        etHikeName = binding.hikeNameEditText;
        tvDuration = binding.durationNewHikeTextView;
        tvDistance = binding.completedHikeDistanceTextView;
        tvHighestElevation = binding.highestElevationNewHikeTextView;
        tvSpeed = binding.completedHikeSpeedResultTextView;

        long timeRegistered = hikeActivity.timeRegistered;
        Date date = new Date();
        long currentTime = date.getTime();
        long duration = currentTime - timeRegistered;
        tvDuration.setText(((int) duration/1000) + " sec");


        GeoPoint startPoint = hikeActivity.hikeActivityStartingPoint;
        GeoPoint endPoint = viewModel.getCurrentLocation().getValue();

        double highestElevation = viewModel.getHighestElevation().getValue();

        tvHighestElevation.setText(String.valueOf((int)highestElevation));

        float[] results = new float[1];
        Location.distanceBetween(startPoint.getLatitude(),
                startPoint.getLongitude(), endPoint.getLatitude(),
                endPoint.getLongitude(), results);

        tvDistance.setText(String.valueOf(((int) results[0])/1000) +
                " km");

        int speed = (int) (results[0] / duration);
        tvSpeed.setText(String.valueOf(((int) speed)));

        btnSave = binding.saveNewHikeButton;
        btnSave.setOnClickListener(v -> {

            hikeActivity.hikeActivityName
                    = etHikeName.getText().toString();

            //oppdaterer hike med input fra skjema og navigerer til Home
            db.hikeActivityDao().updateHikeActivity(hikeActivity);
            Navigation.findNavController(getView()).navigate(R.id.
                    action_completedHikeFragment_to_navigation_home);
        });

        // Open calendar when clicking on date text view
        calendarEditText = root.findViewById(R.id.calendarEditText);
        calendarEditText.setInputType(InputType.TYPE_NULL);
        calendarEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calendarEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        // Dropdown list for hike difficulty
        Spinner difficultySpinner = root.findViewById(R.id.difficultySpinner);
        // Array adapter with default layout and string array from resources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.hike_difficulty_entries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply adapter to the spinner
        difficultySpinner.setAdapter(adapter);

        return root;

    }

    //TODO : listener for hike difficulty dropdown

    // Implement OnItemSelectedListener
    /* public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}