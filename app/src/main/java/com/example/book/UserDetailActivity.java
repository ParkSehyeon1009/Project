package com.example.book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.UserService.User;
import com.example.book.UserService.UserService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {
    private static final String TAG = "UserDetailActivity";

    // 레이아웃에 있는 텍스트 뷰 변수 선언
    private TextView usernameValue, emailValue, studentNumValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Button chatListButton = findViewById(R.id.ChatList);
        Button tradeListButton = findViewById(R.id.TradeList);
        Button myInfoButton = findViewById(R.id.MyInfo);
        Button HomeButton = findViewById(R.id.HomeButton);
        Button PostListButton = findViewById(R.id.PostListButton);


        // 채팅내역 버튼 클릭 리스너
        chatListButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserDetailActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        // 거래내역 버튼 클릭 리스너
        tradeListButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserDetailActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        // 내정보 버튼 클릭 리스너
        myInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserDetailActivity.this, UserDetailActivity.class);
            startActivity(intent);
        });
        HomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserDetailActivity.this, MainActivity.class);
            startActivity(intent);
        });
        PostListButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserDetailActivity.this, PostListActivity.class);
            startActivity(intent);
        });

        // 레이아웃에서 텍스트 뷰 연결
        usernameValue = findViewById(R.id.usernameValue);
        emailValue = findViewById(R.id.emailValue);
        studentNumValue = findViewById(R.id.studentNumValue);

        // 사용자 정보 가져오기
        fetchUserDetails();
    }

    private void fetchUserDetails() {
        UserService userService = RetrofitInstance.getUserService(this);

        // SharedPreferences에서 로그인한 사용자 ID 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String sessionCookie = sharedPreferences.getString("session_cookie", null);
        Log.d(TAG, "Stored Cookie: " + sessionCookie);

        Call<List<User>> call = userService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                int loggedInUserId = sharedPreferences.getInt("logged_in_user_id", -1); // user id 불러오기
                // 디버깅 코드 추가
                Log.d(TAG, "Response Code: " + response.code());
                try {
                    if (response.body() != null) {
                        Log.d(TAG, "Response Body: " + response.body().toString());
                    } else {
                        Log.e(TAG, "Response Body is null");
                    }
                    if (response.errorBody() != null) {
                        Log.e(TAG, "Error Body: " + response.errorBody().string());
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error reading error body", e);
                }

                // 기존 응답 처리 코드
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    Log.d(TAG, "Loaded users: " + users.toString());
                    if (!users.isEmpty()) {
                        User user = users.get(loggedInUserId); // 사용자 정보 불러오기
                        updateUI(user);
                    }
                } else {
                    Log.e(TAG, "Failed to load users. Response code: " + response.code());
                    Toast.makeText(UserDetailActivity.this, "사용자 정보를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(TAG, "Request failed: " + t.getMessage(), t);
                Toast.makeText(UserDetailActivity.this, "네트워크 오류 발생", Toast.LENGTH_LONG).show();
            }
        });
    }



    private void updateUI(User user) {
        // 사용자 정보를 텍스트 뷰에 설정
        usernameValue.setText(user.getUsername());
        emailValue.setText(user.getEmail());
        studentNumValue.setText(String.valueOf(user.getStudentNum()));

        // 디버그 로그 출력
        Log.d(TAG, "UI 업데이트 완료: " +
                "Username=" + user.getUsername() +
                ", Email=" + user.getEmail() +
                ", StudentNum=" + user.getStudentNum());
    }
}
