package com.wrist_trainer.bivsa.wristtrainer;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;



public abstract class Animations {
    public static void clickAnimation(View view, float scale) {
        view.animate().scaleX(scale).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(50);
        view.animate().scaleY(scale).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(50);
        view.animate().start();
    }
}
