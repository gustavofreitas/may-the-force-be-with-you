package br.com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.example.data.remote.datasource.PeopleDataSourceFactory
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.domain.entity.People

class PeopleRepositoryImpl(
    private val peopleDataSourceFactory: PeopleDataSourceFactory,
    private val pagingConfiguration: PagedList.Config

): PeopleRepository {
    override fun getPeople(): LiveData<PagedList<People>> =
        LivePagedListBuilder(peopleDataSourceFactory, pagingConfiguration).build()

    override fun getPeople(search: String?) {
        peopleDataSourceFactory.doSearch(search)
    }

}