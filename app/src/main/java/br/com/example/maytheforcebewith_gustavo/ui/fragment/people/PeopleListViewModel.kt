package br.com.example.maytheforcebewith_gustavo.ui.fragment.people

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.example.domain.entity.People
import br.com.example.domain.usecase.GetPaginatedPeopleUseCase
import br.com.example.domain.usecase.SaveFavoriteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PeopleListViewModel(
    private val favoriteUseCase: SaveFavoriteUseCase,
    private val paginatedPeopleUseCase: GetPaginatedPeopleUseCase
) : ViewModel() {

    val peoples: Flow<PagingData<People>> =
        Pager(
            PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                enablePlaceholders = true,
            )
        ) {
            PeoplePagingSource(paginatedPeopleUseCase)
        }
            .flow
            .cachedIn(viewModelScope)

    fun saveFavorite(people: People) {
        viewModelScope.launch {
            try {
                favoriteUseCase.execute(people)
                Log.v("saveFavorite", "Web hook called successfully")
            } catch (error: Throwable) {
                Log.e("saveFavorite", error.message ?: "An error occurred")
            }
        }
    }
}
