package com.example.wzm.codeaides.thirdLogin_share;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.utils.Utility;

/**
 * Created by wzm on 2016/6/27.
 */
public class SinaWeiboPlatfrom {

    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";//默认回调页
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    private Activity context;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private IWeiboShareAPI mWeiboShareAPI;
    private IWeiboHandler.Response response;

    public SinaWeiboPlatfrom(Activity context) {
        this.context = context;
    }

    public void regist(String appid, IWeiboHandler.Response response) {
        this.response = response;
        //登录
        mAuthInfo = new AuthInfo(context, appid, REDIRECT_URL, SCOPE);
        mSsoHandler = new SsoHandler(context, mAuthInfo);
        //分享
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, "appid");
        mWeiboShareAPI.registerApp();

        mWeiboShareAPI.handleWeiboResponse(context.getIntent(), response);

    }

    public void onNewIntent(Activity context, Intent intent) {
        mWeiboShareAPI.handleWeiboResponse(context.getIntent(), response);
    }

    public void login(WeiboAuthListener weiboAuthListener) {
        mSsoHandler.authorizeClientSso(weiboAuthListener);
    }

    public void share(Activity context, ShareParams shareParams) {
        if (shareParams == null)
            return;
        if (mWeiboShareAPI.isWeiboAppInstalled()) {
            if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
                int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
                if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
                    sendMultiMessage(context, shareParams);
                } else {
                    sendSingleMessage(context, shareParams);
                }
            } else {
                Toast.makeText(context, "微博客户端不支持 SDK 分享或微博客户端是非官方版本", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "微博客户端未安装", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
     */
    private void sendMultiMessage(Activity context, ShareParams shareParams) {

        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        String text = shareParams.getText();
        if (text != null) {
            weiboMessage.textObject = getTextObj(text);
        }
        String img = shareParams.getImageUrl();
        if (img != null) {
            weiboMessage.imageObject = getImageObj(img);
        }
        String sit = shareParams.getSiteUrl();
        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
        if (sit != null) {
            weiboMessage.mediaObject = getWebpageObj(shareParams);
        }
//        if (hasMusic) {
//            weiboMessage.mediaObject = getMusicObj();
//        }
//        if (hasVideo) {
//            weiboMessage.mediaObject = getVideoObj();
//        }
//        if (hasVoice) {
//            weiboMessage.mediaObject = getVoiceObj();
//        }

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(context, request);
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 时，只支持分享单条消息，即
     * 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
     */
    private void sendSingleMessage(Activity context, ShareParams shareParams) {

        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        WeiboMessage weiboMessage = new WeiboMessage();
        String text = shareParams.getText();
        if (text != null) {
            weiboMessage.mediaObject = getTextObj(text);
        }
        String img = shareParams.getImageUrl();
        if (img != null) {
            weiboMessage.mediaObject = getImageObj(img);
        }
        String sit = shareParams.getSiteUrl();
        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
        if (sit != null) {
            weiboMessage.mediaObject = getWebpageObj(shareParams);
        }
//        if (hasMusic) {
//            weiboMessage.mediaObject = getMusicObj();
//        }
//        if (hasVideo) {
//            weiboMessage.mediaObject = getVideoObj();
//        }
        /*if (hasVoice) {
            weiboMessage.mediaObject = getVoiceObj();
        }*/

        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(context, request);
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj(String img) {
        ImageObject imageObject = new ImageObject();
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
//        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);

//        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(ShareParams shareParams) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = shareParams.getTitle();
        mediaObject.description = shareParams.getText();

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = shareParams.getSiteUrl();
        mediaObject.defaultText = "1905电影网";
        return mediaObject;
    }

    public void initonActivityResult(int requestCode, int resultCode, Intent data) {
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
