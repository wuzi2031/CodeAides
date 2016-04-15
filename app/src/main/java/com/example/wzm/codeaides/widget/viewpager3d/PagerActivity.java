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
    private ArrayList<PageItem> pagerViewes;
    private MyViewPager myViewPager;
    private LinearLayout.LayoutParams bigParams;
    private LinearLayout.LayoutParams smallParams;
    private int upPos, downPos;

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
        bigParams = new LinearLayout.LayoutParams(LinearLayout.
                LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        smallParams = new LinearLayout.LayoutParams(LinearLayout.
                LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        smallParams.setMargins(0, 20, 0, 20);
        pagerViewes = inflatViews(imgLs);
        vp_pager = (ViewPager) findViewById(R.id.vp_pager);
        myViewPager = new MyViewPager(imgLs);
        vp_pager.setAdapter(myViewPager);
        vp_pager.setCurrentItem(1);
        vp_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("positionOffset", positionOffset + "");
                Log.e("positionOffsetPixels", positionOffsetPixels + "");
                upPos = position - 1;
                downPos = position + 1;
                RelativeLayout rl_pagercurr = getPagerItem(position);
                bigParams.setMargins(0, (int) (20 * positionOffset), 0, (int) (20 * positionOffset));
                rl_pagercurr.setLayoutParams(bigParams);
                if (upPos >= 0 && upPos < pagerViewes.size()) {
                    RelativeLayout rl_pagerup = getPagerItem(upPos);
                    rl_pagerup.setLayoutParams(smallParams);
                }
                if (downPos >= 0 && downPos < pagerViewes.size()) {
                    RelativeLayout rl_pagerdown = getPagerItem(downPos);
                    rl_pagerdown.setLayoutParams(smallParams);
                }
                vp_pager.requestLayout();
            }

            @Override
            public void onPageSelected(int position) {
                if (imgLs.size() > 1) { //多于1，才会循环跳转
                    if (position < 1) { //首位之前，跳转到末尾（N）
                        position = pagerViewes.size() - 2;
                        vp_pager.setCurrentItem(position, false);
                    } else if (position > pagerViewes.size() - 2) { //末位之后，跳转到首位（1）
                        vp_pager.setCurrentItem(1, false); //false:不显示跳转过程的动画
                    }
                }
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
            RelativeLayout rl_pagercurr = pagerViewes.get(pos).getAnimView();
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

    public ArrayList<PageItem> inflatViews(ArrayList<Integer> imges) {
        if (imges == null || imges.size() == 0)
            return null;
        ArrayList<PageItem> views = new ArrayList<>();
        for (int i = 0; i < imges.size() + 2; i++) {

            View view = LayoutInflater.from(PagerActivity.this).inflate(R.layout.item_viewpager, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_pager);
            RelativeLayout rl_pager = (RelativeLayout) view.findViewById(R.id.rl_pager);
            if (i == 0) {
                iv.setImageResource(imges.get(imges.size() - 1));
            } else if (i == imges.size() + 1) {
                iv.setImageResource(imges.get(0));
            } else {
                iv.setImageResource(imges.get(i - 1));
            }

            if (i == 1) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 0, 0);
                rl_pager.setLayoutParams(params);
            } else {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 20, 0, 20);
                rl_pager.setLayoutParams(params);
            }
            PageItem pageItem = new PageItem();
            pageItem.setAnimView(rl_pager);
            pageItem.setRootView(view);
            views.add(pageItem);
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
            return pagerViewes.size();
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
            if (pagerViewes == null || pagerViewes.size() == 0) {
                return null;
            }
            View view = pagerViewes.get(position).getRootView();
            container.addView(view);
            return view;
        }
    }
}
