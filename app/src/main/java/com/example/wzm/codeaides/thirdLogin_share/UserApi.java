package com.example.wzm.codeaides.thirdLogin_share;

import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wzm on 2016/6/27.
 */
public interface UserApi {
    @POST("/User/getMauthUserInfo")
    Observable<String> getUser(@Field("access_token") String access_token,
                             @Field("uid") String openid,
                             @Field("remind_in") String remind_in,
                             @Field("expires_in") String expires_in,
                             @Field("callbackname") String callbackname);
}
