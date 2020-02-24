package br.com.example.data.remote.di

import br.com.example.data.remote.api.FavoriteWebHookService
import br.com.example.data.remote.api.StarWarsApi
import br.com.example.data.remote.datasource.FavoriteDataSource
import br.com.example.data.remote.datasource.FavoriteDataSourceImpl
import br.com.example.data.remote.datasource.PeopleDataSourceFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val apiModule = module {
    single<StarWarsApi>{
        StarWarsApi.getApi(androidContext())
    }
    single<FavoriteWebHookService> {
        FavoriteWebHookService.getService()
    }
}

val dataSourceModule = module {
    single<PeopleDataSourceFactory>{
        PeopleDataSourceFactory(get(), get())
    }
    single<FavoriteDataSource>{
        FavoriteDataSourceImpl(get())
    }
}

val remoteModule = listOf(dataSourceModule, apiModule)