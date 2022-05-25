package com.doubleclick.marktinhome.Views.colorpickerview;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.RestrictTo;

import com.doubleclick.marktinhome.R;

@RestrictTo(LIBRARY_GROUP)
public class FadeUtils {

  public static void fadeIn(View view) {
    Animation fadeIn =
        AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in_colorpickerview_skydoves);
    fadeIn.setFillAfter(true);
    view.startAnimation(fadeIn);
  }

  public static void fadeOut(View view) {
    Animation fadeOut =
        AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_out_colorpickerview_skydoves);
    fadeOut.setFillAfter(true);
    view.startAnimation(fadeOut);
  }
}
