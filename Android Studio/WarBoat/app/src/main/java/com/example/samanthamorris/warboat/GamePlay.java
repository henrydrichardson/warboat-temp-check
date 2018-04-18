package com.example.samanthamorris.warboat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebHistoryItem;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Button;
import android.os.SystemClock;

import java.util.HashMap;
import java.util.Random;
import java.lang.Math;

public class GamePlay extends AppCompatActivity {


    public boolean isShipsPlaced = false;
    public boolean isRotated = false;
    public Grid[] GridManager;
    public HashMap<Integer, Integer> displayMap;
    int shipTracker;
    int turn;

    private void redrawGrid(boolean displayShips, int whichGrid) {
        android.support.v7.widget.GridLayout mainGrid = (android.support.v7.widget.GridLayout) findViewById(R.id.mainGrid);
        int childCount = mainGrid.getChildCount();

        //Checks and sets which ships are sunk
        for(int i = 0; i < 4; i++)
        {
            GridManager[whichGrid].setSunk(i);
        }

        for( int j = 0; j < childCount; j++) {
            final Button button = (Button) mainGrid.getChildAt(j);
            if(GridManager[whichGrid].getSunkPoints().contains(displayMap.get(button.getId()))) {
                button.setBackgroundColor(Color.GREEN);
            } else if (GridManager[whichGrid].getAttackPoints().contains(displayMap.get(button.getId())) && GridManager[whichGrid].getSHIP_POINTS().contains(displayMap.get(button.getId()))){
                button.setBackgroundColor(Color.RED);
            } else if (GridManager[whichGrid].getAttackPoints().contains(displayMap.get(button.getId()))) {
                button.setBackgroundColor(Color.GRAY);
            } else if (GridManager[whichGrid].getSHIP_POINTS().contains(displayMap.get(button.getId())) && displayShips) {
                button.setBackgroundColor(Color.BLACK);
            } else {
                button.setBackgroundColor(Color.TRANSPARENT);
            }
        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_grid_buttons);
        GridManager = new Grid[2];
        displayMap = new HashMap<>(86);
        this.shipTracker = 0;
        GridManager[0] = new Grid();
        GridManager[1] = new Grid();


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


        // Generate AI Grid
        final Random random = new Random();
        while(true) {
            if (GridManager[1].populateShips(shipTracker, random.nextBoolean(), Math.abs(random.nextInt()%64))) {
                shipTracker++;
                if (shipTracker == 5) {
                    break;
                }
            }

        }

        shipTracker = 0;
        turn = 0;
        redrawGrid(true, 0);
        final Handler handler = new Handler();
        //Creates a listener on all Grid button
        for(int i = 0; i < childCount; i++) {
            final Button button = (Button) mainGrid.getChildAt(i);
            displayMap.put(button.getId(),i);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (turn%2 == 0) {
                        //Populate
                        if (!isShipsPlaced) {
                            if (GridManager[0].populateShips(shipTracker, isRotated, displayMap.get(button.getId()))) {
                                shipTracker++;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        redrawGrid( true, 0);
                                    }
                                }, 0);
                                if (shipTracker == 5) {
                                    isShipsPlaced = true;
                                  //  SystemClock.sleep(7000);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            redrawGrid(false,1);
                                        }
                                    }, 1000);

                                }
                            }
                        } else {
                            if (GridManager[1].setAttackPoint(displayMap.get(button.getId()))) {
                                turn++;

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        redrawGrid(true,0);
                                    }
                                }, 0);
                             //   SystemClock.sleep(7000);
                                int aiAttackLocation = Math.abs(random.nextInt()%64);
                                boolean previousHit = false;
                                while (!GridManager[0].setAttackPoint(aiAttackLocation)) {
                                        aiAttackLocation = Math.abs(random.nextInt()%64);
                                    }
                                if (GridManager[0].getSHIP_POINTS().contains(aiAttackLocation))
                                    previousHit = true;
                           //     SystemClock.sleep(7000);

                                if (GridManager[(turn+1)%2].isGameLost())
                                {
                                    Intent myIntent = new Intent(GamePlay.this,
                                            LostGame.class);
                                    startActivity(myIntent);
                                }

                                turn++;

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        redrawGrid(false,1);
                                    }
                                }, 10000);
                            }
                        }



                    } else {
                        // No no area
                    }

                   // redrawGrid(true, 0);
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
