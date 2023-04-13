package com.github.cptzee.kusinniimang.Authentication.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.cptzee.kusinniimang.Data.Credential;
import com.github.cptzee.kusinniimang.Data.Helper.CredentialHelper;
import com.github.cptzee.kusinniimang.MainActivity;
import com.github.cptzee.kusinniimang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterFragment extends Fragment {
    public RegisterFragment() {
        super(R.layout.fragment_register);
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
            if(!fieldsAreValid())
                return;
            registerUser();
        });
    }

    private void registerUser(){
        FirebaseAuth mAuth = ((MainActivity) getActivity()).getmAuth();
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.i("Auth", "Account successfully created!");
                        Credential credential = new Credential();
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null)
                            credential.setUuid(user.getUid());
                        credential.setEmail(email.getText().toString());
                        credential.setPassword(encryptedPassword(password.getText().toString()));
                        CredentialHelper.instance(getContext()).insert(credential);

                        Toast.makeText(getContext(), "Account successfully registered!", Toast.LENGTH_SHORT).show();

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
    }

    private String encryptedPassword(String toEncrypt){
        String encryptedPassword = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(toEncrypt.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedPassword = s.toString();
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("Password Encrypter", "Unable to encrypt password!", e.getCause());
        }
        return encryptedPassword;
    }

    private boolean fieldsAreValid(){
        boolean fieldsAreValid = true;
        if (email.getText().toString().isEmpty()){
            email.setError("Username must not be empty!");
            fieldsAreValid = false;
        }
        if (password.getText().toString().isEmpty()){
            password.setError("Username must not be empty!");
            fieldsAreValid = false;
        }
        if(!password.getText().toString().equals(password2.getText().toString())) {
            password2.setError("Password does not match!");
            fieldsAreValid = false;
        }
        return fieldsAreValid;
    }
}
