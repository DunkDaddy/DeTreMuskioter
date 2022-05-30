package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.widget.Button;
import android.widget.LinearLayout;
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
    List<MenuItem_Class> orderMenuItems;
    OrderAdapter orderAdapter;
    ListView orderMenu;

    Order_Class newOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        newOrder  = new Order_Class();
        orderMenuItems = new ArrayList<>();
        orderMenu = findViewById(R.id.order_menu);

        requestQueue = Volley.newRequestQueue(this);
        getMenuData();
        orderAdapter = new OrderAdapter(this, R.layout.order_item, orderMenuItems);
        orderMenu.setAdapter(orderAdapter);

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
                        orderMenuItems.add(item);
                    }
                    orderAdapter.notifyDataSetChanged();


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
}