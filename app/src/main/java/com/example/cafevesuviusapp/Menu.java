package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.view.View;
import android.widget.RelativeLayout;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cafevesuviusapp.Classes.MenuItem_Class;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private String server_url = "http://10.0.2.2:8000/menuitems-list/?format=json";
    RequestQueue requestQueue;
    List<MenuItem_Class> menuList = new ArrayList<MenuItem_Class>();
    RelativeLayout appetizers, burger, Sandwich, pasta, salad, drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        appetizers = findViewById(R.id.Appetizers);
        burger = findViewById(R.id.Burger);
        Sandwich = findViewById(R.id.Sandwich);
        pasta = findViewById(R.id.Pasta);
        salad = findViewById(R.id.Salad);
        drink = findViewById(R.id.Drinks);

        requestQueue = Volley.newRequestQueue(this);
        getData();
        for (MenuItem_Class menuItem : menuList) {
            View row = getLayoutInflater().inflate(R.layout.menu_item, null);
            switch (menuItem.category_id)
            {
                //Burger
                case 1:
                    burger.addView(row);
                    break;
                //drink
                case 2:
                    drink.addView(row);
                    break;
                default:
                    break;
            }
            TextView name, description, price;

            name = row.findViewById(R.id.Dish_Name);
            name.setText(menuItem.name);

            description = row.findViewById(R.id.Dish_Description);
            description.setText(menuItem.description);

            price = row.findViewById(R.id.Dish_Price);
            price.setText(menuItem.price.toString());
        }

    }

    private void getData()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, server_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        Double price = jsonObject.getDouble("price");
                        String description = jsonObject.getString("description");
                        int categoryId = jsonObject.getInt("categoryId");
                        menuList.add(new MenuItem_Class(id, name, price, description, categoryId));
                    }
                } catch (Exception w) {
                    Toast.makeText(Menu.this, w.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Menu.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}