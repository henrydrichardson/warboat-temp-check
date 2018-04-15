package com.example.samanthamorris.warboat;

import android.app.DialogFragment;
import android.app.Dialog;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import com.example.samanthamorris.warboat.R;
import android.content.Intent;
import android.util.Log;

public class PurchaseDialogueFragment extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.purchaseQuestion)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If user chose to buy...
                        Intent intent = new Intent(PurchaseDialogueFragment.this.getActivity(), Shop.class);
                        //intent.putExtra("some_key", value);
                        String y = "yes";
                        intent.putExtra("response", y);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If user chose not to buy...
                    }
                }
                );
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
