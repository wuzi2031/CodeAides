package com.example.wzm.codeaides.openGL;

import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by wzm on 2016/7/7.
 */
public class OpenGLRenderer implements GLSurfaceView.Renderer {
    private float cr, cg, cb;
    private float[] mTriangleArray = { 0f,1f,0f, -1f,-1f,0f, 1f,-1f,0f };
    private FloatBuffer mTriangleBuffer;
    public void setColor(float r, float g, float b) {
        cr = r;

        cg = g;

        cb = b;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glShadeModel(GL10.GL_SMOOTH);

        gl.glClearColor(cr, cg, cb, 0.0f);

        gl.glClearDepthf(1.0f);

        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //如果启用，顶点矩阵可以用来写入以及调用gldrawarrays方法或者gldrawelements方法时进行渲染
        mTriangleBuffer = BufferUtil.floatToBuffer(mTriangleArray);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置输出屏幕大小
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        Log.e("onDrawFrame", "onDrawFrame:");
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();
//        gl.glClearColor(cr, cg, cb, 0.0f);
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mTriangleBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
    }
}
