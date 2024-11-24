package com.example.book;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.book.UserService.LoginRequest;
import com.example.book.UserService.LoginResponse;


public class LoginActivity extends AppCompatActivity {
    private EditText edtemail, edtPassword;
    private Button btnLogin, btnSignUp, btnGoToTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtemail = findViewById(R.id.edtID);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnGoToTest = findViewById(R.id.btnGoToTest);

        btnLogin.setOnClickListener(v -> {
            String email = edtemail.getText().toString();
            String password = edtPassword.getText().toString();
            LoginRequest loginRequest = new LoginRequest(email, password);

            RetrofitInstance.getUserService().login(loginRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String token = response.body().getToken();
                        Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                        // 로그인 성공 시 MainActivity로 이동
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("token", token); // 토큰이 필요한 경우 전달
                        startActivity(intent);
                        finish(); // LoginActivity 종료
                    } else {
                        try {
                            String errorBody = response.errorBody().string(); // 에러 본문 출력
                            Log.e("LoginError", "Error code: " + response.code() + ", Error body: " + errorBody);
                        } catch (Exception e) {
                            Log.e("LoginError", "Error while reading error body", e);
                        }
                        Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnSignUp.setOnClickListener(v -> {
            // SignUpActivity로 이동
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        btnGoToTest.setOnClickListener(v -> {
            // 테스트 화면으로 이동 (예: UserListActivity로 이동)
            Intent intent = new Intent(LoginActivity.this, PostListActivity.class);
            startActivity(intent);
        });
    }
}
