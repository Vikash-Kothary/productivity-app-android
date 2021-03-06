package com.vikashkothary.life.injection.module;

import android.app.Application;
import android.content.Context;

import com.vikashkothary.life.data.remote.RemindersService;
import com.vikashkothary.life.data.remote.RibotsService;
import com.vikashkothary.life.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    RibotsService provideRibotsService() {
        return RibotsService.Creator.newRibotsService();
    }

    @Provides
    @Singleton
    RemindersService provideRemindersService() {
        return RemindersService.Creator.newRemindersService();
    }
}
