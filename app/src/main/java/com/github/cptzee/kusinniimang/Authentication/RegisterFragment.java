package com.github.cptzee.kusinniimang.Authentication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.cptzee.kusinniimang.MainActivity;
import com.github.cptzee.kusinniimang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class RegisterFragment extends Fragment {
    public RegisterFragment() {
        super(R.layout.register_fragment);
    }
    private EditText email, password, password2;
    private TextView login;
    private Button registerButton;


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        email = view.findViewById(R.id.registerEmail);
        password = view.findViewById(R.id.registerPassword);
        password2 = view.findViewById(R.id.registerPassword2);
        login = view.findViewById(R.id.registerLoginText);
        registerButton = view.findViewById(R.id.registerButton);

        login.setOnClickListener(v -> {
            Log.i("AuthenticationForm", "Login text was clicked!");
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.mainFragmentHolder, LoginFragment.class, null)
                    .commit();
        });
        registerButton.setOnClickListener(v -> {
            Log.i("AuthenticationForm", "Register button was clicked!");
            boolean validFields = true;
            if (email.getText().toString().isEmpty()){
                email.setError("Username must not be empty!");
                validFields = false;
            }
            if (password.getText().toString().isEmpty()){
                password.setError("Username must not be empty!");
                validFields = false;
            }
            if(!password.getText().toString().equals(password2.getText().toString())) {
                password2.setError("Password does not match!");
                validFields = false;
            }
            if(!validFields)
                return;
            FirebaseAuth mAuth = ((MainActivity) getActivity()).getmAuth();
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(getActivity(), task -> {
                        if (task.isSuccessful()) {
                            Log.i("Auth", "Account successfully created!");
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.mainFragmentHolder, LoginFragment.class, null)
                                    .commit();
                        } else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            email.setError("Email already in use!");
                        }else {
                            Log.e("Auth", "Unable to create account!", task.getException());
                            Toast.makeText(getActivity(), "Registration failed, make sure that you are connected to the internet!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
