package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cafevesuviusapp.Classes.MenuItem_Class;
import com.example.cafevesuviusapp.Classes.OrderItems_Class;
import com.example.cafevesuviusapp.Classes.Order_Class;
import com.example.cafevesuviusapp.Classes.Status_Class;

import java.util.ArrayList;

public class Kitchen_Order_Two extends AppCompatActivity {


    private String orders_url = "http://5.186.68.226:8000/order-list/?format=json";
    private String menu_url = "http://5.186.68.226:8000/menuitems-list/?format=json";
    private String orderItems_url = "http://5.186.68.226:8000/orderitems-list/?format=json";
    private String status_url = "http://5.186.68.226:8000/status-list/?format=json";

    ArrayList<MenuItem_Class> menuItems = new ArrayList<MenuItem_Class>();
    ArrayList<Order_Class> orders = new ArrayList<Order_Class>();
    ArrayList<OrderItems_Class> orderItems = new ArrayList<>();
    ArrayList<Status_Class> status = new ArrayList<Status_Class>();

    public void putIntoMenuitems(int id, String name){
        MenuItem_Class menuItem = new MenuItem_Class(id, name);
        menuItems.add(menuItem);
    }
    public void putIntoStatus(int id, String name){
        Status_Class newStatus = new Status_Class(id, name);
        status.add(newStatus);
    }
    public void putIntoOrders(int id, int statusId){
        Order_Class order = new Order_Class(id, statusId);
        orders.add(order);
    }
    public void putIntoOrderItems(int menuItemId, int orderItemId){
        OrderItems_Class orderItem = new OrderItems_Class(menuItemId, orderItemId);
        orderItems.add(orderItem);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_order_two);
    }
}