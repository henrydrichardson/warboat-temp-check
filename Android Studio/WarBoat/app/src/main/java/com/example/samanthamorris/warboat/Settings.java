package com.example.samanthamorris.warboat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;

public class Settings extends AppCompatActivity {

    private Spinner skinSpinner;
    private ArrayList<String> spinnerArray = new ArrayList<>();
    public static String selected = new String("Basic");

    private void getItems() {
        final RequestQueue queue = Volley.newRequestQueue(this);
        String getItemsUrl = "http://10.32.224.175:8080/human/get/items?email=" + Login.account.getEmail();
        JsonArrayRequest ItemsRequest = new JsonArrayRequest( getItemsUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject skin = response.getJSONObject(i);
                        spinnerArray.add(skin.get("name").toString());
                    } catch (JSONException e) {

                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                skinSpinner.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        });

        queue.add(ItemsRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        spinnerArray.add("Basic");


        // Home Screen Button
        ImageButton back = (ImageButton) findViewById(R.id.home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Settings.this,
                        StartupScreen.class);
                startActivity(myIntent);
            }
        });

        // Get Purchased Items

        skinSpinner = (Spinner) findViewById(R.id.skinSpin);
        getItems();

        skinSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selected = String.valueOf(skinSpinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }






}
