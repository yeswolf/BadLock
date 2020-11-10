package com.yeswolf.badlock

import android.app.Application
import com.facebook.stetho.Stetho
import com.yeswolf.badlock.di.AppModule
import com.yeswolf.badlock.di.NetworkModule
import com.yeswolf.badlock.di.Scopes
import timber.log.Timber
import toothpick.configuration.Configuration
import toothpick.ktp.KTP

@Suppress("unused")
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
            KTP.setConfiguration(Configuration.forDevelopment())
        }

        KTP.openScope(Scopes.APP)
            .installModules(
                AppModule(this),
                NetworkModule()
            )
    }
}