package com.faanggang.wisetrack.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.faanggang.wisetrack.R;
import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter {
    private ArrayList<Comment> comments;
    public CommentAdapter(ArrayList<Comment> c) {
        this.comments = c;
    }

    @NonNull
    @Override
    public CommentItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row_item,
                parent, false);
        return new CommentItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        CommentItemView item = (CommentItemView) holder;
        Comment c = comments.get(pos);
        item.getCommentAuthor().setText(c.getAuthorID());
        item.getCommentDatetime().setText(c.getDatetimeString());
        item.getCommentContent().setText(c.getContent());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
