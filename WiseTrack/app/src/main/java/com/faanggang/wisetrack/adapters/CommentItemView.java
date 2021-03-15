package com.faanggang.wisetrack.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;

public class CommentItemView extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView comment_author;
    private TextView comment_datetime;
    private TextView comment_content;
    private Context context;

    public CommentItemView(@NonNull View view, Context context){
        super(view);
        this.comment_author = view.findViewById(R.id.comment_item_username);
        this.comment_datetime = view.findViewById(R.id.comment_item_datetime);
        this.comment_content = view.findViewById(R.id.comment_item_content);
        this.context = context;
    }

    public TextView getCommentAuthor() {
        return comment_author;
    }

    public TextView getCommentDatetime() {
        return comment_datetime;
    }

    public TextView getCommentContent() {
        return comment_content;
    }

    @Override
    public void onClick(View v) {

    }
}
