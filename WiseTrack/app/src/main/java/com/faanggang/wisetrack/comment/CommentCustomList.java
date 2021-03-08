package com.faanggang.wisetrack.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.faanggang.wisetrack.R;

import java.util.ArrayList;
import java.util.Collection;

public class CommentCustomList extends ArrayAdapter<Comment> {
    private ArrayList<Comment> comments;
    private Context context;
    public CommentCustomList(Context context, ArrayList<Comment> questions) {
        super(context,0, questions);
        this.comments = questions;
        this.context = context;
    }

    // This is where we set up a custom GUI for each element of the ArrayAdapter for a comment
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.comment_adapter, parent, false);
        }
        Comment cmt = comments.get(pos);
        TextView cmtUsername = view.findViewById(R.id.comment_username);
        TextView cmtDateTime = view.findViewById(R.id.comment_datetime);
        TextView cmtContent = view.findViewById(R.id.comment_content);
        cmtUsername.setText(cmt.getAuthorID());
        cmtContent.setText(cmt.getContent());
        cmtDateTime.setText(cmt.getDatetimeString());
        return view;
    }
}
