package br.com.example.di

import br.com.example.domain.usecase.people.SaveFavoriteUseCase
import br.com.example.domain.usecase.people.SaveFavoriteUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module{
    factory<SaveFavoriteUseCase> {
        SaveFavoriteUseCaseImpl(get())
    }
}

val domainModule = listOf(useCaseModule)