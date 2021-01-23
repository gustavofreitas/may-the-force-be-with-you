package br.com.example.domain.usecase

import br.com.example.domain.entity.People
import br.com.example.domain.repository.FavoritePeopleRepository

class SaveFavoriteUseCaseImpl(
    private val repository: FavoritePeopleRepository
) : SaveFavoriteUseCase {
    override suspend fun execute(people: People) {
        repository.save(people)
    }
}