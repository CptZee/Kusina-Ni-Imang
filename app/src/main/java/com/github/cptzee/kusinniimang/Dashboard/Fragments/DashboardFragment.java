package com.github.cptzee.kusinniimang.Dashboard.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.kusinniimang.MainActivity;
import com.github.cptzee.kusinniimang.R;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardFragment extends Fragment {
    public DashboardFragment() {
        super(R.layout.fragment_dashboard);
    }

    private SharedPreferences preferences;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = getActivity().getSharedPreferences("Kusina", MODE_PRIVATE);

        view.findViewById(R.id.dashboard_logout).setOnClickListener(v->{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("accountID", 0);
            editor.commit();
            FirebaseAuth.getInstance().signOut();
            getActivity().finish();
            startActivity(new Intent(getContext(), MainActivity.class));
        });
    }
}
