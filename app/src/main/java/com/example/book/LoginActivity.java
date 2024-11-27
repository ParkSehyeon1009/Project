package com.example.book;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.UserService.LoginRequest;
import com.example.book.UserService.LoginResponse;
import com.example.book.UserService.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText edtUserId, edtPassword;
    private Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // UI 요소 초기화
        edtUserId = findViewById(R.id.edtUserId);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        // 로그인 버튼 클릭 리스너
        btnLogin.setOnClickListener(v -> {
            String userId = edtUserId.getText().toString();
            String password = edtPassword.getText().toString();

            if (userId.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 로그인 요청 객체 생성
            LoginRequest loginRequest = new LoginRequest(userId, password);

            // Retrofit으로 로그인 요청
            UserService userService = RetrofitInstance.getUserService(this);
            userService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        // 서버에서 반환한 쿠키 확인
                        String cookie = response.headers().get("Set-Cookie");
                        Log.d("LoginActivity", "Set-Cookie: " + cookie);

                        if (cookie != null && !cookie.isEmpty()) {
                            // SharedPreferences에 저장
                            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("session_cookie", cookie);
                            editor.apply();

                            Log.d("LoginActivity", "Session Cookie save: " + cookie);

                            // MainActivity로 이동
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("LoginActivity", "Set-Cookie is null or empty.");
                        }
                    } else {
                        Log.e("LoginActivity", "Login failed with code: " + response.code());
                    }
                }


                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e(TAG, "로그인 요청 실패", t);
                    Toast.makeText(LoginActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // 회원가입 버튼 클릭 리스너
        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}
