package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.example.data.remote.api.PeopleApi
import br.com.example.data.remote.datasource.PeopleDataSource
import br.com.example.data.remote.datasource.PeopleDataSourceFactory
import br.com.example.data.remote.datasource.State
import br.com.example.data.remote.model.PeoplePayload
import io.reactivex.disposables.CompositeDisposable

class PeopleListViewModel : ViewModel() {

    private val peopleApi = PeopleApi.getApi()
    var peopleList: LiveData<PagedList<PeoplePayload>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 10
    private val peopleDataSourceFactory: PeopleDataSourceFactory

    init {
        peopleDataSourceFactory = PeopleDataSourceFactory(compositeDisposable, peopleApi)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setPrefetchDistance(1)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(true)
            .build()
        peopleList = LivePagedListBuilder(peopleDataSourceFactory, config).build()

    }

    fun getState(): LiveData<State> =
        Transformations.switchMap<PeopleDataSource, State>(
            peopleDataSourceFactory.peopleDataSourceLiveData,
            PeopleDataSource::state
        )

    fun retry(){
        peopleDataSourceFactory.peopleDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean{
        return peopleList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}
