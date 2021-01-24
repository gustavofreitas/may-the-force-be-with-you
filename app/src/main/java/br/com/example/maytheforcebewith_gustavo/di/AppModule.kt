package br.com.example.maytheforcebewith_gustavo.di

import br.com.example.maytheforcebewith_gustavo.ui.fragment.people.PeopleListViewModel
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

val appModule = listOf(uiModule)