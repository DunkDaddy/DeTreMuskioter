package com.example.cafevesuviusapp.Classes;

import java.util.List;

public class Order_Class {

    public int id;
    public int table_Id;
    public int waiter_Id;
    public List<Dish_Class> dishes;
    public List<Drink_Class> drinks;

    public void assign_Dish(Dish_Class dish){
        dishes.add(dish);
    }
    public void remove_Dish(Dish_Class dish){
        dishes.remove(dish);
    }
    public void assign_Drink(Drink_Class drink){
        drinks.add(drink);
    }
    public void remove_Drink(Drink_Class drink){
        drinks.remove(drink);
    }
}
