package com.example.popballoonsgame;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.widget.AppCompatImageView;


public class Balloon extends AppCompatImageView implements View.OnTouchListener {


  private ValueAnimator animator;
  private BalloonListener listener;
  private boolean isPopped;

  private PopListener mainactivity;
  private final String TAG = getClass().getName();

  public Balloon(Context context) {
    super(context);
  }

  public Balloon(Context context, int color, int height, int level ) {
    super(context);

    mainactivity = (PopListener) context;

    setImageResource(R.mipmap.balloon);
    setColorFilter(color);

    int width = height / 2;

    int dpHeight = pixelsToDp(height, context);
    int dpWidth = pixelsToDp(width, context);

    ViewGroup.LayoutParams params =
        new ViewGroup.LayoutParams(dpWidth, dpHeight);
    setLayoutParams(params);

    listener = new BalloonListener(this);
    setOnTouchListener(this);

  }

  public void pop(boolean isTouched) {
    // notify the mainactivity that the balloon is popped
    mainactivity.popBalloon(this, isTouched);
  }

  public boolean isPopped() {
    return isPopped;
  }


  public void release(int scrHeight, int duration) {

    animator = new ValueAnimator(); // begin with a value animator

    animator.setDuration(duration); // set the duration = 5000 ms (5 sec) to run the animation

    animator.setFloatValues(scrHeight, 0f); // animate a floating point number from scrHeight=1080 here to 0

    animator.setInterpolator(new LinearInterpolator()); // values will change linearly
    // animator.setInterpolator(new AccelerateInterpolator()); // // increase vertical speed for a greater challenge

    animator.setTarget(this); // set animation on this balloon instance

    animator.addListener(listener); // needed to detect start / end of animation
    animator.addUpdateListener(listener); // needed to process each animation change / frame

    animator.start(); // start the animation
  }

  public static int pixelsToDp(int px, Context context) {
    return (int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, px,
        context.getResources().getDisplayMetrics());
  }

  @Override
  public boolean onTouch(View view, MotionEvent motionEvent) {
    Log.d(TAG, "TOUCHED");
    if(!isPopped) {
      // notify mainactivity that the balloon is popped by a player touch
      mainactivity.popBalloon(this, true);
      isPopped = true;

      animator.cancel(); // stop animation
    }
    return true;
  }

  public void setPopped(boolean b) {
    isPopped = true;
  }
}
