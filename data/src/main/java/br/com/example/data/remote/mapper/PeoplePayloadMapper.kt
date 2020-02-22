package br.com.example.data.remote.mapper

import android.net.Uri
import br.com.example.data.remote.model.PeoplePayload
import br.com.example.domain.entity.People

object PeoplePayloadMapper {

    fun map(peoplesPayload: List<PeoplePayload>) = peoplesPayload.map{map(it)}

    fun map(peoplePayload: PeoplePayload): People = People(
        getIdFromUrl(peoplePayload.url),
        peoplePayload.name,
        peoplePayload.height,
        peoplePayload.mass,
        peoplePayload.hairColor,
        peoplePayload.skinColor,
        peoplePayload.eyeColor,
        peoplePayload.birthYear,
        peoplePayload.gender,
        getIdFromUrl(peoplePayload.homeworld),
        peoplePayload.species.map { getIdFromUrl(it) }.toTypedArray(),
        peoplePayload.starships.map { getIdFromUrl(it) }.toTypedArray(),
        peoplePayload.vehicles.map { getIdFromUrl(it) }.toTypedArray()
    )

    fun getIdFromUrl(url: String): Int{
        val uri: Uri = Uri.parse(url)
        return uri.pathSegments.last().toInt()
    }

}