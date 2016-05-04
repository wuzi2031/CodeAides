package com.example.wzm.codeaides.thirdLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wzm.codeaides.R;

/**
 * Created by wzm on 2016/5/4.
 */
public class ThirdLoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginListener {
    private ImageView iv_qq, iv_wechat, iv_weibo;
    private TextView tv_result;
    private ThirdLoginUtils thirdLoginUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_login);
        iv_qq = (ImageView) findViewById(R.id.iv_qq);
        iv_wechat = (ImageView) findViewById(R.id.iv_wechat);
        iv_weibo = (ImageView) findViewById(R.id.iv_weibo);
        tv_result = (TextView) findViewById(R.id.tv_result);
        iv_qq.setOnClickListener(this);
        iv_wechat.setOnClickListener(this);
        iv_weibo.setOnClickListener(this);
        //第三方登录
        thirdLoginUtils = ThirdLoginUtils.newInstance(this);
        thirdLoginUtils.setLoginListener(this);
        thirdLoginUtils.registPlatformes();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_qq:
                thirdLoginUtils.login(ThirdLoginUtils.TYPE_TENCENT);
                break;
            case R.id.iv_wechat:
                break;
            case R.id.iv_weibo:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        thirdLoginUtils.initTencentOnActivityResult(requestCode, resultCode, data);//tencent需要
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSucess(Object o, int type) {
        //第三方成功回调
    }

    @Override
    public void onFailure(Object o, int type) {
        //第三方失败回调
    }
}
