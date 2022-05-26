package com.doubleclick.ViewHolder;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.doubleclick.OnMessageClick;
import com.doubleclick.marktinhome.BaseApplication;
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.R;

import java.text.SimpleDateFormat;


/**
 * Created By Eslam Ghazy on 2/7/2022
 */
public class FileViewHolder extends BaseViewHolder {
    private LottieAnimationView lottieAnimationView;
    private OnMessageClick onMessageClick;
    private ImageView download, seen;
    private ProgressBar progressBar;
    private String myId;
    private TextView time;

    public FileViewHolder(View itemView, OnMessageClick onMessageClick, String myId) {
        super(itemView);
        this.onMessageClick = onMessageClick;
        this.myId = myId;
        lottieAnimationView = itemView.findViewById(R.id.file);
        download = itemView.findViewById(R.id.download);
        seen = itemView.findViewById(R.id.seen);
        progressBar = itemView.findViewById(R.id.progressBar);
        time = itemView.findViewById(R.id.time);
    }


    @SuppressLint({"UseCompatLoadingForDrawables", "SimpleDateFormat"})
    public void downloadFile(Chat chat, int position) {
        time.setText(new SimpleDateFormat("M/d/yy, h:mm a").format(chat.getDate()).toString());
        if (chat.getReceiver().equals(myId)) {
            seen.setVisibility(View.GONE);
        } else {
            seen.setImageDrawable(chat.isSeen() ? itemView.getContext().getResources().getDrawable(R.drawable.done_all) : itemView.getContext().getResources().getDrawable(R.drawable.done));
        }
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMessageClick.download(chat, getAdapterPosition());
            }
        });
        itemView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.text_chat_option, popupMenu.getMenu());
            popupMenu.getMenu().findItem(R.id.copy).setVisible(false);
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
                    } else if (item.getItemId() == R.id.open) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setDataAndType(Uri.parse(chat.getUri()), "application/*");
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        i.setPackage("com.android.chrome");
                        try {
                            itemView.getContext().startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is probably not installed
                            // Try with the default browser
                            i.setPackage(null);
                            itemView.getContext().startActivity(i);
                        }
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
