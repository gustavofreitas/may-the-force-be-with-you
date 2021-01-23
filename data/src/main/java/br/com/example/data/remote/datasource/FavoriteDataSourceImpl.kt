package br.com.example.data.remote.datasource

import android.accounts.NetworkErrorException
import br.com.example.data.remote.api.FavoriteWebHookService
import br.com.example.domain.entity.People
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


class FavoriteDataSourceImpl(
    private val webHookService: FavoriteWebHookService
) : FavoriteDataSource {
    override suspend fun saveFavorite(people: People) {
        withContext(IO) {
           webHookService.postFavorite(people)
        }
    }
}