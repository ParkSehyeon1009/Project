package com.example.book;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.book.UserService.User;
import com.example.book.UserService.SignupResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Intent;
import android.util.Log;

public class SignupActivity extends AppCompatActivity {
    private EditText edtUserId, edtUsername, edtStudentNum, edtEmail, edtPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtUserId = findViewById(R.id.edtUserId);
        edtUsername = findViewById(R.id.edtUsername);
        edtStudentNum = findViewById(R.id.edtStudentNum);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(v -> {
            User user = new User(
                    edtUserId.getText().toString(),
                    edtUsername.getText().toString(),
                    Integer.parseInt(edtStudentNum.getText().toString()),
                    edtEmail.getText().toString(),
                    edtPassword.getText().toString()
            );

            RetrofitInstance.getUserService(this).signUp(user).enqueue(new Callback<SignupResponse>() {
                @Override
                public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("SignupError", "Error: " + errorBody);
                        } catch (Exception e) {
                            Log.e("SignupError", "Error parsing error body", e);
                        }
                        Toast.makeText(SignupActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<SignupResponse> call, Throwable t) {
                    Toast.makeText(SignupActivity.this, "오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        });
    }
}
