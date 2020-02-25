package br.com.example.data.remote.datasource


import br.com.example.domain.entity.PeopleWithPagingInfo
import io.reactivex.Maybe

interface PeopleRemoteDataSource {
    fun getPeople(page: Int, search: String?): Maybe<PeopleWithPagingInfo>
}