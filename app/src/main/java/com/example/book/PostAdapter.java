package com.example.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.book.UserService.Post;

import java.util.List;

public class PostAdapter extends BaseAdapter {
    private Context context;
    private List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        }

        // View 연결
        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView authorTextView = convertView.findViewById(R.id.authorTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        TextView publisherTextView = convertView.findViewById(R.id.publisherTextView);
        TextView priceTextView = convertView.findViewById(R.id.priceTextView);

        // 데이터 설정
        Post post = postList.get(position);

        titleTextView.setText("Title: " + post.getTitle());
        authorTextView.setText("Author: " + post.getAuthor());
        //dateTextView.setText("Date: " + post.getCreatedAt());
        publisherTextView.setText("Publisher: " + post.getPublisher());
        priceTextView.setText("Price: " + post.getPrice());

        return convertView;
    }

    // 필터링된 목록 업데이트 메서드
    public void updateList(List<Post> filteredList) {
        postList = filteredList;
        notifyDataSetChanged();
    }
}
