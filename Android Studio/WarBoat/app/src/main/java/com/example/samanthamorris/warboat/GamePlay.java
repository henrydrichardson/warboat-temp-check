package com.example.samanthamorris.warboat;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import android.widget.Button;

import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;
import java.lang.Math;

import com.android.volley.Request;

public class GamePlay extends AppCompatActivity {


    public boolean isShipsPlaced = false;
    public boolean isRotated = false;
    public Grid[] GridManager;
    public HashMap<Integer, Integer> displayMap;
    int shipTracker;
    int turn;
    AI buddy = new AI();


    private void updateWithLoss() {
        final RequestQueue queue = Volley.newRequestQueue(this);
        String lostUrl = BuildConfig.URL_SERVER + "/human/update/lost?email=" + Login.account.getEmail();
        StringRequest lostRequest = new StringRequest(Request.Method.GET, lostUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(lostRequest);
    }

    private void updateWithWin() {
        final RequestQueue queue = Volley.newRequestQueue(this);
        String winUrl = BuildConfig.URL_SERVER + "/human/update/win?email=" + Login.account.getEmail();
        StringRequest winRequest = new StringRequest(Request.Method.GET, winUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(winRequest);
    }

    private void loadBackground() {
        final ImageView img = (ImageView) findViewById(R.id.background);

        if (Settings.selected.toString().compareTo("Basic") == 0) {
            img.setImageResource(R.drawable.water);
        } else {
            final RequestQueue queue = Volley.newRequestQueue(this);
            String getItemsUrl = BuildConfig.URL_SERVER + "/texture/get?name=" + Settings.selected;
            JsonArrayRequest ItemRequest = new JsonArrayRequest(getItemsUrl, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONObject skin = response.getJSONObject(0);
                        byte[] texture = Base64.decode(skin.getString("texture"), Base64.DEFAULT);

                        Drawable image = new BitmapDrawable(BitmapFactory.decodeByteArray(texture, 0, texture.length));
                        img.setImageDrawable(image);
                } catch (JSONException e) {}
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError e) {

                }
            });

            queue.add(ItemRequest);
        }
    }


    private void redrawGrid(boolean displayShips, int whichGrid) {
        android.support.v7.widget.GridLayout mainGrid = (android.support.v7.widget.GridLayout) findViewById(R.id.mainGrid);
        int childCount = mainGrid.getChildCount();

        //Checks and sets which ships are sunk
        for (int i = 0; i < GridManager[whichGrid].Ships.length; i++) {
            GridManager[whichGrid].setSunk(i);
        }

        for (int j = 0; j < childCount; j++) {
            final Button button = (Button) mainGrid.getChildAt(j);
            if (GridManager[whichGrid].getSunkPoints().contains(displayMap.get(button.getId()))) {
                button.setBackground(getDrawable(R.drawable.sunk));
            } else if (GridManager[whichGrid].getAttackPoints().contains(displayMap.get(button.getId())) && GridManager[whichGrid].getSHIP_POINTS().contains(displayMap.get(button.getId()))) {
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


        loadBackground();


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
        while (true) {
            if (GridManager[1].populateShips(shipTracker, random.nextBoolean(), Math.abs(random.nextInt() % 64))) {
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
        for (int i = 0; i < childCount; i++) {
            final Button button = (Button) mainGrid.getChildAt(i);
            displayMap.put(button.getId(), i);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (turn % 2 == 0) {
                        //Populate
                        if (!isShipsPlaced) {
                            if (GridManager[0].populateShips(shipTracker, isRotated, displayMap.get(button.getId()))) {

                                shipTracker++;
                                // Which boat we are placing
                                if (shipTracker == 0) {
                                    findViewById(R.id.ac).setVisibility(View.VISIBLE);
                                    findViewById(R.id.wb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.ds).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.sb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.pb).setVisibility(View.INVISIBLE);
                                } else if (shipTracker == 1) {
                                    findViewById(R.id.ac).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.wb).setVisibility(View.VISIBLE);
                                    findViewById(R.id.ds).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.sb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.pb).setVisibility(View.INVISIBLE);
                                } else if (shipTracker == 2) {
                                    findViewById(R.id.ac).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.wb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.ds).setVisibility(View.VISIBLE);
                                    findViewById(R.id.sb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.pb).setVisibility(View.INVISIBLE);
                                } else if (shipTracker == 3) {
                                    findViewById(R.id.ac).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.wb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.ds).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.sb).setVisibility(View.VISIBLE);
                                    findViewById(R.id.pb).setVisibility(View.INVISIBLE);
                                } else if (shipTracker == 4) {
                                    findViewById(R.id.ac).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.wb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.ds).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.sb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.pb).setVisibility(View.VISIBLE);
                                }

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        redrawGrid(true, 0);
                                    }
                                }, 0);
                                if (shipTracker == 5) {
                                    findViewById(R.id.placeText).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.ac).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.wb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.ds).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.sb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.pb).setVisibility(View.INVISIBLE);
                                    findViewById(R.id.RotateButton).setVisibility(View.INVISIBLE);
                                    isShipsPlaced = true;
                                    //  SystemClock.sleep(7000);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            redrawGrid(false, 1);
                                        }
                                    }, 300);

                                }
                            }
                        } else {
                            if (GridManager[1].setAttackPoint(displayMap.get(button.getId()))) {
                                turn++;

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        redrawGrid(true, 0);
                                    }
                                }, 1000);
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

                                if (GridManager[0].isGameLost()) {
                                    updateWithLoss();
                                    Intent myIntent = new Intent(GamePlay.this,
                                            LostGame.class);
                                    startActivity(myIntent);
                                } else if (GridManager[1].isGameLost()) {
                                    updateWithWin();
                                    Intent myIntent = new Intent(GamePlay.this,
                                            WinGame.class);
                                    startActivity(myIntent);
                                }


                                turn++;


                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        redrawGrid(false, 1);
                                    }
                                }, 2000); //WINDOW TIMING
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



}
