package com.vasberc.musicplayer.application

import android.app.Application
import com.vasberc.data_local.di.DataLocalModule
import com.vasberc.domain.di.DomainModule
import com.vasberc.musicplayer.di.AppModule
import com.vasberc.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.ksp.generated.module
import timber.log.Timber

class MusicPlayerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@MusicPlayerApp)
            modules(
                AppModule().module,
                DataLocalModule().module,
                PresentationModule().module,
                DomainModule().module
            )
        }
    }

    private fun initTimber() {
        Timber.Forest.plant(Timber.DebugTree())
    }
}