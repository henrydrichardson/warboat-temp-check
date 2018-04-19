package com.example.samanthamorris.warboat;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    private Spinner skinSpinner;
    private ArrayList<String> itemName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageButton back = (ImageButton) findViewById(R.id.home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Settings.this,
                        StartupScreen.class);
                startActivity(myIntent);
            }
        });

        skinSpinner = (Spinner) findViewById(R.id.skinSpin);

        String s = String.valueOf(skinSpinner.getSelectedItem());

        Log.d("SKIN", s);




        String url = "http://10.32.224.175:8080/item";
        final RequestQueue queue = Volley.newRequestQueue(this);

       /* JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<ArrayList<Skin>>() {

            @Override
            public void onResponse(ArrayList<Skin> response) {

                //TODO
                for(int i =0; i < response.size(); i++)
                {
                    itemName.add(response.get(i).getName());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        }
        );*/

        //queue.add(stringRequest);


    }






}
