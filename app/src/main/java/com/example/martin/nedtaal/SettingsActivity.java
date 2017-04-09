package com.example.martin.nedtaal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    private static Activity mContext;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean inverse = sharedPref.getBoolean("background", true);
        if(inverse) { setTheme(R.style.AppTheme_Inverse); }
        else { setTheme(R.style.AppTheme); }

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.settings);
            updateSummary(getPreferenceScreen());
        }

        private String updateSummary(Preference p)
        {
            p.setSummary("");

            if (p instanceof ListPreference)
            {
                ListPreference preference = (ListPreference) p;
                p.setSummary(preference.getEntry() == null ? "" : preference.getEntry().toString());
            }
            return p.getSummary().toString();
        }

        @Override public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
        {
            updateSummary(getPreferenceScreen());
            mContext.recreate();
        }

        @Override
        public void onResume()
        {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }
        @Override
        public void onPause()
        {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}

