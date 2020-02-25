package br.com.example.data.di

import br.com.example.data.remote.di.remoteModule
import br.com.example.data.repository.FavoritePeopleRepositoryImpl
import br.com.example.domain.repository.PeopleRepository
import br.com.example.data.repository.PeopleRepositoryImpl
import br.com.example.domain.repository.FavoritePeopleRepository
import org.koin.dsl.module


val repositoryModule = module {

    single<PeopleRepository> {
        PeopleRepositoryImpl(
            get()
        )
    }

    factory<FavoritePeopleRepository> {
        FavoritePeopleRepositoryImpl(get())
    }
}

val dataModules = listOf(repositoryModule) + remoteModule