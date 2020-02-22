package br.com.example.data.remote.datasource

import br.com.example.data.remote.api.FavoriteWebHookService
import br.com.example.domain.entity.People

class FavoriteDataSourceImpl(
    private val webHookService: FavoriteWebHookService
): FavoriteDataSource {
    override fun saveFavorite(people: People) {
        webHookService.postFavite(people)
    }
}