package br.com.example.domain.repository

import br.com.example.domain.entity.PeopleWithPagingInfo
import io.reactivex.Maybe

interface PeopleRepository {
    fun getPaginatedPeople(page: Int, search: String?): Maybe<PeopleWithPagingInfo>
}