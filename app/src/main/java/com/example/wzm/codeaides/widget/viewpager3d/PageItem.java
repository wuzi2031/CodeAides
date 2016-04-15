package com.example.wzm.codeaides.widget.viewpager3d;

import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by wzm on 2016/4/15.
 */
public class PageItem {
    private View rootView;
    private RelativeLayout animView;

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public RelativeLayout getAnimView() {
        return animView;
    }

    public void setAnimView(RelativeLayout animView) {
        this.animView = animView;
    }
}
