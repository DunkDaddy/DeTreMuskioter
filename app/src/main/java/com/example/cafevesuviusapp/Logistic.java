package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.EditText;
import android.widget.TextView;

public class Logistic extends AppCompatActivity {

    ScrollView Days_Sale, Waiter_Sale;
    EditText TotalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistic);

        Days_Sale = (ScrollView) findViewById(R.id.Scroll_Days_Sale);
        Waiter_Sale = (ScrollView) findViewById(R.id.Scroll_Waiter_Sale);

        TotalPrice = (EditText) findViewById(R.id.TotalPrice);

    }
}