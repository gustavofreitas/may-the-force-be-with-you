package br.com.example.domain.usecase

import br.com.example.domain.entity.PeopleWithPagingInfo
import br.com.example.domain.repository.PeopleRepository
import io.reactivex.Maybe

class GetPaginatedPeopleUseCaseImpl(
    val repository: PeopleRepository
): GetPaginatedPeopleUseCase{
    override fun execute(page: Int, search: String?): Maybe<PeopleWithPagingInfo> =
        repository.getPaginatedPeople(page, search)
}