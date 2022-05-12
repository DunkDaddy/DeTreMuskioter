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

    public void gotoLogistic(View view){
        Intent intent = new Intent(Login_Main.this, Logistic.class);
        startActivity(intent);
    }

    public void gotoAllOrders(View view){
        Intent intent = new Intent(Login_Main.this, AllOrders.class);
        startActivity(intent);
    }

    public void gotoMenu(View view){
        Intent intent = new Intent(Login_Main.this, Menu.class);
        startActivity(intent);
    }

    public void gotoOrder(View view){
        Intent intent = new Intent(Login_Main.this, Order.class);
        startActivity(intent);
    }

    public void gotoReservations(View view){
        Intent intent = new Intent(Login_Main.this, Reservations.class);
        startActivity(intent);
    }

    public void gotoTable(View view){
        Intent intent = new Intent(Login_Main.this, Tables.class);
        startActivity(intent);
    }


}