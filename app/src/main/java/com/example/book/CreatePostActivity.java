package com.example.book;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.book.UserService.Post;

public class CreatePostActivity extends AppCompatActivity {
    private EditText edtTitle, edtAuthor, edtContent, edtBookTitle, edtPublisher, edtPrice;
    private Button btnSubmitPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        Button chatListButton = findViewById(R.id.ChatList);
        Button tradeListButton = findViewById(R.id.TradeList);
        Button myInfoButton = findViewById(R.id.MyInfo);
        Button HomeButton = findViewById(R.id.HomeButton);
        Button PostListButton = findViewById(R.id.PostListButton);


        // 채팅내역 버튼 클릭 리스너
        chatListButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreatePostActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        // 거래내역 버튼 클릭 리스너
        tradeListButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreatePostActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        // 내정보 버튼 클릭 리스너
        myInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreatePostActivity.this, UserDetailActivity.class);
            startActivity(intent);
        });
        HomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreatePostActivity.this, MainActivity.class);
            startActivity(intent);
        });
        PostListButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreatePostActivity.this, PostListActivity.class);
            startActivity(intent);
        });

        edtTitle = findViewById(R.id.edtTitle);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtBookTitle = findViewById(R.id.edtBookTitle);
        edtPublisher = findViewById(R.id.edtPublisher);
        edtPrice = findViewById(R.id.edtPrice);
        btnSubmitPost = findViewById(R.id.btnSubmitPost);


        btnSubmitPost.setOnClickListener(v -> {
            String title = edtTitle.getText().toString();
            String author = edtAuthor.getText().toString();
            String publisher = edtBookTitle.getText().toString();
            String price = edtBookTitle.getText().toString();
            String bookTitle = edtBookTitle.getText().toString();


            // 빈 칸 확인
            if (title.isEmpty() || author.isEmpty() || bookTitle.isEmpty() || publisher.isEmpty() || price.isEmpty()) {
                Toast.makeText(CreatePostActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Post 객체 생성
            Post post = new Post(title, bookTitle, author, publisher, price);

            // Retrofit을 이용해 서버에 게시글 등록 요청
            RetrofitInstance.getUserService(this).createPost(post).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CreatePostActivity.this, "Post submitted successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close activity
                    } else {
                        Toast.makeText(CreatePostActivity.this, "Failed to submit post", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CreatePostActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}