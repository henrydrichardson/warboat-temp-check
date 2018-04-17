package com.example.samanthamorris.warboat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Button;

import java.util.HashMap;


public class GamePlay extends AppCompatActivity {


    public boolean isShipsPlaced = false;
    public boolean isRotated = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_grid_buttons);



        android.support.v7.widget.GridLayout mainGrid = (android.support.v7.widget.GridLayout) findViewById(R.id.mainGrid);
        int childCount = mainGrid.getChildCount();


        // Listener to calculte if the ship will be rotated.
        final Button rotateButton = (Button) findViewById(R.id.RotateButton);
        rotateButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                isRotated = !isRotated;
                                            }
                                        });



        // Used to map display objects to Grid Class locations
        HashMap<Integer, Integer> displayMap = new HashMap<Integer, Integer>(86);


        //Creates a listener on all Grid button
        for(int i = 0; i < childCount; i++) {
            final Button button = (Button) mainGrid.getChildAt(i);
            displayMap.put(button.getId(),i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ColorDrawable drawableButton = (ColorDrawable) button.getBackground();

                    //Populate
                    if (!isShipsPlaced) {

                    }

                    if (drawableButton.getColor() == Color.RED)
                        button.setBackgroundColor(Color.BLACK);
                    else
                        button.setBackgroundColor(Color.RED);
                }
            });
        }


    }

    public GamePlay() {



    }

    /*

    public Grid[] GridManager = new Grid[2];

    public GamePlay(Player One, Player Two)
    {


//        GridLayout mainGrid = findViewById(R.id.mainGrid);
//        int childCount = mainGrid.getChildrenCount();
//
//        for(int i = 0; i < childCount; i++){
//            RelativeLayout container = (RelativeLayout) mainGrid.getChildAt(i);
//            container.setOnClickListener(new View.OnClickListener(){
//                public void onClick(View view){
//                    // do stuff
//                }
//            });
//        }

        // TODO: Populate Boats

        // Turn Manager
        int turn = 0;
        boolean gameIsOver = true;

        while (!gameIsOver)
        {
            // Display other player's grid
//            GridManager[(turn+1)%2].display();
//            GridManager[turn%2].player.attack();
//
//            // If attack causes a game loss for the other player, the game is over
//            if (GridManager[(turn+1)%2].isLost())
//                gameIsOver = false;
//            turn++;
        }


    }


    public void updateGridOne()
    {

    }
    public void updateGridTwo()
    {

    }
    */


}
