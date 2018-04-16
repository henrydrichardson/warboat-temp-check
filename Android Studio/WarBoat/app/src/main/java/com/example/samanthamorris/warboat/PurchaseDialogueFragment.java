package com.example.samanthamorris.warboat;

import android.app.DialogFragment;
import android.app.Dialog;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import com.example.samanthamorris.warboat.R;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

public class PurchaseDialogueFragment extends DialogFragment {
    public static interface onCompleteListener {
        public abstract void onComplete(String dec);
    }

    private onCompleteListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.listener = (onCompleteListener)context;
        }
        catch(final ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement");
        }

    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.purchaseQuestion)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If user chose to buy...
                        String choice = "yes";
                        listener.onComplete(choice);

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If user chose not to buy...
                        String choice = "no";
                        listener.onComplete(choice);

                    }
                }
                );
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
