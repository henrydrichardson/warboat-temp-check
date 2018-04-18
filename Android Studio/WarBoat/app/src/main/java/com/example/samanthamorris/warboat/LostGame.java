package com.example.samanthamorris.warboat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

public class LostGame extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_lost);

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
