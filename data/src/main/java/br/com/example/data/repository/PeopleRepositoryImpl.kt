package br.com.example.data.repository

import br.com.example.data.remote.datasource.PeopleRemoteDataSource

import br.com.example.domain.entity.PeopleWithPagingInfo
import br.com.example.domain.repository.PeopleRepository

class PeopleRepositoryImpl(
    private val remoteDataSource: PeopleRemoteDataSource
) : PeopleRepository {
    override suspend fun getPaginatedPeople(page: Int, search: String?): PeopleWithPagingInfo =
        remoteDataSource.getPeople(page, search)

}