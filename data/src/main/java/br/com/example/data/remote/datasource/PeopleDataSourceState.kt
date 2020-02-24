package br.com.example.data.remote.datasource

import androidx.lifecycle.MutableLiveData

sealed class PeopleDataSourceState {
    data class Error(val error: Throwable) : PeopleDataSourceState()
    object Loading : PeopleDataSourceState()
    object Done : PeopleDataSourceState()
    object End : PeopleDataSourceState()
}

fun MutableLiveData<PeopleDataSourceState>.toError(error: Throwable) {
     postValue(PeopleDataSourceState.Error(error))
}

fun MutableLiveData<PeopleDataSourceState>.toLoading() {
    postValue(PeopleDataSourceState.Loading)
}

fun MutableLiveData<PeopleDataSourceState>.toDone() {
    postValue(PeopleDataSourceState.Done)
}

fun MutableLiveData<PeopleDataSourceState>.toEnd(){
    postValue(PeopleDataSourceState.End)
}