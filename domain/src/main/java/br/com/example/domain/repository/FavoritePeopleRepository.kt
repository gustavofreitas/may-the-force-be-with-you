package br.com.example.domain.repository

import br.com.example.domain.entity.People
import io.reactivex.Completable

interface FavoritePeopleRepository {
    fun save(people: People): Completable
}