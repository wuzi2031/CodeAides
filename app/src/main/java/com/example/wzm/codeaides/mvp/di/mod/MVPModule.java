package com.example.wzm.codeaides.mvp.di.mod;

import com.example.wzm.codeaides.mvp.view.IView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wzm on 2016/6/22.
 */
@Module
public class MVPModule {
    private final IView iView;

    public MVPModule(IView iView) {
        this.iView = iView;
    }

    @Provides
    IView provideIView() {
        return iView;
    }
}
