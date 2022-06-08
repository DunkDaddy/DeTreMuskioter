package com.example.cafevesuviusapp.Classes;

public class OrderItems_Class {
    public int order_Id;
    public int menuItem_Id;

    public OrderItems_Class(){}

    public OrderItems_Class(int order_Id, int menuItem_Id){
        this.menuItem_Id = menuItem_Id;
        this.order_Id = order_Id;
    }
}
