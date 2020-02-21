package br.com.example.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.example.data.remote.datasource.PeopleDataSourceFactory
import br.com.example.data.remote.model.PeoplePayload

class PeopleRepositoryImpl(
    private val peopleDataSourceFactory: PeopleDataSourceFactory,
    private val pagingConfiguration: PagedList.Config

): PeopleRepository {
    override fun getPeople(): LiveData<PagedList<PeoplePayload>> =
        LivePagedListBuilder(peopleDataSourceFactory, pagingConfiguration).build()

}