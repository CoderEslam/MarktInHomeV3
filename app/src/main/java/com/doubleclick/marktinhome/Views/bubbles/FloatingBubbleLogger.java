package com.doubleclick.marktinhome.Views.bubbles;

import android.util.Log;

public class FloatingBubbleLogger {
    private boolean isDebugEnabled;
    private String tag;

    public FloatingBubbleLogger() {
        isDebugEnabled = false;
        tag = FloatingBubbleLogger.class.getSimpleName();
    }

    public FloatingBubbleLogger setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public FloatingBubbleLogger setDebugEnabled(boolean enabled) {
        this.isDebugEnabled = enabled;
        return this;
    }

    public void log(String message) {
        if (isDebugEnabled) {
            Log.d(tag, message);
        }
    }

    public void log(String message, Throwable throwable) {
        if (isDebugEnabled) {
            Log.e(tag, message, throwable);
        }
    }
}
