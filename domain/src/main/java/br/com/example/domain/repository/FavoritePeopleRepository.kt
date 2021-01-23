package br.com.example.domain.repository

import br.com.example.domain.entity.People

interface FavoritePeopleRepository {
    suspend fun save(people: People)
}