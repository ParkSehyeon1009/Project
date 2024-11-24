package com.example.book;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.book.UserService.User;
import com.example.book.UserService.UserService;
import com.example.book.RetrofitInstance;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {
    private static final String TAG = "UserDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        // Fetch user details
        fetchUserDetails();
    }

    private void fetchUserDetails() {
        UserService userService = RetrofitInstance.getRetrofitInstance().create(UserService.class);

        Call<List<User>> call = userService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    Log.d(TAG, "Users loaded successfully: " + users);
                    Toast.makeText(UserDetailActivity.this, "Users loaded successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Failed to load users. Response code: " + response.code());
                    Log.e(TAG, "Error body: " + response.errorBody());
                    Toast.makeText(UserDetailActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(TAG, "Error loading users: " + t.getMessage(), t);
                Toast.makeText(UserDetailActivity.this, "Failed to load users: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}


