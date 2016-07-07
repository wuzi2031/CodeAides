package com.example.wzm.codeaides.thirdLogin_share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wzm on 2016/6/27.
 */
public interface IThirdLoginPresenter {
    void onCreat();
    void onDestroy();
    void getUser(int type);
    void share(Activity context, int platfrom, int subType);
    void onNewIntent(Activity context,Intent intent);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
