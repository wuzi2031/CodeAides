package com.example.wzm.codeaides.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.wzm.codeaides.R;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.UmengRegistrar;
import com.umeng.message.entity.UMessage;

/**
 * Created by wzm on 2016/4/13.
 */
public class UmengPushUtils {
    /**
     * 启动页调用,开启推送服务
     *
     * @param context
     */
    public static void initStartAct(Context context) {
        PushAgent mPushAgent = PushAgent.getInstance(context);
        mPushAgent.enable();
        startStatistics(context);
    }

    /**
     * 统计应用启动数据,所有的Activity 的onCreate 函数添加
     *
     * @param context
     */
    public static void startStatistics(Context context) {
        PushAgent.getInstance(context).onAppStart();
    }

    /**
     * 获取设备的device_token
     *
     * @param context
     * @return
     */
    public static String getDeviceToken(Context context) {
        String device_token = UmengRegistrar.getRegistrationId(context);
        return device_token;
    }

    /**
     * 免打扰设置
     *
     * @param context
     */
    public static void setNoDisturbMode(Context context, int startHour, int startMinute, int endHour, int endMinute) {
        PushAgent.getInstance(context).setNoDisturbMode(startHour, startMinute, endHour, endMinute);
    }

    /**
     * 通知栏按数量显示
     *
     * @param context
     * @param num     0~10，0时，表示不合并通知
     */
    public static void setDisplayNotificationNumber(Context context, int num) {
        PushAgent.getInstance(context).setDisplayNotificationNumber(num);
    }

    /**
     * Application onCreate中调用，通知/消息的处理
     *
     * @param context
     * @param debug
     */
    public static void initApplica(Context context, boolean debug) {
        PushAgent mPushAgent = PushAgent.getInstance(context);
        mPushAgent.setDebugMode(debug);
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 参考集成文档的1.6.3
             * http://dev.umeng.com/push/android/integration#1_6_3
             * 自定义消息接受
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(context).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(context).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(context)
                                        .setSmallIcon(R.drawable.logo)
                                        .setContentTitle("My notification")
                                        .setContentText(msg.custom)
                                        .setAutoCancel(true);
                        NotificationManager mNotificationManager =
                                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
                    }
                });
            }

            /**
             * 参考集成文档的1.6.4
             * http://dev.umeng.com/push/android/integration#1_6_4
             * 自定义收到通知样式，builder_id在推送消息高级设置
             * */
            @Override
            public Notification getNotification(Context context,
                                                UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
//                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
//                        builder.setContent(myNotificationView)
//                                .setSmallIcon(getSmallIconId(context, msg))
//                                .setTicker(msg.ticker)
//                                .setAutoCancel(true);
//
//                        return builder.build();

                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
        /**
         * 该Handler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * 参考集成文档的1.6.2
         * http://dev.umeng.com/push/android/integration#1_6_2
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {//用户通知自定义行为
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }
}
