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

import com.example.wzm.codeaides.R;

import java.util.ArrayList;

/**
 * Created by wzm on MARGINPX16/4/14.
 */
public class PagerActivity extends AppCompatActivity {
    public final static int MARGINPX = 10;
    private final static int INITPOS = 10000;
    private ViewPager vp_pager;
    private ArrayList<Integer> imgLs = new ArrayList<>();

    private ArrayList<View> pagerViewes = new ArrayList<>();
    private MyViewPager myViewPager;


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

        vp_pager = (ViewPager) findViewById(R.id.vp_pager);
        myViewPager = new MyViewPager(imgLs);
        vp_pager.setAdapter(myViewPager);
        vp_pager.setCurrentItem(INITPOS * imgLs.size());

        vp_pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                float defaultScale = (float) 13 / (float) 15;
                View cardView = view.findViewById(R.id.rl_pager);
                int diffWidth = (cardView.getWidth() - cardView.getWidth()) / 2;
                Log.e("transformPage","--------");
                if (position < -1) { // [-Infinity,-1)
//                    cardView.setScaleX(defaultScale);
                    cardView.setScaleY(defaultScale);
                    cardView.setTranslationX(diffWidth);
//                    Log.e("transformPage", cardView+"");
                } else if (position <= 0) { // [-1,0)
//                    cardView.setScaleX((float) 1 + position / (float) 15);
                    cardView.setScaleY((float) 1 + (position-1) / (float) 15);
                    cardView.setTranslationX((0 - position) * diffWidth);
//                    Log.e("transformPage", cardView + "");
//                    Log.e("transformPage", position + "");
                } else if (position <= 1) { // [0,1)
//                    cardView.setScaleX((float) 1 - position / (float) 15);
                    cardView.setScaleY((float) 1 - (position) / (float) 15);
                    cardView.setTranslationX((0 - position) * diffWidth);
                    Log.e("transformPage", cardView + "");
                    Log.e("transformPage", position + "");
                } else { // [1,+Infinity]
//                    cardView.setScaleX(defaultScale);
                    cardView.setScaleY(defaultScale);
                    cardView.setTranslationX(-diffWidth);
//                    Log.e("transformPage", cardView + "");
                }

            }
        });
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
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(PagerActivity.this).inflate(R.layout.item_viewpager, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_pager);
            iv.setImageResource(ls.get(position % ls.size()));
            pagerViewes.add(view);
            container.addView(view);
            return view;
        }
    }
}
