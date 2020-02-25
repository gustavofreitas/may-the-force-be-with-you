package br.com.example.data.remote.datasource

import br.com.example.data.remote.api.StarWarsApi
import br.com.example.data.remote.mapper.PeoplePayloadMapper
import br.com.example.data.remote.model.PagedRequestPayload
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.domain.entity.PeopleWithPagingInfo
import io.reactivex.Maybe

class PeopleRemoteDataSourceImpl(private val starWarsApi: StarWarsApi) : PeopleRemoteDataSource {

    override fun getPeople(page: Int, search: String?): Maybe<PeopleWithPagingInfo> {
        return starWarsApi.getPeopleList(page, search).flatMap { response ->
            when (response.code()) {
                200 -> Maybe.just(PeoplePayloadMapper.map(response.body() as PagedRequestPayload<PeoplePayload>))
                404 -> Maybe.empty<PeopleWithPagingInfo>()
                else -> Maybe.error(Throwable( "${response.code()} ${response.errorBody().toString()}"))
            }

        }
    }

}