package com.example.wzm.codeaides.mvp.presenter;

import com.example.wzm.codeaides.mvp.modle.ContentlistEntity;
import com.example.wzm.codeaides.mvp.modle.IModle;
import com.example.wzm.codeaides.mvp.modle.JokeEntity;
import com.example.wzm.codeaides.mvp.modle.JokerApi;
import com.example.wzm.codeaides.mvp.modle.Modle;
import com.example.wzm.codeaides.mvp.modle.RxService;
import com.example.wzm.codeaides.mvp.view.IView;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wzm on 2016/6/13.
 */
public class Presenter implements IPresenter {
    private IModle modle;
    private IView view;
    Subscriber subscriber;
    Observable observable;

    @Inject
    public Presenter(IView view) {
        this.view = view;
        modle = new Modle();
    }

    @Override
    public void onCreat() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onClick() {
        RxService.creatApi(JokerApi.class).getJokestr(1)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, JokeEntity>() {
                    @Override
                    public JokeEntity call(String s) {
                        Gson gson = new Gson();
                        JokeEntity jokeEntity = gson.fromJson(s, JokeEntity.class);
                        return jokeEntity;
                    }
                })
                .map(new Func1<JokeEntity, List<ContentlistEntity>>() {
                    @Override
                    public List<ContentlistEntity> call(JokeEntity jokeEntity) {
                        return jokeEntity.getShowapi_res_body().getContentlist();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ContentlistEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.setData(null);
                    }

                    @Override
                    public void onNext(List<ContentlistEntity> contentlistEntities) {
                        view.setData(contentlistEntities);
                    }
                });
    }
}
