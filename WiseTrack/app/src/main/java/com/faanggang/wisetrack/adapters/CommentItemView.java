package com.faanggang.wisetrack.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.comment.Comment;
import com.faanggang.wisetrack.comment.ViewAllCommentActivity;
import com.faanggang.wisetrack.comment.ViewAllResponseActivity;

public class CommentItemView extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView comment_author;
    private TextView comment_datetime;
    private TextView comment_content;
    private Context context;
    private Comment comment;

    public CommentItemView(@NonNull View view, Context context){
        super(view);
        view.setClickable(true);
        view.setOnClickListener(this);
        this.context = context;
        this.comment_author = view.findViewById(R.id.comment_item_username);
        this.comment_datetime = view.findViewById(R.id.comment_item_datetime);
        this.comment_content = view.findViewById(R.id.comment_item_content);
    }

    public TextView getAuthorView() {
        return comment_author;
    }

    public TextView getDatetimeView() {
        return comment_datetime;
    }

    public TextView getContentView() {
        return comment_content;
    }

    public void setComment(Comment c){this.comment=c;}

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ViewAllResponseActivity.class);
        intent.putExtra("CMT_ID", comment.getFirebaseID());
        intent.putExtra("PARENT_CONTENT", comment_content.getText());
        intent.putExtra("PARENT_DATE", comment_datetime.getText());
        intent.putExtra("PARENT_USERNAME", comment_author.getText());
        context.startActivity(intent);
    }
}
