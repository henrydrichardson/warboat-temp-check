package com.example.samanthamorris.warboat;

import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.api.ApiException;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Login extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, OnClickListener {

    private static final int RC = 49404;

    // The core Google Play Services client.
    private GoogleSignInClient mGoogleSignInClient;

    // A progress dialog to display when the user is connecting in
    // case there is a delay in any of the dialogs being ready.
    private ProgressDialog mConnectionProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.sign_in_button).setOnClickListener(this);


    }

    @Override
    public void onStart() {
        super.onStart();

        //Check if already logged in
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("CONNECTION RESULT", "onConnectionFailed:" + connectionResult);
    }


    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);

        if (requestCode == RC) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
            handleSignInResult(task);
        }
    }

    public void onClick(View view) {

        signIn();

    }

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC);
    }

    private void checkUser(final GoogleSignInAccount account) {
        String url = "http://10.32.224.175:8080/human/check?email=" + account.getEmail();
        final RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response == "True") {
                    Log.i("Login", "User Exists");
                    updateUI(account);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                Log.i("Login", "Registering User");

                String registerUrl = "http://10.32.224.175:8080/human/add?email=" + account.getEmail() + "&gamerTag=" + account.getDisplayName();
                StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("Login", "Successful Register");
                        updateUI(account);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.e("Login", "Error:" + e);
                    }
                });
                queue.add(registerRequest);
            }
        }
        );

        queue.add(stringRequest);

    }

    private void updateUI(GoogleSignInAccount account) {


        Intent myIntent = new Intent(this,
                StartupScreen.class);
        startActivity(myIntent);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> result) {
        try {
            GoogleSignInAccount account = result.getResult(ApiException.class);
            checkUser(account);
        } catch (ApiException e) {
            Log.w("Login", "signInResult: failed code=" + e.getStatusCode());
        }
    }
}