package com.doubleclick.ViewHolder;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.OnMessageClick;
import com.doubleclick.marktinhome.BaseApplication;
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.R;

import java.text.SimpleDateFormat;

/**
 * Created By Eslam Ghazy on 2/7/2022
 */
public class ContactViewHolder extends BaseViewHolder {

    private TextView nameContact, numberContact;
    private String massege;
    private OnMessageClick onMessageClick;
    private ImageView seen;
    private String myId;
    private TextView time;

    public ContactViewHolder(@NonNull View itemView, OnMessageClick onMessageClick, String myId) {
        super(itemView);
        this.onMessageClick = onMessageClick;
        this.myId = myId;
        nameContact = itemView.findViewById(R.id.nameContact);
        numberContact = itemView.findViewById(R.id.numberContact);
        seen = itemView.findViewById(R.id.seen);
        time = itemView.findViewById(R.id.time);

    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SimpleDateFormat"})
    public void Contact(Chat chat, int position) {
        this.massege = chat.getMessage();
        time.setText(new SimpleDateFormat("M/d/yy, h:mm a").format(chat.getDate()).toString());
        if (chat.getReceiver().equals(myId)) {
            seen.setVisibility(View.INVISIBLE);
        } else {
            seen.setImageDrawable(chat.isSeen() ? itemView.getContext().getResources().getDrawable(R.drawable.done_all) : itemView.getContext().getResources().getDrawable(R.drawable.done));
        }
        String[] n = massege.split("\n");
        nameContact.setText(n[0]);
        numberContact.setText(n[1]);
        itemView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.text_chat_option, popupMenu.getMenu());
            popupMenu.getMenu().findItem(R.id.open).setVisible(false);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.deleteForme) {
                        onMessageClick.deleteForMe(chat, position);
                        return true;
                    } else if (item.getItemId() == R.id.deleteforeveryone) {
                        if (BaseApplication.isNetworkConnected()) {
                            onMessageClick.deleteForAll(chat, position);
                        } else {
                            Toast.makeText(itemView.getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    } else if (item.getItemId() == R.id.copy) {
                        ClipboardManager clipboardManager = (ClipboardManager) itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setText(massege);
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
