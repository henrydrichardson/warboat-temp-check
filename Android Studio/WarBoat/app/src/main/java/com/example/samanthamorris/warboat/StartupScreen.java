package com.example.samanthamorris.warboat;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class StartupScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_screen);
        Log.d("STARTUP", "Startup screen opened successfully.");

        /**
         * Go back to login activity upon hitting home icon.
         */


        ImageButton settings = (ImageButton) findViewById(R.id.settingsButton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(StartupScreen.this,
                        Settings.class);
                startActivity(myIntent);
            }
        });

        ImageButton shop = (ImageButton) findViewById(R.id.shopButton);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(StartupScreen.this,
                        Shop.class);
                startActivity(myIntent);
            }
        });


        Button singleGame = (Button) findViewById(R.id.singlePlayerButton);
        singleGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(StartupScreen.this,
                        GamePlay.class);
                startActivity(myIntent);
            }
        });


        /**
         * Alignment is funky
         * TODO: adjust constrains of TextViews in activity_startup_screen.xml
         */

        /**
         * Display high score
         * Dummy high score value
         * TODO: set to actual score value
         */

        final RequestQueue queue = Volley.newRequestQueue(this);
        final TextView scoreView = (TextView) findViewById(R.id.displayscore);

        String scoreUrl = "http://10.32.224.175:8080/human/get/score?email=" + Login.account.getEmail();
        StringRequest scoreRequest = new StringRequest(Request.Method.GET, scoreUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                scoreView.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        }
        );

        queue.add(scoreRequest);

        final TextView currView = (TextView) findViewById(R.id.displaycurrency);

        String currUrl = "http://10.32.224.175:8080/human/get/currency?email=" + Login.account.getEmail();
        StringRequest currRequest = new StringRequest(Request.Method.GET, currUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                currView.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        }
        );

        queue.add(currRequest);

    }


}