package com.example.cipraaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText UserEmailText = findViewById(R.id.SignInEmailAddress);
        EditText UserPasswordText = findViewById(R.id.SignInPassword);
        Button SignInButton = findViewById(R.id.SignInButton);

        SignInButton.setOnClickListener(v -> {
            String email = String.valueOf(UserEmailText.getText());
            String password = String.valueOf(UserPasswordText.getText());


            NetworkHandler.sendGetRequest(
                NetworkHandler.buildUrl(email, password),
                new NetworkHandler.GetResponseCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Intent intent = new Intent(
                                MainActivity.this,
                                Recommendation.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        MainActivity.this.runOnUiThread(() -> Toast.makeText(
                            MainActivity.this,
                            "Login Failed: No Record Found!",
                            Toast.LENGTH_LONG
                        ).show());
                    }
            });
        });
    }
}