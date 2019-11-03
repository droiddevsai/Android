package com.sai.testclub

import android.util.Log
import timber.log.Timber

//Only logging error messages
class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR) {
            Log.e(tag, message)
        }
    }
}