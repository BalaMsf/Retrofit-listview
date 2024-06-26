package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.recyclerview.adapter.PostAdapter;
import com.example.recyclerview.database.AppDatabase;
import com.example.recyclerview.model.Post;
import com.example.recyclerview.retrofit.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDatabase appDatabase;
    Button fetchApiData, saveEditedData;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycerlview);
        fetchApiData = findViewById(R.id.fetch);
        saveEditedData = findViewById(R.id.save);

        fetchApiData.setOnClickListener(v -> fetchData());
        saveEditedData.setOnClickListener(v -> saveEditedData());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData();
        appDatabase = AppDatabase.getInstance(this);
        loadPostsFromDatabase();
    }

    public void fetchData() {
        Call<List<Post>> call = RetrofitClient.getInstance().getMyApi().getPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
                    return;
                }
                postList = response.body();
                savePostsToDatabase(postList);
                loadPostsFromDatabase();
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
                loadPostsFromDatabase();
            }
        });
    }

    private void savePostsToDatabase(List<Post> postList) {
        AsyncTask.execute(() -> appDatabase.postDao().insertPosts(postList));
    }

    private void loadPostsFromDatabase() {
        AsyncTask.execute(() -> {
            postList = appDatabase.postDao().getAllPosts();
            runOnUiThread(() -> {
                PostAdapter postAdapter = new PostAdapter(MainActivity.this, postList);
                recyclerView.setAdapter(postAdapter);
            });
        });
    }

    private void saveEditedData() {
        AsyncTask.execute(() -> {
            appDatabase.postDao().insertPosts(postList);
            runOnUiThread(() -> {
                Toast.makeText(MainActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
                loadPostsFromDatabase();
            });
        });
    }
}
