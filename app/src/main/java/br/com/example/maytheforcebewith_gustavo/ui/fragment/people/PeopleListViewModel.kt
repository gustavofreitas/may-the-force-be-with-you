package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import br.com.example.data.remote.datasource.PeopleDataSource
import br.com.example.data.remote.datasource.PeopleDataSourceFactory
import br.com.example.data.remote.datasource.PeopleDataSourceState
import br.com.example.data.repository.PeopleRepository
import br.com.example.domain.entity.People
import br.com.example.domain.usecase.people.SaveFavoriteUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PeopleListViewModel(
    private val repository: PeopleRepository,
    private val compositeDisposable: CompositeDisposable,
    private val peopleDataSourceFactory: PeopleDataSourceFactory,
    private val favoriteUseCase: SaveFavoriteUseCase
) : ViewModel() {

    lateinit var peopleList: LiveData<PagedList<People>>

    var fromSearch: Boolean = false
        private set

    fun initPeopleList() {
        peopleList = repository.getPeople()
        fromSearch = false
    }

    fun updatePeopleList(search: String? = null){
        repository.getPeople(search)
        fromSearch = !search.isNullOrEmpty()
    }

    fun clearSearch(){
        repository.clearSearch()
    }

    fun getState(): LiveData<PeopleDataSourceState> =
        Transformations.switchMap<PeopleDataSource, PeopleDataSourceState>(
            peopleDataSourceFactory.peopleDataSourceLiveData,
            PeopleDataSource::state
        )

    fun saveFavorite(people: People) {
        compositeDisposable.add(
            favoriteUseCase.execute(people)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { Log.v("saveFavorite", "Web hook called successfully") },
                    { error -> Log.e("saveFavorite", error.message) }
                )
        )
    }

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
