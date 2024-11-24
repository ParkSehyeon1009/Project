package com.example.book;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

import com.example.book.UserService.UserService;

import java.io.IOException;

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static String sessionCookie;

    public static void setSessionCookie(String cookie) {
        sessionCookie = cookie;
    }

    public static UserService getUserService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder();
                    if (sessionCookie != null && !sessionCookie.isEmpty()) {
                        requestBuilder.addHeader("Cookie", sessionCookie);
                    }
                    return chain.proceed(requestBuilder.build());
                })
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-39-46-144.ap-northeast-2.compute.amazonaws.com:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit.create(UserService.class);
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-3-39-46-144.ap-northeast-2.compute.amazonaws.com:3000/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}




