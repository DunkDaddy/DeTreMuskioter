package com.example.cafevesuviusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cafevesuviusapp.Classes.Tables_Class;
import com.google.android.material.snackbar.Snackbar;


public class Tables extends AppCompatActivity {

    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                Snackbar fail = Snackbar.make(view, table, Snackbar.LENGTH_LONG);
                fail.show();
            }
        });

        changeList(1);

        //For dropdown Menu with table locations.
        Spinner tableSpinner = (Spinner) findViewById(R.id.planets_spinner3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TableLocations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tableSpinner.setAdapter(adapter);
        tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) adapter.getItem(position).toString();

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
        String tables1[] = {"Table 1", "Table 3", "Table 4", "Table 5", "Table 10"};

        if (listItem == 1){
            arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tables1);
            listTest.setAdapter(arr);
        }

    }




}