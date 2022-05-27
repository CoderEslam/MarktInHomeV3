package com.doubleclick.ViewHolder;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.doubleclick.OnMessageClick;
import com.doubleclick.marktinhome.BaseApplication;
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.PhotoView.PhotoView;

import java.io.File;
import java.text.SimpleDateFormat;


/**
 * Created By Eslam Ghazy on 2/7/2022
 */
public class ImageViewHolder extends BaseViewHolder {
    private PhotoView imageView;
    private ImageView optins, seen;
    private OnMessageClick onMessageClick;
    private ProgressBar progressBar;
    private String myId;
    private TextView time;
    private File file;

    public ImageViewHolder(@NonNull View itemView, OnMessageClick onMessageClick, String myId) {
        super(itemView);
        this.onMessageClick = onMessageClick;
        this.myId = myId;
        imageView = itemView.findViewById(R.id.image);
        optins = itemView.findViewById(R.id.optins);
        seen = itemView.findViewById(R.id.seen);
        progressBar = itemView.findViewById(R.id.progressBar);
        time = itemView.findViewById(R.id.time);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SimpleDateFormat"})
    public void ShowImage(Chat chat, int position) {
        Log.e("CHATS", chat.toString());
        time.setText(new SimpleDateFormat("M/d/yy, h:mm a").format(chat.getDate()).toString());
        if (!chat.getUri().toString().equals("")) {
            /* to check if file exist or not
             * */
            file = new File(chat.getUri());
            if (chat.getReceiver().equals(myId)) {
                seen.setVisibility(View.INVISIBLE);
            } else {
                seen.setImageDrawable(chat.isSeen() ? itemView.getContext().getResources().getDrawable(R.drawable.done_all) : itemView.getContext().getResources().getDrawable(R.drawable.done));
            }
            progressBar.setVisibility(View.GONE);
            Glide.with(itemView.getContext()).load(new File(chat.getUri())).into(imageView);

        } else {
            if (!chat.getMessage().toString().equals("")) {
                if (chat.getReceiver().equals(myId)) {
                    seen.setVisibility(View.INVISIBLE);
                } else {
                    seen.setImageDrawable(chat.isSeen() ? itemView.getContext().getResources().getDrawable(R.drawable.done_all) : itemView.getContext().getResources().getDrawable(R.drawable.done));
                }
                Glide.with(itemView.getContext()).load(chat.getMessage()).into(imageView);
                progressBar.setVisibility(View.GONE);
            }
        }

        optins.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(imageView.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.menu_chat_image_video, popupMenu.getMenu());
            if (!chat.getUri().toString().equals("")) {
                popupMenu.getMenu().findItem(R.id.download).setVisible(false);
                if (!file.exists()) {
                    popupMenu.getMenu().findItem(R.id.download).setVisible(true);
                }
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (R.id.deleteforeveryone == item.getItemId()) {
                        if (BaseApplication.isNetworkConnected()) {
                            onMessageClick.deleteForAll(chat, position);
                        } else {
                            Toast.makeText(itemView.getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (R.id.deleteForme == item.getItemId()) {
                        onMessageClick.deleteForMe(chat, position);
                    }
                    if (R.id.download == item.getItemId()) {
                        onMessageClick.download(chat, position);
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
            });
            popupMenu.show();
        });

    }
}
