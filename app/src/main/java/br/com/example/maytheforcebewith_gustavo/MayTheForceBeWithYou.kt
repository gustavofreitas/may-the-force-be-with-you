package br.com.example.maytheforcebewith_gustavo

import android.app.Application
import br.com.example.data.remote.di.dataModules
import br.com.example.maytheforcebewith_gustavo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MayTheForceBeWithYou: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MayTheForceBeWithYou)
            modules(dataModules + appModule)
        }


    }
}