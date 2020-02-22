package br.com.example.repository

import br.com.example.domain.entity.People

interface FavoritePeopleRepository {
    fun save(people: People)
}