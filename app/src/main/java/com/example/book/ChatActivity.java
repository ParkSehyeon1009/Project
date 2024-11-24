package com.example.book;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.book.UserService.Chat;

public class ChatActivity extends AppCompatActivity {
    private ListView listViewChats;
    private ChatAdapter chatAdapter;
    private List<Chat> chatList;
    private EditText edtMessage;
    private Button btnSend;
    private int userId = 1; // 현재 사용자 ID (테스트용)
    private int receiverId; // 전달받은 대화 상대방의 ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listViewChats = findViewById(R.id.listViewChats);
        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);

        // 전달받은 receiver_id를 가져옴
        receiverId = getIntent().getIntExtra("receiver_id", -1);
        if (receiverId == -1) {
            Toast.makeText(this, "Invalid user selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        chatList = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatList, userId);
        listViewChats.setAdapter(chatAdapter);

        loadChats();

        btnSend.setOnClickListener(v -> {
            String message = edtMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage(new Chat(userId, receiverId, message));
                edtMessage.setText("");
            }
        });
    }

    private void loadChats() {
        RetrofitInstance.getUserService().getChats(userId, receiverId).enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chatList.clear();
                    chatList.addAll(response.body());
                    chatAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ChatActivity.this, "Failed to load chats", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage(Chat chat) {
        RetrofitInstance.getUserService().sendMessage(chat).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    chatList.add(chat);
                    chatAdapter.notifyDataSetChanged();
                    listViewChats.setSelection(chatList.size() - 1);
                } else {
                    Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

