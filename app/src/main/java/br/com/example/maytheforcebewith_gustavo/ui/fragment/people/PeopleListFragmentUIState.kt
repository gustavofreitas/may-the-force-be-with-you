package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import androidx.lifecycle.MutableLiveData

sealed class PeopleListFragmentUIState {
    object Initial : PeopleListFragmentUIState()
    data class Error(val error: Throwable) : PeopleListFragmentUIState()
    object Loading : PeopleListFragmentUIState()
    data class Success(val fromSearch: Boolean) : PeopleListFragmentUIState()
}

fun MutableLiveData<PeopleListFragmentUIState>.toInitial() {
    value = PeopleListFragmentUIState.Initial
}

fun MutableLiveData<PeopleListFragmentUIState>.toError(error: Throwable) {
    value = PeopleListFragmentUIState.Error(error)
}

fun MutableLiveData<PeopleListFragmentUIState>.toLoading() {
    value = PeopleListFragmentUIState.Loading
}

fun MutableLiveData<PeopleListFragmentUIState>.toSucess(fromSearch: Boolean = false) {
    value = PeopleListFragmentUIState.Success(fromSearch)
}