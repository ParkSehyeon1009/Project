package com.example.book;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.UserService.User;
import com.example.book.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {

    private TextView usernameValue, emailValue, studentNumValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        // XML 요소 연결
        usernameValue = findViewById(R.id.usernameValue);
        emailValue = findViewById(R.id.emailValue);
        studentNumValue = findViewById(R.id.studentNumValue);

        // Intent에서 사용자 ID 가져오기
        int userId = getIntent().getIntExtra("user_id", -1);

        if (userId != -1) {
            loadUserDetails(userId);
        } else {
            Toast.makeText(this, "유효하지 않은 사용자 ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadUserDetails(int userId) {
        RetrofitInstance.getUserService().getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    usernameValue.setText(user.getUsername());
                    emailValue.setText(user.getEmail());
                    studentNumValue.setText(String.valueOf(user.getStudent_num()));
                } else {
                    Toast.makeText(UserDetailActivity.this, "사용자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UserDetailActivity.this, "오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
