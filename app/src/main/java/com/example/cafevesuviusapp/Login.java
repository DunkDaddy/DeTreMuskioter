package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cafevesuviusapp.Classes.Employee_Class;
import com.example.cafevesuviusapp.Classes.Rank_Class;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {


    private String employee_url = "http://5.186.68.226:8000/employeegoodluckguessingthis9-list9/?format=json";
    ArrayList<Employee_Class> employeeList = new ArrayList<>();
    ArrayList<Rank_Class> rankList = new ArrayList<>();
    RequestQueue requestQueue;




    public void putIntoLoginList(int id, String name, String password, int rank){
        Employee_Class newLogin = new Employee_Class(id, name, password, rank);
        employeeList.add(newLogin);
    }


    private void getEmployees(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, employee_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String password = jsonObject.getString("password");
                        int rank = jsonObject.getInt("rankId");
                        putIntoLoginList(id, name, password, rank);
                    }
                } catch (Exception w) {
                    Toast.makeText(Login.this, w.getMessage(), Toast.LENGTH_LONG);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);
        EditText UserName = (EditText) findViewById(R.id.Login_UserInp);
        EditText Password = (EditText) findViewById(R.id.LoginPassInp);
        UserName.getText().clear();
        Password.getText().clear();
        getEmployees();
    }

    public void authentication(View view){

        EditText UserName = (EditText) findViewById(R.id.Login_UserInp);
        EditText Password = (EditText) findViewById(R.id.LoginPassInp);
        boolean validator = false;

        String usernameInput = UserName.getText().toString();

        String passwordInput = Password.getText().toString();

        for (int i = 0; i < employeeList.size(); i++){
            if (employeeList.get(i).name.matches(usernameInput) && employeeList.get(i).password.matches(passwordInput)){
                validator = true;
            }

        }
        if (validator == true){
            Intent intent = new Intent(Login.this, Login_Main.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Enter Correct UserName And Password", Toast.LENGTH_SHORT).show();
        }

    }





}