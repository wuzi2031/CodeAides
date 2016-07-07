package com.example.wzm.codeaides.thirdLogin_share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by wzm on 2016/6/27.
 */
public class WechatPlatfrom {
    private static final int THUMB_SIZE = 150;
    private Activity context;
    private IWXAPI api;

    public WechatPlatfrom(Activity context) {
        this.context = context;
    }

    public void regist(String appid) {
        api = WXAPIFactory.createWXAPI(context, appid, false);
        api.registerApp(appid);
    }

    public void login() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "post_timeline";
        req.state = "none";
        api.sendReq(req);
    }

    public void share(ShareParams shareParams, int scene) {
        if (shareParams == null)
            return;
        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = getMsg(shareParams);
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("1905"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = scene;
        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    private WXMediaMessage getMsg(ShareParams shareParams) {
        WXMediaMessage wxMediaMessage = new WXMediaMessage();
        switch (shareParams.getShareType()) {
            case ShareParams.SHARE_TEXT:
                WXTextObject textObject = new WXTextObject();
                textObject.text = shareParams.getTitle();
                wxMediaMessage.mediaObject = textObject;
                wxMediaMessage.title=shareParams.getTitle();
                wxMediaMessage.description = shareParams.getText();
                break;
            case ShareParams.SHARE_IMAGE:
                WXImageObject imgObject = new WXImageObject();
                imgObject.imageUrl = shareParams.getImageUrl();
                wxMediaMessage.mediaObject = imgObject;
                Bitmap bmp = null;
                try {
                    bmp = BitmapFactory.decodeStream(new URL(shareParams.getImageUrl()).openStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                bmp.recycle();
                wxMediaMessage.title=shareParams.getTitle();
                wxMediaMessage.description = shareParams.getText();
                wxMediaMessage.thumbData = bmpToByteArray(thumbBmp, true);
                break;
            case ShareParams.SHARE_WEBPAGE:
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = shareParams.getSiteUrl();
                wxMediaMessage.mediaObject = webpage;
                wxMediaMessage.title = shareParams.getTitle();
                wxMediaMessage.description = shareParams.getText();
//                wxMediaMessage.thumbData
                break;
        }
        return wxMediaMessage;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void unregisterApp() {
        api.unregisterApp();
    }

    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
