package br.com.example.data.di

import androidx.paging.PagedList
import br.com.example.data.remote.api.FavoriteWebHookService
import br.com.example.data.remote.api.StarWarsApi
import br.com.example.data.remote.datasource.FavoriteDataSource
import br.com.example.data.remote.datasource.FavoriteDataSourceImpl
import br.com.example.data.remote.datasource.PeopleDataSourceFactory
import br.com.example.data.remote.di.apiModule
import br.com.example.data.remote.di.dataSourceModule
import br.com.example.data.remote.di.remoteModule
import br.com.example.data.repository.FavoritePeopleRepositoryImpl
import br.com.example.data.repository.PeopleRepository
import br.com.example.data.repository.PeopleRepositoryImpl
import br.com.example.repository.FavoritePeopleRepository
import org.koin.dsl.module

private const val pageSize = 10

val repositoryModule = module {

    factory<PagedList.Config> {
        PagedList.Config.Builder()
            .setPageSize(pageSize)
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

val dataModules = listOf(repositoryModule) + remoteModule