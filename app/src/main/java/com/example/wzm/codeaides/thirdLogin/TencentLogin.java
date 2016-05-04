package com.example.wzm.codeaides.thirdLogin;

import android.app.Activity;
import android.content.Intent;

import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

/**
 * Created by wzm on 2016/5/4.
 */
public class TencentLogin {
    private Activity context;
    private Tencent tencent;

    public TencentLogin(Activity context) {
        this.context = context;
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
        if (!tencent.isSessionValid()) {
            tencent.login(context, "all", listener);
        }
    }

    public void logout() {
        if (tencent != null)
            tencent.logout(context);
    }

    /**
     * 应用调用Andriod_SDK接口时，如果要成功接收到回调，需要在调用接口的Activity的onActivityResult方法中增加
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @param listener
     */
    public void initOnActiviyResult(int requestCode, int resultCode, Intent data, IUiListener listener) {
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
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
