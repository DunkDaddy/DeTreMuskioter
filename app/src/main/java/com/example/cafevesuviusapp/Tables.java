package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.Volley;
import com.example.cafevesuviusapp.Classes.Location_Class;
import com.example.cafevesuviusapp.Classes.Tables_Class;
import com.google.android.material.snackbar.Snackbar;


public class Tables extends AppCompatActivity {


    String number;
    int variable;
    private String table_url = "http://10.0.2.2:8000/menuitems-list/?format=json";
    private String locations_url = "http://10.0.2.2:8000/location-list/?format=json";
    ArrayList<String> locations = new ArrayList<>();
    RequestQueue requestQueue;
    String[] s;
    ArrayList<Location_Class> locationList = new ArrayList<>();
    ArrayList<Tables_Class> tableList = new ArrayList<>();
    ArrayAdapter<String> Locationadapter;


    public void putIntoTableList(int id, int size, int placement){
        Tables_Class newTable = new Tables_Class(id, size, placement);
        tableList.add(newTable);
    }
    public void putIntoLocationList(int id, String name){
        Location_Class newLocation = new Location_Class(id, name);
        locationList.add(newLocation);
        locations.add(name);
    }
    public void workaround(String a){
        number = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(this);
        getLocationData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        number = getIntent().getStringExtra("People");
        TextView test = findViewById(R.id.TableTest);
        if (number.matches("")){
            test.setText("");
        }
        else{
            test.setText("Number of People: " + number);
        }



        //Code for reacting to clicked Item in Listview
        ListView lv = (ListView) findViewById(R.id.LisviewTest);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String table = lv.getItemAtPosition(position).toString();
                if (number.matches("")){
                    moveTable("Want to move " + table);
                }
            }
        });

        //Set Viewlist on load.
        changeList(1);

        //For dropdown Menu with table locations.
        Spinner tableSpinner = (Spinner) findViewById(R.id.planets_spinner3);

        Locationadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        Locationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tableSpinner.setAdapter(Locationadapter);
        //TODO function to retrieve locations
        tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) Locationadapter.getItem(position).toString();

                if (selectedItem.matches("Bygning 1")){
                     changeList(1);
                }
                else if(selectedItem.matches("Udendørs")){
                    changeList(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //Method for showing tables at specific location.
    public void changeList(int listItem){

        ListView listTest = (ListView) findViewById(R.id.LisviewTest);
        ArrayAdapter<String> arr;
        //TODO code for retrieving table from Database
        String[] S = {"new", "pew"};
        arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, S);
        listTest.setAdapter(arr);

    }

    public void moveTable(String text){
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            //TODO Code to retrive locations from Database
            String[] s = {"Bygning 1", "Bygning 2", "Udendørs"};
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        finalMoveTable(s, text);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //If no is pressed
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text).setPositiveButton("Yes", dialogListener).setNegativeButton("No", dialogListener).show();
    }

    //Creates a popup when you've chosen a table to move
    public void finalMoveTable(String[] locations, String table){

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(Tables.this,
                android.R.layout.simple_spinner_item, locations);
        final Spinner sp = new Spinner(Tables.this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) adp.getItem(position).toString();
                workaround(selectedItem);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO code changing a tables location
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Tables.this);
        builder.setMessage("Where do you want to move table " + table);
        builder.setPositiveButton("Ok",dialogListener );
        builder.setView(sp);
        builder.create().show();


    }

    //TODO--------------------------------------------
    private void getLocationData(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, locations_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        putIntoLocationList(id, name);
                    }
                } catch (Exception w) {
                    Toast.makeText(Tables.this, w.getMessage(), Toast.LENGTH_LONG);
                }
                Locationadapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Tables.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getTableData(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, table_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        int size = jsonObject.getInt("size");
                        int locationId = jsonObject.getInt("locationId_id");
                        putIntoTableList(id, size, locationId);
                    }
                } catch (Exception w) {
                    Toast.makeText(Tables.this, w.getMessage(), Toast.LENGTH_LONG);
                }
                Locationadapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Tables.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }





}