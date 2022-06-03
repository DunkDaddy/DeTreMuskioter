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
import com.android.volley.toolbox.StringRequest;
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

    ArrayList<MenuItem_Class> menuItems;
    ArrayList<Order_Class> orders;
    ArrayList<OrderItems_Class> orderItems;
    ArrayList<Status_Class> statusArray;
    List<String> received, preparing, awaiting, done = new ArrayList<String>();

    ArrayAdapter<String> receiveAdapter, workingAdapter, awaitingAdapter;




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

        getMenuItems();
        getStatus();
        getOrders();
        getOrderItems();

        ListView receivedView = (ListView) findViewById(R.id.recievedListId);
        ListView doingView = (ListView) findViewById(R.id.workingListId);
        ListView awaitingView = (ListView) findViewById(R.id.awaitingListId);
        receivedView.setClickable(true);
        doingView.setClickable(true);
        awaitingView.setClickable(true);
        receivedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String order = receivedView.getItemAtPosition(position).toString();
                int orderId = findOrder(order);
                String currentStatus = "";
                for (Order_Class item : orders)
                {
                    if (item.id == orderId)
                    {
                        currentStatus = statusArray.get(item.status_Id).statusName;
                    }
                }
                //String currentStatus = statusArray.get(orders.get(orderId).status_Id).statusName;
                String Test = "Test";
                nextStatus(order, orderId, currentStatus);
            }
        });
        doingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String order = doingView.getItemAtPosition(position).toString();
                int orderId = findOrder(order);
                nextStatus(order, orderId, statusArray.get(orders.get(orderId).status_Id).statusName);
            }
        });
        awaitingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String order = doingView.getItemAtPosition(position).toString();
                int orderId = findOrder(order);
                nextStatus(order, orderId, statusArray.get(orders.get(orderId).status_Id).statusName);
            }
        });

    }

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
                        putIntoMenuitems(id, name);

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
                        putIntoOrders(id, statusId);

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

    public void putIntoMenuitems(int id, String name){
        MenuItem_Class menuItem = new MenuItem_Class(id, name);
        menuItems.add(menuItem);
    }
    public void putIntoStatus(int id, String name){
        Status_Class newStatus = new Status_Class(id, name);
        statusArray.add(newStatus);
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }
    public void putIntoOrders(int id, int statusId){
        Order_Class order = new Order_Class(id, statusId);
        orders.add(order);
    }
    public void putIntoOrderItems(int menuItemId, int orderItemId){
        OrderItems_Class orderItem = new OrderItems_Class(orderItemId, menuItemId);
        orderItems.add(orderItem);
    }

    public void clearStatusLists(){
        received.clear();
        preparing.clear();
        awaiting.clear();
        done.clear();
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

    public void nextStatus(String fullOrder, int orderId, String status){
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(fullOrder).setPositiveButton("Move order to " + status + "?", dialogListener).setNegativeButton("Cancel", dialogListener).show();

    }

    public void updateView(){
        ListView awaitingList = (ListView) findViewById(R.id.awaitingListId);
        ListView workingList = (ListView) findViewById(R.id.workingListId);
        ListView receivedList = (ListView) findViewById(R.id.recievedListId);
        receiveAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, received);
        workingAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, preparing);
        awaitingAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, done);
        awaitingList.setAdapter(awaitingAdapter);
        workingList.setAdapter(workingAdapter);
        receivedList.setAdapter(receiveAdapter);
    }

    public String addItemToOrder(int orderId){
        String items = String.valueOf(orderId) + ": ";
        int menuItem = 0;
        for (int i = 0; i < orderItems.size(); i++){
            if (orderItems.get(i).order_Id == orderId){
                menuItem = orderItems.get(i).menuItem_Id;
                items = items + menuItems.get(menuItem).name + " - ";
            }
        }
        //orders.get(orderId).fullOrder = items;
        return items;

    }
    public int findOrder(String fullOrder){
        int orderId = 0;
        for (int i = 0; i < orders.size(); i++){
            if (orders.get(i).fullOrder.matches(fullOrder)){
                orderId = orders.get(i).id;
            }
        }
        return orderId;
    }

    public void manualViewUpdate(View view){
        statusLists();
        updateView();
    }

}