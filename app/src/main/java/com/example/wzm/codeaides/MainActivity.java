package com.example.wzm.codeaides;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.wzm.codeaides.push.UmengPushUtils;
import com.example.wzm.codeaides.widget.loading.LoadingCenterActivity;
import com.example.wzm.codeaides.widget.viewpager3d.PagerActivity;

import java.util.ArrayList;

/**
 * Created by wzm on 2016/4/11.
 */
public class MainActivity extends AppCompatActivity {
    private ListView lv_widgets;
    private MainListAdapter mainListAdapter;
    private ArrayList<String> widgetLs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UmengPushUtils.initStartAct(this);
        widgetLs.add("Loading");
        widgetLs.add("ViewPager3D");
        lv_widgets = (ListView) findViewById(R.id.lv_widgets);
        mainListAdapter = new MainListAdapter(widgetLs);
        lv_widgets.setAdapter(mainListAdapter);
        lv_widgets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, LoadingCenterActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, PagerActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }


}
