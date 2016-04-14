package com.example.wzm.codeaides.widget.viewpager3d;

import android.animation.TypeEvaluator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.wzm.codeaides.R;

import java.util.ArrayList;

/**
 * Created by wzm on 2016/4/14.
 */
public class PagerActivity extends AppCompatActivity {
    private ViewPager vp_pager;
    private ArrayList<Integer> imgLs = new ArrayList<>();
    private ArrayList<View> pagerViewes;
    private MyViewPager myViewPager;
    private RelativeLayout.LayoutParams nomalParams;
    private RelativeLayout.LayoutParams smallParams;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager3d);
        imgLs.add(R.drawable.top_pic_jiepai);
        imgLs.add(R.drawable.top_pic_lvxing);
        imgLs.add(R.drawable.top_pic_meishi);
        imgLs.add(R.drawable.top_pic_qiche);
        imgLs.add(R.drawable.top_pic_xiaozu);
        nomalParams = new RelativeLayout.LayoutParams(RelativeLayout.
                LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        nomalParams.setMargins(0, 0, 0, 0);
        smallParams = new RelativeLayout.LayoutParams(RelativeLayout.
                LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        smallParams.setMargins(0, 20, 0, 20);
        pagerViewes = inflatViews(imgLs);
        vp_pager = (ViewPager) findViewById(R.id.vp_pager);
        myViewPager = new MyViewPager(imgLs);
        vp_pager.setAdapter(myViewPager);
        vp_pager.setCurrentItem(5000);

        vp_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("position", position % imgLs.size() + "");
                Log.e("positionOffset", positionOffset + "");
                Log.e("positionOffsetPixels", positionOffsetPixels + "");
                RelativeLayout rl_pagercurr = getPagerItem(position % pagerViewes.size());
                RelativeLayout rl_pagerup = getPagerItem((position - 1) % pagerViewes.size());
                RelativeLayout rl_pagerdown = getPagerItem((position + 1) % pagerViewes.size());
                rl_pagercurr.setLayoutParams(nomalParams);
                rl_pagerup.setLayoutParams(smallParams);
                rl_pagerdown.setLayoutParams(smallParams);
                rl_pagercurr.getParent().requestLayout();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            public int getChangePx() {
                return dip2px(PagerActivity.this, 40);
            }

            /**
             * dip转为 px
             */
            public int dip2px(Context context, float dipValue) {
                final float scale = context.getResources().getDisplayMetrics().density;
                return (int) ((dipValue * scale) + 0.5);
            }
        });
    }

    private RelativeLayout getPagerItem(int pos) {
        try {
            View view = pagerViewes.get(pos);
            RelativeLayout rl_pagercurr = (RelativeLayout) view.findViewById(R.id.rl_pager);
            return rl_pagercurr;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * This evaluator can be used to perform type interpolation between <code>float</code> values.
     */
    public static class FloatEvaluator implements TypeEvaluator<Number> {

        /**
         * This function returns the result of linearly interpolating the start and end values, with
         * <code>fraction</code> representing the proportion between the start and end values. The
         * calculation is a simple parametric calculation: <code>result = x0 + t * (v1 - v0)</code>,
         * where <code>x0</code> is <code>startValue</code>, <code>x1</code> is <code>endValue</code>,
         * and <code>t</code> is <code>fraction</code>.
         *
         * @param fraction   The fraction from the starting to the ending values
         * @param startValue The start value; should be of type <code>float</code> or
         *                   <code>Float</code>
         * @param endValue   The end value; should be of type <code>float</code> or <code>Float</code>
         * @return A linear interpolation between the start and end values, given the
         * <code>fraction</code> parameter.
         */
        public Float evaluate(float fraction, Number startValue, Number endValue) {
            float startFloat = startValue.floatValue();
            return startFloat + fraction * (endValue.floatValue() - startFloat);
        }
    }

    private void executeAnimator(View view, float currentChangeValue) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int marginsValue = (int) currentChangeValue;
        params.setMargins(marginsValue, marginsValue, marginsValue, marginsValue);
        view.setLayoutParams(params);
        view.getParent().requestLayout();
    }

    public ArrayList<View> inflatViews(ArrayList<Integer> imges) {
        if (imges == null || imges.size() == 0)
            return null;
        ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < imges.size(); i++) {
            View view = LayoutInflater.from(PagerActivity.this).inflate(R.layout.item_viewpager, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_pager);
            RelativeLayout rl_pager = (RelativeLayout) view.findViewById(R.id.rl_pager);
            iv.setImageResource(imges.get(i));
            if (i == 0) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 0, 0);
                rl_pager.setLayoutParams(params);
            }
            views.add(view);
        }
        return views;
    }

    class MyViewPager extends PagerAdapter {
        private ArrayList<Integer> ls = new ArrayList<>();

        public MyViewPager(ArrayList<Integer> ls) {
            this.ls = ls;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = pagerViewes.get(position % pagerViewes.size());
            if (view != null && view.getParent() == null) {
//                container.removeView(view);
                container.addView(view);
            }

            return view;
        }
    }
}
