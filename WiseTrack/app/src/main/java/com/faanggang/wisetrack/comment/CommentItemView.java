package com.faanggang.wisetrack.comment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;

public class CommentItemView extends RecyclerView.ViewHolder {
    private TextView comment_author;



    private TextView comment_datetime;
    private TextView comment_content;

    public CommentItemView(@NonNull View view){
        super(view);
        this.comment_author = view.findViewById(R.id.comment_item_username);
        this.comment_datetime = view.findViewById(R.id.comment_item_datetime);
        this.comment_content = view.findViewById(R.id.comment_item_content);
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

}
