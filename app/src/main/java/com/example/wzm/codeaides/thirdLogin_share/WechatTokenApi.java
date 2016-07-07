package com.example.wzm.codeaides.thirdLogin_share;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wzm on 2016/6/27.
 */
public interface WechatTokenApi {
    @GET
    Observable<WechatTokenBean> getToken(@Query("appid") String appid, @Query("secret") String secret,
                                         @Query("code") String code, @Query("grant_type") String grant_type);
}
