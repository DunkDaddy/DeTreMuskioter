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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.toolbox.Volley;
import com.example.cafevesuviusapp.Classes.Location_Class;
import com.example.cafevesuviusapp.Classes.Tables_Class;
import com.google.android.material.snackbar.Snackbar;


public class Tables extends AppCompatActivity {


    String number;
    String location;
    int tableId;
    int tableSize;
    private String table_url = "http://5.186.68.226:8000/tables-list/?format=json";
    private String locations_url = "http://5.186.68.226:8000/location-list/?format=json";
    private String tableUpdate_url = "http://5.186.68.226:8000/tables-update/";
    ArrayList<String> locations = new ArrayList<>();
    RequestQueue requestQueue;
    ArrayList<String> tables = new ArrayList<>();
    ArrayList<Location_Class> locationList = new ArrayList<>();
    ArrayList<Tables_Class> tableList = new ArrayList<>();
    ArrayAdapter<String> Locationadapter, tableAdapter;


    public void editTableList(int id, int locationId){
        int x = 0;
        for (int i = 0; i < tableList.size(); i++){
            if (tableList.get(i).id == id){
                x = tableList.get(i).placementInt;
                tableList.get(i).placementInt = locationId;
            }
        }
        changeList(x);
    }
    public void putIntoTableList(int id, int size, int placement){
        Tables_Class newTable = new Tables_Class(id, size, placement, String.valueOf(id) + " - Size: " + String.valueOf(size));
        tableList.add(newTable);
    }
    public void putIntoLocationList(int id, String name){
        Location_Class newLocation = new Location_Class(id, name);
        locationList.add(newLocation);
        locations.add(name);
    }
    public void workaround(String a){
        location = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(this);
        getLocationData();
        getTables();
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
                for (int i = 0; i < tableList.size(); i++){
                    if (table.matches(tableList.get(i).placement)){
                        tableId = tableList.get(i).id;
                        tableSize = tableList.get(i).customerSize;
                    }
                }
                if (number.matches("")){
                    moveTable(table, tableId, tableSize);
                }
            }
        });
        
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
                for (int i = 0; i < locationList.size(); i++){
                    if (selectedItem.matches(locationList.get(i).placement)){
                        changeList(locationList.get(i).id);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //Method for showing tables at specific location.
    public void changeList(int location){
        ListView listTest = (ListView) findViewById(R.id.LisviewTest);
        tables.clear();
        for (int i = 0; i < tableList.size(); i++){
            if (location == tableList.get(i).placementInt){
                tables.add(String.valueOf(tableList.get(i).id) + " - Size: " + String.valueOf(tableList.get(i).customerSize));
                //tableList.get(i).placement = String.valueOf(tableList.get(i).id) + " - Size: " + String.valueOf(tableList.get(i).customerSize);
            }
        }
        tableAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tables);
        listTest.setAdapter(tableAdapter);

    }

    public void moveTable(String text, int id, int size){
        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        finalMoveTable(text, id, size);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //If no is pressed
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("What do you want do do with table " + text).setPositiveButton("Move the Table", dialogListener).setNegativeButton("Delete the Table", dialogListener).show();
    }

    //Creates a popup when you've chosen a table to move
    public void finalMoveTable (String table, int id, int size){

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
                for (int i = 0; i < locationList.size(); i++){
                    if (location.matches(locationList.get(i).placement)){
                        updateTable(id, locationList.get(i).id, size );
                    }
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Tables.this);
        builder.setMessage("Where do you want to move table " + table);
        builder.setPositiveButton("Ok",dialogListener );
        builder.setView(sp);
        builder.create().show();


    }

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

    private void getTables(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, table_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        int size = jsonObject.getInt("size");
                        int location = jsonObject.getInt("locationId");
                        putIntoTableList(id, size, location);
                    }
                } catch (Exception w) {
                    Toast.makeText(Tables.this, w.getMessage(), Toast.LENGTH_LONG);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Tables.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void updateTable(int id, int locationId, int size) {
        StringRequest request = new StringRequest(Request.Method.POST, tableUpdate_url + id + "/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Tables.this, "Data added to API", Toast.LENGTH_SHORT).show();
                editTableList(id, locationId);
                try {
                    JSONObject respObj = new JSONObject(response);
                } catch (JSONException jE) {
                    jE.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Tables.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("size", String.valueOf(size));
                params.put("locationId", String.valueOf(locationId));
                return params;
            }
        };
        requestQueue.add(request);
    }





}