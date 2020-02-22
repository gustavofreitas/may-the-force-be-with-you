package br.com.example.data.remote.di

import androidx.paging.PagedList
import br.com.example.data.remote.api.FavoriteWebHookService
import br.com.example.data.remote.api.StarWarsApi
import br.com.example.data.remote.datasource.FavoriteDataSource
import br.com.example.data.remote.datasource.FavoriteDataSourceImpl
import br.com.example.data.remote.datasource.PeopleDataSourceFactory
import br.com.example.data.repository.FavoritePeopleRepositoryImpl
import br.com.example.data.repository.PeopleRepository
import br.com.example.data.repository.PeopleRepositoryImpl
import br.com.example.repository.FavoritePeopleRepository
import org.koin.dsl.module

private const val pageSize = 10

val apiModule = module {
    single<StarWarsApi>{
        StarWarsApi.getApi()
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

val repositoryModule = module {

    factory<PagedList.Config> {
        PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setPrefetchDistance(2)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(true)
            .build()
    }
    factory<PeopleRepository>{
        PeopleRepositoryImpl(
            get(),
            get()
        )
    }

    factory<FavoritePeopleRepository> {
        FavoritePeopleRepositoryImpl(get())
    }
}

val dataModules = listOf(apiModule, dataSourceModule, repositoryModule)