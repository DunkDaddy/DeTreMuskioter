package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.cafevesuviusapp.Classes.MenuItem_Class;
import com.example.cafevesuviusapp.Classes.OrderItems_Class;
import com.example.cafevesuviusapp.Classes.Order_Class;
import com.example.cafevesuviusapp.Classes.Status_Class;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Kitchen_Order_Two extends AppCompatActivity {

    RequestQueue requestQueue;

    private String orders_url = "http://5.186.68.226:8000/order-list/?format=json";
    private String menu_url = "http://5.186.68.226:8000/menuitems-list/?format=json";
    private String orderItems_url = "http://5.186.68.226:8000/orderitems-list/?format=json";
    private String status_url = "http://5.186.68.226:8000/status-list/?format=json";
    private String category_url = "http://5.186.68.226:8000/category-list/?format=json";

    int drinkId;

    ArrayList<MenuItem_Class> menuItems;
    ArrayList<Order_Class> orders;
    ArrayList<OrderItems_Class> orderItems;
    ArrayList<Status_Class> statusArray;
    List<String> received, preparing, awaiting, done = new ArrayList<String>();

    ArrayAdapter<String> receiveAdapter, workingAdapter, awaitingAdapter;


    private void getMenuItems(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, menu_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        int catId = jsonObject.getInt("categoryId");
                        putIntoMenuitems(id, name, catId);

                    }
                } catch (Exception w) {
                    Toast.makeText(Kitchen_Order_Two.this, w.getMessage(), Toast.LENGTH_LONG);
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
    private void getStatus(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, status_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("statusName");
                        putIntoStatus(id, name);

                    }
                } catch (Exception w) {
                    Toast.makeText(Kitchen_Order_Two.this, w.getMessage(), Toast.LENGTH_LONG);
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
    private void getOrders(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, orders_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        int tableId = jsonObject.getInt("tableId");
                        int statusId = jsonObject.getInt("statusId");
                        putIntoOrders(id, statusId, tableId);

                    }
                } catch (Exception w) {
                    Toast.makeText(Kitchen_Order_Two.this, w.getMessage(), Toast.LENGTH_LONG);
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
    private void getOrderItems(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, orderItems_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int menuItemsId = jsonObject.getInt("menuItemsId");
                        int orderId = jsonObject.getInt("orderId");
                        putIntoOrderItems(menuItemsId, orderId);

                    }
                } catch (Exception w) {
                    Toast.makeText(Kitchen_Order_Two.this, w.getMessage(), Toast.LENGTH_LONG);
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
    private void getCategory(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, category_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        getCategoryId(id, name);

                    }
                } catch (Exception w) {
                    Toast.makeText(Kitchen_Order_Two.this, w.getMessage(), Toast.LENGTH_LONG);
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

    public void getCategoryId(int id, String category){
        if (category.matches("Drinks")){
            drinkId = id;
            Toast.makeText(this, String.valueOf(drinkId), Toast.LENGTH_SHORT).show();
        }
    }
    public void putIntoMenuitems(int id, String name, int catId){
        if (catId != drinkId){
            MenuItem_Class menuItem = new MenuItem_Class(id, name, catId);
            menuItems.add(menuItem);
        }
    }
    public void putIntoStatus(int id, String name){
        Status_Class newStatus = new Status_Class(id, name);
        statusArray.add(newStatus);
    }
    public void putIntoOrders(int id, int statusId, int tableId){
        Order_Class order = new Order_Class(id, statusId, tableId);
        orders.add(order);
    }
    public void putIntoOrderItems(int menuItemId, int orderItemId){
        OrderItems_Class orderItem = new OrderItems_Class(orderItemId, menuItemId);
        orderItems.add(orderItem);
    }

    public void statusLists(){
        String fullOrder = "";
        for (int i = 0; i < orders.size(); i++){
            if (orders.get(i).status_Id == 1){
                fullOrder = addItemToOrder(orders.get(i).id);
                orders.get(i).fullOrder = fullOrder;
                received.add(fullOrder);
            }
            else if(orders.get(i).status_Id == 2){
                fullOrder = addItemToOrder(orders.get(i).id);
                orders.get(i).fullOrder = fullOrder;
                preparing.add(fullOrder);
            }
            else if(orders.get(i).status_Id == 3){
                fullOrder = addItemToOrder(orders.get(i).id);
                orders.get(i).fullOrder = fullOrder;
                awaiting.add(fullOrder);
            }
            else if(orders.get(i).status_Id == 4){
                fullOrder = addItemToOrder(orders.get(i).id);
                orders.get(i).fullOrder = fullOrder;
                done.add(fullOrder);
            }

        }
    }
    public void updateView(){
        ListView awaitingList = (ListView) findViewById(R.id.AwaitingListId);
        ListView workingList = (ListView) findViewById(R.id.WorkingListId);
        ListView receivedList = (ListView) findViewById(R.id.RecievedListId);
        receiveAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, received);
        workingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preparing);
        awaitingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, awaiting);
        awaitingList.setAdapter(awaitingAdapter);
        workingList.setAdapter(workingAdapter);
        receivedList.setAdapter(receiveAdapter);
    }

    public void manualUpdate(View view){
        statusLists();
        updateView();
    }

    public String addItemToOrder(int orderId){
        String items = String.valueOf(orderId) + ":\n";
        int menuItem = 0;
        for (int i = 0; i < orderItems.size(); i++){
            if (orderItems.get(i).order_Id == orderId){
                menuItem = orderItems.get(i).menuItem_Id;
                for (int e = 0; e < menuItems.size(); e++){
                    if (menuItems.get(e).id == menuItem){
                        items = items + menuItems.get(e).name + "\n";
                    }
                }
            }
        }
        return items;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_order_two);
        requestQueue = Volley.newRequestQueue(this);

        orders = new ArrayList<>();
        orderItems = new ArrayList<>();
        menuItems = new ArrayList<>();
        statusArray = new ArrayList<>();
        received = new ArrayList<>();
        preparing = new ArrayList<>();
        awaiting = new ArrayList<>();
        done = new ArrayList<>();


        getCategory();
        getOrders();
        getStatus();
        getOrderItems();
        getMenuItems();

    }


}