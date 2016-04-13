package com.example.wzm.codeaides.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.wzm.codeaides.R;

/**
 * Created by wzm on 2016/4/12.
 */
public class LoadingCenter extends FrameLayout {
    public LoadingCenter(Context context) {
        super(context);
        init(context);
    }

    public LoadingCenter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingCenter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.animation_loading, null);
        addView(v);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
