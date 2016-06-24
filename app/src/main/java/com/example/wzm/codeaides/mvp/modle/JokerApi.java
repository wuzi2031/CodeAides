package com.example.wzm.codeaides.mvp.modle;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wzm on 2016/6/24.
 */
public interface JokerApi {
    @Headers("apikey:83ec99fff780989a5376a1bc595ed5ff")
    @GET("showapi_joke/joke_text")
    Observable<JokeEntity> getJoke(@Query("page") int page);
}
