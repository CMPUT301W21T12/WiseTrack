package com.faanggang.wisetrack.comment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.faanggang.wisetrack.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCommentFragment extends DialogFragment {
    //private EditText descriptionView;
    //private EditText dateView;
    private EditText cmtContentView;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        //void onOkPressed(Experiment newExperiment);
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_comment, null);
        cmtContentView = view.findViewById(R.id.commentContentFragment);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Experiment")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String content = cmtContentView.getText().toString();
                    }}).create();
    }
}