package com.example.cafevesuviusapp.Classes;

public class Tables_Class {

    public int id;
    public int customerSize;
    public int placementInt;
    public String placement;

    public Tables_Class(){}
    public Tables_Class(int id, int size, int placement){
        this.id = id;
        this.customerSize = size;
        this.placementInt = placement;
    }
    public Tables_Class(int id, int size, int placementId, String placement){
        this.id = id;
        this.customerSize = size;
        this.placementInt = placementId;
        this.placement = placement;
    }

}
