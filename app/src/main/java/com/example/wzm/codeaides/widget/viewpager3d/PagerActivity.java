package com.example.wzm.codeaides.widget.viewpager3d;

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
 * Created by wzm on MARGINPX16/4/14.
 */
public class PagerActivity extends AppCompatActivity {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    public final static int MARGINPX = 10;
    private final static int INITPOS = 10000;
    private ViewPager vp_pager;
    private ArrayList<Integer> imgLs = new ArrayList<>();
    //    private ArrayList<PageItem> pagerViewes = new ArrayList<>();
    private ArrayList<View> pagerViewes = new ArrayList<>();
    private MyViewPager myViewPager;
    private LinearLayout.LayoutParams bigParams;
    private LinearLayout.LayoutParams upParams;
    private LinearLayout.LayoutParams downParams;
    private int currPos, upPos, downPos, limitPageSize;
    private boolean once = true, isSorcing;
    float first = 0;

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

        upParams = new LinearLayout.LayoutParams(LinearLayout.
                LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        upParams.setMargins(0, MARGINPX, 0, MARGINPX);
        downParams = new LinearLayout.LayoutParams(LinearLayout.
                LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        downParams.setMargins(0, MARGINPX, 0, MARGINPX);
//        pagerViewes = inflatViews(imgLs);
        vp_pager = (ViewPager) findViewById(R.id.vp_pager);
        myViewPager = new MyViewPager(imgLs);
        vp_pager.setAdapter(myViewPager);
        vp_pager.setCurrentItem(INITPOS * imgLs.size());
        limitPageSize = vp_pager.getOffscreenPageLimit();
        if (limitPageSize < 2) {
            limitPageSize = 2;
        }
        vp_pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
               float defaultScale = (float) 14 / (float) 15;
                View cardView = view.findViewById(R.id.rl_pager);
                int diffWidth = (cardView.getWidth() - cardView.getWidth()) / 2;

                if (position < -1) { // [-Infinity,-1)
                    cardView.setScaleX(defaultScale);
                    cardView.setScaleY(defaultScale);
                    cardView.setTranslationX(diffWidth);

                } else if (position <= 0) { // [-1,0]
                    cardView.setScaleX((float) 1 + position / (float) 15);
                    cardView.setScaleY((float) 1 + position / (float) 15);
                    cardView.setTranslationX((0 - position) * diffWidth);

                } else if (position <= 1) { // (0,1]
                    cardView.setScaleX((float) 1 - position / (float) 15);
                    cardView.setScaleY((float) 1 - position / (float) 15);
                    cardView.setTranslationX((0 - position) * diffWidth);

                } else { // (1,+Infinity]
                    cardView.setScaleX(defaultScale);
                    cardView.setScaleY(defaultScale);
                    cardView.setTranslationX(-diffWidth);
                }

            }
        });
//        vp_pager.setCurrentItem(imgLs.size() / 2);
//        vp_pager.setOffscreenPageLimit(0);
        vp_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e("positionOffset", positionOffset + "");
//                Log.e("positionOffsetPixels", positionOffsetPixels + "");

//                currPos = position % pagerViewes.size();
//                currPos = position;
//                if (currPos == 0) {
//                    upPos = pagerViewes.size() - 1;
//                    downPos = currPos + 1;
//                } else if (currPos == pagerViewes.size() - 1) {
//                    upPos = currPos - 1;
//                    downPos = 0;
//                } else {
//                upPos = currPos - 1;
//                downPos = currPos + 1;
//                }
//                Log.e("position", position + "");
//                Log.e("upPos", upPos + "");
//                Log.e("downPos", downPos + "");
//                boolean isdown = false, isup = false;
//                if (positionOffset != 0) {
//                    if (once) {
//                        first = positionOffset;
//                        Log.e("first", first + "");
//                        once = false;
//                    }
//                    Log.e("vula", (first - 0.5f) + "");
//                    if (first - 0.5f > 0) {
//                        isup = false;
//                        isdown = true;
//                    } else {
//                        isdown = false;
//                        isup = true;
//                    }
//
//                    if (isdown) {
//                        RelativeLayout rl_pagercurr = getPagerItem(limitPageSize-1);
//                        Log.e("isdown", (int) (MARGINPX * (1 - positionOffset)) + "");
//                        bigParams.setMargins(0, (int) (MARGINPX * positionOffset), 0, (int) (MARGINPX * positionOffset));
//                        rl_pagercurr.setLayoutParams(bigParams);
//                    } else if (isup) {
////                        if (downPos >= 0 && downPos < pagerViewes.size()) {
//                        Log.e("isup", (int) (MARGINPX * positionOffset) + "");
//                        RelativeLayout rl_pagerdown = getPagerItem(limitPageSize + 1);
//                        downParams.setMargins(0, (int) (MARGINPX * (1 - positionOffset)), 0, (int) (MARGINPX * (1 - positionOffset)));
//                        rl_pagerdown.setLayoutParams(downParams);
////                        }
//                    }
//                } else {
//                    Log.e("---", "---------------------------");
//                    Log.e("position", position + "");
//                    Log.e("none", "none");
////                    once = true;
////                    RelativeLayout rl_pagercurr = getPagerItem(limitPageSize);
////                    bigParams.setMargins(0, 0, 0, 0);
////                    rl_pagercurr.setLayoutParams(bigParams);
////
////
////                    RelativeLayout rl_pagerup = getPagerItem(limitPageSize+1);
////
////                    upParams.setMargins(0, MARGINPX, 0, MARGINPX);
////
////
////                    rl_pagerup.setLayoutParams(upParams);
////
////
////                    RelativeLayout rl_pagerdown = getPagerItem(limitPageSize -1);
////                    downParams.setMargins(0, MARGINPX, 0, MARGINPX);
////                    rl_pagerdown.setLayoutParams(downParams);
//
//                }
//                vp_pager.invalidate();
            }

            @Override
            public void onPageSelected(int position) {
//                if (imgLs.size() > 1) { //多于1，才会循环跳转
//                    if (position < 1) { //首位之前，跳转到末尾（N）
//                        position = pagerViewes.size() - 2;
//                        vp_pager.setCurrentItem(position, false);
//                    } else if (position > pagerViewes.size() - 2) { //末位之后，跳转到首位（1）
//                        vp_pager.setCurrentItem(1, false); //false:不显示跳转过程的动画
//                    }
//                }
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


    public ArrayList<PageItem> inflatViews(ArrayList<Integer> imges) {
        if (imges == null || imges.size() == 0)
            return null;
        ArrayList<PageItem> views = new ArrayList<>();
        for (int i = 0; i < imges.size(); i++) {

            View view = LayoutInflater.from(PagerActivity.this).inflate(R.layout.item_viewpager, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_pager);
            RelativeLayout rl_pager = (RelativeLayout) view.findViewById(R.id.rl_pager);
//            if (i == 0) {
//                iv.setImageResource(imges.get(imges.size() - 1));
//            } else if (i == imges.size() + 1) {
//                iv.setImageResource(imges.get(0));
//            } else {
//                iv.setImageResource(imges.get(i - 1));
//            }
            iv.setImageResource(imges.get(i));
            if (i == 0) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 0, 0);
                rl_pager.setLayoutParams(params);
            } else {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, MARGINPX, 0, MARGINPX);
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
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            pagerViewes.remove(object);
//            Log.e("--------remove", pagerViewes.size() + "");
//            Log.e("remove", position + "");
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            if (pagerViewes == null || pagerViewes.size() == 0) {
//                return null;
//            }
//            View view = pagerViewes.get(position % pagerViewes.size()).getRootView();
//            if (view.getParent() != null) {
//                container.removeView(view);
//            }
            View view = LayoutInflater.from(PagerActivity.this).inflate(R.layout.item_viewpager, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_pager);
            RelativeLayout rl_pager = (RelativeLayout) view.findViewById(R.id.rl_pager);
            rl_pager.setLayoutParams(upParams);
            iv.setImageResource(ls.get(position % ls.size()));
//            PageItem pageItem = new PageItem();
//            pageItem.setAnimView(rl_pager);
//            pageItem.setRootView(view);
            pagerViewes.add(view);
            container.addView(view);
//            Log.e("------------pagerViewes", position + "");
//            Log.e("pagerViewes", position % ls.size() + "");
////            Log.e("container", container.getChildCount() + "");
//            Log.e("pagerViewes", pagerViewes.size() + "");
            return view;
        }
    }
}
