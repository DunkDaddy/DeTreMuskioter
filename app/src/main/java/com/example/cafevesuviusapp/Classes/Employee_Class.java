package com.example.cafevesuviusapp.Classes;

public class Employee_Class {

    public int id;
    public String name;
    public String password;
    public int rankId;


    public Employee_Class(){}
    public Employee_Class(int id, String name, String password, int rank){
        this.id = id;
        this.name = name;
        this.password = password;
        this.rankId = rank;
    }
}
