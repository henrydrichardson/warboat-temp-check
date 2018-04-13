package com.example.samanthamorris.warboat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.util.Log;

public class Settings extends AppCompatActivity {

    private Spinner skinSpinner;

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
    }


}
