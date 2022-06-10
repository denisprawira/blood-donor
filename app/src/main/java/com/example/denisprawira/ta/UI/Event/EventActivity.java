package com.example.denisprawira.ta.UI.Event;

import android.animation.Animator;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;

import com.example.denisprawira.ta.R;
import com.example.denisprawira.ta.UI.Fragment.Event.EventStepOne;

public class EventActivity extends AppCompatActivity {

    private View eventContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        setContentView(R.layout.activity_event);

        eventContainer = findViewById(R.id.eventContainer);
        getSupportFragmentManager().beginTransaction().add(R.id.event_fragment,new EventStepOne()).commit();
        if (savedInstanceState == null) {
            eventContainer.setVisibility(View.INVISIBLE);

            final ViewTreeObserver viewTreeObserver = eventContainer.getViewTreeObserver();

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        eventContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                });
            }

        }


    }


    private void circularRevealActivity() {
        int cx = eventContainer.getRight() - getDips(44);
        int cy = eventContainer.getBottom() - getDips(44);

        float finalRadius = Math.max(eventContainer.getWidth(), eventContainer.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                eventContainer,
                cx,
                cy,
                0,
                finalRadius);

        circularReveal.setDuration(400);
        eventContainer.setVisibility(View.VISIBLE);
        circularReveal.start();

    }

    private int getDips(int dps) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = eventContainer.getWidth() - getDips(44);
            int cy = eventContainer.getBottom() - getDips(44);

            float finalRadius = Math.max(eventContainer.getWidth(), eventContainer.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(eventContainer, cx, cy, finalRadius, 0);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    eventContainer.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            circularReveal.setDuration(400);
            circularReveal.start();
        }
        else {

            super.onBackPressed();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
