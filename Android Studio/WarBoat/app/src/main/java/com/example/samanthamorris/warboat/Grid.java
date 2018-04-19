package com.example.samanthamorris.warboat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Grid extends AppCompatActivity {


    private ArrayList<Integer> SHIP_POINTS = new ArrayList<Integer>();
    private ArrayList<Integer> ATTACK_POINTS = new ArrayList<Integer>();
    private ArrayList<Integer> SUNK_POINTS = new ArrayList<Integer>();

    public int[] AircraftCarrier = new int[5];
    public int[] WarBoat = new int[4];
    public int[] Destroyer = new int[3];
    public int[] Submarine = new int[3];
    public int[] PatrolBoat = new int[2];

    public int[][] Ships = {AircraftCarrier, WarBoat, Destroyer, Submarine, PatrolBoat};

    // Adds ships wit their correct points
    public boolean populateShips(int ship, boolean isRotated, int anchor)
    {
        int count = 0;

        // Horizontal
        if (!isRotated){

            if ((anchor%8)+ Ships[ship].length > 8)
            {
                return false;
            }
            // Conflict Manager
            for (int i = 0; i < Ships[ship].length; i++)
            {
                if (SHIP_POINTS.contains(anchor+i))
                {
                    return false;
                }
            }

            for (int i = 0; i < Ships[ship].length; i++)
            {
                //Player Grid Ship Location and add head+i
                Ships[ship][count] = anchor+i;
                SHIP_POINTS.add(anchor+i);

                count++;
            }
        }

        // Vertical
        if (isRotated)
        {
            if (anchor + (Ships[ship].length - 1)*8 >= 64)
            {
                return false;
            }
            // Conflict Manager
            for (int i = 0; i < Ships[ship].length; i++)
            {
                if (SHIP_POINTS.contains(anchor+(i*8)))
                {
                    return false;
                }
            }

            for (int i = 0; i < Ships[ship].length; i++)
            {
                //Player Grid Ship Location and add head + (i*8)
                Ships[ship][count] = anchor+ (i*8);
                SHIP_POINTS.add(anchor+ (i*8));

                count++;
            }
        }

        // Successful ship placement
        return true;

    }


    public Grid()
    {


    }

    // Returns SHIP_POINTS ArrayList
    public ArrayList<Integer> getSHIP_POINTS() {
        return SHIP_POINTS;
    }

    // Checks if ships are visible
    public boolean isVisible(Player Current)
    {
        // TODO
        return true;
    }

    // Keeps track of Attack Points
    public boolean setAttackPoint(int index)
    {
        // Checks if attack point has already been chosen
        if (ATTACK_POINTS.contains(index))
        {
            return false;
        }

        // If valid attack point
        else{
            ATTACK_POINTS.add(index);
            return true;
        }

    }

    // Returning all the Attack blocks on the grid
    public ArrayList<Integer> getAttackPoints()
    {

        return ATTACK_POINTS;
    }

    //Returning Most Recent Attack Point
    public int getLastAttack()
    {
        if (ATTACK_POINTS != null && !ATTACK_POINTS.isEmpty()) {
            return ATTACK_POINTS.get(ATTACK_POINTS.size()-1);
        }

        return -1;
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

    // Checks if a ship has been sunk
    public void setSunk(int ShipCoordinates)
    {
        boolean isSunk = true;
        for (int i = 0; i < Ships[ShipCoordinates].length; i++)
        {
            if (!(ATTACK_POINTS.contains(Ships[ShipCoordinates][i])) || SUNK_POINTS.contains(Ships[ShipCoordinates][i]))
            {
                isSunk = false;
            }
        }
        if (isSunk)
        {
            for (int j = 0; j < Ships[ShipCoordinates].length; j++) {

                SUNK_POINTS.add(Ships[ShipCoordinates][j]);
            }
        }

    }

    public ArrayList<Integer> getSunkPoints() {
        return SUNK_POINTS;
    }
}
