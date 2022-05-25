package com.doubleclick.ViewHolder;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.widget.PopupMenu;

import com.doubleclick.OnMessageClick;
import com.doubleclick.marktinhome.BaseApplication;
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.R;

import java.text.SimpleDateFormat;


/**
 * Created By Eslam Ghazy on 2/5/2022
 */
public class MessageTextViewHolder extends BaseViewHolder {

    public TextView textMessage, textTime;
    private ImageView seen;
    private OnMessageClick onMessageClick;
    private String myId;
//    public ImageView profile_image;
//    public TextView txt_seen;
//    public ImageView image_message;

    public MessageTextViewHolder(View itemView, OnMessageClick onMessageClick, String myId) {
        super(itemView);
        this.onMessageClick = onMessageClick;
        this.myId = myId;
        textMessage = itemView.findViewById(R.id.textMessage);
        textTime = itemView.findViewById(R.id.textTime);
        seen = itemView.findViewById(R.id.seen);

    }

    @SuppressLint({"SimpleDateFormat", "UseCompatLoadingForDrawables"})
    public void SetTextMessage(Chat chat, int postion) {
        textMessage.setText(chat.getMessage());
        textTime.setText(new SimpleDateFormat("M/d/yy, h:mm a").format(chat.getDate()).toString());
        if (chat.getReceiver().equals(myId)) {
            seen.setVisibility(View.INVISIBLE);
        } else {
            seen.setImageDrawable(chat.isSeen() ? itemView.getContext().getResources().getDrawable(R.drawable.done_all) : itemView.getContext().getResources().getDrawable(R.drawable.done));
        }
        itemView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.text_chat_option, popupMenu.getMenu());
            popupMenu.getMenu().findItem(R.id.open).setVisible(false);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.deleteForme) {
                        onMessageClick.deleteForMe(chat, postion);
                        return true;
                    } else if (item.getItemId() == R.id.deleteforeveryone) {
                        if (BaseApplication.isNetworkConnected()) {
                            onMessageClick.deleteForAll(chat, postion);
                        } else {
                            Toast.makeText(itemView.getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    } else if (item.getItemId() == R.id.copy) {
                        ClipboardManager clipboardManager = (ClipboardManager) itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setText(textMessage.getText());
                        Toast.makeText(itemView.getContext(), itemView.getResources().getString(R.string.text_copied), Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            popupMenu.show();
        });
    }
}
