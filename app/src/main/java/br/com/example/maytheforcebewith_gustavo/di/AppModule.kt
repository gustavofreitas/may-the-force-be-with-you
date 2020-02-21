package br.com.example.maytheforcebewith_gustavo.di

import br.com.example.maytheforcebewith_gustavo.ui.fragment.people.PeopleListViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val utilModule = module {
    single {
        CompositeDisposable()
    }
}

val uiModule = module {
    viewModel {
        PeopleListViewModel(
            get(),
            get(),
            get()
        )
    }
}

val appModule = listOf(uiModule, utilModule)