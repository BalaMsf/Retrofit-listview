package com.example.recyclerview.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.R;
import com.example.recyclerview.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final List<Post> postList;
    private final Context context;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        postList = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        EditText userId, title;
        TextView body;

        private TextWatcher userIdWatcher;
        private TextWatcher titleWatcher;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.user_id_tv);
            title = itemView.findViewById(R.id.title_tv);
            body = itemView.findViewById(R.id.body_tv);
        }

        void bind(Post post) {
            // Remove previous TextWatcher if exists
            if (userIdWatcher != null) {
                userId.removeTextChangedListener(userIdWatcher);
            }
            if (titleWatcher != null) {
                title.removeTextChangedListener(titleWatcher);
            }

            // Set current values
            userId.setText(post.getUserId());
            title.setText(post.getTitle());
            body.setText(post.getBody());

            userIdWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    post.setUserId(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) { }
            };
            userId.addTextChangedListener(userIdWatcher);

            titleWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    post.setTitle(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) { }
            };
            title.addTextChangedListener(titleWatcher);
        }
    }
}
