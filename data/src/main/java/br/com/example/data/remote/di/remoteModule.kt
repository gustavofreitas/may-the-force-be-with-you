package br.com.example.data.remote.di

import br.com.example.data.remote.api.*
import br.com.example.data.remote.datasource.*
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val apiModule = module {

    factory<Interceptor>(named("cache")) {
        ApiCacheInterceptor(androidContext())
    }

    factory<Interceptor>(named("mock")) {
        ApiMockInterceptor(androidContext())
    }

    single(named("apiRemote")) {
        StarWarsApi.getApi(ApiHttpClient.getClient(androidContext(), get(named("cache"))))
    }

    single(named("apiMock")) {
        StarWarsApi.getApi(ApiHttpClient.getClient(androidContext(), get(named("mock"))))
    }

    single {
        FavoriteWebHookService.getService()
    }
}



val remoteDataSourceModule = module {
    single<PeopleRemoteDataSource> {
        PeopleRemoteDataSourceImpl(get(named("apiRemote")))
    }
    single<FavoriteDataSource> {
        FavoriteDataSourceImpl(get())
    }
}

val remoteModule = listOf(apiModule, remoteDataSourceModule)