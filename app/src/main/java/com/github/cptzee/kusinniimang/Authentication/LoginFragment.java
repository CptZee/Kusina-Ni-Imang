package com.github.cptzee.kusinniimang.Authentication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.cptzee.kusinniimang.MainActivity;
import com.github.cptzee.kusinniimang.R;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        super(R.layout.login_fragment);
    }

    private EditText username, password;
    private TextView signIn;
    private Button loginButton;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        username = view.findViewById(R.id.loginUsername);
        password = view.findViewById(R.id.loginPassword);
        signIn = view.findViewById(R.id.loginRegsiterText);
        loginButton = view.findViewById(R.id.loginButton);

        signIn.setOnClickListener(v -> {
            Log.i("AuthenticationForm", "Sign in button was clicked!");
            if (getActivity() instanceof MainActivity) {
                Log.i("AuthenticationForm", "Activity found!");
                ((MainActivity) getActivity()).launchRegisterFragment();
            }
        });
    }
}