package com.github.cptzee.kusinniimang.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.cptzee.kusinniimang.Dashboard.Fragments.DashboardFragment;
import com.github.cptzee.kusinniimang.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.dashboard_container, new DashboardFragment())
                .commit();
    }
}