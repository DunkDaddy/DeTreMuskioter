package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.view.View;
import android.widget.RelativeLayout;
import android.os.Bundle;

public class Menu extends AppCompatActivity {

    RelativeLayout appetizers, burger, Sandwich, pasta, salad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        appetizers = findViewById(R.id.Appetizers);
        burger = findViewById(R.id.Burger);
        Sandwich = findViewById(R.id.Sandwich);
        pasta = findViewById(R.id.Pasta);
        salad = findViewById(R.id.Salad);

        //TODO: Need for loop with data from RESTAPI, and then a Switch for each Category of menu items
        View row = getLayoutInflater().inflate(R.layout.menu_item, null);
        appetizers.addView(row);

        TextView dishName = row.findViewById(R.id.Dish_Name);
        dishName.setText("Name");

        TextView dishDescription = row.findViewById(R.id.Dish_Description);
        dishDescription.setText("Description");

        TextView dishPrice = row.findViewById(R.id.Dish_Price);
        dishPrice.setText("price");

    }
}