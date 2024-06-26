package com.example.recyclerview.adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.R;
import com.example.recyclerview.model.Post;

import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        LinearLayout dateLayout, timeLayout;
        TextView body, date, time;
        private TextWatcher userIdWatcher, titleWatcher;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.user_id_tv);
            title = itemView.findViewById(R.id.title_tv);
            body = itemView.findViewById(R.id.body_tv);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            dateLayout = itemView.findViewById(R.id.dateLayout);
            timeLayout = itemView.findViewById(R.id.timeLayout);
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
            if (post.getCurrentTime() != null && !post.getCurrentTime().isEmpty()) {
                timeLayout.setVisibility(View.VISIBLE);
            }
            if (post.getCurrentDate() != null && !post.getCurrentDate().isEmpty()) {
                dateLayout.setVisibility(View.VISIBLE);
            }
            date.setText(post.getCurrentDate());
            time.setText(post.getCurrentTime());
            time.setOnClickListener(v -> {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                @SuppressLint("SetTextI18n") TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(),
                        (view, hourOfDay, minute) -> time.setText(hourOfDay + ":" + minute), mHour, mMinute, false);

                timePickerDialog.show();
                post.setCurrentTime(String.valueOf(mHour) + mMinute);
            });
            date.setOnClickListener(v -> {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        (view, year, monthOfYear, dayOfMonth) -> {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat fr = new SimpleDateFormat("dd-MM-yyyy");
                            date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                post.setCurrentDate(String.valueOf(mYear) + mMonth + mDay);
            });

            userIdWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    post.setUserId(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };
            userId.addTextChangedListener(userIdWatcher);

            titleWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    post.setTitle(s.toString());
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    post.setCurrentDate(date);

                    long currentDateTime = System.currentTimeMillis();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
                    String time = simpleDateFormat.format(currentDateTime);
                    post.setCurrentTime(time);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };
            title.addTextChangedListener(titleWatcher);
        }
    }
}
