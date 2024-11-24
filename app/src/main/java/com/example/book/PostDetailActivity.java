package com.example.book;

import android.os.Bundle;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.UserService.Post;
import com.example.book.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity {
    private TextView titleTextView, contentTextView, authorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail); // PostDetail 레이아웃 생성 필요

        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        authorTextView = findViewById(R.id.authorTextView);

        int postId = getIntent().getIntExtra("post_id", -1);
        loadPostDetails(postId);
    }

    private void loadPostDetails(int postId) {
        RetrofitInstance.getUserService().getPostById(postId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Post post = response.body();
                    titleTextView.setText(post.getTitle());
                    contentTextView.setText(post.getContent());
                    authorTextView.setText(post.getAuthor());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(PostDetailActivity.this, "Failed to load post details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}