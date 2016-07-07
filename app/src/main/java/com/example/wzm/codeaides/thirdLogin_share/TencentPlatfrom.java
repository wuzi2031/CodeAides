package com.example.wzm.codeaides.thirdLogin_share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by wzm on 2016/5/4.
 */
public class TencentPlatfrom {
    private Activity context;
    private Tencent tencent;
    private Bundle params;
    private IUiListener loginiUiListener;
    private IUiListener shareiUiListener;

    public TencentPlatfrom(Activity context) {
        this.context = context;
        params = new Bundle();
    }


    /**
     * Tencent是SDK的功能入口，所有的接口调用都得通过Tencent进行调用。因此，调用SDK，首先需要创建一个Tencent实例
     *
     * @return
     */
    public void registTencent(String appid) {
        tencent = Tencent.createInstance(appid, context.getApplicationContext());
    }

    public void login(IUiListener listener) {
        this.loginiUiListener = listener;
        if (!tencent.isSessionValid()) {
            tencent.login(context, "all", loginiUiListener);
        }
    }

    public void logout() {
        if (tencent != null)
            tencent.logout(context);
    }

    public void share(Activity context, ShareParams shareParams, IUiListener listener) {
        if (params == null || shareParams == null)
            return;
        this.shareiUiListener = listener;
        params.clear();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareParams.getTitle());
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareParams.getText());
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareParams.getSiteUrl());
        ArrayList<String> imageUrls = new ArrayList<String>();
        imageUrls.add(shareParams.getImageUrl());
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        ArrayList<String> siteUrls = new ArrayList<String>();
        imageUrls.add(shareParams.getSit());
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_SITE, siteUrls);
        doShareToQzone(context,params, listener);
    }

    /**
     * 用异步方式启动分享
     *
     * @param params
     */
    private void doShareToQzone(final Activity context, final Bundle params, final IUiListener listener) {
        // QZone分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != tencent) {
                    tencent.shareToQzone( context, params, listener);
                }
            }
        });
    }

    /**
     * 应用调用Andriod_SDK接口时，如果要成功接收到回调，需要在调用接口的Activity的onActivityResult方法中增加
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void initOnActiviyResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QZONE_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, shareiUiListener);
        } else {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginiUiListener);
        }
    }


    /**
     * 使用requestAsync、request等通用方法调用sdk未封装的接口时，例如上传图片、查看相册等，需传入该回调的实例
     */
    public class TencentApiListener implements IRequestListener {
        @Override
        public void onComplete(JSONObject jsonObject) {

        }

        @Override
        public void onIOException(IOException e) {

        }

        @Override
        public void onMalformedURLException(MalformedURLException e) {

        }

        @Override
        public void onJSONException(JSONException e) {

        }

        @Override
        public void onConnectTimeoutException(ConnectTimeoutException e) {

        }

        @Override
        public void onSocketTimeoutException(SocketTimeoutException e) {

        }

        @Override
        public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e) {

        }

        @Override
        public void onHttpStatusException(HttpUtils.HttpStatusException e) {

        }

        @Override
        public void onUnknowException(Exception e) {

        }
    }
}
