package br.com.example.data.repository

import br.com.example.data.remote.datasource.FavoriteDataSource
import br.com.example.domain.entity.People
import br.com.example.repository.FavoritePeopleRepository

class FavoritePeopleRepositoryImpl(
    private val remoteDataSource: FavoriteDataSource
): FavoritePeopleRepository {
    override fun save(people: People) {
        remoteDataSource.saveFavorite(people)
    }
}