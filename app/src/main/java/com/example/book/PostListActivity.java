package com.example.book;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.book.UserService.Post;
import com.example.book.RetrofitInstance;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.annotation.NonNull;

public class PostListActivity extends AppCompatActivity {
    private ListView listViewPosts;
    private PostAdapter postAdapter;
    private List<Post> postList = new ArrayList<>();
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        Button chatListButton = findViewById(R.id.ChatList);
        Button tradeListButton = findViewById(R.id.TradeList);
        Button myInfoButton = findViewById(R.id.MyInfo);
        Button HomeButton = findViewById(R.id.HomeButton);
        Button PostListButton = findViewById(R.id.PostListButton);


        // 채팅내역 버튼 클릭 리스너
        chatListButton.setOnClickListener(v -> {
            Intent intent = new Intent(PostListActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        // 거래내역 버튼 클릭 리스너
        tradeListButton.setOnClickListener(v -> {
            Intent intent = new Intent(PostListActivity.this, UserListActivity.class);
            startActivity(intent);
        });

        // 내정보 버튼 클릭 리스너
        myInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(PostListActivity.this, UserDetailActivity.class);
            startActivity(intent);
        });
        HomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(PostListActivity.this, MainActivity.class);
            startActivity(intent);
        });
        PostListButton.setOnClickListener(v -> {
            Intent intent = new Intent(PostListActivity.this, PostListActivity.class);
            startActivity(intent);
        });

        listViewPosts = findViewById(R.id.listViewPosts);
        searchView = findViewById(R.id.searchView);

        loadPosts();

        listViewPosts.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            Post selectedPost = postList.get(position);
            Intent intent = new Intent(PostListActivity.this, PostDetailActivity.class);
            intent.putExtra("post_id", selectedPost.getId());
            startActivity(intent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterPosts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterPosts(newText);
                return true;
            }
        });
    }

    private void loadPosts() {
        RetrofitInstance.getUserService(this).getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    postList = response.body();
                    postAdapter = new PostAdapter(PostListActivity.this, postList);
                    listViewPosts.setAdapter(postAdapter);
                } else {
                    Toast.makeText(PostListActivity.this, "Failed to load posts", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                Toast.makeText(PostListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterPosts(String query) {
        if (postAdapter == null) {
            // Adapter가 초기화되지 않았으면 리턴
            return;
        }

        List<Post> filteredList = new ArrayList<>();
        for (Post post : postList) {
            if (post.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(post);
            }
        }
        postAdapter.updateList(filteredList); // 필터링된 목록으로 어댑터 업데이트
    }
}
