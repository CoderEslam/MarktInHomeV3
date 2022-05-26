package com.doubleclick.ViewHolder;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.doubleclick.OnMessageClick;
import com.doubleclick.marktinhome.BaseApplication;
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.visualizer.AudioInputReader;
import com.doubleclick.marktinhome.Views.visualizer.VisualizerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import ak.sh.ay.musicwave.MusicWave;

/**
 * Created By Eslam Ghazy on 2/7/2022
 */
public class VoiceViewHolder extends BaseViewHolder implements AudioInputReader.AudioListner {

    private ImageView playVoice;
    private boolean isPlay = false;
    private ProgressBar progress;
    private OnMessageClick onMessageClick;
    private String myId;
    private ImageView seen;
    private TextView time;
    private SeekBar seekBar;
    private int duration = 0, current = 0;
    private VisualizerView mVisualizerView;
    private AudioInputReader mAudioInputReader;

    public VoiceViewHolder(@NonNull View itemView, OnMessageClick onMessageClick, String myId) {
        super(itemView);
        this.onMessageClick = onMessageClick;
        this.myId = myId;
        progress = itemView.findViewById(R.id.progress);
        playVoice = itemView.findViewById(R.id.playVoice);
        seen = itemView.findViewById(R.id.seen);
        time = itemView.findViewById(R.id.time);
        seekBar = itemView.findViewById(R.id.seekBar);
        mVisualizerView = itemView.findViewById(R.id.visualizer);
        mAudioInputReader = new AudioInputReader(mVisualizerView, this, itemView.getContext());
        SetupSharedPrefrences();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SimpleDateFormat"})
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Play(Chat chat, int position) {
        time.setText(new SimpleDateFormat("M/d/yy, h:mm a").format(chat.getDate()).toString());
        if (!chat.getUri().equals("")) {
            if (chat.getReceiver().equals(myId)) {
                seen.setVisibility(View.INVISIBLE);
            } else {
                seen.setImageDrawable(chat.isSeen() ? itemView.getContext().getResources().getDrawable(R.drawable.done_all) : itemView.getContext().getResources().getDrawable(R.drawable.done));
            }
            progress.setVisibility(View.GONE);
            playVoice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.play));
            playVoice.setOnClickListener(v -> {
                if (!chat.getUri().equals("")) {
                    if (isPlay) {
                        mAudioInputReader.shutdown(true);
                        mVisualizerView.setVisibility(View.INVISIBLE);
                        playVoice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.play));
                        isPlay = false;
                    } else {
                        mAudioInputReader.initVisualizer(Uri.parse(chat.getUri()));
                        mVisualizerView.setVisibility(View.VISIBLE);
                        playVoice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.pause));
                        isPlay = true;
                    }
                }
            });
        } else {
            if (!chat.getMessage().equals("")) {
                if (chat.getReceiver().equals(myId)) {
                    seen.setVisibility(View.INVISIBLE);
                } else {
                    seen.setImageDrawable(chat.isSeen() ? itemView.getContext().getResources().getDrawable(R.drawable.done_all) : itemView.getContext().getResources().getDrawable(R.drawable.done));
                }
                playVoice.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable._download_));
            }
        }
        itemView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.text_chat_option, popupMenu.getMenu());
            popupMenu.getMenu().findItem(R.id.copy).setVisible(false);
            popupMenu.getMenu().findItem(R.id.open).setTitle("download");
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
                    } else if (R.id.open == item.getItemId()) {
                        progress.setVisibility(View.VISIBLE);
                        onMessageClick.download(chat, getAdapterPosition());
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            popupMenu.show();
        });
    }

    private void SetupSharedPrefrences() {
        mVisualizerView.setShowBass(true);
        mVisualizerView.setMinSizeScale(1);
        loadColorFromPreferences();
    }

    private void loadColorFromPreferences() {
        mVisualizerView.setColor(R.color.offwhite, R.color.blue, R.color.black);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onComplete(MediaPlayer mp) {
        mVisualizerView.setVisibility(View.INVISIBLE);
        playVoice.setImageDrawable(itemView.getResources().getDrawable(R.drawable.play));
        mAudioInputReader.shutdown(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void OnPreparedListener(MediaPlayer mp) {
        new Thread(() -> {
            duration = mp.getDuration();
            do {
                try {
                    current = mp.getCurrentPosition();
                    seekBar.setProgress((int) ((current * 100) / duration), true);
                } catch (Exception ignored) {

                }
            } while (seekBar.getProgress() <= 100);
        }).start();
    }
}
