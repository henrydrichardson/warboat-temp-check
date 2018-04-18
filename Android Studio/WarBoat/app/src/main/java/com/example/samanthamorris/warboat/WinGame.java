package com.example.samanthamorris.warboat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class WinGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_won);

        ImageButton back = (ImageButton) findViewById(R.id.won_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(WinGame.this,
                        StartupScreen.class);
                startActivity(myIntent);
            }
        });
    }
}
