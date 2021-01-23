package br.com.example.domain.usecase

import br.com.example.domain.entity.People

interface SaveFavoriteUseCase {
    suspend fun execute(people: People)
}