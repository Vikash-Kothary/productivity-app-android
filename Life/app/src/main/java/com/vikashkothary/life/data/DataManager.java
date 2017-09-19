package com.vikashkothary.life.data;

import com.vikashkothary.life.data.local.DatabaseHelper;
import com.vikashkothary.life.data.local.PreferencesHelper;
import com.vikashkothary.life.data.model.Reminder;
import com.vikashkothary.life.data.model.Ribot;
import com.vikashkothary.life.data.remote.RemindersService;
import com.vikashkothary.life.data.remote.RibotsService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

@Singleton
public class DataManager {

    private final RibotsService mRibotsService;
    private final RemindersService mRemindersService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(RibotsService ribotsService, RemindersService remindersService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mRibotsService = ribotsService;
        mRemindersService = remindersService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Ribot> syncRibots() {
        return mRibotsService.getRibots()
                .concatMap(new Func1<List<Ribot>, Observable<Ribot>>() {
                    @Override
                    public Observable<Ribot> call(List<Ribot> ribots) {
                        return mDatabaseHelper.setRibots(ribots);
                    }
                });
    }

    public Observable<List<Ribot>> getRibots() {
        return mDatabaseHelper.getRibots().distinct();
    }

    public List<Reminder> mReminders = new ArrayList<>();

    public Observable<Reminder> setReminders() {
        return Observable.just(mReminders)
                .concatMap(new Func1<List<Reminder>, Observable<Reminder>>() {
                    @Override
                    public Observable<Reminder> call(List<Reminder> reminders) {
                        return mDatabaseHelper.setReminders(reminders);
                    }
                });
    }

    public Observable<List<Reminder>> getReminders() {
        return Observable.just(mReminders);

//        return mDatabaseHelper.getReminders().distinct();
    }

    public void addReminders(Reminder reminder) {
        mReminders.add(reminder);
    }
}
