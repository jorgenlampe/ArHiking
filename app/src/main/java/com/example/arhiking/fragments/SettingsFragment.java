package com.example.arhiking.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.example.arhiking.R;
import com.example.arhiking.databinding.FragmentSettingsBinding;
import com.example.arhiking.viewmodels.SettingsViewModel;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private FragmentSettingsBinding binding;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load preferences from an XML resource
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        SwitchPreferenceCompat themePreference = findPreference("theme_settings");
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        // Settings to change theme
        SwitchPreferenceCompat themePreference = findPreference("theme_settings");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean isChecked = sharedPreferences.getBoolean("theme_settings", false);
        if(isChecked){
            themePreference.setSummaryOn(R.string.dark_theme);
             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            themePreference.setSummaryOff(R.string.light_theme);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }



       //open user data fragment (user statistics) from settings
        Preference userDataPreference = findPreference("user_data");
        userDataPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {

                Navigation.findNavController(getView()).navigate(R.id.action_navigation_settings_to_userDataFragment);
             /*  UserDataFragment userDataFragment = new UserDataFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, userDataFragment)
                        .addToBackStack(null)
                        .commit();
*/
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        SwitchPreferenceCompat themePreference = findPreference("theme_settings");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean isChecked = sharedPreferences.getBoolean("theme_settings", false);
        if(isChecked){
            themePreference.setSummaryOn(R.string.dark_theme);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            themePreference.setSummaryOff(R.string.light_theme);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}