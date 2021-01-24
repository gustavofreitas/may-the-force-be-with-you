package br.com.example.data.remote.datasource

import br.com.example.data.remote.api.StarWarsApi
import br.com.example.data.remote.datasource.parser.UriToIdParser
import br.com.example.data.remote.model.PagedRequestPayload
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.domain.entity.People
import br.com.example.domain.entity.PeopleWithPagingInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PeopleRemoteDataSourceImpl(
    private val starWarsApi: StarWarsApi,
    private val uriToIdParser: UriToIdParser
) : PeopleRemoteDataSource {

    override suspend fun getPeople(page: Int, search: String?): PeopleWithPagingInfo {
        return withContext(Dispatchers.IO) {
            val response = starWarsApi.getPeopleList(page, search)

            when (response.code()) {
                200 -> map(response.body() as PagedRequestPayload<PeoplePayload>)
                404 -> PeopleWithPagingInfo(0, listOf())
                else -> throw Throwable("${response.code()} ${response.errorBody().toString()}")
            }
        }
    }

    private fun map(pagedRequestPayload: PagedRequestPayload<PeoplePayload>) =
        PeopleWithPagingInfo(pagedRequestPayload.count, pagedRequestPayload.results.map { map(it) })

    private fun map(peoplePayload: PeoplePayload): People = People(
        uriToIdParser.parse(peoplePayload.url),
        peoplePayload.name,
        peoplePayload.height.checkValue("m"),
        peoplePayload.mass.checkValue("Kg"),
        peoplePayload.hairColor.checkValue(),
        peoplePayload.skinColor.checkValue(),
        peoplePayload.eyeColor.checkValue(),
        peoplePayload.birthYear.checkValue(),
        peoplePayload.gender.checkValue(),
        uriToIdParser.parse(peoplePayload.homeworld),
        peoplePayload.species.map { uriToIdParser.parse(it) }.toTypedArray(),
        peoplePayload.starships.map { uriToIdParser.parse(it) }.toTypedArray(),
        peoplePayload.vehicles.map { uriToIdParser.parse(it) }.toTypedArray()
    )

    private fun String.checkValue(suffix: String? = null): String? =
        if (listOf("n/a", "none").contains(this)) null
        else suffix?.let { "$this$it" } ?: this
}
