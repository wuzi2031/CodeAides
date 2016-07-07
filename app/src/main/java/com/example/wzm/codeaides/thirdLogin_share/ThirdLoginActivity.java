package com.example.wzm.codeaides.thirdLogin_share;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wzm.codeaides.R;
import com.tencent.mm.sdk.openapi.SendMessageToWX;

/**
 * Created by wzm on 2016/5/4.
 */
public class ThirdLoginActivity extends AppCompatActivity implements View.OnClickListener, IThirdLoginView {
    private ImageView iv_qq, iv_wechat, iv_weibo;
    private TextView tv_result, tv_qq, tv_wechat, tv_wechat_friend, tv_weibo;

    private IThirdLoginPresenter thirdLoginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_login);
        iv_qq = (ImageView) findViewById(R.id.iv_qq);
        iv_wechat = (ImageView) findViewById(R.id.iv_wechat);
        iv_weibo = (ImageView) findViewById(R.id.iv_weibo);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_qq = (TextView) findViewById(R.id.tv_qq);
        tv_wechat = (TextView) findViewById(R.id.tv_wechat);
        tv_wechat_friend = (TextView) findViewById(R.id.tv_wechat_friend);
        tv_weibo = (TextView) findViewById(R.id.tv_weibo);
        iv_qq.setOnClickListener(this);
        iv_wechat.setOnClickListener(this);
        iv_weibo.setOnClickListener(this);
        tv_qq.setOnClickListener(this);
        tv_wechat.setOnClickListener(this);
        tv_wechat_friend.setOnClickListener(this);
        tv_weibo.setOnClickListener(this);
        thirdLoginPresenter = new ThirdLoginPresenter(this);
        thirdLoginPresenter.onCreat();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_qq:
                thirdLoginPresenter.getUser(ThirdPlatfromsUtils.TYPE_TENCENT);
                break;
            case R.id.iv_wechat:
                thirdLoginPresenter.getUser(ThirdPlatfromsUtils.TYPE_WECHAT);
                break;
            case R.id.iv_weibo:
                thirdLoginPresenter.getUser(ThirdPlatfromsUtils.TYPE_WEIBO);
                break;
            case R.id.tv_qq:
                thirdLoginPresenter.share(this,ThirdPlatfromsUtils.TYPE_TENCENT, 0);
                break;
            case R.id.tv_wechat:
                thirdLoginPresenter.share(this,ThirdPlatfromsUtils.TYPE_WECHAT, SendMessageToWX.Req.WXSceneSession);
                break;
            case R.id.tv_wechat_friend:
                thirdLoginPresenter.share(this,ThirdPlatfromsUtils.TYPE_WECHAT, SendMessageToWX.Req.WXSceneTimeline);
                break;
            case R.id.tv_weibo:
                thirdLoginPresenter.share(this,ThirdPlatfromsUtils.TYPE_WEIBO, 0);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        thirdLoginPresenter.onNewIntent(this,intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        thirdLoginPresenter.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thirdLoginPresenter.onDestroy();
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }
}
