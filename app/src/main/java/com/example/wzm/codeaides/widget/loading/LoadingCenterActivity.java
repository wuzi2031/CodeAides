package com.example.wzm.codeaides.widget.loading;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wzm.codeaides.R;
import com.example.wzm.codeaides.push.UmengPushUtils;

/**
 * Created by wzm on 2016/4/14.
 */
public class LoadingCenterActivity extends AppCompatActivity {
    private Button bt_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingcenter);
        UmengPushUtils.initStartAct(this);
        bt_loading = (Button) findViewById(R.id.bt_loading);
        bt_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IphoneProgressDialog iphoneProgressDialog = new IphoneProgressDialog(LoadingCenterActivity.this);
                iphoneProgressDialog.setCancelable(false);
                iphoneProgressDialog.show();
            }
        });
    }
}
