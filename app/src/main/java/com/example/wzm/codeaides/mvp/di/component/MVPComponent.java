package com.example.wzm.codeaides.mvp.di.component;

import com.example.wzm.codeaides.mvp.di.mod.MVPModule;
import com.example.wzm.codeaides.mvp.view.MVPActivity;

import dagger.Component;

/**
 * Created by wzm on 2016/6/22.
 */
@Component(modules = {MVPModule.class})
public interface MVPComponent {
    void inject(MVPActivity mvpActivity);
}
