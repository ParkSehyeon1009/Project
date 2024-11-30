package com.example.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.book.UserService.Chat;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<Chat> {
    private int userId;

    public ChatAdapter(Context context, List<Chat> chats, int userId) {
        super(context, 0, chats);
        this.userId = userId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_chat, parent, false);
        }

        Chat chat = getItem(position);

        TextView messageTextView = convertView.findViewById(R.id.textViewMessage);
        if (chat.getSender_id() == userId) {
            messageTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        } else {
            messageTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        }

        messageTextView.setText(chat.getMessage());

        return convertView;
    }
}

