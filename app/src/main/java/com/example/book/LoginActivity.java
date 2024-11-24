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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // UI 요소 초기화
        edtEmail = findViewById(R.id.edtID);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        // 로그인 버튼 클릭 리스너
        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }

            // 로그인 요청 객체 생성
            LoginRequest loginRequest = new LoginRequest(email, password);

            // Retrofit을 사용해 로그인 요청
            RetrofitInstance.getUserService().login(loginRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("LoginActivity", "Response Headers: " + response.headers().toString());

                        String token = response.body().getToken();
                        String sessionCookie = response.headers().get("Set-Cookie");

                        Log.d("LoginActivity", "Set-Cookie: " + sessionCookie);

                        if (sessionCookie != null) {
                            // SharedPreferences에 세션 쿠키 저장
                            SharedPreferences preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("session_cookie", sessionCookie);
                            editor.apply();

                            Log.d("LoginActivity", "Session Cookie 저장됨: " + sessionCookie);

                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                            // MainActivity로 이동
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("token", token); // 토큰 전달
                            startActivity(intent);
                            finish(); // LoginActivity 종료
                        } else {
                            Log.e("LoginError", "Session cookie not found");
                            Toast.makeText(LoginActivity.this, "로그인 실패: 세션 쿠키 없음", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("LoginError", "Error code: " + response.code() + ", Error body: " + errorBody);
                        } catch (Exception e) {
                            Log.e("LoginError", "Error while reading error body", e);
                        }
                        Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("LoginError", "Request failed", t);
                    Toast.makeText(LoginActivity.this, "오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
