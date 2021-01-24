package br.com.example.domain.usecase

import br.com.example.domain.entity.PeopleWithPagingInfo

interface GetPaginatedPeopleUseCase {
    suspend fun execute(page: Int, search: String? = null): PeopleWithPagingInfo
}