package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login_Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
    }

    public void signOut(View view){
        Intent intent = new Intent(Login_Main.this, MainActivity.class);
        startActivity(intent);
    }
}