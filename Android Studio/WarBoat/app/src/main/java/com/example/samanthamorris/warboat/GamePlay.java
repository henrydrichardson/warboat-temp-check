package com.example.samanthamorris.warboat;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    AI buddy = new AI();



    private void redrawGrid(boolean displayShips, int whichGrid) {
        android.support.v7.widget.GridLayout mainGrid = (android.support.v7.widget.GridLayout) findViewById(R.id.mainGrid);
        int childCount = mainGrid.getChildCount();

        //Checks and sets which ships are sunk
        for(int i = 0; i < GridManager[whichGrid].Ships.length; i++)
        {
            GridManager[whichGrid].setSunk(i);
        }

        for( int j = 0; j < childCount; j++) {
            final Button button = (Button) mainGrid.getChildAt(j);
            if(GridManager[whichGrid].getSunkPoints().contains(displayMap.get(button.getId()))) {
                button.setBackground(getDrawable(R.drawable.sunk));
            } else if (GridManager[whichGrid].getAttackPoints().contains(displayMap.get(button.getId())) && GridManager[whichGrid].getSHIP_POINTS().contains(displayMap.get(button.getId()))){
                button.setBackground(getDrawable(R.drawable.hit));
            } else if (GridManager[whichGrid].getAttackPoints().contains(displayMap.get(button.getId()))) {
                button.setBackground(getDrawable(R.drawable.miss));
            } else if (GridManager[whichGrid].getSHIP_POINTS().contains(displayMap.get(button.getId())) && displayShips) {
                button.setBackground(getDrawable(R.drawable.lifesaver));
            } else {
                button.setBackgroundResource(R.drawable.button_bg_stroke);
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
                                // Which boat we are placing
                                if (shipTracker == 0)
                                {
                                    findViewById(R.id.ac).setVisibility(View.VISIBLE);
                                    findViewById(R.id.wb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.ds).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.sb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.pb).setVisibility(View.INVISIBLE);
                                }
                                else if (shipTracker == 1)
                                {
                                    findViewById(R.id.ac).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.wb).setVisibility(View.VISIBLE);
                                    findViewById(R.id.ds).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.sb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.pb).setVisibility(View.INVISIBLE);
                                }
                                else if (shipTracker == 2)
                                {
                                    findViewById(R.id.ac).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.wb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.ds).setVisibility(View.VISIBLE);
                                    findViewById(R.id.sb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.pb).setVisibility(View.INVISIBLE);
                                }
                                else if (shipTracker == 3)
                                {
                                    findViewById(R.id.ac).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.wb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.ds).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.sb).setVisibility(View.VISIBLE);
                                    findViewById(R.id.pb).setVisibility(View.INVISIBLE);
                                }
                                else if (shipTracker == 4)
                                {
                                    findViewById(R.id.ac).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.wb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.ds).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.sb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.pb).setVisibility(View.VISIBLE);
                                }

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        redrawGrid( true, 0);
                                    }
                                }, 0);
                                if (shipTracker == 5) {
                                    //findViewById(R.id.placeText).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.ac).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.wb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.ds).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.sb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.pb).setVisibility(View.INVISIBLE);
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
                              /*
                                int aiAttackLocation = Math.abs(random.nextInt()%64);
                                boolean previousHit = false;
                                while (!GridManager[0].setAttackPoint(aiAttackLocation)) {
                                        aiAttackLocation = Math.abs(random.nextInt()%64);
                                    }
                                if (GridManager[0].getSHIP_POINTS().contains(aiAttackLocation))
                                    previousHit = true;
                              */
                              buddy.Attack(GridManager[1], GridManager[0]);

                           //     SystemClock.sleep(7000);

                                if(GridManager[0].isGameLost()){
                                    Intent myIntent = new Intent(GamePlay.this,
                                            LostGame.class);
                                    startActivity(myIntent);
                                }
                                else if (GridManager[1].isGameLost())
                                {
                                    Intent myIntent = new Intent(GamePlay.this,
                                            WinGame.class);
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
