package com.doubleclick.marktinhome.Views.storyview.storyview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.PhotoView.PhotoView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class StoryPlayer extends AppCompatActivity implements StoryPlayerProgressView.StoryPlayerListener {
    public static final String STORY_IMAGE_KEY = "storyImages";
    StoryPlayerProgressView storyPlayerProgressView;
    PhotoView imageView;
    TextView name;
    TextView time;
    ArrayList<StoryModel> stories;
    StoryPreference storyPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_player);
        storyPlayerProgressView = findViewById(R.id.progressBarView);
        name = findViewById(R.id.storyUserName);
        time = findViewById(R.id.storyTime);
        storyPlayerProgressView.setSingleStoryDisplayTime(3000);
        imageView = findViewById(R.id.storyImage);
        storyPreference = new StoryPreference(this);
        Intent intent = getIntent();
        if (intent != null) {
            stories = intent.getParcelableArrayListExtra(STORY_IMAGE_KEY);
            initStoryProgressView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        storyPlayerProgressView.cancelAnimation();
    }

    private void initStoryProgressView() {
        if (stories != null && stories.size() > 0) {
            storyPlayerProgressView.setStoryPlayerListener(this);
            storyPlayerProgressView.setProgressBarsCount(stories.size());
            setTouchListener();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener() {
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //pause
                    storyPlayerProgressView.pauseProgress();
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //resume
                    storyPlayerProgressView.resumeProgress();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    @Override
    public void onStartedPlaying(int index) {
        loadImage(index);
        name.setText(stories.get(index).getName());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MM yyyy HH:mm:ss aaa");
        time.setText(simpleDateFormat.format(Long.parseLong(stories.get(index).getTime())));
        storyPreference.setStoryVisited(stories.get(index).getImageUri());
    }

    @Override
    public void onFinishedPlaying() {
        finish();
    }

    private void loadImage(int index) {
        if (!this.isDestroyed()) {
            storyPlayerProgressView.pauseProgress();
            Glide.with(this).clear(imageView);
            Glide.with(this)
                    .load(stories.get(index).getImageUri())
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            storyPlayerProgressView.resumeProgress();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            storyPlayerProgressView.resumeProgress();
                            return false;
                        }
                    })
                    .into(imageView);
        }
    }
}
