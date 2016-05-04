package com.example.wzm.codeaides.thirdLogin;

import android.app.Activity;
import android.content.Intent;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by wzm on 2016/5/4.
 */
public class ThirdLoginUtils {
    public static final int TYPE_TENCENT = 1;
    public static final int TYPE_WECHAT = 2;
    public static final int TYPE_WEIBO = 3;

    private static ThirdLoginUtils thirdLoginUtils;
    private Activity context;
    private ILoginListener loginListener;
    private TencentLogin tencentLogin;
    private Tencent tencent;
    private IUiListener iUiListener;

    private ThirdLoginUtils(Activity context) {
        this.context = context;
    }

    public static ThirdLoginUtils newInstance(Activity context) {
        if (thirdLoginUtils == null) {
            return new ThirdLoginUtils(context);
        }
        return thirdLoginUtils;
    }

    /**
     * 注册所有平台
     */
    public void registPlatformes() {
        tencentLogin = new TencentLogin(context);
        tencentLogin.registTencent("appid");
        iUiListener = new TencentUiListener();
    }

    public void initTencentOnActivityResult(int requestCode, int resultCode, Intent data) {
        tencentLogin.initOnActiviyResult(requestCode, requestCode, data, iUiListener);
    }

    public void setLoginListener(ILoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void login(int type) {
        switch (type) {
            case TYPE_TENCENT:
                tencentLogin.login(iUiListener);
                break;
            case TYPE_WECHAT:
                break;
            case TYPE_WEIBO:
                break;
            default:
                break;
        }
    }

    public void logout(int type) {
        switch (type) {
            case TYPE_TENCENT:
                tencentLogin.logout();
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
     * tencent调用SDK已经封装好的接口时，例如：登录、快速支付登录、应用分享、应用邀请等接口，需传入该回调的实例
     */
    public class TencentUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            loginListener.onSucess(o, TYPE_TENCENT);
        }

        @Override
        public void onError(UiError e) {
            loginListener.onFailure(e, TYPE_TENCENT);
        }

        @Override
        public void onCancel() {
            loginListener.onFailure(null, TYPE_TENCENT);
        }
    }
}
