package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cafevesuviusapp.Classes.Customer_Class;
import com.example.cafevesuviusapp.Classes.Tables_Class;
import com.google.android.material.snackbar.Snackbar;

public class Reservations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
    }
    Customer_Class cust = new Customer_Class();
    Tables_Class table = new Tables_Class();


    public void createReservation(View view){
        EditText customerName = findViewById(R.id.Reservation_PersonName);
        EditText customerPhone = findViewById(R.id.Reservation_Phone);
        EditText customerNumbers = findViewById(R.id.Reservation_NumberofPeople);

        Snackbar fail = Snackbar.make(view, "Missing input data", Snackbar.LENGTH_LONG);

        String name = customerName.getText().toString();
        String phone = customerPhone.getText().toString();
        String numberPeople = customerNumbers.getText().toString();

        if (name.matches("") || phone.matches("") || numberPeople.matches("")){
            fail.show();
        }
        else{
            Intent intent = new Intent(Reservations.this, Tables.class);
            intent.putExtra("Name", name);
            intent.putExtra("Phone", phone);
            intent.putExtra("People", numberPeople);
            startActivity(intent);
        }

    }
}