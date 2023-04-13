package com.github.cptzee.kusinniimang.Authentication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.cptzee.kusinniimang.Dashboard.DashboardActivity;
import com.github.cptzee.kusinniimang.Data.Credential;
import com.github.cptzee.kusinniimang.Data.Helper.CredentialHelper;
import com.github.cptzee.kusinniimang.MainActivity;
import com.github.cptzee.kusinniimang.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        super(R.layout.login_fragment);
    }

    private EditText email, password;
    private TextView signIn;
    private Button loginButton;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        preferences = getActivity().getSharedPreferences("Kusina", MODE_PRIVATE);
        email = view.findViewById(R.id.loginEmail);
        password = view.findViewById(R.id.loginPassword);
        signIn = view.findViewById(R.id.loginRegsiterText);
        loginButton = view.findViewById(R.id.loginButton);
        editor = preferences.edit();
        Credential credential = new Credential();
        credential.setEmail(email.getText().toString());
        credential.setPassword(password.getText().toString());

        signIn.setOnClickListener(v -> {
            if (hasInternet())
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.mainFragmentHolder, RegisterFragment.class, null)
                        .addToBackStack(null)
                        .commit();
            else
                Toast.makeText(getContext(), "An active internet connection is required to register!", Toast.LENGTH_SHORT).show();
        });
        loginButton.setOnClickListener(v -> {
            FirebaseAuth mAuth = ((MainActivity) getActivity()).getmAuth();
            if (hasInternet())
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(getActivity(), task -> {
                            if (task.isSuccessful()) {
                                new syncLocalUserTask().execute(mAuth);
                                launchDashboard();
                            } else
                                launchErrors();
                        });
            else
                new offlineLoginTask().execute(credential);
        });
    }

    private void launchDashboard(){
        startActivity(new Intent(getActivity(), DashboardActivity.class));
        getActivity().finish();
    }

    private void launchErrors(){
        Toast.makeText(getContext(), "Either your email or password is incorrect!", Toast.LENGTH_SHORT).show();
        email.setError("Invalid email!");
        password.setError("Invalid password!");
    }

    private boolean hasInternet() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private class syncLocalUserTask extends AsyncTask<FirebaseAuth, Void, Void> {
        @Override
        protected Void doInBackground(FirebaseAuth... firebaseAuths) {
            FirebaseAuth mAuth = firebaseAuths[0];
            for (Credential cred : CredentialHelper.instance(getContext()).get()) {
                if (cred.getUuid().equals(mAuth.getCurrentUser().getUid())) {
                    editor.putInt("accountID", cred.getAccountID());
                    editor.commit();
                }
            }
            return null;
        }
    }

    private class offlineLoginTask extends AsyncTask<Credential, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Credential... credentials) {
            Credential credential = credentials[0];
            boolean validCredential = false;
            for (Credential cred : CredentialHelper.instance(getContext()).get()) {
                if (cred.getEmail().equals(credential.getEmail()) && cred.getPassword().equals(credential.getPassword())) {
                    editor.putInt("accountID", cred.getAccountID());
                    editor.commit();
                    validCredential = true;
                }
            }
            return validCredential;
        }

        @Override
        protected void onPostExecute(Boolean validCredential) {
            super.onPostExecute(validCredential);
            if(validCredential)
                launchDashboard();
            else
                launchErrors();
        }
    }
}