package br.com.example.data.repository

import br.com.example.data.remote.datasource.FavoriteDataSource
import br.com.example.domain.entity.People
import br.com.example.domain.repository.FavoritePeopleRepository

class FavoritePeopleRepositoryImpl(
    private val remoteDataSource: FavoriteDataSource
) : FavoritePeopleRepository {
    override suspend fun save(people: People) =
        remoteDataSource.saveFavorite(people)
}