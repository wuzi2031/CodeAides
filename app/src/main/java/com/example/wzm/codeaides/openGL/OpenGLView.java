package com.example.wzm.codeaides.openGL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wzm on 2016/7/7.
 */
public class OpenGLView extends GLSurfaceView {
    private OpenGLRenderer mRenderer;

    public OpenGLView(Context context) {
        super(context);
        mRenderer = new OpenGLRenderer();

        setRenderer(mRenderer);
    }

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
