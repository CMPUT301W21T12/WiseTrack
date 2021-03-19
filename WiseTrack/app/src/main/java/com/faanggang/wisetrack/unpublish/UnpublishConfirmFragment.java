package com.faanggang.wisetrack.unpublish;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class UnpublishConfirmFragment extends DialogFragment {


    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onUnpublishPressed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setTitle("Are you sure you want to unpublish this experiment?")
                .setNegativeButton("CANCEL", null)
                .setPositiveButton("UNPUBLISH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        listener.onUnpublishPressed();

                    }}).create();

    }

}
