package com.faanggang.wisetrack.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.model.comment.Comment;
import com.faanggang.wisetrack.controllers.UserManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Comment> comments;
    private Context context;
    private UserManager userManager;
    public CommentAdapter(Context context, ArrayList<Comment> c) {
        this.context = context;
        this.comments = c;
        this.userManager = new UserManager(FirebaseFirestore.getInstance());
    }

    @NonNull
    @Override
    public CommentItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_comment,
                parent, false);
        return new CommentItemView(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        CommentItemView item = (CommentItemView) holder;
        Comment c = comments.get(pos);
        item.setComment(c);
        userManager.getUserInfo(c.getAuthorID(), task -> {
            item.getAuthorView().setText(task.getResult().getString("userName"));
        });
        item.getDatetimeView().setText(c.getDateTimeString());
        item.getContentView().setText(c.getContent());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
