package com.example.popballoonsgame;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;


public class BalloonListener implements
    Animator.AnimatorListener,
    ValueAnimator.AnimatorUpdateListener{

  Balloon balloon;

  float xStart; // initial x coord
  float x, y; // current x,y coords (animated)

  float xDrift; // horizontal offset of x coord relative to launch point, xStart
  private final String TAG = getClass().getName();

  public BalloonListener(Balloon balloon) {
    this.balloon = balloon;
  }


  @Override
  public void onAnimationUpdate(ValueAnimator valueAnimator) {

    // called each value update / each frame

    if(!balloon.isPopped()) {

      //balloon.setY((float) valueAnimator.getAnimatedValue()); // Original Code, simply animate the y coord

     // enhance the animation: animate x and y coords

      // y value is the animated value = almost equates to time or frames so y= 1 * t, v=1 px / frame
      // in general we could animate any range of values and use them to calculate any property values
      // we like

      y = (float)valueAnimator.getAnimatedValue();

      balloon.setY(y); // set y coord

      // calculate a xdrift
      xDrift = 51.38912f - 0.2770866f*y + 0.0008496502f*y*y - 5.925901e-7f*y*y*y;

      // apply this to the xStart to get a new x coord
      x = xStart + xDrift * 15; // magnify drift by 15

      balloon.setX(x);

      Log.d(TAG, "xStart: " + xStart + " Coords: x: " + x + " y: " + y);
    }
  }

  @Override
  public void onAnimationStart(Animator animator) {
      // record the initial x coord (random xPos)
      xStart = balloon.getX();
  }

  @Override
  public void onAnimationEnd(Animator animator) {

    if(!balloon.isPopped()) {
      balloon.pop(false); // pop balloon if reaches top of screen (finished animation)
    }

    Log.d(TAG, "Animation end");
  }

  @Override
  public void onAnimationCancel(Animator animator) {

  }

  @Override
  public void onAnimationRepeat(Animator animator) {

  }

}
