package com.example.samanthamorris.warboat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.util.Log;

public class GamePlay extends AppCompatActivity {


    public Grid[] GridManager = new Grid[2];

    public GamePlay(Player One, Player Two)
    {
        //Populate Boats

        // Turn Manager
        int turn = 0;
        boolean gameisOver = true;

        while (!gameisOver)
        {
            GridManager[(turn+1)%2].display();
            GridManager[turn%2].player.attack();

            if (GridManager[(turn+1)%2].isLost())
                gameisOver = false;
            turn++;
        }


    }


    public void updateGridOne()
    {

    }
    public void updateGridTwo()
    {

    }


}
