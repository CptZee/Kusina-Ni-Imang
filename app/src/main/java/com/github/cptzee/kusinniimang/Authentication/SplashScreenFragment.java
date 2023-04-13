package com.github.cptzee.kusinniimang.Authentication;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.cptzee.kusinniimang.R;

public class SplashScreenFragment extends Fragment {
    public SplashScreenFragment() {
        super(R.layout.splash_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        new Handler().postDelayed(() -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentHolder, LoginFragment.class, null)
                    .commit();
        }, 2000);
    }
}
