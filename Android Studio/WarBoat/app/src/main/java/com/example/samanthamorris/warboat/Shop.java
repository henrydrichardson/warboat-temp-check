package com.example.samanthamorris.warboat;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;


public class Shop extends AppCompatActivity {
    // TODO: Replace cur with actual currency value from database
    int cur = 888;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);



        String strCur = String.valueOf(cur);
        TextView curView = (TextView) findViewById(R.id.currency);
        curView.setText(strCur);

        // Buy screen 1
        Button buy1 = (Button) findViewById(R.id.buySkin_1);
        buy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if user can purchase the item...
                if(cur >= 15) {
                    // Confirm if user wants to purchase
                    DialogFragment probe = new PurchaseDialogueFragment();
                    probe.show(getFragmentManager(), "purchase");


                    // Get user response
                    Bundle bundle = getIntent().getExtras();


                    // FIX THIS
                    /*
                    String decision = (String)bundle.get("response");

                    Log.d("choice", decision);



                    // If user responds yes...
                    if(decision.equals("yes"))  {
                        // Decrease currency and confirm purchase.
                        cur-=15;
                        Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.confirmPurchase, Snackbar.LENGTH_SHORT).show();
                        // TODO: add skin option to list in settings activity
                    }

                    // User decided against purchase
                    else {

                    }

                    */
                }
                else {
                    // User cannot purchase - does not have enough currency
                    Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.denyPurchase, Snackbar.LENGTH_SHORT).show();
                }

                //Intent myIntent = new Intent(Shop.this,
                //        StartupScreen.class);
                //startActivity(myIntent);
            }
        });

        /*
        // Buy screen 2
        Button buy2 = (Button) findViewById(R.id.buySkin_2);
        buy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Shop.this,
                        StartupScreen.class);
                startActivity(myIntent);
            }
        });
        */

    }

}
