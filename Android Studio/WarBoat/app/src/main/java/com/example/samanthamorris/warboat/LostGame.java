package com.example.samanthamorris.warboat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LostGame extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_lost);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final TextView scoreView = (TextView) findViewById(R.id.currency);

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

        ImageButton back = (ImageButton) findViewById(R.id.lost_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LostGame.this,
                        StartupScreen.class);
                startActivity(myIntent);
            }
        });
    }
}
