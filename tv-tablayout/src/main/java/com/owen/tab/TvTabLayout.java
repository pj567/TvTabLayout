package com.owen.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 *
 * @author owen
 * @date 16/8/30
 */
public class TvTabLayout extends TabLayout {
    private static final String LOGTAG = TvTabLayout.class.getSimpleName();
    
    private boolean mTabTextSelectedCentered;
    private float mTabTextSelectedScaleValue;

    public TvTabLayout(Context context) {
        this(context, null);
    }

    public TvTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TvTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabLayout, defStyleAttr, 0);
        if(null != a) {
            mTabTextSelectedCentered = a.getBoolean(R.styleable.TabLayout_tabTextSelectedCentered, false);
            mTabTextSelectedScaleValue = a.getFloat(R.styleable.TabLayout_tabTextSelectedScale, 0);
            a.recycle();
        }
    }
    
    public void setTabTextSelectedCentered(boolean tabTextSelectedCentered) {
        mTabTextSelectedCentered = tabTextSelectedCentered;
    }
    
    public void setTabTextSelectedScaleValue(float tabTextSelectedScaleValue) {
        mTabTextSelectedScaleValue = tabTextSelectedScaleValue;
    }
    
    @Override
    protected void onTabSelected(@NonNull Tab tab) {
        final ViewPropertyAnimator animator = tab.getView().animate();
        if(mTabTextSelectedCentered) {
            animator.scaleX(mTabTextSelectedScaleValue).scaleY(mTabTextSelectedScaleValue)
                    .translationY((getHeight() - tab.getView().getHeight()) / 2)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(700)
                    .start();
        } else if(mTabTextSelectedScaleValue > 0) {
            animator.scaleX(mTabTextSelectedScaleValue).scaleY(mTabTextSelectedScaleValue)
                    .setDuration(500)
                    .start();
        }
    }

    @Override
    protected void onTabUnselected(@NonNull Tab tab) {
        final ViewPropertyAnimator animator = tab.getView().animate();
        if(mTabTextSelectedCentered) {
            animator.scaleX(1f).scaleY(1f)
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator())
                    .setDuration(500)
                    .start();
        } else if(mTabTextSelectedScaleValue > 0) {
            animator.scaleX(1f).scaleY(1f)
                    .setDuration(500)
                    .start();
        }
    }

}

