package br.com.example.domain.usecase

import br.com.example.domain.entity.PeopleWithPagingInfo
import br.com.example.domain.repository.PeopleRepository

class GetPaginatedPeopleUseCaseImpl(
    private val repository: PeopleRepository
) : GetPaginatedPeopleUseCase {
    override suspend fun execute(page: Int, search: String?): PeopleWithPagingInfo =
        repository.getPaginatedPeople(page, search)
}