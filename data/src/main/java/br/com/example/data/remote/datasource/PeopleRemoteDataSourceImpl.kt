package br.com.example.data.remote.datasource

import br.com.example.data.remote.api.StarWarsApi
import br.com.example.data.remote.mapper.PeoplePayloadMapper
import br.com.example.data.remote.model.PagedRequestPayload
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.domain.entity.PeopleWithPagingInfo
import io.reactivex.Maybe

class PeopleRemoteDataSourceImpl(private val starWarsApi: StarWarsApi) : PeopleRemoteDataSource {

    override fun getPeople(page: Int, search: String?): Maybe<PeopleWithPagingInfo> {
        return starWarsApi.getPeopleList(page, search).map { response ->
                PeoplePayloadMapper.map(response.body() as PagedRequestPayload<PeoplePayload>)
        }
    }

}