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
    private ViewPager.LayoutParams nomalParams;
    private ViewPager.LayoutParams smallParams;

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
        nomalParams = new ViewPager.LayoutParams();
        nomalParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        nomalParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        smallParams = new ViewPager.LayoutParams();
        smallParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        smallParams.height = 250;
        pagerViewes = inflatViews(imgLs);
        vp_pager = (ViewPager) findViewById(R.id.vp_pager);
        myViewPager = new MyViewPager(imgLs);
        vp_pager.setAdapter(myViewPager);
        vp_pager.setCurrentItem(5000);
        final FloatEvaluator floatEvaluator = new FloatEvaluator();

        vp_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("position", position % imgLs.size() + "");
                Log.e("positionOffset", positionOffset + "");
                Log.e("positionOffsetPixels", positionOffsetPixels + "");
                float currentChangeValue = 0;
                float preCurrentChangeValue = 0;
//                currentChangeValue = floatEvaluator.evaluate(positionOffset, -getChangePx(), 0);
                preCurrentChangeValue = floatEvaluator.evaluate(positionOffset, 0, getChangePx());

                if (pagerViewes != null && pagerViewes.size() != 0) {
                    executeAnimator(pagerViewes.get(position % pagerViewes.size()), preCurrentChangeValue);
                }

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
            iv.setImageResource(imges.get(i));
            if (i == 0) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 0, 0);
                view.setLayoutParams(params);
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
            if (view != null && view.getParent() != null) {
                container.removeView(view);
            }
            container.addView(view);
            return view;
        }
    }
}
