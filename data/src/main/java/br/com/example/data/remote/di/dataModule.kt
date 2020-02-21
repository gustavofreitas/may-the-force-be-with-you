package br.com.example.data.remote.di

import androidx.paging.DataSource
import androidx.paging.PagedList
import br.com.example.data.remote.api.PeopleApi
import br.com.example.data.remote.datasource.PeopleDataSourceFactory
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.data.remote.repository.PeopleRepository
import br.com.example.data.remote.repository.PeopleRepositoryImpl
import org.koin.dsl.module

private const val pageSize = 10

val apiModule = module {
    single<PeopleApi>{
        PeopleApi.getApi()
    }
}

val dataSourceModule = module {
    single<PeopleDataSourceFactory>{
        PeopleDataSourceFactory(get(), get())
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
}

val dataModules = listOf(apiModule, dataSourceModule, repositoryModule)