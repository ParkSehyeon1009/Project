package com.example.book.UserService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Path;
import java.util.List;

public interface UserService {
    @Headers({"Content-Type: application/json"})
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("signup")
    Call<SignupResponse> signUp(@Body User user);

    @POST("posts")
    Call<Void> createPost(@Body Post post);

    @GET("posts") // 서버의 "posts" 엔드포인트를 가져오는 요청
    Call<List<Post>> getPosts();

    @GET("chats")
    Call<List<Chat>> getChats(
            @Query("sender_id") int senderId,
            @Query("receiver_id") int receiverId
    );

    // 채팅 메시지를 보내는 메서드
    @POST("chats")
    Call<Void> sendMessage(@Body Chat chat);

    @GET("users") // 사용자 목록을 가져오는 엔드포인트
    Call<List<User>> getUsers();

    @GET("posts/{id}") // id를 URL의 일부로 전달
    Call<Post> getPostById(@Path("id") int id); // id를 파라미터로 받아서 개별 게시글을 가져옴

    @GET("users/{id}") // 서버 API의 엔드포인트에 맞게 경로 수정
    Call<User> getUserById(@Path("id") int id);
}
