package com.example.volunteerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.volunteerapp.model.User;
import com.example.volunteerapp.preferences.UserPreferences;
import com.example.volunteerapp.volonter.VolonterMainActivity;
import com.parse.ParseUser;

public class SignInActivity extends AppCompatActivity {

    Button signUpButton, signInButton;
    TextView signUpText;
    ProgressDialog progressDialog;
    EditText login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        UserPreferences ePref = new UserPreferences(this);
        if(getSupportActionBar() != null) { getSupportActionBar().setElevation(0); }
        if(ePref.getEntered()) {
            Intent intent = new Intent(SignInActivity.this, VolonterMainActivity.class);
            startActivity(intent);
            finish();
        }
        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);

        signUpText = findViewById(R.id.signUpText);
        signInButton = findViewById(R.id.signInButton);

        signUpText.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        signInButton.setOnClickListener(view -> signIn());
    }

    private void signIn() { 
        if(!login.getText().toString().equals("") || !password.getText().toString().equals("")){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Загрузка");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ParseUser.logInInBackground(login.getText().toString(), password.getText().toString(), (parseUser, e) -> {
                progressDialog.dismiss();
                if (parseUser != null) {
                    User user = new User();
                    user.setLogin(parseUser.getUsername());
                    user.setLastname(parseUser.getString("lastname"));
                    user.setFirstname(parseUser.getString("firstname"));
                    user.setPatronymic(parseUser.getString("patronymic"));
                    user.setPhone(parseUser.getString("phone"));
                    user.setPost(parseUser.getString("post"));
                    user.setPoints(parseUser.getString("scores"));
                    UserPreferences uPref = new UserPreferences(this);
                    uPref.setEntered(true);
                    uPref.setUser(user);
                    Intent intent = new Intent(SignInActivity.this, VolonterMainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ParseUser.logOut();
                    Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(SignInActivity.this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
        }

    }
}
