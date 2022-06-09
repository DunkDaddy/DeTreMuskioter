package com.example.cafevesuviusapp.Classes;

import java.util.List;

public class MenuItem_Class {
    public int id;
    public String name;
    public Double price;
    public String description;
    public int category_id;

    public int getID(){
        return id;
    }
    public void setId(int newId){
        this.id = newId;
    }


    public MenuItem_Class(int id, String name){
        this.id = id;
        this.name = name;
    }
    public MenuItem_Class(int id, String name, int catId){
        this.id = id;
        this.name = name;
        this.category_id = catId;
    }

    public MenuItem_Class(int id, String name, Double price, String description, int category_id){
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category_id = category_id;
    }

}
