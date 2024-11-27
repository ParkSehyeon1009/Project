package com.example.book;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.SharedPreferences;
import android.util.Log;
import android.content.Context;

import com.example.book.UserService.UserService;

import java.io.IOException;

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://ec2-3-39-46-144.ap-northeast-2.compute.amazonaws.com:3000/";
    private static final String TAG = "RetrofitInstance";

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            // HTTP 로깅 인터셉터 추가 (요청/응답 로깅용)
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // OkHttp 클라이언트 빌드
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(chain -> {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                        String sessionCookie = sharedPreferences.getString("session_cookie", null);

                        Request originalRequest = chain.request();
                        Request.Builder requestBuilder = originalRequest.newBuilder();

                        if (sessionCookie != null && !sessionCookie.isEmpty()) {
                            requestBuilder.addHeader("Cookie", sessionCookie);
                            Log.d(TAG, "Request Cookie: " + sessionCookie);
                        } else {
                            Log.e(TAG, "No Session Cookie Found");
                        }

                        Request request = requestBuilder.build();
                        Log.d(TAG, "Request URL: " + request.url());
                        return chain.proceed(request);
                    })
                    .build();

            // Retrofit 인스턴스 생성
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static UserService getUserService(Context context) {
        return getRetrofitInstance(context).create(UserService.class);
    }
}
