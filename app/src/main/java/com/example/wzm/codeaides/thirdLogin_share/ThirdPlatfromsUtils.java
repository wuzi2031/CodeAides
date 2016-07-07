package com.example.wzm.codeaides.thirdLogin_share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.wzm.codeaides.mvp.modle.RxService;
import com.example.wzm.codeaides.rx_android.rxbus.RxBus;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wzm on 2016/5/4.
 */
public class ThirdPlatfromsUtils {
    public static final int TYPE_TENCENT = 1;
    public static final int TYPE_WECHAT = 2;
    public static final int TYPE_WEIBO = 3;

    private static ThirdPlatfromsUtils thirdPlatfromsUtils;
    private Activity context;
    private ILoginListener loginListener;
    private IShareListener shareListener;
    private ShareParams shareParams;
    //QQ
    private TencentPlatfrom tencentPlatfrom;
    private IUiListener liginiUiListener;
    private IUiListener shareiUiListener;

    //SinaWeiBo
    private SinaWeiboPlatfrom sinaWeiboPlatfrom;
    private WeiboAuthListener weiboAuthListener;

    //wechat
    private WechatPlatfrom wechatPlatfrom;

    private ThirdPlatfromsUtils(Activity context) {
        this.context = context;
    }

    public static ThirdPlatfromsUtils newInstance(Activity context) {
        if (thirdPlatfromsUtils == null) {
            return new ThirdPlatfromsUtils(context);
        }
        return thirdPlatfromsUtils;
    }

    /**
     * 注册所有平台
     */
    public void registPlatformes() {
        //QQ
        if (tencentPlatfrom == null) {
            tencentPlatfrom = new TencentPlatfrom(context);
            tencentPlatfrom.registTencent("appid");
            liginiUiListener = new TencentLoginUiListener();
            shareiUiListener = new TencentShareUiListener();
        }
        //sinaweibo
        if (sinaWeiboPlatfrom == null) {
            sinaWeiboPlatfrom = new SinaWeiboPlatfrom(context);
            sinaWeiboPlatfrom.regist("appkey", new SinaWeiboShareResponseListener());
            weiboAuthListener = new AuthListener();
        }
        //wechat
        if (wechatPlatfrom == null) {
            wechatPlatfrom = new WechatPlatfrom(context);
            wechatPlatfrom.regist("appid");
            RxBus.get().register("wechatCode", String.class)
                    .subscribe(new Action1<String>() {

                        @Override
                        public void call(String s) {
                            String code = s;
                            RxService.creatApi(WechatTokenApi.class, Constant.WECHAT_TOKEN_URL)
                                    .getToken("appid", "secret", code, "authorization_code")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<WechatTokenBean>() {
                                        @Override
                                        public void call(WechatTokenBean wechatTokenBean) {
                                            loginListener.onLoginSucess(wechatTokenBean, TYPE_WECHAT);
                                        }
                                    });
                        }
                    });
        }
    }

    public void onNewIntent(Activity context, Intent intent) {
        sinaWeiboPlatfrom.onNewIntent(context, intent);
    }

    public void setShareParams(ShareParams shareParams) {
        this.shareParams = shareParams;
    }

    public void setShareListener(IShareListener shareListener) {
        this.shareListener = shareListener;
    }

    public void initOnActivityResult(int requestCode, int resultCode, Intent data) {
        tencentPlatfrom.initOnActiviyResult(requestCode, resultCode, data);
        sinaWeiboPlatfrom.initonActivityResult(requestCode, resultCode, data);
    }

    public void setLoginListener(ILoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void login(int type) {
        switch (type) {
            case TYPE_TENCENT:
                tencentPlatfrom.login(liginiUiListener);
                break;
            case TYPE_WECHAT:
                wechatPlatfrom.login();
                break;
            case TYPE_WEIBO:
                sinaWeiboPlatfrom.login(weiboAuthListener);
                break;
            default:
                break;
        }
    }

    public void logout(int type) {
        switch (type) {
            case TYPE_TENCENT:
                tencentPlatfrom.logout();
                break;
            case TYPE_WECHAT:
                break;
            case TYPE_WEIBO:
                break;
            default:
                break;
        }
    }

    /**
     * @param platfrom 分享平台
     * @param subType  微信/微信朋友圈
     */
    public void share(Activity context, int platfrom, int subType) {
        switch (platfrom) {
            case TYPE_TENCENT:
                tencentPlatfrom.share(context, shareParams, shareiUiListener);
                break;
            case TYPE_WECHAT:
                wechatPlatfrom.share(shareParams, subType);
                break;
            case TYPE_WEIBO:
                sinaWeiboPlatfrom.share(context, shareParams);
                break;
            default:
                break;
        }
    }

    /**
     * tencent调用SDK已经封装好的接口时，例如：登录、快速支付登录、应用分享、应用邀请等接口，需传入该回调的实例
     */
    public class TencentLoginUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            loginListener.onLoginSucess(o, TYPE_TENCENT);
        }

        @Override
        public void onError(UiError e) {
            loginListener.onLoginFailure(e, TYPE_TENCENT);
        }

        @Override
        public void onCancel() {
            loginListener.onLoginFailure(null, TYPE_TENCENT);
        }
    }

    public class TencentShareUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            if (shareListener != null)
                shareListener.shareSucess();
        }

        @Override
        public void onError(UiError e) {
            if (shareListener != null)
                shareListener.shareFailure();
        }

        @Override
        public void onCancel() {
            if (shareListener != null)
                shareListener.shareCancel();
        }
    }

    public class SinaWeiboShareResponseListener implements IWeiboHandler.Response {

        @Override
        public void onResponse(BaseResponse baseResponse) {
            if (baseResponse != null) {
                switch (baseResponse.errCode) {
                    case WBConstants.ErrorCode.ERR_OK:
                        if (shareListener != null)
                            shareListener.shareSucess();
                        break;
                    case WBConstants.ErrorCode.ERR_CANCEL:
                        if (shareListener != null)
                            shareListener.shareCancel();
                        break;
                    case WBConstants.ErrorCode.ERR_FAIL:
                        if (shareListener != null)
                            shareListener.shareFailure();
                        break;
                }
            }
        }
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 onActivityResult 中调用 {@link SsoHandler#authorizeCallBack} 后，
     * 该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String phoneNum = mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {

                loginListener.onLoginSucess(mAccessToken, TYPE_WEIBO);
                // 保存 Token 到 SharedPreferences

            } else {

                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                loginListener.onLoginFailure(code, TYPE_WEIBO);

            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onWeiboException(WeiboException e) {
            loginListener.onLoginFailure(e, TYPE_WEIBO);
        }
    }
}
