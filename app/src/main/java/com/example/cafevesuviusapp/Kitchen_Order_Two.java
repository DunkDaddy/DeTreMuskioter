package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cafevesuviusapp.Classes.KitchenOrder_Main_Adapter;
import com.example.cafevesuviusapp.Classes.MenuItem_Class;
import com.example.cafevesuviusapp.Classes.Order_Class;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Kitchen_Order_Two extends AppCompatActivity {

    private final String server_url = "http://5.186.68.226:8000";
    private final String order_url = "/order-list/?format=json";
    private final String orderItem_url = "/orderitems-list/?format=json";
    private final String menuItem_url = "/menuitems-list/?format=json";
    RequestQueue requestQueue;
    List<Order_Class> order1;
    //order2, order3, order4;
    //ListView order1LV, order2LV, order3LV, order4LV;
    //KitchenOrder_Main_Adapter order1_Adapter;
    //order2_Adapter, order3_Adapter, order4_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_order_two);
        order1 = new ArrayList<>();
        /*
        order2 = new ArrayList<>();
        order3 = new ArrayList<>();
        order4 = new ArrayList<>();
        order1LV = findViewById(R.id.order_1_Kitchen);
        order2LV = findViewById(R.id.order_2_Kitchen);
        order3LV = findViewById(R.id.order_3_Kitchen);
        order4LV = findViewById(R.id.order_4_Kitchen);
        */
        requestQueue = Volley.newRequestQueue(this);
        getOrders();


        /*
        order1_Adapter = new KitchenOrder_Main_Adapter(this, R.layout.prepare_order_main, order1);
        order1LV.setAdapter(order1_Adapter);
        order2_Adapter = new KitchenOrder_Main_Adapter(this, R.layout.prepare_order_main, order2);
        order2LV.setAdapter(order2_Adapter);
        order3_Adapter = new KitchenOrder_Main_Adapter(this, R.layout.prepare_order_main, order3);
        order3LV.setAdapter(order3_Adapter);
        order4_Adapter = new KitchenOrder_Main_Adapter(this, R.layout.prepare_order_main, order4);
        order4LV.setAdapter(order4_Adapter);
         */


    }

    private void getOrders() {
        JsonArrayRequest jsonArRequest = new JsonArrayRequest(Request.Method.GET, server_url + order_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jArray = response;
                try {
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jsonObject = jArray.getJSONObject(i);
                        int orderId = jsonObject.getInt("id");
                        int tableId = jsonObject.getInt("tableId");
                        //int statusId = jsonObject.getInt("statusId");

                        Order_Class order = new Order_Class();
                        order.id = orderId;
                        order.table_Id = tableId;
                        getOrderItems(order);

                        AlertDialog.Builder builder = new AlertDialog.Builder(Kitchen_Order_Two.this);

                        builder.setMessage("Order ID = " +order.id);
                        builder.create().show();

                        builder.setMessage("Table ID = " + order.table_Id);
                        builder.create().show();

                        builder.setMessage("menu name = " + order.dishes.get(0).name);
                        builder.create().show();

                        builder.setMessage("menu price = " +  order.dishes.get(0).price);
                        builder.create().show();

                        builder.setMessage("menu ID = " +  order.dishes.get(0).getID());
                        builder.create().show();

                        builder.setMessage("catergoy ID = " +  order.dishes.get(0).category_id);
                        builder.create().show();

                        //order1.add(order);

                        /*
                        switch (statusId)
                        {
                            case 1:
                                order1.add(order);
                                break;
                            case 2:
                                order2.add(order);
                                break;
                            case 3:
                                order3.add(order);
                                break;
                            default:
                                order4.add(order);
                                break;
                        }
                        order1_Adapter.notifyDataSetChanged();
                        order2_Adapter.notifyDataSetChanged();
                        order3_Adapter.notifyDataSetChanged();
                        order4_Adapter.notifyDataSetChanged();
                        */
                    }
                } catch (Exception w) {
                    Toast.makeText(Kitchen_Order_Two.this, w.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Kitchen_Order_Two.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArRequest);
    }
    private void getOrderItems(Order_Class order)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, server_url + orderItem_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int j = 0; j<jsonArray.length(); j++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        int menuItemId = jsonObject.getInt("menuItemsId");
                        int orderId = jsonObject.getInt("orderId");

                        if (orderId == order.id)
                        {
                            getMenuItem(menuItemId, order);
                        }
                    }
                } catch (Exception w) {
                    Toast.makeText(Kitchen_Order_Two.this, w.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Kitchen_Order_Two.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void getMenuItem(int id, Order_Class order)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, server_url + menuItem_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int k = 0; k<jsonArray.length(); k++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(k);
                        String name = jsonObject.getString("name");
                        Double price = jsonObject.getDouble("price");
                        String description = jsonObject.getString("description");
                        int menuId = jsonObject.getInt("id");
                        int categoryId = jsonObject.getInt("categoryId");


                        if (menuId == id)
                        {
                            if (categoryId != 4)
                            {
                                //MenuItem_Class item = new MenuItem_Class(menuId, name, price, description, categoryId);
                                //order.dishes.add(item);
                                insertIntoOrders(menuId, name, price, description, categoryId);

                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(Kitchen_Order_Two.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Kitchen_Order_Two.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void insertIntoOrders(int menuId, String name, double price, String description, int categoryId){
        MenuItem_Class item = new MenuItem_Class(menuId, name, price, description, categoryId);
        order.dishes.add(item);
    }
}