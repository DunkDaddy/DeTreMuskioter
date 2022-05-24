package com.example.cafevesuviusapp.Classes;

import java.util.List;

public class MenuItem_Class {
    private int id;
    public String name;
    public Double price;
    public String description;
    public int category_id;
    public List<Ingridients_Class> dish_Ingredients;
    public List<Ingridients_Class> dish_Extra_Ingredients;

    public int getID(){
        return id;
    }
    public void setId(int newId){
        this.id = newId;
    }

    public void addIngredient(Ingridients_Class ingredient) {
        dish_Extra_Ingredients.add(ingredient);
    }
    public void removeIngredient(Ingridients_Class ingredient){
        dish_Ingredients.remove(ingredient);
    }

    public MenuItem_Class(int id, String name, Double price, String description, int category_id){
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category_id = category_id;
    }

}
