package com.github.cptzee.kusinniimang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.cptzee.kusinniimang.Authentication.LoginFragment;
import com.github.cptzee.kusinniimang.Authentication.SplashScreenFragment;
import com.github.cptzee.kusinniimang.Dashboard.DashboardActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences("Kusina", MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideStatusBar();

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.mainFragmentHolder, SplashScreenFragment.class, null)
                .commit();

        new Handler().postDelayed(() -> {
            runPersistentLogin();
        }, 2000);
    }

    private void runPersistentLogin() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            Log.i("ConnectionHelper", "Internet connection found!");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                runLogin();
                return;
            }
            runDashboard();
            return;
        }
        Log.i("ConnectionHelper", "Internet connection not found!");
        Toast.makeText(this, "No internet connection found! Running in offline mode.", Toast.LENGTH_SHORT).show();
        if (preferences.getInt("accountID", 0) != 0) {
            runDashboard();
            return;
        }
        runLogin();
    }

    private void runLogin(){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.mainFragmentHolder, LoginFragment.class, null)
                .commit();
    }

    private void runDashboard(){
        Toast.makeText(this, "Resuming last session...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, DashboardActivity.class));
        this.finish();
    }

    public void hideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> this.finish())
                    .setNegativeButton("No", (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        } else
            super.onBackPressed();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }
}