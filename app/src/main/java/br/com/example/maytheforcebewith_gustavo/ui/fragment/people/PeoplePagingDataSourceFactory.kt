package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.example.domain.entity.People
import br.com.example.domain.usecase.GetPaginatedPeopleUseCase
import io.reactivex.disposables.CompositeDisposable

class PeoplePagingDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val useCase: GetPaginatedPeopleUseCase
): DataSource.Factory<Int, People>() {

    val peopleDataSourceLiveData = MutableLiveData<PeoplePagingDataSource>()

    private var search: String? = null

    override fun create(): DataSource<Int, People> {
        val peopleDataSource =
            PeoplePagingDataSource(
                compositeDisposable,
                search,
                useCase
            )
        peopleDataSourceLiveData.postValue(peopleDataSource)
        return peopleDataSource
    }

    fun doSearch(search: String?) {
        this.search = search
        peopleDataSourceLiveData.value?.invalidate()
    }

    fun clearSearch(){
        search = null
    }

}