package com.example.cafevesuviusapp.Classes;

import java.util.ArrayList;
import java.util.List;

public class Order_Class {

    public int id;
    public int table_Id;
    public int waiter_Id;
    public List<MenuItem_Class> dishes = new ArrayList<>();
    public List<MenuItem_Class> drinks = new ArrayList<>();

    public void assign_Dish(MenuItem_Class dish){
        dishes.add(dish);
    }
    public void remove_Dish(MenuItem_Class dish){
        dishes.remove(dish);
    }
    public void assign_Drink(MenuItem_Class drink){
        drinks.add(drink);
    }
    public void remove_Drink(MenuItem_Class drink){
        drinks.remove(drink);
    }
}
