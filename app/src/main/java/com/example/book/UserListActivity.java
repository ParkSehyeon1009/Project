package com.example.book;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        Button chatListButton = findViewById(R.id.ChatList);
        Button tradeListButton = findViewById(R.id.TradeList);
        Button myInfoButton = findViewById(R.id.MyInfo);
        Button HomeButton = findViewById(R.id.HomeButton);
        Button PostListButton = findViewById(R.id.PostListButton);


        // 채팅내역 버튼 클릭 리스너
        chatListButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserListActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        // 거래내역 버튼 클릭 리스너
        tradeListButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserListActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        // 내정보 버튼 클릭 리스너
        myInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserListActivity.this, UserDetailActivity.class);
            startActivity(intent);
        });
        HomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserListActivity.this, MainActivity.class);
            startActivity(intent);
        });
        PostListButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserListActivity.this, PostListActivity.class);
            startActivity(intent);
        });

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
        RetrofitInstance.getUserService(this).getUsers().enqueue(new Callback<List<User>>() {
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
