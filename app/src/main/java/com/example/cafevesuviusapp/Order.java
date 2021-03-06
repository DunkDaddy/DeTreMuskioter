package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cafevesuviusapp.Classes.MenuItem_Class;
import com.example.cafevesuviusapp.Classes.OrderAdapter;
import com.example.cafevesuviusapp.Classes.Order_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order extends AppCompatActivity {
    private final String server_url = "http://5.186.68.226:8000";
    private final String menuItemUrl = "/menuitems-list/?format=json";
    private final String categoryUrl = "/category-list/?format=json";
    private final String tableUrl = "";
    private final String orderCreateUrl = "/order-create/";
    private final String orderItemAddUrl = "/orderitems-create/";
    private final String orderBarItemAddUrl = "";
    RequestQueue requestQueue;
    List<MenuItem_Class> orderMenuItems;
    OrderAdapter orderAdapter;
    ListView orderMenu;
    Button ordering;
    Order_Class newOrder = new Order_Class();

    String order = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        newOrder.id = 0;
        newOrder.waiter_Id = 1;
        newOrder.table_Id = 6;
        newOrder.status_Id = 1;
        orderMenuItems = new ArrayList<>();
        orderMenu = findViewById(R.id.order_menu);
        ordering = findViewById(R.id.sendOrder);
        AlertDialog.Builder builder = new AlertDialog.Builder(Order.this);
        ordering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newOrder.id == 0)
                {
                    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    postOrder(Integer.toString(newOrder.table_Id), Integer.toString(newOrder.status_Id));
                                    Toast.makeText(Order.this, "Order Sent", Toast.LENGTH_SHORT).show();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    builder.setMessage("Place the order: " + order).setPositiveButton("OK", dialogListener).setNegativeButton("Cancel", dialogListener).show();
                    //builder.setMessage("Do you want to place the order: " + order);

                }
            }
        });
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

    public void addToOrder(View v) {
        int menuItemId = v.getId();
        if (orderMenuItems.get(menuItemId).category_id == 2)
        {
            newOrder.drinks.add(orderMenuItems.get(menuItemId));
        }
        else
        {
            newOrder.dishes.add(orderMenuItems.get(menuItemId));
        }
        order += orderMenuItems.get(menuItemId).name + " - ";
        AlertDialog.Builder builder = new AlertDialog.Builder(Order.this);
        builder.setMessage(order);
        builder.create().show();
    }
    private void postOrder(String tableID, String statusID) {
        StringRequest request = new StringRequest(Request.Method.POST, server_url + orderCreateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Order.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(response);
                    int orderId = respObj.getInt("id");
                    newOrder.id = orderId;
                    for (MenuItem_Class menuItem : newOrder.dishes) {
                        addItemToOrder(Integer.toString(menuItem.getID()), Integer.toString(newOrder.id));
                    }
                    for (MenuItem_Class menuItem : newOrder.drinks) {
                        //addItemToBar(Integer.toString(menuItem.getID()), Integer.toString(newOrder.id));
                        addItemToOrder(Integer.toString(menuItem.getID()), Integer.toString(newOrder.id));
                    }
                } catch (JSONException jE) {
                    jE.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Order.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected  Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tableId", tableID);
                params.put("statusId", statusID);
                return params;
            }
        };
        requestQueue.add(request);
    }
    private void addItemToOrder(String menuItemId, String orderId) {
        StringRequest request = new StringRequest(Request.Method.POST, server_url + orderItemAddUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Order.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(response);
                } catch (JSONException jE) {
                    jE.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Order.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("menuItemsId", menuItemId);
                params.put("orderId", orderId);
                return params;
            }
        };
        requestQueue.add(request);
    }
    private void addItemToBar(String menuItemId, String orderId) {
        StringRequest request = new StringRequest(Request.Method.POST, server_url + orderBarItemAddUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Order.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(response);
                } catch (JSONException jE) {
                    jE.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Order.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("menuItemsId", menuItemId);
                params.put("orderId", orderId);
                return params;
            }

        };
        requestQueue.add(request);
    }

}