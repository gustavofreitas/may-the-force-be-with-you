package br.com.example.domain.usecase

import br.com.example.domain.entity.PeopleWithPagingInfo
import io.reactivex.Maybe

interface GetPaginatedPeopleUseCase {
    fun execute (page: Int, search: String?): Maybe<PeopleWithPagingInfo>
}