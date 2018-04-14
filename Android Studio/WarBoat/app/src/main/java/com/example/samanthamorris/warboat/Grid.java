package com.example.samanthamorris.warboat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Grid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_grid);


    }
    private ArrayList<Integer> SHIP_POINTS = new ArrayList<Integer>();
    private ArrayList<Integer> ATTACK_POINTS = new ArrayList<Integer>();

    public int[] ButtonIds = {R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8,
                                R.id.b9, R.id.b10, R.id.b11, R.id.b12, R.id.b13, R.id.b14, R.id.b15, R.id.b16,
                                R.id.b17, R.id.b18, R.id.b19, R.id.b20, R.id.b21, R.id.b22, R.id.b23, R.id.b24,
                                R.id.b25, R.id.b26, R.id.b27, R.id.b28, R.id.b29, R.id.b30, R.id.b31, R.id.b32,
                                R.id.b33, R.id.b34, R.id.b35, R.id.b36, R.id.b37, R.id.b38, R.id.b39, R.id.b40,
                                R.id.b41, R.id.b42, R.id.b43, R.id.b44, R.id.b45, R.id.b46, R.id.b47, R.id.b48,
                                R.id.b49, R.id.b50, R.id.b51, R.id.b52, R.id.b53, R.id.b54, R.id.b55, R.id.b56,
                                R.id.b57, R.id.b58, R.id.b59, R.id.b60, R.id.b61, R.id.b62, R.id.b63, R.id.b64
                                };
    public Button[] GridButtons = new Button[ButtonIds.length+1];



    public Grid(Player Current)
    {
        for (int i = 0; i <= ButtonIds.length; i++) {
            GridButtons[i + 1] = (Button) findViewById(ButtonIds[i]);

            GridButtons[i + 1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    public boolean isVisible(Player Current)
    {
        // TODO
        return true;
    }

    public void setAttackPoints(int buttonIndex)
    {
        //Attack is recorded by listner
        //ATTACK_POINTS.add(buttonIndex)

    }

    // Returning all the Attack blocks on the grid
    public ArrayList<Integer> getAttackPoints()
    {

        return ATTACK_POINTS;
    }

    //Finding all the hit blocks on the grid
    public ArrayList<Integer> getHitPoints()
    {
        ArrayList<Integer> Hits = new ArrayList<Integer>();

        // Populating the Hits array
        for (int i = 0; i < SHIP_POINTS.size(); i++)
        {
            if (ATTACK_POINTS.contains(SHIP_POINTS.get(i)))
            {
                Hits.add(SHIP_POINTS.get(i));
            }
        }
        return Hits;
    }

    //Finding all the hit blocks on the grid
    public ArrayList<Integer> getMissPoints()
    {
        ArrayList<Integer> Misses = new ArrayList<Integer>();

            // Populating the Misses array
            for (int i = 0; i < ATTACK_POINTS.size(); i++)
            {
                if (!(SHIP_POINTS.contains(ATTACK_POINTS.get(i))))
                {
                    Misses.add(ATTACK_POINTS.get(i));
                }
            }
            return Misses;

    }


    public boolean isShipVisible(Player Current)
    {
        // TODO
        return true;
    }

    // Checks if the current Grid is lost
    public boolean isGameLost()
    {
        // Checking if every value in the Ship Points array is also in the attack array
        for (int i = 0; i < SHIP_POINTS.size(); i++)
        {
            if (!(ATTACK_POINTS.contains(SHIP_POINTS.get(i))))
            {
                return false;
            }
        }
        return true;
    }


}
