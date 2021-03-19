package com.faanggang.wisetrack.comment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.WiseTrackApplication;
import com.faanggang.wisetrack.user.Users;

import java.util.Date;

public class AddResponseFragment extends DialogFragment {
    private EditText rspContentView;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void addResponseOkPressed(Response response);
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_response, null);
        rspContentView = view.findViewById(R.id.responseContentFragment);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Response")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String content = rspContentView.getText().toString();
                        if (content.trim().length()>0) {
                            Date dt = new Date();
                            Users user = WiseTrackApplication.getCurrentUser();
                            listener.addResponseOkPressed(new Response(
                                    "",
                                    user.getUserID(),
                                    content,
                                    dt
                            ));
                        }
                    }}).create();
    }
}