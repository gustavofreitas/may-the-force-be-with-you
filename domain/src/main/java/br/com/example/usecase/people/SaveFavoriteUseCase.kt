package br.com.example.usecase.people

import br.com.example.domain.entity.People

interface SaveFavoriteUseCase{
    fun execute(people: People)
}