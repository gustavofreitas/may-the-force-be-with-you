package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import br.com.example.data.remote.datasource.PeopleDataSource
import br.com.example.data.remote.datasource.PeopleDataSourceFactory
import br.com.example.data.remote.datasource.State
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.data.repository.PeopleRepository
import br.com.example.domain.entity.People
import io.reactivex.disposables.CompositeDisposable

class PeopleListViewModel(
    private val repository: PeopleRepository,
    private val compositeDisposable: CompositeDisposable,
    private val peopleDataSourceFactory: PeopleDataSourceFactory
) : ViewModel() {

    lateinit var peopleList: LiveData<PagedList<People>>

    fun initPeopleList(){
        peopleList = repository.getPeople()
    }

    fun getState(): LiveData<State> =
        Transformations.switchMap<PeopleDataSource, State>(
            peopleDataSourceFactory.peopleDataSourceLiveData,
            PeopleDataSource::state
        )

    fun retry() {
        peopleDataSourceFactory.peopleDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return peopleList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}
