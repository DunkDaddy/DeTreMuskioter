package com.example.cafevesuviusapp.Classes;

import java.util.ArrayList;
import java.util.List;

public class Order_Class {

    public int id;
    public int table_Id;
    public int waiter_Id;
    public int status_Id;
    public List<MenuItem_Class> dishes = new ArrayList<>();
    public List<MenuItem_Class> drinks = new ArrayList<>();

    public Order_Class(){}

    public Order_Class (int id, int status_Id){
        this.id = id;
        this.status_Id = status_Id;
    }
}
