package com.example.samanthamorris.warboat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;

public class Grid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_grid);


    }
    private ArrayList<Integer> SHIP_POINTS = new ArrayList<Integer>();
    private ArrayList<Integer> ATTACK_POINTS = new ArrayList<Integer>();


    public Grid(Player Current)
    {

    }
//
//    public boolean isVisible(Player Current)
//    {
//
//    }
//
    public void setAttackPoints()
    {
        //Attack is recorded by listner
        //ATTACK_POINTS.add(buttonIndex)

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
    }

//
//    public boolean isShipVisible(Player Current)
//    {
//
//    }

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
