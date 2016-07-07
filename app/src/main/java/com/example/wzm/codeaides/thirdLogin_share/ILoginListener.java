package com.example.wzm.codeaides.thirdLogin_share;

/**
 * Created by wzm on 2016/5/4.
 */
public interface ILoginListener {
    void onLoginSucess(Object o, int type);

    void onLoginFailure(Object o, int type);
}
