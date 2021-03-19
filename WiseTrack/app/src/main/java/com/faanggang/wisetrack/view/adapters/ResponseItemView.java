package com.faanggang.wisetrack.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;

public class ResponseItemView extends RecyclerView.ViewHolder{
    private TextView response_author;
    private TextView response_datetime;
    private TextView response_content;
    private Context context;

    public ResponseItemView(@NonNull View view, Context context){
        super(view);
        this.response_author = view.findViewById(R.id.response_item_username);
        this.response_datetime = view.findViewById(R.id.response_item_datetime);
        this.response_content = view.findViewById(R.id.response_item_content);
        this.context = context;
    }

    public TextView getAuthorView() {
        return response_author;
    }

    public TextView getDatetimeView() {
        return response_datetime;
    }

    public TextView getContentView() {
        return response_content;
    }

}
