package com.example.samanthamorris.warboat;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Shop extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        int intScore = 1000;
        String strScore = String.valueOf(intScore);
        TextView scoreView = (TextView) findViewById(R.id.currency);
        scoreView.setText(strScore);

    }

}
