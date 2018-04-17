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

    public int[] AircraftCarrier = new int[5];
    public int[] WarBoat = new int[5];
    public int[] Destroyer = new int[3];
    public int[] Submarine = new int[3];
    public int[] PatrolBoat = new int[2];

    public int[][] Ships = {AircraftCarrier, WarBoat, Destroyer, Submarine, PatrolBoat};

    public void populateShips(int ship, boolean isRotated, int anchor)
    {
        int count = 0;

        if (!isRotated){

            for (int i = 0; i < Ships[ship].length; i++)
            {
                //Player Grid Ship Location and add head+i
                Ships[ship][count] = anchor+i;
                SHIP_POINTS.add(anchor+i);

                count++;
            }
        }

        if (isRotated)
        {
            for (int i = 0; i < Ships[ship].length; i++)
            {
                //Player Grid Ship Location and add head + (i*8)
                Ships[ship][count] = anchor+ (i*8);
                SHIP_POINTS.add(anchor+ (i*8));

                count++;
            }
        }

    }


    public Grid(Player Current)
    {


    }

    public ArrayList<Integer> getSHIP_POINTS() {
        return SHIP_POINTS;
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
