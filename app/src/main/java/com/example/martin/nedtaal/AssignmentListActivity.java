package com.example.martin.nedtaal;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AssignmentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean inverse = sharedPref.getBoolean("background", true);
        if(inverse) { setTheme(R.style.AppTheme_Inverse); }
        else { setTheme(R.style.AppTheme); }

        setContentView(R.layout.activity_assignmentlist);
    }
}
