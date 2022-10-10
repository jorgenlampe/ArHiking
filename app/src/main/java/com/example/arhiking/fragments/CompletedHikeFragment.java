package com.example.arhiking.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.arhiking.MainActivity;
import com.example.arhiking.R;
import com.example.arhiking.databinding.FragmentCompletedHikeBinding;
import com.example.arhiking.databinding.FragmentHomeBinding;

import java.util.Calendar;

public class CompletedHikeFragment extends Fragment {

    private FragmentCompletedHikeBinding binding;
    DatePickerDialog picker;
    EditText calendarEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompletedHikeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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