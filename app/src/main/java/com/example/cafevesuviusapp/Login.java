package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cafevesuviusapp.Classes.Location_Class;
import com.example.cafevesuviusapp.Classes.Login_Class;
import com.example.cafevesuviusapp.Classes.Rank_Class;
import com.example.cafevesuviusapp.Classes.Tables_Class;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    String employeeName;
    String employeePassword;
    String rankName;
    int employeeId;
    int rankId;
    private String employee_url = "http://5.186.68.226:8000/employee-list/?format=json";
    private String rank_url = "http://5.186.68.226:8000/rank-list/?format=json";
    ArrayList<Login_Class> employeeList = new ArrayList<>();
    ArrayList<Rank_Class> rankList = new ArrayList<>();

    public void putIntoLoginList(int id, String name, String password){
        Login_Class newLogin = new Login_Class(id, name, password);
        employeeList.add(newLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        Intent intent = new Intent(Login.this, Login_Main.class);
        startActivity(intent);
    }
}