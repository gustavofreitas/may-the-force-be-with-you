package br.com.example.data.remote.datasource

import br.com.example.domain.entity.People

interface FavoriteDataSource {
    suspend fun saveFavorite(people: People)
}