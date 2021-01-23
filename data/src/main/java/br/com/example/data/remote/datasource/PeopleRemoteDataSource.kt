package br.com.example.data.remote.datasource


import br.com.example.domain.entity.PeopleWithPagingInfo

interface PeopleRemoteDataSource {
    suspend fun getPeople(page: Int, search: String?): PeopleWithPagingInfo
}