package com.example.book;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View; // View를 사용하기 위한 import
import java.util.ArrayList; // ArrayList를 사용하기 위한 import

import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import com.example.book.UserService.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {
    private ListView listViewUsers;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        listViewUsers = findViewById(R.id.listViewUsers);

        loadUsers();

        listViewUsers.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            User selectedUser = userList.get(position);

            // ChatActivity로 이동하면서 선택한 사용자 정보 전달
            Intent intent = new Intent(UserListActivity.this, ChatActivity.class);
            intent.putExtra("receiver_id", selectedUser.getId());
            intent.putExtra("receiver_name", selectedUser.getUsername());
            startActivity(intent);
        });
    }

    private void loadUsers() {
        RetrofitInstance.getUserService().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(UserListActivity.this,
                            android.R.layout.simple_list_item_1,
                            getUsernames(userList));
                    listViewUsers.setAdapter(adapter);
                } else {
                    Toast.makeText(UserListActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(UserListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> getUsernames(List<User> users) {
        List<String> usernames = new ArrayList<>();
        for (User user : users) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }
}
