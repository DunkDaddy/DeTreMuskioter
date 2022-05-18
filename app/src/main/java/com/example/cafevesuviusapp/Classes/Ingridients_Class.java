package com.example.cafevesuviusapp.Classes;

public class Ingridients_Class {

    public int id;
    public String name;
    public int amount_Left;

    public int lowerAmountLeft(int x){
        return amount_Left - x;
    }
    public int increaseAmountLeft(int x){
        return amount_Left + x;
    }
}
