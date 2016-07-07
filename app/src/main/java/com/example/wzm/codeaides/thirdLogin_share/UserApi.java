package com.example.wzm.codeaides.thirdLogin_share;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wzm on 2016/6/27.
 */
public interface UserApi {
    @POST("/User/getMauthUserInfo")
    Observable<String> getUser(@Query("access_token") String access_token,
                             @Query("uid") String openid,
                             @Query("remind_in") String remind_in,
                             @Query("expires_in") String expires_in,
                             @Query("callbackname") String callbackname);
}
