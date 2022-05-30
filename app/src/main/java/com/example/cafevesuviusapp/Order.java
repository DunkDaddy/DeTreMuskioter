package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
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
import com.example.cafevesuviusapp.Classes.OrderAdapter;
import com.example.cafevesuviusapp.Classes.Order_Class;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Order extends AppCompatActivity {
    private String server_url = "http://10.0.2.2:8000";
    private String menuItemUrl = "/menuitems-list/?format=json";
    private String categoryUrl = "/category-list/?format=json";
    private String waiterUrl = "";
    private String tableUrl = "";
    private String orderUrl = "";

    RequestQueue requestQueue;
    List<MenuItem_Class> burgerMenu, appetizerMenu, sandwichMenu, pastaMenu, saladMenu, drinkMenu;
    OrderAdapter burgerAdapter, appetizersAdapter, sandwichAdapter, pastaAdapter, saladAdapter, drinkAdapter;
    ListView burger, appetizers, Sandwich, pasta, salad, drink;

    Order_Class newOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        newOrder  = new Order_Class();
        burgerMenu = new ArrayList<>();
        appetizerMenu = new ArrayList<>();
        sandwichMenu = new ArrayList<>();
        pastaMenu = new ArrayList<>();
        saladMenu = new ArrayList<>();
        drinkMenu = new ArrayList<>();
        appetizers = findViewById(R.id.order_menu);
        burger = findViewById(R.id.order_menu);
        Sandwich = findViewById(R.id.order_menu);
        pasta = findViewById(R.id.order_menu);
        salad = findViewById(R.id.order_menu);
        drink = findViewById(R.id.order_menu);

        requestQueue = Volley.newRequestQueue(this);
        getMenuData();
        //Appetizers
        appetizersAdapter = new OrderAdapter(this, R.layout.order_item, appetizerMenu);
        appetizers.setAdapter(appetizersAdapter);
        //Burger
        burgerAdapter = new OrderAdapter(this, R.layout.order_item, burgerMenu);
        burger.setAdapter(burgerAdapter);
        //Sandwich
        sandwichAdapter = new OrderAdapter(this, R.layout.order_item, sandwichMenu);
        Sandwich.setAdapter(sandwichAdapter);
        //Pasta
        pastaAdapter = new OrderAdapter(this, R.layout.order_item, pastaMenu);
        pasta.setAdapter(pastaAdapter);
        //Salad
        saladAdapter = new OrderAdapter(this, R.layout.order_item, saladMenu);
        salad.setAdapter(saladAdapter);
        //Drink
        drinkAdapter = new OrderAdapter(this, R.layout.order_item, drinkMenu);
        drink.setAdapter(drinkAdapter);
    }

    private void getMenuData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, server_url+menuItemUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        Double price = jsonObject.getDouble("price");
                        String description = jsonObject.getString("description");
                        int id = jsonObject.getInt("id");
                        int categoryId = jsonObject.getInt("categoryId");
                        MenuItem_Class item = new MenuItem_Class(id, name, price, description, categoryId);

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


                } catch (Exception w) {
                    Toast.makeText(Order.this, w.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Order.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void onClick(View view) {
        if (appetizersAdapter.menuItemsList.get(1).category_id == 2)
        {
            newOrder.assign_Drink(new MenuItem_Class(appetizersAdapter.menuItemsList.get(1).getID(), menuItem.name, menuItem.price, menuItem.description, menuItem.category_id));
        }
        else {
            newOrder.assign_Dish(new MenuItem_Class(menuItem.getID(), menuItem.name, menuItem.price, menuItem.description, menuItem.category_id));
        }
    }
}