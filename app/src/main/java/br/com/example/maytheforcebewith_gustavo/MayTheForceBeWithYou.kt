package br.com.example.maytheforcebewith_gustavo

import android.app.Application
import br.com.example.data.remote.di.dataModules
import br.com.example.di.domainModule
import br.com.example.maytheforcebewith_gustavo.di.appModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MayTheForceBeWithYou: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MayTheForceBeWithYou)
            modules(dataModules + appModule + domainModule)
        }

        Stetho.initializeWithDefaults(this)
    }
}