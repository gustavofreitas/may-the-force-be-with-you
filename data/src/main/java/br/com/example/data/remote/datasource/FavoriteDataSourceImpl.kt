package br.com.example.data.remote.datasource

import android.util.Log
import br.com.example.data.remote.api.FavoriteWebHookService
import br.com.example.domain.entity.People
import io.reactivex.Completable


class FavoriteDataSourceImpl(
    private val webHookService: FavoriteWebHookService
): FavoriteDataSource {
    override fun saveFavorite(people: People): Completable =
        Completable.defer{
            webHookService.postFavorite(people).doOnError {
                Log.e("data", it.message ?: "An error occurred")
            }


        }
}