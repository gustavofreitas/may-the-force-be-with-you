package br.com.example.data.remote.datasource

import br.com.example.domain.entity.People

interface FavoriteDataSource {
    fun saveFavorite(people: People)
}