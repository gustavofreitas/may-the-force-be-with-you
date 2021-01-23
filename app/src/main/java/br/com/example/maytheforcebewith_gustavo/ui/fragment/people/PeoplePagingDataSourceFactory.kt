package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.example.domain.entity.People
import br.com.example.domain.usecase.GetPaginatedPeopleUseCase

class PeoplePagingDataSourceFactory(
    private val useCase: GetPaginatedPeopleUseCase
) : DataSource.Factory<Int, People>() {

    val peopleDataSourceLiveData = MutableLiveData<PeoplePagingDataSource>()

    override fun create(): DataSource<Int, People> {
        val peopleDataSource =
            PeoplePagingDataSource(
                useCase
            )
        peopleDataSourceLiveData.postValue(peopleDataSource)
        return peopleDataSource
    }
}