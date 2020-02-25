package br.com.example.data.repository

import br.com.example.data.remote.datasource.PeopleRemoteDataSource

import br.com.example.domain.entity.PeopleWithPagingInfo
import br.com.example.domain.repository.PeopleRepository
import io.reactivex.Maybe

class PeopleRepositoryImpl(
    private val remoteDataSource: PeopleRemoteDataSource

): PeopleRepository {
   override fun getPaginatedPeople(page: Int, search: String?): Maybe<PeopleWithPagingInfo> =
        remoteDataSource.getPeople(page, search)

}