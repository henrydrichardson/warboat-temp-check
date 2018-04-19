package com.example.samanthamorris.warboat;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.LinearLayout;

import android.widget.LinearLayout.*;


public class Shop extends AppCompatActivity implements PurchaseDialogueFragment.onCompleteListener {
    //Used to track ints. Position 0 represents cost. Position 1 represents item ID
    final int[] pressed = new int[2];

    private void updateCurrency(int i) {
        final TextView currView = (TextView) findViewById(R.id.currency);

        final RequestQueue queue = Volley.newRequestQueue(this);
        String updateCurrUrl = BuildConfig.URL_SERVER + "/human/update/currency?email=" + Login.account.getEmail() + "&latestValue=" + String.valueOf(i);
        StringRequest currRequest = new StringRequest(Request.Method.GET, updateCurrUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                currView.setText(String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        });

        queue.add(currRequest);
    }

    private void updateItems(int id) {
        final RequestQueue queue = Volley.newRequestQueue(this);
        String updateItemsUrl = BuildConfig.URL_SERVER + "/human/update/items?email=" + Login.account.getEmail() + "&newItem=" + String.valueOf(id);
        StringRequest ItemsRequest = new StringRequest(Request.Method.GET, updateItemsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        });

        queue.add(ItemsRequest);
    }

    public void onComplete(String dec) {
        // get skin info
        Log.d("SELECTED", dec);
        // If user responds yes...
        TextView currView = (TextView) findViewById(R.id.currency);
        if (dec.equals("yes")) {
            updateCurrency(Integer.parseInt(currView.getText().toString()) - pressed[0]);
            updateItems(pressed[1]);
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.confirmPurchase, Snackbar.LENGTH_SHORT).show();
        }

        // User decided against purchase
        else {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.cancelPurchase, Snackbar.LENGTH_LONG).show();
        }

    }


    private void loadStore() {
        // Request Queue Manager
        final RequestQueue queue = Volley.newRequestQueue(this);
        String url = BuildConfig.URL_SERVER + "/items";
        String currUrl = BuildConfig.URL_SERVER + "/human/get/currency?email=" + Login.account.getEmail();

        //Load Currency
        final TextView currView = (TextView) findViewById(R.id.currency);

        StringRequest currRequest = new StringRequest(Request.Method.GET, currUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                currView.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        }
        );

        queue.add(currRequest);
        //Common Layout Params
        LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);


        // Name TextView Params
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER_HORIZONTAL;

        // Img Params
        final LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(400,500);
        imgParams.gravity = Gravity.CENTER_HORIZONTAL;

        //Purchase Button Params
        final LinearLayout.LayoutParams purchaseParams = new LinearLayout.LayoutParams(500,150);
        purchaseParams.gravity = Gravity.CENTER_HORIZONTAL;


        // Request all items from Store

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject skin = response.getJSONObject(i);
                                final String price = skin.getString("price");
                                String name = skin.getString("name");
                                final String id = skin.getString("id");

                                byte[] texture = Base64.decode(skin.getString("texture"), Base64.DEFAULT);

                                // Generate Title
                                TextView txtView = new TextView(getApplicationContext());
                                txtView.setText(name);
                                txtView.setTypeface(Typeface.SANS_SERIF);
                                txtView.setTextSize(30);
                                txtView.setLayoutParams(params);
                                layout.addView(txtView);


                                //Create Image Button
                                ImageButton imgBtn = new ImageButton(getApplicationContext());
                                Drawable image = new BitmapDrawable(BitmapFactory.decodeByteArray(texture, 0, texture.length));
                                imgBtn.setBackground(image);
                                imgBtn.setLayoutParams(imgParams);
                                layout.addView(imgBtn);


                                //Create purchase button
                                Button btn = new Button(getApplicationContext());


                                btn.setText("Buy for " + price + " coins");
                                btn.setLayoutParams(purchaseParams);
                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Check if user can purchase the item...
                                        if (Integer.parseInt(currView.getText().toString()) >= Integer.parseInt(price)) {
                                            pressed[0] = Integer.parseInt(price);
                                            pressed[1] = Integer.parseInt(id);
                                            // Confirm if user wants to purchase
                                            DialogFragment probe = new PurchaseDialogueFragment();
                                            probe.show(getFragmentManager(), "purchase");
                                        } else {
                                            // User cannot purchase - does not have enough currency
                                            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.denyPurchase, Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                layout.addView(btn);



                            }
                        } catch (JSONException e) {
                            Log.e("Shop","Json Error");

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Shop", "henry fucked up");
            }
        });

        queue.add(arrayRequest);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        loadStore();
    }

}
