package com.example.wzm.codeaides.thirdLogin;

/**
 * Created by wzm on 2016/5/4.
 */
public interface ILoginListener {
    void onSucess(Object o, int type);

    void onFailure(Object o, int type);
}
