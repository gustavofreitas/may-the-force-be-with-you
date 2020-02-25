package br.com.example.domain.di

import br.com.example.domain.usecase.GetPaginatedPeopleUseCase
import br.com.example.domain.usecase.GetPaginatedPeopleUseCaseImpl
import br.com.example.domain.usecase.SaveFavoriteUseCase
import br.com.example.domain.usecase.SaveFavoriteUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module{
    factory<SaveFavoriteUseCase> {
        SaveFavoriteUseCaseImpl(get())
    }

    factory<GetPaginatedPeopleUseCase> {
        GetPaginatedPeopleUseCaseImpl(get())
    }
}

val domainModule = listOf(useCaseModule)