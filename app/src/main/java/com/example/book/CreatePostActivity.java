package com.example.book;

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
    private EditText edtTitle, edtAuthor, edtContent;
    private Button btnSubmitPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        edtTitle = findViewById(R.id.edtTitle);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtContent = findViewById(R.id.edtContent);
        btnSubmitPost = findViewById(R.id.btnSubmitPost);

        btnSubmitPost.setOnClickListener(v -> {
            String title = edtTitle.getText().toString();
            String author = edtAuthor.getText().toString();
            String content = edtContent.getText().toString();

            // 빈 칸 확인
            if (title.isEmpty() || author.isEmpty() || content.isEmpty()) {
                Toast.makeText(CreatePostActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Post 객체 생성
            Post post = new Post(title, content, author);

            // Retrofit을 이용해 서버에 게시글 등록 요청
            RetrofitInstance.getUserService().createPost(post).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CreatePostActivity.this, "Post submitted successfully", Toast.LENGTH_SHORT).show();
                        finish(); // 등록 후 화면 닫기
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