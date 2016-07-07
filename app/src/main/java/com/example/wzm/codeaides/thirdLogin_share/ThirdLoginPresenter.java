package com.example.wzm.codeaides.thirdLogin_share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.wzm.codeaides.mvp.modle.RxService;
import com.google.gson.Gson;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wzm on 2016/6/27.
 */
public class ThirdLoginPresenter implements IThirdLoginPresenter, ILoginListener, IShareListener {
    private IThirdLoginView loginView;
    private int loginType;
    private ThirdPlatfromsUtils thirdPlatfromsUtils;

    public ThirdLoginPresenter(IThirdLoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void onCreat() {
        //第三方登录分享
        thirdPlatfromsUtils = ThirdPlatfromsUtils.newInstance((Activity) loginView);
        thirdPlatfromsUtils.setLoginListener(this);
        thirdPlatfromsUtils.setShareListener(this);
        thirdPlatfromsUtils.registPlatformes();
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void getUser(int type) {
        this.loginType = type;
        thirdPlatfromsUtils.login(type);
    }

    @Override
    public void share(Activity context, int platfrom, int subType) {
        thirdPlatfromsUtils.setShareParams(null);//分享参数
        thirdPlatfromsUtils.share(context,platfrom, subType);
    }

    @Override
    public void onNewIntent(Activity activity,Intent intent) {
        thirdPlatfromsUtils.onNewIntent(activity,intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        thirdPlatfromsUtils.initOnActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoginSucess(Object o, int type) {
        loginView.showLoading();
        //请求本地服务端,获取User
        RxService.creatApi(UserApi.class)
                .getUser("token", "openid", "", "", "name")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, User>() {
                    @Override
                    public User call(String s) {
                        Gson gson = new Gson();
                        User user = gson.fromJson(s, User.class);
                        return user;
                    }
                })
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.updateUser(null);
                        loginView.cancelLoading();
                    }

                    @Override
                    public void onNext(User user) {
                        loginView.updateUser(user);
                        loginView.cancelLoading();
                    }
                });

    }

    @Override
    public void onLoginFailure(Object o, int type) {
        loginView.cancelLoading();
        loginView.updateUser(null);
    }

    @Override
    public void shareSucess() {

    }

    @Override
    public void shareCancel() {

    }

    @Override
    public void shareFailure() {

    }
}
