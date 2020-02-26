package br.com.example.data.remote.di

import br.com.example.data.remote.api.*
import br.com.example.data.remote.datasource.*
import br.com.example.data.remote.datasource.parser.UriToIdParserImpl
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val apiModule = module {

    factory<Interceptor> {
        ApiCacheInterceptor(androidContext())
    }

    single(named("apiRemote")) {
        StarWarsApi.getApi(ApiHttpClient.getClient(androidContext(), get()))
    }

    single(named("apiMock")) {
        StarWarsApi.getApi(ApiHttpClient.getClient(androidContext(), ApiMockInterceptor()))
    }

    single {
        FavoriteWebHookService.getService()
    }
}



val remoteDataSourceModule = module {
    single<PeopleRemoteDataSource> {
        PeopleRemoteDataSourceImpl(get(named("apiRemote")), UriToIdParserImpl())
    }
    single<FavoriteDataSource> {
        FavoriteDataSourceImpl(get())
    }
}

val remoteModule = listOf(apiModule, remoteDataSourceModule)