package com.example.book;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import com.example.book.UserService.Post;
import java.util.List;
import com.example.book.UserService.Chat;
import androidx.activity.EdgeToEdge;
import android.widget.Button;
import android.content.Intent;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private LinearLayout recentPostsContainer, recentMessagesContainer;
    private TextView noPostsMessage, noMessagesMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button chatListButton = findViewById(R.id.ChatList);
        Button tradeListButton = findViewById(R.id.TradeList);
        Button myInfoButton = findViewById(R.id.MyInfo);
        Button HomeButton = findViewById(R.id.HomeButton);
        Button PostListButton = findViewById(R.id.PostListButton);

        recentPostsContainer = findViewById(R.id.recent_posts_container);
        recentMessagesContainer = findViewById(R.id.recent_messages_container);
        noPostsMessage = findViewById(R.id.no_posts_message);
        noMessagesMessage = findViewById(R.id.no_messages_message);

        displayRecentPosts(getRecentPosts());
        displayRecentMessages(getRecentMessages());

        // 채팅내역 버튼 클릭 리스너
        chatListButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        // 거래내역 버튼 클릭 리스너
        tradeListButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        // 내정보 버튼 클릭 리스너
        myInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserDetailActivity.class);
            startActivity(intent);
        });
        HomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });
        PostListButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PostListActivity.class);
            startActivity(intent);
        });
    }
    private void displayRecentPosts(List<Post> recentPosts) {
        if (recentPosts.isEmpty()) {
            noPostsMessage.setVisibility(View.VISIBLE);
            recentPostsContainer.setVisibility(View.GONE);
        } else {
            noPostsMessage.setVisibility(View.GONE);
            recentPostsContainer.setVisibility(View.VISIBLE);

            for (Post post : recentPosts) {
                TextView postView = new TextView(this);
                postView.setText(post.getTitle()); // 게시글 제목 표시 (필요하면 더 많은 정보 추가)
                postView.setTextSize(16);
                postView.setPadding(0, 8, 0, 8);
                recentPostsContainer.addView(postView);
            }
        }
    }

    private void displayRecentMessages(List<Chat> recentMessages) {
        if (recentMessages.isEmpty()) {
            noMessagesMessage.setVisibility(View.VISIBLE);
            recentMessagesContainer.setVisibility(View.GONE);
        } else {
            noMessagesMessage.setVisibility(View.GONE);
            recentMessagesContainer.setVisibility(View.VISIBLE);

            for (Chat message : recentMessages) {
                TextView messageView = new TextView(this);
                messageView.setText(message.getMessage()); // 메시지 내용 표시 (필요하면 더 많은 정보 추가)
                messageView.setTextSize(16);
                messageView.setPadding(0, 8, 0, 8);
                recentMessagesContainer.addView(messageView);
            }
        }
    }

    // 최근 게시글 3개를 가져오는 예시 메서드
    private List<Post> getRecentPosts() {
        List<Post> posts = new ArrayList<>();
        // TODO: 서버에서 게시글 목록을 가져와 최근 3개 반환 (데이터가 없는 경우 빈 리스트 반환)
        return posts; // 실제 데이터로 교체
    }

    // 최근 메시지 3개를 가져오는 예시 메서드
    private List<Chat> getRecentMessages() {
        List<Chat> messages = new ArrayList<>();
        // TODO: 서버에서 메시지 목록을 가져와 최근 3개 반환 (데이터가 없는 경우 빈 리스트 반환)
        return messages; // 실제 데이터로 교체
    }
}