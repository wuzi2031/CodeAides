package com.example.wzm.codeaides.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.wzm.codeaides.R;
import com.example.wzm.codeaides.mvp.di.component.DaggerMVPComponent;
import com.example.wzm.codeaides.mvp.di.mod.MVPModule;
import com.example.wzm.codeaides.mvp.modle.ContentlistEntity;
import com.example.wzm.codeaides.mvp.presenter.Presenter;
import com.example.wzm.codeaides.widget.loading.IphoneProgressDialog;
import com.example.wzm.codeaides.widget.loading.LoadingCenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by wzm on 2016/6/13.
 */
public class MVPActivity extends AppCompatActivity implements IView {
    private TextView tv_mvp;
    private LoadingCenter v_loading;
    @Inject
    Presenter presenter;
    private IphoneProgressDialog iphoneProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        tv_mvp = (TextView) findViewById(R.id.tv_mvp);
        v_loading = (LoadingCenter) findViewById(R.id.v_loading);
//        presenter = new Presenter(this);
        DaggerMVPComponent.builder().mVPModule(new MVPModule(this)).build().inject(this);
        presenter.onCreat();
        tv_mvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                presenter.onClick();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void setData(List<ContentlistEntity> contentlistEntities) {
        cancelLoading();
    }


    private void showLoading() {
//        if (iphoneProgressDialog == null)
//            iphoneProgressDialog = new IphoneProgressDialog(this);
//        iphoneProgressDialog.setCancelable(false);
//        iphoneProgressDialog.show();
        v_loading.setVisibility(View.VISIBLE);
    }


    private void cancelLoading() {
//        iphoneProgressDialog.cancel();
        v_loading.setVisibility(View.GONE);
    }

}
