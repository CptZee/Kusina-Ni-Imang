package com.github.cptzee.kusinniimang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.github.cptzee.kusinniimang.Authentication.LoginFragment;
import com.github.cptzee.kusinniimang.Authentication.RegisterFragment;
import com.github.cptzee.kusinniimang.Authentication.SplashScreenFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideStatusBar();

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.mainFragmentHolder, SplashScreenFragment.class, null)
                .commit();
        new Handler().postDelayed(() -> {
            launchLoginFragment();
        }, 2000);
    }
    public void launchLoginFragment(){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.mainFragmentHolder, LoginFragment.class, null)
                .commit();
    }

    public void launchRegisterFragment(){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.mainFragmentHolder, RegisterFragment.class, null)
                .addToBackStack(null) //Return to the last fragment
                .commit();
    }
    public void hideStatusBar(){
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}