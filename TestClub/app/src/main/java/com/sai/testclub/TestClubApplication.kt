package com.sai.testclub

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree


class TestClubApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

}