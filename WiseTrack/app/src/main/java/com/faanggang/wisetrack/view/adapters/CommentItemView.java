package com.faanggang.wisetrack.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.model.comment.Comment;
import com.faanggang.wisetrack.view.comment.ViewAllResponseActivity;
import com.faanggang.wisetrack.view.user.ViewOtherActivity;

public class CommentItemView extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView commentAuthorView;
    private TextView commentDatetimeView;
    private TextView commentContentView;
    private Context context;
    private Comment comment;

    public CommentItemView(@NonNull View view, Context context){
        super(view);
        view.setClickable(true);
        view.setOnClickListener(this);
        this.context = context;
        this.commentAuthorView = view.findViewById(R.id.comment_item_username);
        this.commentDatetimeView = view.findViewById(R.id.comment_item_datetime);
        this.commentContentView = view.findViewById(R.id.comment_item_content);
    }

    public TextView getAuthorView() {
        return commentAuthorView;
    }

    public TextView getDatetimeView() {
        return commentDatetimeView;
    }

    public TextView getContentView() {
        return commentContentView;
    }

    public void setComment(Comment c){this.comment=c;}

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(context, ViewAllResponseActivity.class);
        intent.putExtra("CMT_ID", comment.getFirebaseID());
        intent.putExtra("PARENT_CONTENT", commentContentView.getText());
        intent.putExtra("PARENT_DATE", commentDatetimeView.getText());
        intent.putExtra("PARENT_USERNAME", commentAuthorView.getText());
        context.startActivity(intent);
    }
}
