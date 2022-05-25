package com.doubleclick.RichEditor.sample.interfaces;

import com.doubleclick.RichEditor.mricheditor.ActionType;

public interface OnActionPerformListener {
    void onActionPerform(ActionType type, Object... values);
}
