package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.example.domain.entity.People
import br.com.example.domain.usecase.SaveFavoriteUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PeopleListViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val peoplePagingDataSourceFactory: PeoplePagingDataSourceFactory,
    private val favoriteUseCase: SaveFavoriteUseCase
) : ViewModel() {

    lateinit var peopleList: LiveData<PagedList<People>>
    private val pagingConfiguration: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(10)
        .setEnablePlaceholders(true)
        .build()

    var fromSearch: Boolean = false
        private set

    fun initPeopleList() {
        peopleList =
            LivePagedListBuilder(peoplePagingDataSourceFactory, pagingConfiguration).build()
        fromSearch = false
    }

    fun getState(): LiveData<PeopleDataSourceState> =
        Transformations.switchMap<PeoplePagingDataSource, PeopleDataSourceState>(
            peoplePagingDataSourceFactory.peopleDataSourceLiveData,
            PeoplePagingDataSource::state
        )

    fun saveFavorite(people: People) {
        compositeDisposable.add(
            favoriteUseCase.execute(people)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { Log.v("saveFavorite", "Web hook called successfully") },
                    { error -> Log.e("saveFavorite", error.message ?: "An error occurred") }
                )
        )
    }

    fun retry() {
        peoplePagingDataSourceFactory.peopleDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return peopleList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
