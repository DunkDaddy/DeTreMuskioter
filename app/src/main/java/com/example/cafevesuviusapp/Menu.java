package com.example.cafevesuviusapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cafevesuviusapp.Classes.MenuAdapter;
import com.example.cafevesuviusapp.Classes.MenuItem_Class;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private String server_url = "http://10.0.2.2:8000/menuitems-list/?format=json";
    RequestQueue requestQueue;
    List<MenuItem_Class> burgerMenu, appetizerMenu, sandwichMenu, pastaMenu, saladMenu, drinkMenu;
    MenuAdapter burgerAdapter, appetizersAdapter, sandwichAdapter, pastaAdapter, saladAdapter, drinkAdapter;
    ListView burger, appetizers, Sandwich, pasta, salad, drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        burgerMenu = new ArrayList<>();
        appetizerMenu = new ArrayList<>();
        sandwichMenu = new ArrayList<>();
        pastaMenu = new ArrayList<>();
        saladMenu = new ArrayList<>();
        drinkMenu = new ArrayList<>();
        appetizers = findViewById(R.id.Appetizers);
        burger = findViewById(R.id.Burger);
        Sandwich = findViewById(R.id.Sandwich);
        pasta = findViewById(R.id.Pasta);
        salad = findViewById(R.id.Salad);
        drink = findViewById(R.id.Drinks);

        requestQueue = Volley.newRequestQueue(this);
        getData();
        appetizersAdapter = new MenuAdapter(this, R.layout.menu_item, appetizerMenu);
        burgerAdapter = new MenuAdapter(this, R.layout.menu_item, burgerMenu);
        sandwichAdapter = new MenuAdapter(this, R.layout.menu_item, sandwichMenu);
        pastaAdapter = new MenuAdapter(this, R.layout.menu_item, pastaMenu);
        saladAdapter = new MenuAdapter(this, R.layout.menu_item, saladMenu);
        drinkAdapter = new MenuAdapter(this, R.layout.menu_item, drinkMenu);
        appetizers.setAdapter(appetizersAdapter);
        burger.setAdapter(burgerAdapter);
        Sandwich.setAdapter(sandwichAdapter);
        pasta.setAdapter(pastaAdapter);
        salad.setAdapter(saladAdapter);
        drink.setAdapter(drinkAdapter);
    }
    private void getData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, server_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        Double price = jsonObject.getDouble("price");
                        String description = jsonObject.getString("description");
                        int id = jsonObject.getInt("id");
                        int categoryId = jsonObject.getInt("categoryId");
                        MenuItem_Class item = new MenuItem_Class(id, name, price, description, categoryId);
                        //TODO: instead of switch use data from category Table, and find the right one that we need to use.
                        // /category-list/
                        switch (categoryId)
                        {

                            //Burger
                            case 1:
                                burgerMenu.add(item);
                                break;
                            //Drink
                            case 2:
                                drinkMenu.add(item);
                                break;
                            //Appetizer
                            case 3:
                                appetizerMenu.add(item);
                                break;
                            //Sandwich
                            case 4:
                                sandwichMenu.add(item);
                                break;
                            //Pasta
                            case 5:
                                pastaMenu.add(item);
                                break;
                            //Salad
                            case 6:
                                saladMenu.add(item);
                                break;
                            default:
                                break;
                        }

                    }
                    burgerAdapter.notifyDataSetChanged();
                    drinkAdapter.notifyDataSetChanged();
                    appetizersAdapter.notifyDataSetChanged();
                    sandwichAdapter.notifyDataSetChanged();
                    pastaAdapter.notifyDataSetChanged();
                    saladAdapter.notifyDataSetChanged();
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