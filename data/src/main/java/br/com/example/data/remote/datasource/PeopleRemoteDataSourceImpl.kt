package br.com.example.data.remote.datasource

import br.com.example.data.remote.api.StarWarsApi
import br.com.example.data.remote.datasource.parser.UriToIdParser
import br.com.example.data.remote.model.PagedRequestPayload
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.domain.entity.People
import br.com.example.domain.entity.PeopleWithPagingInfo
import io.reactivex.Maybe

class PeopleRemoteDataSourceImpl(private val starWarsApi: StarWarsApi, private val uriToIdParser: UriToIdParser) : PeopleRemoteDataSource {

    override fun getPeople(page: Int, search: String?): Maybe<PeopleWithPagingInfo> {
        return starWarsApi.getPeopleList(page, search).flatMap { response ->
            when (response.code()) {
                200 -> Maybe.just(map(response.body() as PagedRequestPayload<PeoplePayload>))
                404 -> Maybe.empty<PeopleWithPagingInfo>()
                else -> Maybe.error(Throwable( "${response.code()} ${response.errorBody().toString()}"))
            }

        }
    }

    private fun map(pagedRequestPayload: PagedRequestPayload<PeoplePayload>) =
        PeopleWithPagingInfo(pagedRequestPayload.count, pagedRequestPayload.results.map{map(it)})

    private fun map(peoplePayload: PeoplePayload): People = People(
        uriToIdParser.parse(peoplePayload.url),
        peoplePayload.name,
        peoplePayload.height,
        peoplePayload.mass,
        peoplePayload.hairColor,
        peoplePayload.skinColor,
        peoplePayload.eyeColor,
        peoplePayload.birthYear,
        peoplePayload.gender,
        uriToIdParser.parse(peoplePayload.homeworld),
        peoplePayload.species.map { uriToIdParser.parse(it) }.toTypedArray(),
        peoplePayload.starships.map { uriToIdParser.parse(it) }.toTypedArray(),
        peoplePayload.vehicles.map { uriToIdParser.parse(it) }.toTypedArray()
    )

}