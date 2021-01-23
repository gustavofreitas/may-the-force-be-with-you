package br.com.example.maytheforcebewith_gustavo.di

import br.com.example.maytheforcebewith_gustavo.ui.fragment.people.PeopleListViewModel
import br.com.example.maytheforcebewith_gustavo.ui.fragment.people.PeoplePagingDataSourceFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        PeopleListViewModel(
            get(),
            get()
        )
    }
}

val uiPagingModule = module {

    single {
        PeoplePagingDataSourceFactory(get())
    }
}

val appModule = listOf(uiModule, uiPagingModule)