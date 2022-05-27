package com.doubleclick.ViewHolder;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;

import com.doubleclick.OnMessageClick;
import com.doubleclick.marktinhome.BaseApplication;
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.R;

import java.io.File;
import java.text.SimpleDateFormat;


/**
 * Created By Eslam Ghazy on 2/7/2022
 */
public class VideoViewHolder extends BaseViewHolder {

    private VideoView video;
    private ImageView options;
    private OnMessageClick onMessageClick;
    private ImageView download, seen;
    private ProgressBar progressBar;
    private String myId;
    private TextView time;
    private File file;

    public VideoViewHolder(@NonNull View itemView, OnMessageClick onMessageClick, String myId) {
        super(itemView);
        this.onMessageClick = onMessageClick;
        this.myId = myId;
        video = itemView.findViewById(R.id.video);
        options = itemView.findViewById(R.id.options);
        download = itemView.findViewById(R.id.download);
        seen = itemView.findViewById(R.id.seen);
        progressBar = itemView.findViewById(R.id.progressBar);
        time = itemView.findViewById(R.id.time);
    }


    @SuppressLint({"UseCompatLoadingForDrawables", "SimpleDateFormat"})
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void play(Chat chat, int position) {
        time.setText(new SimpleDateFormat("M/d/yy, h:mm a").format(chat.getDate()).toString());
        file = new File(chat.getUri());
        if (!chat.getUri().equals("")) {
            progressBar.setVisibility(View.GONE);
            download.setVisibility(View.GONE);
            video.setVideoURI(Uri.parse(chat.getUri())); //the string of the URL mentioned above
//            video.setVideoURI(Uri.parse("/storage/emulated/0/Download/MarketEslam/video1653414775765")); // for example
            video.stopPlayback();
            video.pause();
            MediaController ctlr = new MediaController(itemView.getContext());
            ctlr.setMediaPlayer(video);
            video.setMediaController(ctlr);
            video.requestFocus();
        } else if (!chat.getMessage().equals("")) {
            if (chat.getReceiver().equals(myId)) {
                seen.setVisibility(View.INVISIBLE);
            } else {
                seen.setImageDrawable(chat.isSeen() ? itemView.getContext().getResources().getDrawable(R.drawable.done_all) : itemView.getContext().getResources().getDrawable(R.drawable.done));
            }
            progressBar.setVisibility(View.GONE);
            video.setVideoURI(Uri.parse(chat.getMessage())); //the string of the URL mentioned above
            video.stopPlayback();
            video.pause();
        } else {
            download.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            itemView.setVisibility(View.GONE);
        }


        options.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), v);
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
                    int id = item.getItemId();
                    if (R.id.deleteforeveryone == id) {
                        if (BaseApplication.isNetworkConnected()) {
                            onMessageClick.deleteForAll(chat, position);
                        } else {
                            Toast.makeText(itemView.getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (R.id.deleteForme == id) {
                        onMessageClick.deleteForMe(chat, position);
                    }
                    if (R.id.download == id) {
                        download.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        onMessageClick.download(chat, getAdapterPosition());
                    }
                    return true;
                }
            });
            popupMenu.show();
        });
    }


}
