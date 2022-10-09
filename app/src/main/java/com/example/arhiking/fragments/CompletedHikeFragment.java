package com.example.arhiking.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

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
        // TODO : fix datepicking for current day
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

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}