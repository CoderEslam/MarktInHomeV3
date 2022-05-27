package com.doubleclick.marktinhome.ui.MainScreen.Groups;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;

import com.bumptech.glide.Glide;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.PhotoView.PhotoView;

public class ViewActivity extends AppCompatActivity implements OnPreparedListener {

    private VideoView video;
    private String url;
    private String type;
    private PhotoView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        video = findViewById(R.id.video);
        image = findViewById(R.id.image);
        url = getIntent().getStringExtra("url");
        type = getIntent().getStringExtra("type");
        if (type.equals("video")) {
            image.setVisibility(View.GONE);
            video.setOnPreparedListener(this);
            video.setVideoURI(Uri.parse(url));
//            MediaController ctlr = new MediaController(this);
//            ctlr.setMediaPlayer(video);
//            video.setMediaController(ctlr);
//            video.requestFocus();
        }
        if (type.equals("image")) {
            video.setVisibility(View.GONE);
            Glide.with(this).load(url).into(image);
        }


    }

    @Override
    public void onPrepared() {
        video.start();
    }
}