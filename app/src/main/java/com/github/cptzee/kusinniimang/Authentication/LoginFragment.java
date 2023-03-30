package com.github.cptzee.kusinniimang.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.cptzee.kusinniimang.Dashboard.DashboardActivity;
import com.github.cptzee.kusinniimang.MainActivity;
import com.github.cptzee.kusinniimang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        super(R.layout.login_fragment);
    }

    private EditText email, password;
    private TextView signIn;
    private Button loginButton;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        email = view.findViewById(R.id.loginEmail);
        password = view.findViewById(R.id.loginPassword);
        signIn = view.findViewById(R.id.loginRegsiterText);
        loginButton = view.findViewById(R.id.loginButton);

        signIn.setOnClickListener(v -> {
            Log.i("AuthenticationForm", "Sign in text was clicked!");
            Log.i("AuthenticationForm", "Activity found!");
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.mainFragmentHolder, RegisterFragment.class, null)
                    .addToBackStack(null) //Return to the last fragment
                    .commit();
        });
        loginButton.setOnClickListener(v -> {
            Log.i("AuthenticationForm", "Login button was clicked!");
            FirebaseAuth mAuth = ((MainActivity) getActivity()).getmAuth();
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(getActivity(), (OnCompleteListener<AuthResult>) task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getActivity(), DashboardActivity.class));
                            getActivity().finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            email.setError("Invalid email!");
                            password.setError("Invalid password!");
                        }
                    });
        });
    }
}