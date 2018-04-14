package com.example.samanthamorris.warboat;

        import android.content.Intent;
        import android.os.Bundle;
        import android.app.Activity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.TextView;

public class StartupScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_screen);
        Log.d("STARTUP","Startup screen opened successfully.");

        /**
         * Go back to login activity upon hitting home icon.
         */


        ImageButton settings = (ImageButton) findViewById(R.id.settingsButton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(StartupScreen.this,
                        Settings.class);
                startActivity(myIntent);
            }
        });

        ImageButton shop = (ImageButton) findViewById(R.id.shop);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(StartupScreen.this,
                        Shop.class);
                startActivity(myIntent);
            }
        });

//        Button singleGame = (Button) findViewById(R.id.singlePlayerButton);
//        shop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent myIntent = new Intent(StartupScreen.this,
//                        GamePlay.class);
//                startActivity(myIntent);
//            }
//        });

//        Button multiGame = (Button) findViewById(R.id.multiPlayerButton);
//        shop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent myIntent = new Intent(StartupScreen.this,
//                        GamePlay.class);
//                startActivity(myIntent);
//            }
//        });

        /**
         * Alignment is funky
         * TODO: adjust constrains of TextViews in activity_startup_screen.xml
         */

        /**
         * Display high score
         * Dummy high score value
         * TODO: set to actual score value
         */

        int intScore = 1000;
        String strScore = String.valueOf(intScore);
        TextView scoreView = (TextView) findViewById(R.id.displayscore);
        scoreView.setText(strScore);

        /**
         * Display current currency
         * Dummy currency value
         * TODO: set to actual currency value
         */
        int intCurr = 888;
        String strCurr = String.valueOf(intCurr);
        TextView currView = (TextView) findViewById(R.id.displaycurrency);
        currView.setText(strCurr);

    }


}