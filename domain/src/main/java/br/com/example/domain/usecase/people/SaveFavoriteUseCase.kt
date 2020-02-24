package br.com.example.domain.usecase.people

import br.com.example.domain.entity.People
import io.reactivex.Completable

interface SaveFavoriteUseCase{
    fun execute(people: People): Completable
}