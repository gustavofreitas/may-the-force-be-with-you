package br.com.example.data.remote.datasource

import br.com.example.domain.entity.People
import io.reactivex.Completable

interface FavoriteDataSource {
    fun saveFavorite(people: People): Completable
}