package com.doubleclick.marktinhome.Views.bubbles;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;


public class SimpleService extends FloatingBubbleService {

    private Intent intent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.intent = intent;
        if (intent == null)
            return START_NOT_STICKY;
        if (intent.getAction() == null)
            return super.onStartCommand(intent, flags, startId);
        switch (intent.getAction()) {
            case "increase":
                super.increaseNotificationCounterBy(1);
                break;
            case "decrease":
                super.decreaseNotificationCounterBy(1);
                break;
            case "updateIcon":
                super.updateBubbleIcon(ContextCompat.getDrawable(getContext(), R.drawable.close_default_icon));
                break;
            case "restoreIcon":
                super.restoreBubbleIcon();
                break;
            case "toggleExpansion":
                toggleExpansionVisibility();
                break;
        }
        return START_STICKY;
    }

    @Override
    protected FloatingBubbleConfig getConfig() {
        Context context = getApplicationContext();
        Log.e("Chhhhhhhtaaaaas",intent.getSerializableExtra("chats").toString());
        return new FloatingBubbleConfig.Builder()
                .bubbleIcon(intent.getStringExtra("image"))
                .removeBubbleIcon(ContextCompat.getDrawable(context, R.drawable.close_default_icon))
                .bubbleIconDp(54)
                .expandableView(getInflater().inflate(R.layout.sample_view, null,false))
                .removeBubbleIconDp(54)
                .setArrayListChat((ArrayList<Chat>) intent.getSerializableExtra("chats"))
                .paddingDp(4)
                .borderRadiusDp(0)
                .expandableColor(Color.WHITE)
                .triangleColor(0xFF215A64)
                .gravity(Gravity.LEFT)
                .physicsEnabled(true)
                .moveBubbleOnTouch(false)
                .touchClickTime(100)
                .bubbleExpansionIcon(ContextCompat.getDrawable(context, R.drawable.triangle_icon))
                .build();
    }
}

