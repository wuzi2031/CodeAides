package com.example.wzm.codeaides;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wzm.codeaides.push.UmengPushUtils;
import com.example.wzm.codeaides.widget.IphoneProgressDialog;

/**
 * Created by wzm on 2016/4/11.
 */
public class MainActivity extends AppCompatActivity {
    private Button bt_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UmengPushUtils.initStartAct(this);
        bt_loading = (Button) findViewById(R.id.bt_loading);
        bt_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IphoneProgressDialog iphoneProgressDialog = new IphoneProgressDialog(MainActivity.this);
                iphoneProgressDialog.setCancelable(false);
                iphoneProgressDialog.show();
            }
        });
    }


}
