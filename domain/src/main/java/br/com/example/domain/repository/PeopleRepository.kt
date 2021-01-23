package br.com.example.domain.repository

import br.com.example.domain.entity.PeopleWithPagingInfo

interface PeopleRepository {
    suspend fun getPaginatedPeople(page: Int, search: String?): PeopleWithPagingInfo
}