package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import androidx.paging.PagingSource
import br.com.example.domain.entity.People
import br.com.example.domain.usecase.GetPaginatedPeopleUseCase

class PeoplePagingSource(
        private val useCase: GetPaginatedPeopleUseCase
) : PagingSource<Int, People>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, People> {
        return try {
            val nextPage = params.key ?: 1
            val response = useCase.execute(nextPage)

            LoadResult.Page(
                    data = response.peoples,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = nextPage + 1,
                    itemsAfter = response.total
            )
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }
}