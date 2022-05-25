package com.doubleclick.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.marktinhome.R;

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
public class ChatViewHolder extends BaseViewHolder {
    public TextView textMessage, textTime;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        textMessage = itemView.findViewById(R.id.textMessage);
        textTime = itemView.findViewById(R.id.textTime);
    }
}
