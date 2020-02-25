package br.com.example.domain.usecase

import br.com.example.domain.entity.People
import br.com.example.domain.repository.FavoritePeopleRepository

class SaveFavoriteUseCaseImpl(
    private val reposirory: FavoritePeopleRepository
): SaveFavoriteUseCase {
    override fun execute(people: People) = reposirory.save(people)
}